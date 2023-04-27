package br.com.sarah.dangeous_dragons.repository;

import br.com.sarah.dangeous_dragons.entity.Batalha;
import br.com.sarah.dangeous_dragons.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    Optional<Turno> findFirstByBatalhaOrderByIdDesc(Batalha batalha);
    List<Turno> findByBatalha(Batalha batalha);
}

