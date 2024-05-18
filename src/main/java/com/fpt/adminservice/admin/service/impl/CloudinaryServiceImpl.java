package com.fpt.adminservice.admin.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fpt.adminservice.admin.service.CloudinaryService;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

  private final Cloudinary cloudinary;

  @Override
  public String uploadFile(MultipartFile file) throws IOException {
    Map options = ObjectUtils.asMap("public_id", "G63_" + UUID.randomUUID());
    Map result = cloudinary.uploader().upload(file.getBytes(), options);
    return (String) result.get("url");
  }
}
