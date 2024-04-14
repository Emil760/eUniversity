package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;

@Getter
public class SendStudentGradeRequest {

    private int studentWorkId;

    private String feedback;

    private short mark;
}
