package com.fpt.adminservice.admin.controller;

import com.fpt.adminservice.admin.dto.VerifyEmailCodeRequest;
import com.fpt.adminservice.admin.service.UserService;
import com.fpt.adminservice.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public/getContract")
@RequiredArgsConstructor
public class GetContractController {

    private final UserService userService;

    @GetMapping("/{email}")
    public BaseResponse getAuthenCodeByEmail(@PathVariable String email) {
        return userService.AuthenticationMailWithCode(email);
    }

    @PostMapping("/get-contract")
    public BaseResponse getContract(@RequestBody VerifyEmailCodeRequest verifyEmailCodeRequest) {
        return userService.GetCompanyContract(verifyEmailCodeRequest.getEmail(), verifyEmailCodeRequest.getCode());
    }
}
