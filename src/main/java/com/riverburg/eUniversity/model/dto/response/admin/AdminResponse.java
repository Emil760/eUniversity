package com.riverburg.eUniversity.model.dto.response.admin;

import com.riverburg.eUniversity.model.dto.response.base.BaseUserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminResponse extends BaseUserResponse {

    private int id;
}
