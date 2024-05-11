package com.fpt.adminservice.auth.controller;

import com.fpt.adminservice.auth.dto.UserCreateRequest;
import com.fpt.adminservice.auth.dto.UserDto;
import com.fpt.adminservice.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/manager/company")
@RequiredArgsConstructor
public class ManagerCompanyController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDto> createUser(
            @RequestBody UserCreateRequest request
    ) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping()
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }
}
