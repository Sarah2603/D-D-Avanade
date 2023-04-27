package br.com.sarah.dangeous_dragons.controller;

import br.com.sarah.dangeous_dragons.dto.PersonagemRequest;
import br.com.sarah.dangeous_dragons.entity.Personagem;
import br.com.sarah.dangeous_dragons.enums.ClassePersonagemEnum;
import br.com.sarah.dangeous_dragons.enums.TipoPersonagemEnum;
import br.com.sarah.dangeous_dragons.exception.ResourceNotFoundException;
import br.com.sarah.dangeous_dragons.repository.BatalhaRepository;
import br.com.sarah.dangeous_dragons.repository.PersonagemRepository;
import br.com.sarah.dangeous_dragons.service.BatalhaService;
import br.com.sarah.dangeous_dragons.service.PersonagemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("batalha")
public class BatalhaController {
    private final BatalhaService batalhaService;
    private final BatalhaRepository batalhaRepository;
    private final PersonagemRepository personagemRepository;
    private final PersonagemService personagemService;
    @PostMapping
    public ResponseEntity iniciaBatalha(@RequestBody Long idPersonagem) throws Exception {
        Personagem jogador = personagemRepository.findById(idPersonagem).orElseThrow(
                () -> new ResourceNotFoundException("Personagem não encontrado!!")
        );
        Personagem oponente = personagemService.criaPersonagem(
                PersonagemRequest.builder()
                        .nomePersonagem("Oponente")
                        .tipoPersonagem(TipoPersonagemEnum.MONSTRO)
                        .classePersonagem(ClassePersonagemEnum.Lobisomen)
                        .build()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(batalhaService.iniciativa(jogador, oponente));
    }
    @PostMapping("ataque/{id}")
    public ResponseEntity ataque(@RequestBody Long idPersonagem, @PathVariable("id") Long idBatalha) {
        Personagem jogador = personagemRepository.findById(idPersonagem).orElseThrow(
                () -> new ResourceNotFoundException("Personagem não encontrado!!")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(batalhaService.atacar(jogador, idBatalha));
    }

    @PostMapping("defesa/{id}")
    public ResponseEntity defesa(@RequestBody Long idPersonagem, @PathVariable("id") Long idBatalha) {
        Personagem jogador = personagemRepository.findById(idPersonagem).orElseThrow(
                () -> new ResourceNotFoundException("Personagem não encontrado!!")
        );

        Personagem oponente = batalhaRepository.findById(idBatalha).get().getOponente();

        return ResponseEntity.status(HttpStatus.CREATED).body(batalhaService.defender(jogador, oponente, idBatalha));
    }

    @GetMapping("calculo_dano/{id}")
    public ResponseEntity calculoDano(@PathVariable Long idBatalha) {
        return ResponseEntity.status(HttpStatus.OK).body(batalhaService.calculoDano(idBatalha));
    }
}
