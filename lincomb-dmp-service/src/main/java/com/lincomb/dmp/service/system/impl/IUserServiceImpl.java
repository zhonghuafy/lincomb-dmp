package com.lincomb.dmp.service.system.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lincomb.dmp.persistence.mapper.system.UserMapper;
import com.lincomb.dmp.service.system.IUserService;
import com.lincomb.dmp.warpper.UserWarpper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class IUserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Page<Map<String, Object>> selectUsersPage(Page<Map<String, Object>> page) {
        EntityWrapper<Map<String, Object>> entityWrapper = new EntityWrapper<>();
        Map<String, Object> params = page.getCondition();
        if (null != params.get("name")) {
            entityWrapper.like("phone", params.get("name").toString())
                    .or().like("account", params.get("name").toString())
                    .or().like("name", params.get("name").toString());
        }
        if (null != params.get("beginTime") && !"".equals(params.get("beginTime").toString())) {
            entityWrapper.andNew("DATE_FORMAT(createTime,'%Y-%m-%d') >= {0}", params.get("beginTime").toString());
        }
        if (null != params.get("endTime") && !"".equals(params.get("endTime").toString())) {
            entityWrapper.andNew("DATE_FORMAT(createTime,'%Y-%m-%d') <= {0}", params.get("endTime").toString());
        }
        if (null != page.getOrderByField()) {
            entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
        } else {
            entityWrapper.orderBy("createTime", false);
        }
        List<Map<String, Object>> users = userMapper.selectUsersPage(page, entityWrapper);
        page.setRecords((List<Map<String,Object>>) new UserWarpper(users).warp());
        return page;
    }
}
