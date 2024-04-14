package com.riverburg.eUniversity.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoomResponse {

    int id;

    String number;

    short deskCount;

    String eduProcessName;

    int eduProcessId;

    String description;

    boolean isActive;
}
