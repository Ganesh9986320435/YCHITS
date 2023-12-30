package com.Ychits.data.entity;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class Chit extends BaseEntity{
    private String id;
    private String name;
    private String amount;
    private LocalDate startMonth;
    private LocalDate endMonth;
    private long durationInMonths;
    private String maintainerCharge;
    private List<String> userId;  //list of users with auction completed or not
    private Map<String,Auction> auction;
}
