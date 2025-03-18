package com.vrtkarim.usergateway.services;


import com.vrtkarim.usergateway.dto.JwtAuthenticationResponse;
import com.vrtkarim.usergateway.dto.SignInRequest;
import com.vrtkarim.usergateway.dto.SignUpRequest;
import com.vrtkarim.usergateway.filters.JwtAuthenticationFilter;
import com.vrtkarim.usergateway.models.User;
import com.vrtkarim.usergateway.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private  final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;
    public JwtAuthenticationResponse signup(SignUpRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        userService.save(user);
        String token = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(token).build();
    }

    public JwtAuthenticationResponse signin(SignInRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new UsernameNotFoundException("something went wrong"));
        String token = jwtService.generateToken(user);
        return  JwtAuthenticationResponse.builder().token(token).build();
    }



}
