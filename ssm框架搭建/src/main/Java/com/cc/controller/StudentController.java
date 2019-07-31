package com.cc.controller;

import com.cc.pojo.Student;
import com.cc.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/stu")
public class StudentController {
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "stu/register";
    }


    /**
     * @RequestBody：把json数据转成模型对象
     * @ResponseBody：返回json数据，把模型对象转成json
     */
    @RequestMapping(value = "/register",method = {RequestMethod.POST})
    @ResponseBody
    public Student register(@RequestBody Student student){
        System.out.println(student);
        return student;
    }

    @RequestMapping("/test")
    public Student test(){
        Student stu = new Student();
        stu.setName("cc");
        stu.setPassword("123456");
        return stu;
    }
}
