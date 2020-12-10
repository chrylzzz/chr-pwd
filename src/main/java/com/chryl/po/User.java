package com.chryl.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * Created by Chryl on 2019/10/15.
 */
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = -126376395371096706L;
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "user_password")
    private String userPassowrd;

    public User() {
    }

    public User(String id, String userName, String userPassowrd) {
        this.id = id;
        this.userName = userName;
        this.userPassowrd = userPassowrd;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassowrd() {
        return userPassowrd;
    }

    public void setUserPassowrd(String userPassowrd) {
        this.userPassowrd = userPassowrd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassowrd='" + userPassowrd + '\'' +
                '}';
    }
}
