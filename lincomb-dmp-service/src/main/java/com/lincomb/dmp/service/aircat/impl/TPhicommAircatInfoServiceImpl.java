package com.lincomb.dmp.service.aircat.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lincomb.dmp.persistence.mapper.aircat.TPhicommAircatInfoLogMapper;
import com.lincomb.dmp.persistence.mapper.aircat.TPhicommAircatInfoMapper;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfo;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfoLog;
import com.lincomb.dmp.service.aircat.IPhicommAircatInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Created by shiyu.cao on 2017/12/28.
 */
@Service
public class TPhicommAircatInfoServiceImpl extends ServiceImpl<TPhicommAircatInfoMapper, TPhicommAircatInfo> implements IPhicommAircatInfoService {

    @Resource
    public TPhicommAircatInfoMapper tPhicommAircatInfoMapper;

    @Resource
    public TPhicommAircatInfoLogMapper tPhicommAircatInfoLogMapper;

    @Override
    public Page<TPhicommAircatInfo> selectAircatListPage(Page<TPhicommAircatInfo> page) {

        Map<String, Object> params = page.getCondition();
        EntityWrapper<TPhicommAircatInfo> entityWrapper = new EntityWrapper<TPhicommAircatInfo>();

        if (null != params.get("mac")) {
            entityWrapper.like("mac", params.get("mac").toString());
        }

        if (null != params.get("startTime") && !"".equals(params.get("startTime").toString())) {
            entityWrapper.andNew("DATE_FORMAT(cat_time,'%Y-%m-%d') >= {0}", params.get("startTime").toString());
        }

        if (null != params.get("endTime") && !"".equals(params.get("endTime").toString())) {
            entityWrapper.andNew("DATE_FORMAT(cat_time,'%Y-%m-%d') <= {0}", params.get("endTime").toString());
        }

        if (null != page.getOrderByField()) {
            entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
        } else {
            entityWrapper.orderBy("cat_time", false);
        }

        List<TPhicommAircatInfo> tPhicommAircatInfoList = tPhicommAircatInfoMapper.listPages(page, entityWrapper);
        page.setRecords(tPhicommAircatInfoList);

        return page;

    }


    @Override
    public Page<TPhicommAircatInfoLog> selectAircatLogListPage(Page<TPhicommAircatInfoLog> page) {
        Map<String, Object> params = page.getCondition();
        EntityWrapper<TPhicommAircatInfoLog> entityWrapper = new EntityWrapper<TPhicommAircatInfoLog>();

        if (null != params.get("mac")) {
            entityWrapper.like("mac", params.get("mac").toString());
        }

        if (null != page.getOrderByField()) {
            entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
        } else {
            entityWrapper.orderBy("cat_time", false);
        }

        List<TPhicommAircatInfoLog> tPhicommAircatInfoLogList = tPhicommAircatInfoLogMapper.listLogPages(page, entityWrapper);
        page.setRecords(tPhicommAircatInfoLogList);

        return page;

    }

    /**
     * 批量插入操作
     *
     */
    public  boolean batchInserList(List<TPhicommAircatInfo> tPhicommAircatInfoList)  {
        boolean bool = tPhicommAircatInfoMapper.batchInserList(tPhicommAircatInfoList);
        return  true;
    }
}
