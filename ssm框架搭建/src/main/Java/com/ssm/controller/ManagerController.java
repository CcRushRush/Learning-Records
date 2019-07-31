package com.ssm.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ssm.pojo.Manager;
import com.ssm.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/managerController")
public class ManagerController {
    private List<Manager> managerList;
    private Manager cManager = new Manager();
    @Autowired
    private IManagerService managerService;

    @RequestMapping("/managerLogin")
    @ResponseBody
    public Boolean managerLogin(@RequestBody String message, HttpSession session) throws Exception {
        System.out.println(message);
        JSONObject json = JSONArray.parseObject(message);
        String name = (String) json.get("name");
        String password = (String) json.get("password");
        cManager = new Manager();
        cManager.setName(name);
        cManager.setPassword(password);
        cManager = managerService.managerLogin(cManager);
        session.setAttribute("manager",cManager);
        if (cManager == null)
            return false;
        else{
            return true;
        }
    }

    @RequestMapping("/toManagerList")
    public String toManagerList(Model model) throws Exception {
        managerList = managerService.getManagerList();
        model.addAttribute("managerList", managerList);
        return "manager/managerList";
    }

    @RequestMapping("/toEditManager/{mid}")
    public String toEditManager(@PathVariable Integer mid, Model model) throws Exception {
        System.out.println(mid);
        cManager = managerService.searchUpdateManagerFromIdService(mid);
        System.out.println(cManager);
        model.addAttribute("manager", cManager);
        return "manager/managerEdit";
    }

    @RequestMapping("/editManager")
    public String editManager(Manager manager) throws Exception {
        System.out.println("editManager:" + manager);
        managerService.updateManagerFromIdService(manager);
        return "redirect:toManagerList.do";
    }

    @RequestMapping("/toDeleteManager/{mid}")
    public String toDeleteManager(@PathVariable Integer mid) throws Exception {
        System.out.println(mid);
        managerService.deleteManagerFromIdService(mid);
        System.out.println("delete id :" + mid);
        return "redirect:/managerController/toManagerList.do";
    }

    @RequestMapping("/toAddManager")
    public String toAddManager() {
        return "manager/managerAdd";
    }

    @RequestMapping("/addUser")
    public String addUser(Manager manager) throws Exception {
        managerService.insertManagerService(manager);
        return "redirect:toManagerList.do";
    }

}
