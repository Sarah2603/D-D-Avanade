package br.com.sarah.dangeous_dragons.repository;

import br.com.sarah.dangeous_dragons.entity.Batalha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatalhaRepository extends JpaRepository<Batalha, Long> {
}
