package com.riverburg.eUniversity.model.dto.request.put;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
public class UpdatePaidFaculty {

   private int id;

   @Min(value = 0, message = "Min value is 0")
   private short count;
}
