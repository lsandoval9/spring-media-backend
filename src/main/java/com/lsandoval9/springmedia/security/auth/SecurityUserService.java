package com.lsandoval9.springmedia.security.auth;

import com.lsandoval9.springmedia.dto.UserDto;
import com.lsandoval9.springmedia.entities.RoleEntity;
import com.lsandoval9.springmedia.entities.UserEntity;
import com.lsandoval9.springmedia.exceptions.CreadentialsAlreadyTakenException;
import com.lsandoval9.springmedia.helpers.enums.CredentialTaken;
import com.lsandoval9.springmedia.repositories.RoleRepository;
import com.lsandoval9.springmedia.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public SecurityUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .or(() -> userRepository.findUserEntityByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("cannot found username: " + username));


        SecurityUser user = userEntity.getSecurityUser();

        return user;
    }


    public void createUser(UserDto userDto) {

        userRepository.findUserEntityByUsername(userDto.getUsername())
                .ifPresent(userEntity -> {
                    throw new CreadentialsAlreadyTakenException("This username is already taken", CredentialTaken.USERNAME);
                });

        userRepository.findUserEntityByEmail(userDto.getEmail())
                .ifPresent(userEntity -> {
                    throw new CreadentialsAlreadyTakenException("This email is already taken", CredentialTaken.EMAIL);
                });


        UserEntity userEntity = new UserEntity();

        userEntity.setIsUserEnabled(false);
        userEntity.setUsername(userDto.getUsername());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setFirstname(userDto.getFirstname());
        userEntity.setLastname(userDto.getLastname());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(userEntity);

        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setRole(Roles.USER);

        roleEntity.setUser(userEntity);

        roleRepository.save(roleEntity);

    }


    public UserEntity getUser(String username) {

        Optional<UserEntity> user = userRepository.findUserEntityByUsername(username);

        UserEntity userEntity
                = user.orElseThrow(() -> new RuntimeException("ERROR: USER NOT FOUND"));


        return userEntity;
    }


}
