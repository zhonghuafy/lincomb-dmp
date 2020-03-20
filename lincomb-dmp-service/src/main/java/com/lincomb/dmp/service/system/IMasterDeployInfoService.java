package com.lincomb.dmp.service.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.lincomb.dmp.persistence.model.TMasterDeployInfo;

import java.util.List;
import java.util.Map;

public interface IMasterDeployInfoService {

    /**
     * 查询所有信息
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectDeploys(Page<Map<String, Object>> page);

    Integer selectMax();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    TMasterDeployInfo selectByPrimaryKey(Integer id);

    /**
     * 新增信息 返回ID
     * @param record
     * @return
     */
    int insert(TMasterDeployInfo record);

    /**
     * 修改信息
     * @param record
     * @return
     */
    boolean update(TMasterDeployInfo record);

    boolean updateOrders(TMasterDeployInfo tMasterDeployInfo);

    /**
     * 根据ID修改orders值
     * @param maxOrders
     * @param id
     * @return
     */
    boolean updateOrdersById(Integer maxOrders,Integer id);

    /**
     * 删除信息
     * @param id
     * @return
     */
    boolean delete(int id);
}