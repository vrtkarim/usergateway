package com.vrtkarim.usergateway.services;


import com.vrtkarim.usergateway.models.User;
import com.vrtkarim.usergateway.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
            }
        };
    }
    public com.vrtkarim.usergateway.models.User save(User user) {
        return userRepository.save(user);
    }
}
