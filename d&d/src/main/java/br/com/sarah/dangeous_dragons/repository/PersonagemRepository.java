package br.com.sarah.dangeous_dragons.repository;

import br.com.sarah.dangeous_dragons.entity.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
}

