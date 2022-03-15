package com.spring.security.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AppRoleRepository extends JdbcDaoSupport {

    @Autowired
    public AppRoleRepository(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public List<String> getRoleNames(Long userId) {
        var sql = "Select r.Role_Name " //
                + " from User_Role ur, App_Role r " //
                + " where ur.Role_Id = r.Role_Id and ur.User_Id = ? ";

        var params = new Object[] { userId };

        return getJdbcTemplate().queryForList(sql, params, String.class);
    }

}
