package com.spring.security.service;

import com.spring.security.domain.AppUser;
import com.spring.security.repository.AppRoleRepository;
import com.spring.security.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserDAO;

    @Autowired
    private AppRoleRepository appRoleDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = appUserDAO.findUserAccount(userName);

        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]
        var roleNames = appRoleDAO.getRoleNames(appUser.getUserId());

        var grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                var authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        return new User(appUser.getUserName(), //
                appUser.getEncryptedPassword(), grantList);
    }
}
