package com.bankInk.credits.Service;


import com.bankInk.credits.Model.Entity.Tarjeta;
import com.bankInk.credits.Model.Request.TarjetaRequest;
import com.bankInk.credits.Model.Response.BalanceResponse;
import com.bankInk.credits.Model.Response.CardNumberResponse;
import com.bankInk.credits.Model.Response.GenericResponse;

public interface tarjetasService {

    public CardNumberResponse genarateCardNumber(int ProductID);


    public GenericResponse ActivateCard(String cardNumber);

    public GenericResponse blockCard(String cardNumber);


    public GenericResponse AddBalance(TarjetaRequest card);

    public BalanceResponse getBalance(String cardId);


    public Tarjeta ValidarTarjeta(String Number);

}
