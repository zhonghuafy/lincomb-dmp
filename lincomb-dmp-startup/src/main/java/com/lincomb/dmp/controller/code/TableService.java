package com.lincomb.dmp.controller.code;

import com.lincomb.dmp.datasource.DruidProperties;
import com.lincomb.dmp.util.SpringContextHolder;
import org.apache.ibatis.jdbc.SqlRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * 获取数据库所有的表
 *
 * @author fengshuonan
 * @date 2017-12-04-下午1:37
 */
@Service
public class TableService {

    @Autowired
    private DruidProperties druidProperties;

    /**
     * 获取当前数据库所有的表信息
     */
    public List<Map<String, Object>> getAllTables() throws Exception {

        Class.forName(druidProperties.getDriverClassName());
        Connection conn = DriverManager.getConnection(druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());
        String sql = "select TABLE_NAME as tableName,TABLE_COMMENT as tableComment from information_schema.`TABLES` where TABLE_SCHEMA = '"+ conn.getCatalog() +"'";
        return new SqlRunner(conn).selectAll(sql);
    }
}
