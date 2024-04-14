package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class AddThemeFileRequest {

    private int themeId;

    private MultipartFile file;
}
