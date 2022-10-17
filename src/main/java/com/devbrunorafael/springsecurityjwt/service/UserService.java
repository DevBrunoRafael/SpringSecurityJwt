package com.devbrunorafael.springsecurityjwt.service;

import com.devbrunorafael.springsecurityjwt.model.Role;
import com.devbrunorafael.springsecurityjwt.model.UserEntity;
import com.devbrunorafael.springsecurityjwt.repository.RoleRepository;
import com.devbrunorafael.springsecurityjwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByUsername(username);
        if (user == null){
            String message = "Usuário não encontrado no banco de dados.";
            log.error(message);
            throw new UsernameNotFoundException(message);
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities
        );
    }


    public UserEntity registerUser(UserEntity user){
        log.info("Novo usuário {} salvo no banco de dados", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public Role saveRole(Role role){
        log.info("Nova role {} salva no banco de dados", role.getRoleName());
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName){
        log.info("Role {} adicionada ao usuário {}", roleName, username);
        var user = userRepository.findUserByUsername(username);
        System.out.println(user);
        var role = roleRepository.findRoleByRoleName(roleName);
        user.getRoles().add(role);
    }

    public UserEntity getUser(String username){
        log.info("Busca pelo usuário {}", username);
        return userRepository.findUserByUsername(username);
    }

    public List<UserEntity> getAllUsers(){
        log.info("Busca por usuários cadastrados");
        return userRepository.findAll();
    }

}
