package com.fpt.adminservice.admin.service;


import com.fpt.adminservice.admin.model.MailAuthedCode;
import com.fpt.adminservice.admin.repository.MailAuthenCodeRepository;
import com.fpt.adminservice.admin.repository.PricePlanRepository;
import com.fpt.adminservice.admin.dto.UserCreateRequest;
import com.fpt.adminservice.admin.dto.UserDto;
import com.fpt.adminservice.admin.dto.UserInterface;
import com.fpt.adminservice.auth.model.User;
import com.fpt.adminservice.config.MailService;
import com.fpt.adminservice.enums.UserStatus;
import com.fpt.adminservice.auth.repository.UserRepository;
import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.utils.Constants;
import com.fpt.adminservice.utils.QueryUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PricePlanRepository pricePlanRepository;
    private final CloudinaryService cloudinaryService;
    private final MailService mailService;
    private final MailAuthenCodeRepository mailAuthenCodeRepository;

    public String delete(String id) {
        var user = userRepository.findById(id).orElseThrow();

        user.setStatus(UserStatus.LOCKED);
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);
        return "Successfully";
    }

    public UserDto createUser(UserCreateRequest userCreateRequest)
    {
        var planPrice = pricePlanRepository.findById(userCreateRequest.getPlanpriceId()).orElseThrow();
        User user = User.builder()
                .taxCode(userCreateRequest.getTaxCode())
                .companyName(userCreateRequest.getCompanyName())
                .presenter(userCreateRequest.getPresenter())
                .email(userCreateRequest.getEmail())
                .phone(userCreateRequest.getPhone())
                .status(UserStatus.PROCESSING)
                .price(planPrice.getPrice())
                .pricePlan(userCreateRequest.getPlanpriceId())
                .createdDate(LocalDateTime.now())
                .registerDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return UserDto.builder()
                .companyName(user.getCompanyName())
                .taxCode(user.getTaxCode())
                .build();
    }

    public Page<UserInterface> nkmgetUsers(Pageable pageable, String userStatus, String name, LocalDateTime fromDate, LocalDateTime toDate) {
        return userRepository.search(QueryUtils.appendPercent(name), QueryUtils.appendPercent(userStatus), fromDate, toDate, pageable);
//        if(user.isEmpty()) {
//            return Page.empty();
//        }
//        List<UserDto> userDtos = user.stream().map(item -> UserDto.builder()
//                .id(item.getId())
//                .companyName(item.getCompanyName())
//                .taxCode(item.getTaxCode())
//                .status(item.getStatus())
//                .price(item.getPrice())
//                .createdDate(item.getCreatedDate())
//                .endDateUseService(item.getEndDateUseService())
//                .registerDate(item.getRegisterDate())
//                .pricePlanId(item.getPlanId())
//                .updatedDate(item.getUpdatedDate())
//                .pricePlanName(item.getPlanName())
//                .startDateUseService(item.getStartDateUseService())
//                .build() ).toList();
//        return new PageImpl<>(userDtos, pageable, userDtos.size());
    }

    public UserDto approve(String id) {
        var user = userRepository.findById(id).orElseThrow();
        var planPrice = pricePlanRepository.findById(user.getPricePlan()).orElseThrow();
        user.setStatus(UserStatus.INUSE);
        user.setUpdatedDate(LocalDateTime.now());
        LocalDateTime startDate = LocalDateTime.now();
        user.setStartDateUseService(startDate);
        user.setEndDateUseService(startDate.plusYears(planPrice.getTimeWithYears()));
        user.setPrice(planPrice.getPrice());
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);
        return UserDto.builder()
                .presenter(user.getPresenter())
                .companyName(user.getCompanyName())
                .taxCode(user.getTaxCode())
                .status(user.getStatus())
                .startDateUseService(startDate)
                .endDateUseService(user.getEndDateUseService())
                .build();
    }

    public UserDto extendService(String id, String pricePlanId) {
        var user = userRepository.findById(id).orElseThrow();
        var pricePlan = pricePlanRepository.findById(pricePlanId).orElseThrow();

        user.setPrice(user.getPrice() + pricePlan.getPrice());
        if (user.getEndDateUseService() == null) {
            user.setEndDateUseService(LocalDateTime.now());
        }
        if(user.getStatus() == UserStatus.LOCKED || user.getStatus() == UserStatus.PROCESSING) {
            user.setStatus(UserStatus.INUSE);
        }
        int year
        = pricePlan.getTimeWithYears();
        user.setEndDateUseService(user.getEndDateUseService().plusYears(year));
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);

        return UserDto.builder()
                .pricePlanId(pricePlanId)
                .companyName(user.getCompanyName())
                .taxCode(user.getTaxCode())
                .status(user.getStatus())
                .id(user.getId())
                .endDateUseService(user.getEndDateUseService())
                .price(user.getPrice())
                .build();
    }

    public UserDto uploadContract(File file, String id) throws IOException {
        var user = userRepository.findById(id).orElseThrow();
        String fileLink = cloudinaryService.uploadPdf(file);
        user.setFile(fileLink);
        userRepository.save(user);
        return UserDto.builder()
                .pricePlanId(user.getPricePlan())
                .companyName(user.getCompanyName())
                .taxCode(user.getTaxCode())
                .status(user.getStatus())
                .id(user.getId())
                .endDateUseService(user.getEndDateUseService())
                .price(user.getPrice())
                .build();

    }

    public BaseResponse AuthenticationMailWithCode(String email) {
        var user = userRepository.findByEmail(email);
        String[] emailList = new String[]{email};
        if (user.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "user not found", true, null);
        }
        int code = new Random().nextInt(999999);
        var mailCode = mailAuthenCodeRepository.findByEmail(email);
        if (mailCode.isEmpty()) {
            LocalDateTime startTime = LocalDateTime.now();
            MailAuthedCode mailAuthedCode = MailAuthedCode.builder()
                    .email(email)
                    .code(code)
                    .expiryTime(startTime.plusMinutes(5))
                    .startTime(startTime)
                    .build();
            mailAuthenCodeRepository.save(mailAuthedCode);
        } else {
            mailCode.get().setCode(code);
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime expiryTime = startTime.plusMinutes(5);
            mailCode.get().setStartTime(startTime);
            mailCode.get().setExpiryTime(expiryTime);
            mailAuthenCodeRepository.save(mailCode.get());
        }

        try {
            mailService.sendNewMail(emailList, null, "OTP CODE", "<h1>" + code + "</h1>", null);
        } catch (MessagingException e) {
            return new BaseResponse(Constants.ResponseCode.FAILURE, e.getMessage(), false, null);
        }
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "found user", true, null);
    }

    public BaseResponse GetCompanyContract(String email, Integer AuthenCode) {
        var company = userRepository.findByEmail(email);
        var mailAuthedCode = mailAuthenCodeRepository.findByEmail(email);

        if(mailAuthedCode.isEmpty() || company.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.FAILURE, "User not exist", true, null);
        }

        if(!Objects.equals(mailAuthedCode.get().getCode(), AuthenCode)) {
            return new BaseResponse(Constants.ResponseCode.FAILURE, "Your code is invalid", true, null);
        }
        if((mailAuthedCode.get().getExpiryTime().isBefore(LocalDateTime.now()))) {
            return new BaseResponse(Constants.ResponseCode.FAILURE, "Your code is expired", true, null);
        }
        User user = company.get();
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "found user", true, UserDto.builder()
                .id(user.getId())
                .taxCode(user.getTaxCode())
                .companyName(user.getCompanyName())
                .presenter(user.getPresenter())
                .email(user.getEmail())
                .phone(user.getPhone())
                .status(user.getStatus())
                .price(user.getPrice())
                .pricePlanId(user.getPricePlan())
                .createdDate(user.getCreatedDate())
                .registerDate(user.getRegisterDate())
                .file(user.getFile())
                .build());
    }


}
