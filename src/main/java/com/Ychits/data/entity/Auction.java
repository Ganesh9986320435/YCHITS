package com.Ychits.data.entity;

import lombok.Data;

@Data
public class Auction {
    private long amount;
    private String userId;
    private String userName;
    private long monthNumber;
    private String monthName;
    private long reducedAmount;   // amount - charge/no of current chit payers
    private long amountPayable;   // chit amount - reduced amount
}
