package com.lincomb.dmp.service.system.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lincomb.dmp.persistence.mapper.system.RelationMapper;
import com.lincomb.dmp.persistence.mapper.system.RoleMapper;
import com.lincomb.dmp.persistence.model.Relation;
import com.lincomb.dmp.service.system.IRoleService;
import com.lincomb.dmp.util.Convert;
import com.lincomb.dmp.warpper.RoleWarpper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    RoleMapper roleMapper;

    @Resource
    RelationMapper relationMapper;

    @Override
    @Transactional(readOnly = false)
    public void setAuthority(Integer roleId, String ids) {

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);

        // 添加新的权限
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", ids))) {
            Relation relation = new Relation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delRoleById(Integer roleId) {
        //删除角色
        this.roleMapper.deleteById(roleId);

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);
    }

    @Override
    public Page<Map<String, Object>> selectRolesPage(Page<Map<String, Object>> page) {
        EntityWrapper<Map<String, Object>> entityWrapper = new EntityWrapper<>();
        if (null != page.getCondition().get("roleName")) {
            entityWrapper.like("name", page.getCondition().get("roleName").toString());
        }
        if (null != page.getOrderByField()) {
            entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
        }
        List<Map<String, Object>> roles = roleMapper.selectRolesPage(page, entityWrapper);
        page.setRecords((List<Map<String,Object>>) new RoleWarpper(roles).warp());
        return page;
    }

}
