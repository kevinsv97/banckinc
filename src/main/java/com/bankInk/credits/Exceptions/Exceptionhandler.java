package com.bankInk.credits.Exceptions;

import com.bankInk.credits.Model.Response.ErrorResponses;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class Exceptionhandler {

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody // que la respuesta va a ser personalizada.
    @ExceptionHandler(productException.class)
    public ErrorResponses handleBusinessException(productException exception) {
        return new ErrorResponses("412",exception.getMensaje());
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody // que la respuesta va a ser personalizada.
    @ExceptionHandler(cardAlreadyException.class)
    public ErrorResponses cardAlreadyException(cardAlreadyException exception) {
        return new ErrorResponses("412",exception.getMensaje());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody // que la respuesta va a ser personalizada.
    @ExceptionHandler(cardNotFoundException.class)
    public ErrorResponses cardNotFoundException(cardNotFoundException exception) {
        return new ErrorResponses("404",exception.getMensaje());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody // que la respuesta va a ser personalizada.
    @ExceptionHandler(transactionNotfoundException.class)
    public ErrorResponses transactionNotfoundException(transactionNotfoundException exception) {
        return new ErrorResponses("404",exception.getMensaje());
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody // que la respuesta va a ser personalizada.
    @ExceptionHandler(noBalanceException.class)
    public ErrorResponses noBalanceException(noBalanceException exception) {
        return new ErrorResponses("412",exception.getMensaje());
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody // que la respuesta va a ser personalizada.
    @ExceptionHandler(transactionExpiredException.class)
    public ErrorResponses noBalanceException(transactionExpiredException exception) {
        return new ErrorResponses("412",exception.getMensaje());
    }


}
