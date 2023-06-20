package com.bankInk.credits.Controller;

import com.bankInk.credits.Model.Entity.Transaccion;
import com.bankInk.credits.Model.Request.TarjetaRequest;
import com.bankInk.credits.Model.Response.GenericResponse;
import com.bankInk.credits.Service.TrasactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/transaction")
public class transactionController {

    private final TrasactionService trasactionService;

    public transactionController(TrasactionService trasactionService) {
        this.trasactionService = trasactionService;
    }

    @PostMapping(value = "/purchase")
    public GenericResponse createTransaction(@RequestBody(required = true) TarjetaRequest cardId) {
        return trasactionService.CreateTransaction(cardId);
    }

    @GetMapping(value = "/{transactionId}")
    public Transaccion getTransaction(@PathVariable("transactionId") int cardId) {
        return trasactionService.getTransaction(cardId);
    }

    @PostMapping(value = "/anulation")
    public GenericResponse AnulacionTransaction(@RequestBody(required = true) TarjetaRequest cardId) {
        return trasactionService.AnularTransaction(cardId);
    }


}
