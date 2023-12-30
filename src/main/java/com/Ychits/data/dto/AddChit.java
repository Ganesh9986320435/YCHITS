package com.Ychits.data.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
@Data
public class AddChit {
    private String userId;
    private String name;
    private String amount;
    private LocalDate startMonth;
    private LocalDate endMonth;
    private long durationInMonths;
    private String maintainerCharge;
}
