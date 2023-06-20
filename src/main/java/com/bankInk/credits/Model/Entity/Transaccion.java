package com.bankInk.credits.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Transacciones")
public class Transaccion {
    @Id
    @Column(name = "id_transaccion")
    private int idTransaccion;

    @Column(name = "id_tarjeta")
    private int idTarjeta;

    @Column(name = "fecha_transaccion")
    private LocalDateTime fechaTransaccion;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "anulado")
    private Boolean anulado;

    @Column(name = "Estado")
    private String estado;

}