package com.fpt.adminservice.admin.controller;

import com.fpt.adminservice.admin.dto.UserDto;
import com.fpt.adminservice.admin.dto.UserInterface;
import com.fpt.adminservice.admin.dto.VerifyEmailCodeRequest;
import com.fpt.adminservice.admin.service.UserService;
import com.fpt.adminservice.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/manager/company")
@RequiredArgsConstructor
public class ManagerCompanyController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Page<UserInterface>> getAllUsers(
            @RequestParam(name = "status", required = false) String status,
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.delete(id));
    }


    @PutMapping("/uploadContract")
    public ResponseEntity<UserDto> uploadContract(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") String id
    ){
        if (file == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(userService.uploadContract(convertToFile(file), id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File convertToFile(MultipartFile multipartFile) throws IOException {
        Path tempDir = Files.createTempDirectory("upload-");
        Path tempFile = tempDir.resolve(multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);
        return tempFile.toFile();
    }


}
