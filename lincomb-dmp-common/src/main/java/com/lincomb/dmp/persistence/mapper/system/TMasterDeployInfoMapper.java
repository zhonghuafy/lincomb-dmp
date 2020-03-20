package com.lincomb.dmp.persistence.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lincomb.dmp.persistence.model.TMasterDeployInfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 信息部署表 Mapper 接口
 * </p>
 *
 * @author qing.xiong
 * @since 2018-1-26
 */
public interface TMasterDeployInfoMapper extends BaseMapper<TMasterDeployInfo>{

    /**
     * 查询所有信息
     * @param page
     * @return
     */
    List<Map<String, Object>> selectDeploysPage(Page<Map<String, Object>> page, @Param("ew") QueryWrapper<Map<String, Object>> entityWrapper);

    /**
     * 查询最大值
     * @return
     */
    int selectMax();

    /**
     * 根据条件查询信息列表
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectDeploys(@Param("slaveName") String slaveName,@Param("slaveStatus") Integer slaveStatus,@Param("createTime")String createTime,@Param("updateTime")String updateTime,@Param("remark")String remark);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    TMasterDeployInfo selectByPrimaryKey(Integer id);

    /**
     * 修改信息
     * @param record
     * @return
     */
    boolean update(TMasterDeployInfo record);

    /**
     * 修改orders值
     * @param tMasterDeployInfo
     * @return
     */
    boolean updateOrders(TMasterDeployInfo tMasterDeployInfo);

    /**
     * 根据ID修改orders值
     * @param maxOrders
     * @param id
     * @return
     */
    boolean updateOrdersById(@Param("maxOrders")Integer maxOrders,@Param("id")Integer id);

    /**
     * 删除信息
     * @param id
     * @return
     */
    boolean delete(int id);
}