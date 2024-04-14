package com.riverburg.eUniversity.model.dto.response.student;

import com.riverburg.eUniversity.model.dto.response.base.BaseUserResponse;
import lombok.*;
import org.springframework.lang.Nullable;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudentResponse extends BaseUserResponse {

    private int id;

    @Nullable
    private int groupId;

    private String groupName;

    private short ball;

    @Nullable
    private boolean isPaid;
}