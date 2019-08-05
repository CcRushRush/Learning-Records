package com.cc.pojo;

import java.util.*;

public class UserExt {
    private User user;
    private List<User> userList = new ArrayList<User>();
    private Map<String, Object> userMap = new HashMap<String, Object>();

    public Map<String, Object> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, Object> userMap) {
        this.userMap = userMap;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserExt{" +
                "user=" + user +
                '}';
    }
}
