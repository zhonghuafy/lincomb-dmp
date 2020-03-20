package com.lincomb.dmp.persistence.mapper.aircat;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatDevice;
/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author kaiyi.chen
 * @since 2018-1-23
 */

public interface TPhicommAircatDeviceMapper extends BaseMapper<TPhicommAircatDevice> {

	/**
	 * 查询全部
	 * @return
	 */
	List<TPhicommAircatDevice> getAll();
	 /**
     * 批量插入数据
     *
     */
	boolean batchInsert(List<TPhicommAircatDevice> tPhicommAircatDevicesList);
	
	/***
	 * 查询设备
	 */
	 List<TPhicommAircatDevice> listDevice(Page<TPhicommAircatDevice> page, @Param("ew") QueryWrapper<TPhicommAircatDevice> entityWrapper);


	/**
	 * 获取集合  limit 函数
	 */
	List<TPhicommAircatDevice> listLimit(@Param("startSize") int startSize, @Param("sizeNum") int sizeNum);
}