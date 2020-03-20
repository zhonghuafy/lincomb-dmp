package com.lincomb.dmp.persistence.mapper.aircat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfo;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfoLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author shiyu.cao
 * @since 2017-12-28
 */
public interface TPhicommAircatInfoMapper extends BaseMapper<TPhicommAircatInfo> {

    /**
     * @param page
     * @param entityWrapper
     * @return
     */
    List<TPhicommAircatInfo> listPages(Page<TPhicommAircatInfo> page, @Param("ew") QueryWrapper<TPhicommAircatInfo> entityWrapper);

    /**
     * 批量插入
     * @param tPhicommAircatInfoList
     * @return
     */
    boolean batchInserList(List<TPhicommAircatInfo> tPhicommAircatInfoList);

    /**
     * 批量修改
     * @param tPhicommAircatInfoLogList
     * @return
     */
    boolean batchUpdateList(List<TPhicommAircatInfoLog> tPhicommAircatInfoLogList);
}