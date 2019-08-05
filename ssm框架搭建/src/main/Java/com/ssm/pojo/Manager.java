package com.ssm.pojo;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement //加上XmlRootElement可以获取该类的xml格式
@Component
public class Manager {
    private Integer mid;
    private String name;
    private String password;
    private String gender;
    private Integer age;
    private String picture;

    public Manager() {
    }

    public Manager(Integer mid, String name, String password, String gender, Integer age, String picture) {
        this.mid = mid;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.picture = picture;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "mid=" + mid +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", picture='" + picture + '\'' +
                '}';
    }
}
