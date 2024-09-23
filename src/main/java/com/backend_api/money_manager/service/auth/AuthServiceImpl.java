package com.backend_api.money_manager.service.auth;

import com.backend_api.money_manager.dto.request.users.AccountVerification;
import com.backend_api.money_manager.dto.request.users.LoginRequest;
import com.backend_api.money_manager.dto.request.users.RegisterRequest;
import com.backend_api.money_manager.dto.response.users.SignResponse;
import com.backend_api.money_manager.entity.AccountStatus;
import com.backend_api.money_manager.entity.Role;
import com.backend_api.money_manager.entity.Users;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.repository.UsersRepository;
import com.backend_api.money_manager.security.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;

    @Autowired
    InfoAccount infoAccount;

    @Override
    public ResponseEntity<Object> login(LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var data  = usersRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(data);
        String refreshToken = jwtService.generateRefreshToken(data.getId());


        SignResponse response = new SignResponse(data.getEmail(),data.getRole().name(),token,refreshToken);
        return ResponseHandler.generateResponseSuccess(response);
    }

    @Override
    public ResponseEntity<Object> register(RegisterRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        Users userMapper = modelMapper.map(request, Users.class);
        userMapper.setId(UUID.randomUUID().toString());
        userMapper.setRole(Role.USER);
        userMapper.setFullName(request.getFullName());
        userMapper.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.setAccountStatus(AccountStatus.UNVERIFIED);
        var data = usersRepository.save(userMapper);
        return ResponseHandler.generateResponseSuccess(data);
    }


}
