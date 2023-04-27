package br.com.sarah.dangeous_dragons.service;

import br.com.sarah.dangeous_dragons.dto.PersonagemRequest;
import br.com.sarah.dangeous_dragons.entity.ClassePersonagem;
import br.com.sarah.dangeous_dragons.entity.Personagem;
import br.com.sarah.dangeous_dragons.enums.ClassePersonagemEnum;
import br.com.sarah.dangeous_dragons.enums.TipoPersonagemEnum;
import br.com.sarah.dangeous_dragons.exception.ResourceNotFoundException;
import br.com.sarah.dangeous_dragons.repository.ClassePersonagemRepository;
import br.com.sarah.dangeous_dragons.repository.PersonagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PersonagemService {

    private final ClassePersonagemRepository classePersonagemRepository;
    private final PersonagemRepository personagemRepository;
    public Boolean validaTipoPersonagemEClasse(TipoPersonagemEnum tipoPersonagem, ClassePersonagemEnum classePersonagem) {
        if(tipoPersonagem.equals(TipoPersonagemEnum.HEROI)){
            String[] herois = new String[]{
                    ClassePersonagemEnum.Cavaleiro.toString(),
                    ClassePersonagemEnum.Guerreiro.toString(),
                    ClassePersonagemEnum.Barbaro.toString()
            };
            return Arrays.asList(herois).contains(classePersonagem.toString());
        } else if (tipoPersonagem.equals(TipoPersonagemEnum.MONSTRO)) {
            String[] monstros = new String[] {
                    ClassePersonagemEnum.Gigante.toString(),
                    ClassePersonagemEnum.Lobisomen.toString(),
                    ClassePersonagemEnum.Orc.toString()
            };
            return Arrays.asList(monstros).contains(classePersonagem.toString());
        }
        return false;
    }

    public Personagem criaPersonagem(PersonagemRequest personagemRequest) {
        Optional<ClassePersonagem> classePersonagem = classePersonagemRepository.findByClasse(
                personagemRequest.getClassePersonagem()
        );
        Personagem personagem = Personagem.builder()
                .classePersonagem(classePersonagem.get())
                .nome(personagemRequest.getNomePersonagem())
                .build();
        return personagemRepository.save(personagem);
    }

    public void atualizaPersonagem(PersonagemRequest personagemRequest, Long personagemId) {
        personagemRepository.findById(personagemId).orElseThrow(
                () -> new ResourceNotFoundException("Personagem Não encontrado")
        );

        Optional<ClassePersonagem> classePersonagem = classePersonagemRepository.findByClasse(
                personagemRequest.getClassePersonagem()
        );

        Personagem personagemAtualizado = Personagem.builder()
                .id(personagemId)
                .classePersonagem(classePersonagem.get())
                .nome(personagemRequest.getNomePersonagem())
                .build();

        personagemRepository.save(personagemAtualizado);
    }

    public List<Personagem> listaTodosPersonagens() {
        return personagemRepository.findAll();
    }

    public Personagem personagemPorID(Long idPersonagem) {
        return personagemRepository.findById(idPersonagem).orElseThrow(
                () -> new ResourceNotFoundException("Personagem Não encontrado")
        );
    }

    public void removePersonagem(Long personagemId) {
        personagemRepository.findById(personagemId).orElseThrow(
                () -> new ResourceNotFoundException("Personagem Não encontrado")
        );

        personagemRepository.deleteById(personagemId);
    }

}
