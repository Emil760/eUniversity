package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AddStudentHomeworkRequest {

    private int themeId;

    private MultipartFile file;
}
