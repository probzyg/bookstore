package com.example.bookstore.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdatePriceRequest {
    private String title;
    private BigDecimal price;

    public UpdatePriceRequest() {
    }
}
