package com.badminton.backend.dto.midtrans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetail {
    private String id;
    private Long price;
    private Integer quantity;
    private String name;
}
