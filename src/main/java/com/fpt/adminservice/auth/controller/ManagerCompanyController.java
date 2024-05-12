package com.fpt.adminservice.auth.controller;

import com.fpt.adminservice.auth.dto.UserCreateRequest;
import com.fpt.adminservice.auth.dto.UserDto;
import com.fpt.adminservice.auth.model.UserStatus;
import com.fpt.adminservice.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/manager/companyd")
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
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam(name = "status", required = false) UserStatus status, Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable, status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> approveUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.approve(id));
    }
}
