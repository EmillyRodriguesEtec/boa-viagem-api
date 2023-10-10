package br.com.etechoracio.boa_viagem.repository;

import br.com.etechoracio.boa_viagem.entity.Gasto;
import br.com.etechoracio.boa_viagem.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByViagem(Viagem viagem);
    Boolean existsByViagem(Viagem viagem);

}
