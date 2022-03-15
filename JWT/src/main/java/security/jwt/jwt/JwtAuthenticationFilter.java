package security.jwt.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import security.jwt.security.CustomerUserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomerUserDetailService service;

    private String getTokenFromRequest(HttpServletRequest request){
        var token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")){
            return token.substring(7);
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var jwt = getTokenFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
                var username = tokenProvider.getUsernameFromJwt(jwt);
                var userDetail = service.loadUserByUsername(username);
                if (userDetail != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (Exception ex){
            log.error("doFilterInternal: " + ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
