package com.riverburg.eUniversity.model.dto.response.user;

import com.riverburg.eUniversity.model.entity.StudentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class StudentInfoResponse {

    private String fullName;

    private Integer age;

    private String group;

    public static StudentInfoResponse toDTO(StudentEntity studentEntity) {
        return StudentInfoResponse
                .builder()
                .fullName(studentEntity.getAccountEntity().getFullName())
                .group(studentEntity.getGroupEntity() == null ? "NON_GROUP" : studentEntity.getGroupEntity().getName())
                .age(studentEntity.getAccountEntity().getAge())
                .build();
    }

}
