package com.ssm.service;

import com.ssm.pojo.Manager;

import java.util.List;

public interface IManagerService {
    //    登录判断，根据id
    public Manager managerLogin(Manager manager) throws Exception;

    //    获取所有管理员
    public List<Manager> getManagerList() throws Exception;

    //    查找需要更新的manager
    public Manager searchUpdateManagerFromIdService(Integer id) throws Exception;

    //    更新管理员信息
    public void updateManagerFromIdService(Manager manager) throws Exception;

    //    添加管理员
    public void insertManagerService(Manager manager) throws Exception;

    //    删除管理员
    public void deleteManagerFromIdService(Integer id) throws Exception;

}
