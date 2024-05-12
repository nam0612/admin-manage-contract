package com.fpt.adminservice.auth.service;


import com.fpt.adminservice.auth.dto.UserCreateRequest;
import com.fpt.adminservice.auth.dto.UserDto;
import com.fpt.adminservice.auth.model.User;
import com.fpt.adminservice.auth.model.UserStatus;
import com.fpt.adminservice.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String delete(String id) {
        var user = userRepository.findById(id).orElseThrow();

        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        return "Successfully";
    }

    public UserDto createUser(UserCreateRequest userCreateRequest)
    {
        User user = User.builder()
                .taxCode(userCreateRequest.getTaxCode())
                .companyName(userCreateRequest.getCompanyName())
                .presenter(userCreateRequest.getPresenter())
                .email(userCreateRequest.getEmail())
                .phone(userCreateRequest.getPhone())
                .status(UserStatus.PROCESSING)
                .createdDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return UserDto.builder()
                .companyName(user.getCompanyName())
                .taxCode(user.getTaxCode())
                .build();
    }

    public Page<UserDto> getUsers(Pageable pageable, UserStatus userStatus) {
        var user = userRepository.findByStatus(userStatus, pageable);
        if(user.isEmpty()) {
            return Page.empty();
        }
        List<User> userList = user.getContent();
        List<UserDto> userDtos = userList.stream().map(item -> UserDto.builder()
                .companyName(item.getCompanyName())
                .taxCode(item.getTaxCode())
                .presenter(item.getPresenter())
                .status(item.getStatus())
                .phone(item.getPhone())
                .email(item.getEmail())
                .build() ).toList();
        return new PageImpl<>(userDtos, pageable, userDtos.size());
    }

    public UserDto approve(String id) {
        var user = userRepository.findById(id).orElseThrow();
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        return UserDto.builder()
                .presenter(user.getPresenter())
                .companyName(user.getCompanyName())
                .taxCode(user.getTaxCode())
                .status(user.getStatus())
                .build();
    }


}
