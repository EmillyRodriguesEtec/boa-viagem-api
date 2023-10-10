package br.com.etechoracio.boa_viagem.repository;

import br.com.etechoracio.boa_viagem.entity.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.etechoracio.boa_viagem.entity.Viagem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    List<Viagem> findByDestinoIgnoreCase(String destino);

    @Query("select * from Gasto g where g.viagem.id = :idViagem")
    List<Gasto> findByGasto(Long idViagem);

}
