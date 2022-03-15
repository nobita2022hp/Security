package com.spring.security.repository;

import com.spring.security.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;

@Repository
@Transactional
public class AppUserRepository extends JdbcDaoSupport {

    @Autowired
    public AppUserRepository(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public AppUser findUserAccount(String userName) {
        // Select .. from App_User u Where u.User_Name = ?
        var sql = AppUserMapper.BASE_SQL + " where u.User_Name = ? ";

        var params = new Object[] { userName };
        var mapper = new AppUserMapper();
        try {
            return getJdbcTemplate().queryForObject(sql, params, mapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
