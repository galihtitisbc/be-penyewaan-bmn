package com.badminton.backend.dto.midtrans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ItemDetail {
    private String id;
    private Long price;
    private Integer quantity;
    private String name;
}
