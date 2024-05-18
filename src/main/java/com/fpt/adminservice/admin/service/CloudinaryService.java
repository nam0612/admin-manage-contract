package com.fpt.adminservice.admin.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

  public String uploadFile(MultipartFile multipartFile) throws IOException;

}
