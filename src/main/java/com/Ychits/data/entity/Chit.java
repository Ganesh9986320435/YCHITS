package com.Ychits.data.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
@Data
public class Chit {
    private String id;
    private String name;
    private String amount;
    private LocalDate startData;
    private LocalDate endDate;
    private String maintainerCharge;
}
