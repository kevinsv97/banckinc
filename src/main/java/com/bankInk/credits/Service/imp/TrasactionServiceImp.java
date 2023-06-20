package com.bankInk.credits.Service.imp;

import com.bankInk.credits.Exceptions.noBalanceException;
import com.bankInk.credits.Exceptions.transactionExpiredException;
import com.bankInk.credits.Exceptions.transactionNotfoundException;
import com.bankInk.credits.Model.Entity.Anulacion;
import com.bankInk.credits.Model.Entity.Tarjeta;
import com.bankInk.credits.Model.Entity.Transaccion;
import com.bankInk.credits.Model.Request.TarjetaRequest;
import com.bankInk.credits.Model.Response.GenericResponse;
import com.bankInk.credits.Repository.AnulacionRepository;
import com.bankInk.credits.Repository.TarjetasRepository;
import com.bankInk.credits.Repository.TrasactionRepository;
import com.bankInk.credits.Service.TrasactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class TrasactionServiceImp implements TrasactionService {
    private final TarjetasRepository tarjetasRepository;
    private final TrasactionRepository trasactionRepository;

    private final com.bankInk.credits.Service.tarjetasService tarjetasService;

    private final AnulacionRepository anulacionRepository;

    public TrasactionServiceImp(TarjetasRepository tarjetasRepository, TrasactionRepository trasactionRepository, com.bankInk.credits.Service.tarjetasService tarjetasService, AnulacionRepository anulacionRepository) {
        this.tarjetasRepository = tarjetasRepository;
        this.trasactionRepository = trasactionRepository;
        this.tarjetasService = tarjetasService;
        this.anulacionRepository = anulacionRepository;
    }

    @Override
    public GenericResponse CreateTransaction(TarjetaRequest tarjetar) {
        GenericResponse response = new GenericResponse();
        Tarjeta tarjeta = tarjetasService.ValidarTarjeta(tarjetar.getCardId());
        int saldo = tarjeta.getSaldo().compareTo(tarjetar.getPrice());
        Transaccion transaccion = new Transaccion();
        transaccion.setFechaTransaccion(LocalDateTime.now());
        transaccion.setIdTarjeta(tarjeta.getIdTarjeta());
        transaccion.setMonto(tarjetar.getPrice());
        transaccion.setAnulado(false);

        if (saldo > 0) {
            tarjeta.setSaldo(tarjeta.getSaldo().subtract(tarjetar.getPrice()).max(BigDecimal.ZERO));
            transaccion.setEstado("REALIZADA");
            response.setMensaje("Transaccion realizada");
        }else{
            transaccion.setEstado("RECHAZADA");
            trasactionRepository.save(transaccion);
            throw new noBalanceException("No hay saldo suficiente para realizar la transaccion,transaccion rechazada");
        }
        trasactionRepository.save(transaccion);
        tarjetasRepository.save(tarjeta);
        return response;
    }

    public Transaccion getTransaction(int Idtransaccion){return validarTransaction(Idtransaccion);}


    public GenericResponse AnularTransaction(TarjetaRequest card){
        GenericResponse response = new GenericResponse();
        Transaccion transaccion = validarTransaction(card.getTransactionId());
        //validar validaes de transaccion
        LocalDateTime fechaTransaccion = transaccion.getFechaTransaccion();
        long horasPasadas = fechaTransaccion.until(LocalDateTime.now(), ChronoUnit.HOURS);
        if (horasPasadas > 24){
            throw new transactionExpiredException("La traccacion ocurrio hace mas de 24 Horas, fallo al anular");
        }else{
            transaccion.setEstado("ANULADO");
            transaccion.setAnulado(true);

            Tarjeta targeta = tarjetasService.ValidarTarjeta(card.getCardId());
            targeta.setSaldo(targeta.getSaldo().add(transaccion.getMonto()));
            Anulacion anulacion = new Anulacion();
            anulacion.setFechaAnulacion(LocalDateTime.now());
            anulacion.setIdTransaccion(card.getTransactionId());

            anulacionRepository.save(anulacion);
            trasactionRepository.save(transaccion);
            tarjetasRepository.save(targeta);
            response.setMensaje("transaccion Anulada existosamente");
        }
    return response;
    }

    private Transaccion validarTransaction(int transaccionID){

        Optional<Transaccion> OptTransaction = trasactionRepository.findById(transaccionID);
        Transaccion transaccion = null;
        if (OptTransaction.isPresent()) {
            transaccion = OptTransaction.get();
        } else {
            throw new transactionNotfoundException("transaccion no existe");
        }
      return transaccion;
    }
}
