package com.lincomb.dmp.service.system.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lincomb.dmp.persistence.mapper.system.TMasterDeployInfoMapper;
import com.lincomb.dmp.persistence.model.TMasterDeployInfo;
import com.lincomb.dmp.service.system.IMasterDeployInfoService;
import com.lincomb.dmp.warpper.DeployWarpper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MasterDeployInfoServiceImpl implements IMasterDeployInfoService {

    @Resource
    private TMasterDeployInfoMapper tMasterDeployInfoMapper;

    @Override
    public Page<Map<String, Object>> selectDeploys(Page<Map<String, Object>> page) {
        EntityWrapper<Map<String, Object>> entityWrapper = new EntityWrapper<>();
        Map<String, Object> params = page.getCondition();
        if (null != page.getCondition().get("slaveName")) {
            entityWrapper.like("slave_name", page.getCondition().get("slaveName").toString());
        }
        if (null != page.getCondition().get("slaveStatus")) {
            entityWrapper.like("slave_status", page.getCondition().get("slaveStatus").toString());
        }
        if (null != params.get("createTime") && !"".equals(params.get("createTime").toString())) {
            entityWrapper.andNew("DATE_FORMAT(create_time,'%Y-%m-%d') >= {0}", params.get("createTime").toString());
        }
        if (null != params.get("updateTime") && !"".equals(params.get("updateTime").toString())) {
            entityWrapper.andNew("DATE_FORMAT(update_time,'%Y-%m-%d') >= {0}", params.get("updateTime").toString());
        }
        if (null != page.getCondition().get("remark")) {
            entityWrapper.like("remark", page.getCondition().get("remark").toString());
        }

        List<Map<String, Object>> deployList = tMasterDeployInfoMapper.selectDeploysPage(page, entityWrapper);
        page.setRecords((List<Map<String,Object>>) new DeployWarpper(deployList).warp());
        return page;
    }

    @Override
    public Integer selectMax() {
        return tMasterDeployInfoMapper.selectMax();
    }

    @Override
    public TMasterDeployInfo selectByPrimaryKey(Integer id) {
        return tMasterDeployInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(TMasterDeployInfo record) {
        return tMasterDeployInfoMapper.insert(record);
    }

    @Override
    public boolean update(TMasterDeployInfo record) {
        return tMasterDeployInfoMapper.update(record);
    }

    @Override
    public boolean updateOrders(TMasterDeployInfo tMasterDeployInfo) {
        return tMasterDeployInfoMapper.updateOrders(tMasterDeployInfo);
    }

    @Override
    public boolean updateOrdersById(Integer maxOrders, Integer id) {
        return tMasterDeployInfoMapper.updateOrdersById(maxOrders, id);
    }

    @Override
    public boolean delete(int id) {
        return tMasterDeployInfoMapper.delete(id);
    }
}
