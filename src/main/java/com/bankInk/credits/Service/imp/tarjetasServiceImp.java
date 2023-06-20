package com.bankInk.credits.Service.imp;

import com.bankInk.credits.Exceptions.cardAlreadyException;
import com.bankInk.credits.Exceptions.cardNotFoundException;
import com.bankInk.credits.Exceptions.productException;
import com.bankInk.credits.Model.Entity.Cliente;
import com.bankInk.credits.Model.Entity.Tarjeta;
import com.bankInk.credits.Model.Request.TarjetaRequest;
import com.bankInk.credits.Model.Response.BalanceResponse;
import com.bankInk.credits.Model.Response.CardNumberResponse;
import com.bankInk.credits.Model.Response.GenericResponse;
import com.bankInk.credits.Repository.ClienteRepository;
import com.bankInk.credits.Repository.TarjetasRepository;
import com.bankInk.credits.Service.tarjetasService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Service
public class tarjetasServiceImp implements tarjetasService {
    private final TarjetasRepository tarjetasRepository;
    private final ClienteRepository clienteRepository;

    public tarjetasServiceImp(TarjetasRepository tarjetasRepository, ClienteRepository clienteRepository) {
        this.tarjetasRepository = tarjetasRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public CardNumberResponse genarateCardNumber(int ProductID) {

        String NumberCard;
        if (!(String.valueOf(ProductID).length() == 6)){
            throw new productException("EL numero de producto Ingresado no es Valido");
        }
        do {
             NumberCard = NumeroAleatorio(ProductID);
        }while(tarjetasRepository.existsByNumeroTarjeta(NumberCard));
        Tarjeta nuevaTarjeta = new Tarjeta();
        Cliente cliente = clienteRepository.findClienteByIdCliente(1);
        LocalDate fechaVencimiento = LocalDate.now().plusYears(3);
        nuevaTarjeta.setNumeroTarjeta(NumberCard);
        nuevaTarjeta.setNombreTitular(cliente.getPrimerNombre()+" "+cliente.getApellido());
        nuevaTarjeta.setActiva(false);
        nuevaTarjeta.setSaldo(new BigDecimal(0));
        nuevaTarjeta.setBloqueada(false);
        nuevaTarjeta.setFechaVencimiento(fechaVencimiento);
        tarjetasRepository.save(nuevaTarjeta);

        return new CardNumberResponse(NumberCard,"Numero de Tarjeta Generado Exitosamente");
    }

    public GenericResponse ActivateCard(String cardNumber){
        GenericResponse response = new GenericResponse();
        ValidarTarjeta(cardNumber);
        if (tarjetasRepository.existsByNumeroTarjetaAndActiva(cardNumber, true)){
            throw new cardAlreadyException("tarjeta ya se encuentra activa");
        }else {
            Tarjeta tarjeta = tarjetasRepository.findByNumeroTarjeta(cardNumber);
            tarjeta.setActiva(true);
            tarjetasRepository.save(tarjeta);
            response.setMensaje("tarjeta Activada");
        }
        return response;
    }


    public GenericResponse blockCard(String cardNumber){
        GenericResponse response = new GenericResponse();
        ValidarTarjeta(cardNumber);
        if (tarjetasRepository.existsByNumeroTarjetaAndBloqueada(cardNumber, true)){
            throw new cardAlreadyException("tarjeta ya se encuentra Bloqueada");
        }else {
            Tarjeta tarjeta = tarjetasRepository.findByNumeroTarjeta(cardNumber);
            tarjeta.setBloqueada(true);
            tarjetasRepository.save(tarjeta);
            response.setMensaje("tarjeta Bloqueada");
        }
        return response;
    }

    public GenericResponse AddBalance(TarjetaRequest card){
        GenericResponse response = new GenericResponse();

            Tarjeta tarjeta = ValidarTarjeta(card.getCardId());
            tarjeta.setSaldo(tarjeta.getSaldo().add(card.getBalance()));
            tarjetasRepository.save(tarjeta);
            response.setMensaje("se ha recargado el valor de " + card.getBalance() + " dolares");
            return response;
    }


    public BalanceResponse getBalance(String cardId){
        BalanceResponse response = new BalanceResponse();
        Tarjeta tarjeta = ValidarTarjeta(cardId);
        response.setBalance(tarjeta.getSaldo());
        return response;
    }

    private String NumeroAleatorio(int ProductID){
        return ProductID + generarNumeroAleatorio(10);
    }


    private static String generarNumeroAleatorio(int cantidadDigitos) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(cantidadDigitos);

        for (int i = 0; i < cantidadDigitos; i++) {
            int digito = random.nextInt(10); // Generar un dígito aleatorio del 0 al 9
            sb.append(digito);
        }

        return sb.toString();
    }


    public Tarjeta ValidarTarjeta(String Number) {
        Tarjeta tarjeta = tarjetasRepository.findByNumeroTarjeta(Number);
        if (tarjeta == null) {
            throw new cardNotFoundException("numero de tarjeta no existe");
        }
        return tarjeta;
    }
}
