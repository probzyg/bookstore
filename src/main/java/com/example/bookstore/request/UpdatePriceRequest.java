package com.example.bookstore.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePriceRequest {
    private String title;
    private String price;

    public UpdatePriceRequest() {
    }
}
