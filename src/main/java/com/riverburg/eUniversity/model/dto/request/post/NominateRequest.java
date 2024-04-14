package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NominateRequest {

    private UUID teacherId;

    private Integer nominationId;
}
