package br.com.sarah.dangeous_dragons.service;

import br.com.sarah.dangeous_dragons.entity.Batalha;
import br.com.sarah.dangeous_dragons.entity.ClassePersonagem;
import br.com.sarah.dangeous_dragons.entity.Personagem;
import br.com.sarah.dangeous_dragons.entity.Turno;
import br.com.sarah.dangeous_dragons.enums.AtacanteEnum;
import br.com.sarah.dangeous_dragons.enums.SituacaoBatalhaEnum;
import br.com.sarah.dangeous_dragons.exception.NotPermittedActionException;
import br.com.sarah.dangeous_dragons.exception.ResourceNotFoundException;
import br.com.sarah.dangeous_dragons.repository.BatalhaRepository;
import br.com.sarah.dangeous_dragons.repository.ClassePersonagemRepository;
import br.com.sarah.dangeous_dragons.repository.TurnoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;


@AllArgsConstructor
@Service
public class BatalhaService {
    private final BatalhaRepository batalhaRepository;
    private final ClassePersonagemRepository classePersonagemRepository;

    private final TurnoRepository turnoRepository;


    public Turno iniciativa(Personagem jogador, Personagem oponente) throws Exception {
        int somaDosDadosJogador = jogarDado(1,20);
        int somaDosDadosOponente = jogarDado(1,20);

        AtacanteEnum atacanteEnum = AtacanteEnum.JOGADOR;
        Turno.TurnoBuilder turno = null;
        int dadoAtaque = 0;
        if (somaDosDadosJogador > somaDosDadosOponente) {
            dadoAtaque = calcularAtaque(jogador);
            turno = calcularDefesa(jogador, oponente, dadoAtaque);
        } else if (somaDosDadosOponente > somaDosDadosJogador) {
            atacanteEnum = AtacanteEnum.OPONENTE;
            dadoAtaque = calcularAtaque(oponente);
            turno = calcularDefesa(oponente, jogador, dadoAtaque);
        } else {
            iniciativa(jogador, oponente);
        }

        if (turno != null) {

            Batalha batalha = Batalha.builder()
                    .iniciativa(atacanteEnum)
                    .jogador(jogador)
                    .oponente(oponente)
                    .situacao(SituacaoBatalhaEnum.INICIADA)
                    .build();

            turno.ultimoAtacante(atacanteEnum)
                    .batalha(batalha);

            batalhaRepository.save(batalha);
            return turnoRepository.save(turno.build());
        }
        throw new Exception("Ocorreu um erro desconhecido");
    }

    private Integer calcularAtaque(Personagem atacante) {

        int somaDosDadosJogador;
        somaDosDadosJogador = jogarDado(
                atacante.getClassePersonagem().getQuantidadeDeDados(),
                atacante.getClassePersonagem().getFacesDoDado()
        );
        int forcaDoAtaque = somaDosDadosJogador + atacante.getClassePersonagem().getForca() + atacante.getClassePersonagem().getAgilidade();

        return forcaDoAtaque;
    }

    private Turno.TurnoBuilder calcularDefesa(Personagem atacante, Personagem defensor, Integer forcaDoAtaque) {
        int somaDosDadosOponente;
        somaDosDadosOponente = jogarDado(
                defensor.getClassePersonagem().getQuantidadeDeDados(),
                defensor.getClassePersonagem().getFacesDoDado()
        );
        int forcaDeDefesa = somaDosDadosOponente + defensor.getClassePersonagem().getDefesa() + defensor.getClassePersonagem().getAgilidade();

        if (forcaDoAtaque > forcaDeDefesa) {
            int somaDosDadosJogador = jogarDado(
                    atacante.getClassePersonagem().getQuantidadeDeDados(),
                    atacante.getClassePersonagem().getFacesDoDado()
            );

            forcaDoAtaque = somaDosDadosJogador + atacante.getClassePersonagem().getForca();
            int vidaRestante = defensor.getClassePersonagem().getVida() - forcaDoAtaque;
            ClassePersonagem classeOponente = defensor.getClassePersonagem();
            classeOponente.setVida(vidaRestante);
            defensor.setClassePersonagem(classeOponente);
            return Turno.builder().dadoDano(forcaDoAtaque).dadoDefesaOponente(forcaDeDefesa);
        }
        return Turno.builder().dadoDano(0);
    }

