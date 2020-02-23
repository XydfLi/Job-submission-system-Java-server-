package com.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Hello {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date now;
    private String name;


    public void setNow(Date now) {
        this.now = now;
    }

    public Date getNow() {
        return now;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
