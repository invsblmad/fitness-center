package com.inai.courseproject.services;

import com.inai.courseproject.dto.CredentialsDto;
import com.inai.courseproject.dto.UserDto;
import com.inai.courseproject.exceptions.UsernameNotUniqueException;
import com.inai.courseproject.models.user.Role;
import com.inai.courseproject.models.user.User;
import com.inai.courseproject.repositories.UsersRepository;
import com.inai.courseproject.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User user;

    @Transactional
    public void register(UserDto userDto) {
        User user = usersRepository.findByFullNameAndPhoneAndEmail(userDto.getFullName(), userDto.getPhone(),
                userDto.getEmail());

        if (usersRepository.findByUsername(userDto.getUsername()).isPresent())
            throw new UsernameNotUniqueException("Such username is taken");

        user.setCredentials(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()));

        user.setRole(Role.ROLE_CLIENT); // регистрация для клиента
        usersRepository.save(user);
    }

    public void login(CredentialsDto credentialsDto) {
        Authentication authObject = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (credentialsDto.getUsername(), credentialsDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authObject);
        setUser();
    }

    public void setUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        this.user = userDetails.getUser();
    }

    public User getUser() {
        return this.user;
    }
}
