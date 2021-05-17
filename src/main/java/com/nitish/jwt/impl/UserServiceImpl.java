package com.nitish.jwt.impl;

import com.nitish.jwt.model.User;
import com.nitish.jwt.repo.UserRepository;
import com.nitish.jwt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    @Override
    public Integer saveUser(User user) {
        user.setPassword(
                pwdEncoder.encode(user.getPassword()));

        return  userRepository.save(user).getId();

    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      Optional<User> opt =   findByUserName(username);
      if(!opt.isPresent()){
        throw new UsernameNotFoundException("User does not exist");

      }
    User user = opt.get();
        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),
                user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList())
                );
    }
}


