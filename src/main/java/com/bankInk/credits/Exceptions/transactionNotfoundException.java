package com.bankInk.credits.Exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class transactionNotfoundException extends RuntimeException{
    private String Mensaje;
}
