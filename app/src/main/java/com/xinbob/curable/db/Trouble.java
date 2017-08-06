package com.xinbob.curable.db;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by xinbob on 7/17/17.
 * JavaBean troubles CRUD使用LitePal
 */

public class Trouble extends DataSupport implements Serializable {

    /**
     * LitePal auto generate
     */
    private int id;

    /**
     * Timestamp current date
     * 2017-07-17
     */
    @Column(nullable = false)
    private String timestamp;

    /**
     * Your worry some thing
     */
    @Column(nullable = false)
    private String worry;

    /**
     * Yourself resolve worry
     */
    private String resolve;

    /**
     * Ten types muilt-choose
     * use # split
     */
    private String tenTypes;

    /**
     * After write mood
     */
    @Column(defaultValue = "-1")
    private int mood;


    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getWorry() {
        return worry;
    }

    public void setWorry(String worry) {
        this.worry = worry;
    }

    public String getResolve() {
        return resolve;
    }

    public void setResolve(String resolve) {
        this.resolve = resolve;
    }

    public String getTenTypes() {
        return tenTypes;
    }

    public void setTenTypes(String tenTypes) {
        this.tenTypes = tenTypes;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "Trouble{" +
                "id=" + id +
                ", timestamp='" + timestamp + '\'' +
                ", worry='" + worry + '\'' +
                ", resolve='" + resolve + '\'' +
                ", tenTypes='" + tenTypes + '\'' +
                ", mood=" + mood +
                '}';
    }
}
