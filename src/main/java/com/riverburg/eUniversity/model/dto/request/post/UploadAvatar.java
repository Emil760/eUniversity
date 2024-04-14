package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@ToString
public class UploadAvatar {

    private UUID id;

    private MultipartFile multipartFile;
}
