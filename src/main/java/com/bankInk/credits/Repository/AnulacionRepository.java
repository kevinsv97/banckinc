package com.bankInk.credits.Repository;

import com.bankInk.credits.Model.Entity.Anulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnulacionRepository extends JpaRepository<Anulacion, Integer > {

}
