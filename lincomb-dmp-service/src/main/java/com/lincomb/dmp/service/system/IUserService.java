package com.lincomb.dmp.service.system;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.Map;

public interface IUserService {
    Page<Map<String, Object>> selectUsersPage(Page<Map<String, Object>> page);
}
