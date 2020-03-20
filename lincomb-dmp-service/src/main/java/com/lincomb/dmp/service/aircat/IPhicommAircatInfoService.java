package com.lincomb.dmp.service.aircat;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfo;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfoLog;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by shiyu.cao on 2017/12/28.
 */
public interface IPhicommAircatInfoService extends IService<TPhicommAircatInfo>{

    /**
     * 查询空气猫数据
     * @param page
     * @return
     */
    Page<TPhicommAircatInfo> selectAircatListPage(Page<TPhicommAircatInfo> page);


    /**
     * 查询空气猫数据历史
     * @param page
     * @return
     */
    Page<TPhicommAircatInfoLog> selectAircatLogListPage(Page<TPhicommAircatInfoLog> page);


    /**
     * 批量插入操作
     *
     */
    boolean batchInserList(List<TPhicommAircatInfo> tPhicommAircatInfoList) ;
}
