package com.cc.controller;

import com.cc.pojo.User;
import com.cc.pojo.UserExt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/userController")
public class UserController {

    @RequestMapping("/toListUser")
    public String toListUser(Model model) {
//        模拟数据库存取数据
        List<User> userList = new ArrayList<User>();
        User user1 = new User("cc", "123456", 22);
        User user2 = new User("djy", "123456", 23);
        User user3 = new User("cc-djy", "123456", 2);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        model.addAttribute("userList", userList);
        return "user/userList";
    }

    @RequestMapping("/toAddUser")
    public String toAddUser() {
        return "user/addUser";
    }

    @RequestMapping("/toEditUser")
    public String toEditUser(String name, Model model) {
        System.out.println("接受到修改的名字" + name);
        //数据库通过name查找并返回
        User user = new User("cc-djy", "123456", 2);
        model.addAttribute("user", user);
        return "user/editUser";
    }

    //    @PathVariable是用来获得请求url中的动态参数
    @RequestMapping("/toEditUser1/{username}")
    public String toEditUser1(@PathVariable String username, Model model) {
        System.out.println("接受到修改的名字" + username);
        //数据库通过name查找并返回
        User user = new User("cc-djy", "123456", 2);
        model.addAttribute("user", user);
        return "user/editUser";
    }

    @RequestMapping("/editUser")
    public String editUser(User user, Model model) {
        System.out.println(user.toString());
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        model.addAttribute("userList", userList);
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("User",user);
        return "user/userList";
    }

    @RequestMapping("/addUser")
    public String addUser(User user) {
        System.out.println(user.toString());

//        ModelAndView mv = new ModelAndView();
//        mv.addObject("User",user);
        return "user/userList";
    }

    @RequestMapping("/addUser2")
    public String addUser2(UserExt userExt) {
        System.out.println("ext" + userExt.getUser());
        return "user/userList";
    }

    @RequestMapping("/addUser3")
    public String addUser3(UserExt userExt) {
        System.out.println("list" + userExt.getUserList());
        return "user/userList";
    }

    @RequestMapping("/addUser4")
    public String addUser4(UserExt userExt) {
        System.out.println("map" + userExt.getUserMap());
        return "user/userList";
    }

    @RequestMapping("/test")
    public String test() {
        //forward:转发，地址没有改变
        //return "forward:toListUser.do";

        //redirect:重定向，地址改变
        return "redirect:toListUser.do";
    }

    /**
     * @RequestParam(value = "uid",required = true,defaultValue = "30")
     * required = true时，没有加入defaultValue时，uid为必传
     * efaultValue = "30",当uid没有传时，默认给30
     */
    @RequestMapping("/test1")
    public String test1(@RequestParam(value = "uid", required = true, defaultValue = "30") Integer uid) {
        System.out.println(uid);
        //redirect:重定向，地址改变
        return "redirect:toListUser.do";
    }
}
