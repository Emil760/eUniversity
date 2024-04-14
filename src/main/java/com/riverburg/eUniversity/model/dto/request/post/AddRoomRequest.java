package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class AddRoomRequest {

    @NotBlank(message = "Number can`t be empty")
    String number;

    int eduProcessId;

    short deskCount;

    String description;
}
