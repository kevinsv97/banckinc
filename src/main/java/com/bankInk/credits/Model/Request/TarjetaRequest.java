package com.bankInk.credits.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TarjetaRequest {

    private String cardId;
    private BigDecimal balance;
    private BigDecimal price;
    private Integer transactionId;
}
