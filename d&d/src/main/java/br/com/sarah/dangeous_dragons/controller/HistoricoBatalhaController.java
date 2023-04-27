package br.com.sarah.dangeous_dragons.controller;

import br.com.sarah.dangeous_dragons.entity.Batalha;
import br.com.sarah.dangeous_dragons.exception.ResourceNotFoundException;
import br.com.sarah.dangeous_dragons.repository.BatalhaRepository;
import br.com.sarah.dangeous_dragons.repository.TurnoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("historico_batalha")
public class HistoricoBatalhaController {

    private final BatalhaRepository batalhaRepository;
    private final TurnoRepository turnoRepository;

    @GetMapping
    public ResponseEntity buscarBatalhas() {
        return ResponseEntity.status(HttpStatus.OK).body(turnoRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity buscarBatalhaPorID(@PathVariable("id") Long idBatalha) {
        Batalha batalha = batalhaRepository.findById(idBatalha).orElseThrow(
                () -> new ResourceNotFoundException("Batalha nao encontrada")
        );
        return ResponseEntity.status(HttpStatus.OK).body(turnoRepository.findByBatalha(batalha));
    }
}
