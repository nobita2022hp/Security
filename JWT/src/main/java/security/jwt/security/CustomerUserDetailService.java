package security.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.jwt.domain.AccountRepository;

import java.util.ArrayList;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       var account = repository.findById(username);
       if (account.isPresent()){
           var acc = account.get();
           var auth = new ArrayList<GrantedAuthority>();
           auth.add(new SimpleGrantedAuthority(acc.getRole()));
           return new User(acc.getId(), acc.getPassword(), auth);
       }

       throw new UsernameNotFoundException("Khong tim thay user: " + username);
    }
}
