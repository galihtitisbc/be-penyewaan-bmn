package com.badminton.backend.dto.midtrans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MidtransRequest {
    @NotNull(message = "transaction details tidak boleh kosong")
    @JsonProperty("transaction_details")
    private TransactionDetails transactionDetails;

    @JsonProperty("credit_card")
    private CreditCard creditCard;

    @JsonProperty("item_details")
    private List<ItemDetail> itemDetails;

    @JsonProperty("customer_details")
    private CustomerDetails customerDetails;
}
