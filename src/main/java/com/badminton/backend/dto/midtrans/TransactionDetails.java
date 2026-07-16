package com.badminton.backend.dto.midtrans;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
    @JsonAlias("order_id")
    private String orderId;

    @JsonAlias("gross_amount")
    private Long grossAmount;

}
