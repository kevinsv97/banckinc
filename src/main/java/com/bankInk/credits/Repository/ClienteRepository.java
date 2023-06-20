package com.bankInk.credits.Repository;

import com.bankInk.credits.Model.Entity.Cliente;
import com.bankInk.credits.Model.Entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer > {

    public Cliente findClienteByIdCliente(int idCliente);
}
