package com.Ychits.data.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
@Data
public class AddChit {
    private String name;
    private String amount;
    private LocalDate startData;
    private LocalDate endDate;
    private String maintainerCharge;
}
