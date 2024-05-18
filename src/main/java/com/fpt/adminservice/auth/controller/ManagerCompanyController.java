package com.fpt.adminservice.auth.controller;

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
@RequestMapping("/manager/company")
@RequiredArgsConstructor
public class ManagerCompanyController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(name = "status", required = false) UserStatus status,
            @RequestParam(name = "name", required = false) String name,
            Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable, status, name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> approveUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.approve(id));
    }

//    @PatchMapping("/{id}/{pricePlan}")
//    public ResponseEntity<UserDto> extend(@PathVariable("id") String id, @PathVariable("pricePlan") String pricePlan) {
//        return ResponseEntity.ok(userService.extendService(id, pricePlan));
//    }
}
