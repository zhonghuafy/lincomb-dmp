package com.lincomb.dmp.service.aircat.impl;

import com.lincomb.dmp.persistence.mapper.aircat.TPhicommAircatInfoLogMapper;
import com.lincomb.dmp.persistence.mapper.aircat.TPhicommAircatInfoMapper;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfoLog;
import com.lincomb.dmp.service.aircat.IPhicommAircatDeviceService;
import com.lincomb.dmp.service.aircat.IPhicommAircatInfoLogService;
import com.lincomb.dmp.service.aircat.ISchedulerService;
import com.lincomb.dmp.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by shiyu.cao on 2018/1/25.
 */
@Service
public class TSchedulerServiceImpl implements ISchedulerService {

    @Resource
    private IPhicommAircatDeviceService iPhicommAircatDeviceService;

    @Resource
    private IPhicommAircatInfoLogService iPhicommAircatInfoLogService;

    @Resource
    public TPhicommAircatInfoMapper tPhicommAircatInfoMapper;

    @Resource
    public TPhicommAircatInfoLogMapper tPhicommAircatInfoLogMapper;

    private static Logger log = LoggerFactory.getLogger(TSchedulerServiceImpl.class);

    public  boolean aircatSecheduler(){

        //获取当前日期
        String time = DateUtil.getBeforeDate();
        //测试
        time = "2018-01-22";

        try {
            //调用插入设备
            iPhicommAircatDeviceService.batchInsertService(time);
            iPhicommAircatInfoLogService.batchInserList();
        }catch (IOException e){
            log.info("===========>>>定时器批量插入异常！");
        }

        //查询log  并更新
        List<TPhicommAircatInfoLog> tPhicommAircatInfoLogList = tPhicommAircatInfoLogMapper.getAircatInfoLogList();
        boolean bool = tPhicommAircatInfoMapper.batchUpdateList(tPhicommAircatInfoLogList);

        if (bool == true) {
            log.info("===========>>>定时器执行批量修改主表成功!");
        }else {
            log.info("===========>>>定时器执行批量修改主表失败!");
        }

        return true;
    }
}
