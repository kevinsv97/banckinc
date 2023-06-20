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
@Table(name = "Anulaciones")
public class Anulacion {
    @Id
    @Column(name = "id_anulacion")
    private int idAnulacion;

    @Column(name = "id_transaccion")
    private int idTransaccion;

    @Column(name = "fecha_anulacion")
    private LocalDateTime fechaAnulacion;

}