package com.lincomb.dmp.service.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.lincomb.dmp.page.PageInfoBT;
import com.lincomb.dmp.persistence.model.Role;

import java.util.Map;

/**
 * 角色相关业务
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午9:11:57
 */
public interface IRoleService {

    /**
     * 设置某个角色的权限
     *
     * @param roleId 角色id
     * @param ids    权限的id
     * @date 2017年2月13日 下午8:26:53
     */
    void setAuthority(Integer roleId, String ids);

    /**
     * 删除角色
     *
     * @author stylefeng
     * @Date 2017/5/5 22:24
     */
    void delRoleById(Integer roleId);

    /**
     * 分页查询列表
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectRolesPage(Page<Map<String, Object>> page);
}
