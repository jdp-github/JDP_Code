package com.jdp.arsenal.test;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Creash处理类
 * 当程序发生UncaughtException的时候，会调用uncaughtException方法
 * 由当前类处理未被捕获的异常
 * 需要在Application中注册
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler INSTANCE = new CrashHandler();
    //上下文
    private Context mContext;
    //系统默认的异常处理
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static boolean hasrun;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && mDefaultHandler != null) {
            //如果没有处理就使用系统默认的异常处理
            mDefaultHandler.uncaughtException(thread, throwable);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return true;
        }

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                Toast toast = Toast.makeText(mContext, getId() + "",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
//              MsgPrompt.showMsg(mContext, "程序出错啦", msg+"\n点确认退出");
                Looper.loop();
            }
        }.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }
}
