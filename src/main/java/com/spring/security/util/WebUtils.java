package com.spring.security.util;

import org.springframework.security.core.userdetails.User;

public class WebUtils {
    public static String toString(User user) {
        var sb = new StringBuilder();

        sb.append("UserName:").append(user.getUsername());

        var authorities = user.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            sb.append(" (");
            var first = true;
            for (var a : authorities) {
                if (first) {
                    sb.append(a.getAuthority());
                    first = false;
                } else {
                    sb.append(", ").append(a.getAuthority());
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }
}
