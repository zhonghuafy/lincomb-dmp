package com.lincomb.dmp.persistence.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lincomb.dmp.persistence.model.Dict;
import org.apache.ibatis.annotations.Param;
import com.lincomb.dmp.persistence.model.OperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 操作日志 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    /**
     * 获取操作日志
     *
     * @author stylefeng
     * @Date 2017/4/16 23:48
     */
    List<Map<String, Object>> getOperationLogs(@Param("page") Page<OperationLog> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("logType") String logType, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

    /**
     * 获取登录日志
     *
     * @author stylefeng
     * @Date 2017/4/16 23:48
     */
    List<Map<String, Object>> getLoginLogs(@Param("page") Page<OperationLog> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

}