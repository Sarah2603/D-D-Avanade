package br.com.sarah.dangeous_dragons.controller;

import br.com.sarah.dangeous_dragons.dto.PersonagemRequest;
import br.com.sarah.dangeous_dragons.entity.Personagem;
import br.com.sarah.dangeous_dragons.service.BatalhaService;
import br.com.sarah.dangeous_dragons.service.PersonagemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("personagem")
public class PersonagemController {
    private final PersonagemService personagemService;
    @PostMapping
    public ResponseEntity criarPersonagem(@RequestBody PersonagemRequest personagemRequest) {
        if (personagemService.validaTipoPersonagemEClasse(
                personagemRequest.getTipoPersonagem(),
                personagemRequest.getClassePersonagem()
        )){
            Personagem personagem = personagemService.criaPersonagem(personagemRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(personagem);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de personagem inválido");
    }

    @GetMapping
    public ResponseEntity listaPersonagens() {
        return ResponseEntity.status(HttpStatus.OK).body(personagemService.listaTodosPersonagens());
    }

    @GetMapping("{id}")
    public ResponseEntity buscaPersonagemPorId(@PathVariable("id") Long personagemId) {
        return ResponseEntity.status(HttpStatus.OK).body(personagemService.personagemPorID(personagemId));
    }

    @PutMapping("{id}")
    public ResponseEntity atualizaPersonagem(@PathVariable("id") Long personagemId, @RequestBody PersonagemRequest personagem) {
        if (personagemService.validaTipoPersonagemEClasse(
                personagem.getTipoPersonagem(),
                personagem.getClassePersonagem()
        )) {
            personagemService.atualizaPersonagem(personagem, personagemId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de personagem inválido");
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletaPersonagem(@PathVariable("id") Long personagemId) {
        personagemService.removePersonagem(personagemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
