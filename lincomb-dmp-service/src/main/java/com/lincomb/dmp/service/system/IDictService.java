package com.lincomb.dmp.service.system;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.Map; /**
 * 字典服务
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
public interface IDictService {

    /**
     * 添加字典
     *
     * @author fengshuonan
     * @Date 2017/4/27 17:01
     */
    void addDict(String dictName, String dictValues ,String account);

    /**
     * 编辑字典
     *
     * @author fengshuonan
     * @Date 2017/4/28 11:01
     */
    void editDict(Integer dictId, String dictName, String dicts, String account);

    /**
     * 删除字典
     *
     * @author fengshuonan
     * @Date 2017/4/28 11:39
     */
    void delteDict(Integer dictId);

    Page<Map<String,Object>> selectDitsPage(Page<Map<String, Object>> page);
}