    public Turno atacar(Personagem atacante, Long idBatalha) {
        Batalha batalha = batalhaRepository.findById(idBatalha).orElseThrow(
                () -> new NotPermittedActionException("Inicie a batalha primeiro")
        );

        Turno turno = turnoRepository.findFirstByBatalhaOrderByIdDesc(batalha).orElseThrow(
                () -> new NotPermittedActionException("Inicie a batalha primeiro")
        );

        if (turno.getDadoDefesaOponente() != null && turno.getDadoAtaque() != null) {
            Integer calculoDano = calcularAtaque(atacante);
            AtacanteEnum atacanteEnum = AtacanteEnum.JOGADOR;
            if (turno.getUltimoAtacante().equals(AtacanteEnum.JOGADOR)) {
                atacanteEnum = AtacanteEnum.OPONENTE;
            }
            Turno novoTurno = Turno.builder()
                    .ultimoAtacante(atacanteEnum)
                    .dadoAtaque(calculoDano)
                    .batalha(batalha)
                    .build();
            return turnoRepository.save(novoTurno);
        } else if (turno.getDadoAtaque() != null) {
            throw new NotPermittedActionException("Ataque ja realizado, faça a defesa");
        }
        return turno;
    }

    public Turno defender(Personagem atacante, Personagem defensor, Long idBatalha) {
        Batalha batalha = batalhaRepository.findById(idBatalha).orElseThrow(
                () -> new NotPermittedActionException("Inicie a batalha primeiro")
        );

        Turno turno = turnoRepository.findFirstByBatalhaOrderByIdDesc(batalha).orElseThrow(
                () -> new NotPermittedActionException("Inicie a batalha primeiro")
        );

        if (turno.getDadoAtaque() != null && turno.getDadoDefesaOponente() == null) {
            Turno.TurnoBuilder turnoAtualizado = calcularDefesa(atacante, defensor, turno.getDadoAtaque());
            AtacanteEnum atacanteEnum = AtacanteEnum.JOGADOR;
            if (turno.getUltimoAtacante().equals(AtacanteEnum.JOGADOR)) {
                atacanteEnum = AtacanteEnum.OPONENTE;
            }
            return turnoRepository.save(turnoAtualizado.build());
        } else if (turno.getDadoAtaque() != null) {
            throw new NotPermittedActionException("Ataque nao realizado, faça seu ataque!!");
        }
        return turno;
    }

    public int jogarDado(int quantidadeDeDados, int quantidadeDeFaces) {
        int soma = 0;
        for (int i = 0; i < quantidadeDeDados; i++) {
            soma += new Random().nextInt(quantidadeDeFaces) + 1;
        }
        return soma;
    }

    public Turno calculoDano(Long idBatalha) {
        Batalha batalha = batalhaRepository.findById(idBatalha).orElseThrow(
                () -> new NotPermittedActionException("Inicie a batalha primeiro")
        );

        atacar(batalha.getOponente(), idBatalha);
        defender(batalha.getOponente(), batalha.getJogador(), idBatalha);

        return turnoRepository.findFirstByBatalhaOrderByIdDesc(batalha).orElseThrow(
                () -> new NotPermittedActionException("Inicie a batalha primeiro")
        );
    }

    private ClassePersonagem buscarDadosDoPersonagem(Personagem personagem) {
        return classePersonagemRepository.findByClasse(personagem.getClassePersonagem().getClasse()).orElseThrow(
                () -> new ResourceNotFoundException("Classe personagem não encontrada")
        );
    }

}
