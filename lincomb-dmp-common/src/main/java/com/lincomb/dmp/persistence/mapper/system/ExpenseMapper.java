package com.lincomb.dmp.persistence.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lincomb.dmp.persistence.model.Dict;
import org.apache.ibatis.annotations.Param;
import com.lincomb.dmp.persistence.model.Expense;

/**
 * <p>
  * 报销表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-12-04
 */
public interface ExpenseMapper extends BaseMapper<Expense> {

}