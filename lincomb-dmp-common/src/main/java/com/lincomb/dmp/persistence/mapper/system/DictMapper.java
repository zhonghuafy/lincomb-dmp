package com.lincomb.dmp.persistence.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lincomb.dmp.persistence.model.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 字典表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 根据编码获取词典列表
     *
     * @param code
     * @return
     * @date 2017年2月13日 下午11:11:28
     */
    List<Dict> selectByCode(@Param("code") String code);

    /**
     * 查询字典列表
     *
     * @author fengshuonan
     * @Date 2017/4/26 13:04
     */
    List<Map<String,Object>> list(@Param("condition") String conditiion);

    List<Map<String,Object>> selectDictsPage(Page<Map<String, Object>> page, @Param("ew") QueryWrapper<Map<String, Object>> entityWrapper);
}