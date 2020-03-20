package com.lincomb.dmp.service.aircat;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatDevice;

import java.io.IOException;
import java.util.List;

/**
 * Created by kaiyi.chen on 2018/1/23.
 */
public interface IPhicommAircatDeviceService extends IService<TPhicommAircatDevice>{


	/**
     * 批量插入设备数据
     * @param page
     * @return
     */
	boolean batchInsertService(String time) throws IOException;
	/**
     * 查询设备数据
     * @param page
     * @return
     */
	Page<TPhicommAircatDevice> selectAircatDevice(Page<TPhicommAircatDevice> page);
}
