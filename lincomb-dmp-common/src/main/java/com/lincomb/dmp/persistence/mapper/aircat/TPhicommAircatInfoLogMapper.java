package com.lincomb.dmp.persistence.mapper.aircat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfo;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfoLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author shiyu.cao
 * @since 2017-12-28
 */
public interface TPhicommAircatInfoLogMapper extends BaseMapper<TPhicommAircatInfoLog> {

    /**
     * @param page
     * @param entityWrapper
     * @return
     */
    List<TPhicommAircatInfoLog> listLogPages(Page<TPhicommAircatInfoLog> page, @Param("ew") QueryWrapper<TPhicommAircatInfoLog> entityWrapper);


    /**
     * 查询最新
     */
    List<TPhicommAircatInfoLog> getAircatInfoLogList();

    /**
     * 批量新增
     * @param list
     * @return
     */
    boolean batchInserList(List<TPhicommAircatInfoLog> list);
}