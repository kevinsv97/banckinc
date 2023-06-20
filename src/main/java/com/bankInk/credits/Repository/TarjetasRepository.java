package com.bankInk.credits.Repository;

import com.bankInk.credits.Model.Entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetasRepository  extends JpaRepository<Tarjeta, Integer > {
    boolean existsByNumeroTarjeta(String Numero);

    boolean existsByNumeroTarjetaAndActiva(String Numero,Boolean Activa);

    boolean existsByNumeroTarjetaAndBloqueada(String Numero,Boolean Bloqueada);

    Tarjeta findByNumeroTarjeta(String Number);
}
