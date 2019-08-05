package com.ssm.service.Impl;

import com.ssm.mapper.ManagerMapper;
import com.ssm.pojo.Manager;
import com.ssm.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements IManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    @Override

    public Manager managerLogin(Manager manager) throws Exception {
        return managerMapper.managerLogin(manager);
    }

    @Override
    public List<Manager> getManagerList() throws Exception {
        return managerMapper.getManagerList();
    }

    @Override
    public Manager searchUpdateManagerFromIdService(Integer id) throws Exception {
        return managerMapper.searchUpdateManagerFromId(id);
    }

    @Override
    public void updateManagerFromIdService(Manager manager) throws Exception {
        managerMapper.updateManagerFromId(manager);
    }

    @Override
    public void insertManagerService(Manager manager) throws Exception {
        managerMapper.insertManager(manager);
    }

    @Override
    public void deleteManagerFromIdService(Integer id) throws Exception {
        managerMapper.deleteManagerFromId(id);
    }
}
