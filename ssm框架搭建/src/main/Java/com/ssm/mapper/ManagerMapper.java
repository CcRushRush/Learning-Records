package com.ssm.mapper;
import com.ssm.pojo.Manager;
import java.util.List;

//实现方法看config,里面的mapper,接口的命名和mapper里的xml命名要一致
public interface ManagerMapper {
    //    登录判断，根据id
    Manager managerLogin(Manager manager) throws Exception;

    //    获取所有管理员
    public List<Manager> getManagerList() throws Exception;

    //    查找需要更新的manager
    public Manager searchUpdateManagerFromId(Integer id) throws Exception;

    //    更新管理员信息
    public void updateManagerFromId(Manager manager) throws Exception;

    //    添加管理员
    public void insertManager(Manager manager) throws Exception;

    //    删除管理员
    public void deleteManagerFromId(Integer id) throws Exception;
}
