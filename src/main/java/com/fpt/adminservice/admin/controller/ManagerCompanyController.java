package com.fpt.adminservice.admin.controller;

import com.fpt.adminservice.admin.dto.UserDto;
import com.fpt.adminservice.admin.model.UserStatus;
import com.fpt.adminservice.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;


@Slf4j
@RestController
@RequestMapping("/manager/company")
@RequiredArgsConstructor
public class ManagerCompanyController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(name = "status", required = false) UserStatus status,
            @RequestParam(name = "fromDate", required = false) LocalDateTime fromDate,
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "name", required = false) String name,
            Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable, status, name, fromDate, startDate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> approveUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.approve(id));
    }

    @PatchMapping("/{id}/{pricePlan}")
    public ResponseEntity<UserDto> extend(@PathVariable("id") String id, @PathVariable("pricePlan") String pricePlan) {
        return ResponseEntity.ok(userService.extendService(id, pricePlan));
    }

    @PutMapping("/uploadContract")
    public ResponseEntity<UserDto> uploadContract(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") String id
    ) throws IOException {
        if (file == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userService.uploadContract(file, id));
    }
}
