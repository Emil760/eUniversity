package com.riverburg.eUniversity.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ThemeResponse {

    private int id;

    private String eduProcessName;

    private String name;

    private String description;

    private Short order;

    private Date from;

    private Date to;

    private int fileId;

    private String fileName;
}
