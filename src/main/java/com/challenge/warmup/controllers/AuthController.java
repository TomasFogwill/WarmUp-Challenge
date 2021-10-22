package com.challenge.warmup.controllers;
import java.util.Set;
import java.io.IOException;
import java.util.HashSet;

import javax.validation.Valid;

import com.challenge.warmup.dto.JwtDto;
import com.challenge.warmup.dto.LoginUser;
import com.challenge.warmup.dto.Message;
import com.challenge.warmup.dto.NewUser;
import com.challenge.warmup.enums.RoleName;
import com.challenge.warmup.jwt.JwtProvider;
import com.challenge.warmup.models.Role;
import com.challenge.warmup.models.User;
import com.challenge.warmup.services.RoleService;
import com.challenge.warmup.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/sign_up")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult)
            throws IOException {
        if (bindingResult.hasErrors()) {

            return new ResponseEntity<>(new Message("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUsername(newUser.getUsername())) {
            return new ResponseEntity<>(new Message("ese email ya existe"), HttpStatus.BAD_REQUEST);
        }
        User user = new User(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
        if (newUser.getRoles().contains("admin")) {
            roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return new ResponseEntity<>(new Message("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Message("campos mal puestos"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
    }
}
