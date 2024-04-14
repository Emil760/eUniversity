package com.riverburg.eUniversity.model.dto.request.put;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UpdateRoomRequest {
    private int id;

    @NotBlank(message = "Number can`t be empty")
    private String number;

    private int eduProcessId;

    private short deskCount;

    private String description;
}
