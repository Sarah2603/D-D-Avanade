package br.com.sarah.dangeous_dragons.repository;

import br.com.sarah.dangeous_dragons.entity.ClassePersonagem;
import br.com.sarah.dangeous_dragons.enums.ClassePersonagemEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassePersonagemRepository extends JpaRepository<ClassePersonagem, Long> {
    Optional<ClassePersonagem> findByClasse(ClassePersonagemEnum classePersonagemEnum);
}
