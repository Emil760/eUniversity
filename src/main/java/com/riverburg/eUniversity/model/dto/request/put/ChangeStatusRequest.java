package com.riverburg.eUniversity.model.dto.request.put;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ChangeStatusRequest {

    private UUID accountId;

    private boolean status;
}
