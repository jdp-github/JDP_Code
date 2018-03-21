package com.jdp.arsenal.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Describe:
 * Author: jidp
 * Date: 2017/2/10
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    @Unique
    private String name;
    private String address;
    private int phone;
    private String info;
    private String test1;
    private String test2;

    @Generated(hash = 969092505)
    public User(Long id, @NotNull String name, String address, int phone,
            String info, String test1, String test2) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.info = info;
        this.test1 = test1;
        this.test2 = test2;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return this.phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTest1() {
        return this.test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getTest2() {
        return this.test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }
}
