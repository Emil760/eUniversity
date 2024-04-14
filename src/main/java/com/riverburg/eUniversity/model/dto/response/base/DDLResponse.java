package com.riverburg.eUniversity.model.dto.response.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DDLResponse<TId> {

    public TId id;

    public String name;

}
