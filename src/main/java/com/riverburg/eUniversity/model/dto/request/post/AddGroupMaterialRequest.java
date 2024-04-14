package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class AddGroupMaterialRequest {

    private Integer groupId;

    private Integer facultyDisciplineId;

    private Integer eduProcessId;

    private String description;

    private MultipartFile file;
}
