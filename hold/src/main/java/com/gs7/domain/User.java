package com.gs7.domain;

import com.chinaren.framework.common.annotation.AutowareInit;
import com.chinaren.framework.model.LongPrimaryObject;

@AutowareInit
public class User extends LongPrimaryObject {

    /**
     * 
     */
    private static final long serialVersionUID = 3454303336147247744L;

    public static final int sex_man = 0;

    public static final int sex_woman = 1;

    public static final int status_nomore = 0;

    private String nick;

    private String desc;

    private String img;

    private long salt;

    private int score;

    private int grade;

    private String ip;

    private int sex;

    private int status;

    public long getSalt() {
        return salt;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
