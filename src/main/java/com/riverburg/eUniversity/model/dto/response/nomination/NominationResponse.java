package com.riverburg.eUniversity.model.dto.response.nomination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NominationResponse {

    private int id;

    private String name;

    private boolean isActive;
}
