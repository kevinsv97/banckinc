package com.bankInk.credits.Controller;

import com.bankInk.credits.Model.Request.TarjetaRequest;
import com.bankInk.credits.Model.Response.BalanceResponse;
import com.bankInk.credits.Model.Response.CardNumberResponse;
import com.bankInk.credits.Model.Response.GenericResponse;
import com.bankInk.credits.Service.tarjetasService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/card")
public class tarjetasController {

    private final tarjetasService tarjetasService;

    public tarjetasController(tarjetasService tarjetasService) {
        this.tarjetasService = tarjetasService;
    }


    @GetMapping(value = "/{productId}/number")
    public CardNumberResponse generarNumeroTarjeta(@PathVariable("productId")int productId) {
        return tarjetasService.genarateCardNumber(productId);
    }

    @PostMapping(value = "/enroll")
    public GenericResponse ActivarTarjeta(@RequestBody(required = true ) TarjetaRequest cardId) {
        return tarjetasService.ActivateCard(cardId.getCardId());
    }

    @DeleteMapping (value = "/{cardId}")
    public GenericResponse BloquearTarjeta(@PathVariable("cardId") String cardId) {
        return tarjetasService.blockCard(cardId);
    }

    @PostMapping(value = "/balance")
    public GenericResponse AddBalance(@RequestBody(required = true ) TarjetaRequest cardId) {
        return tarjetasService.AddBalance(cardId);
    }

    @GetMapping (value = "/balance/{cardId}")
    public BalanceResponse AddBalanceConsult(@PathVariable("cardId") String cardId) {
        return tarjetasService.getBalance(cardId);
    }
}
