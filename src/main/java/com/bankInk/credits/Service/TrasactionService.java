package com.bankInk.credits.Service;


import com.bankInk.credits.Model.Entity.Transaccion;
import com.bankInk.credits.Model.Request.TarjetaRequest;
import com.bankInk.credits.Model.Response.BalanceResponse;
import com.bankInk.credits.Model.Response.CardNumberResponse;
import com.bankInk.credits.Model.Response.GenericResponse;

public interface TrasactionService {

public GenericResponse CreateTransaction(TarjetaRequest Tarjeta);

public Transaccion getTransaction(int Idtransaccion);

public GenericResponse AnularTransaction(TarjetaRequest card);

}
