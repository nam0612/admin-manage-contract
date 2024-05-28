package com.fpt.adminservice.admin.service;


import com.fpt.adminservice.admin.repository.PricePlanRepository;
import com.fpt.adminservice.admin.dto.UserCreateRequest;
import com.fpt.adminservice.admin.dto.UserDto;
import com.fpt.adminservice.admin.dto.UserInterface;
import com.fpt.adminservice.auth.model.User;
import com.fpt.adminservice.enums.UserStatus;
import com.fpt.adminservice.auth.repository.UserRepository;
import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.utils.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PricePlanRepository pricePlanRepository;
    private final CloudinaryService cloudinaryService;

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

    public Page<UserInterface> getUsers(Pageable pageable, String userStatus, String name, LocalDateTime fromDate, LocalDateTime toDate) {
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



}
