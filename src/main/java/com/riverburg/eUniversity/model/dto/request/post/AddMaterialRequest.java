package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class AddMaterialRequest {

    private String description;

    private Integer disciplineId;

    private Integer sectorId;

    private MultipartFile file;
}
