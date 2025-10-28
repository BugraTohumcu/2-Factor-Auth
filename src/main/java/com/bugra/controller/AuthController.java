package com.bugra.controller;

import com.bugra.dto.LoginUser;
import com.bugra.dto.RegisterUser;
import com.bugra.dto.ResponsePattern;
import com.bugra.dto.UserResponse;
import com.bugra.facade.AuthFacade;
import com.bugra.mapper.UserResponseMapper;
import com.bugra.model.RefreshToken;
import com.bugra.model.User;
import com.bugra.service.UserService;
import com.bugra.shared.AuthMessages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final AuthFacade authFacade;

    public AuthController(UserService userService, AuthFacade authFacade) {
        this.userService = userService;
        this.authFacade = authFacade;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponsePattern<UserResponse>>
    login(@RequestBody  @Valid LoginUser loginUser, HttpServletResponse response) {
        logger.info("User: {} is logging in", loginUser.email());
        User user = userService.login(loginUser);
        authFacade.saveTokenAndSetCookie(user, response);
        UserResponse userResponse = UserResponseMapper.mapToUserResponse(user);
        logger.info("User: {} is logged in", loginUser.email());
        return ResponseEntity.ok(new ResponsePattern<>(
                AuthMessages.LOGIN_SUCCESS,
                userResponse,
                true
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponsePattern<UserResponse>>
    register(@RequestBody @Valid RegisterUser user,HttpServletResponse response) {
        logger.info("User: {} is Registering", user.email());
        User registeredUser = userService.register(user);
        authFacade.saveTokenAndSetCookie(registeredUser,response);
        UserResponse userResponse = UserResponseMapper.mapToUserResponse(registeredUser);
        return ResponseEntity.ok(new ResponsePattern<>(
                AuthMessages.USER_CREATED_SUCCESSFULLY,
                userResponse,
                true));
    }

    @GetMapping("/user")
    public ResponseEntity<ResponsePattern<UserResponse>>
    getUser(HttpServletRequest request){
        UserResponse userResponses = userService.getUser(request);
        return ResponseEntity.ok(new ResponsePattern<>("User Info Fetched",userResponses,true));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<ResponsePattern<?>>
    refreshToken(HttpServletRequest request) {

        return null;
    }
}
