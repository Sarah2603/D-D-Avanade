package br.com.sarah.dangeous_dragons.entity;

import br.com.sarah.dangeous_dragons.enums.AtacanteEnum;
import br.com.sarah.dangeous_dragons.enums.SituacaoBatalhaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table
public class Batalha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_jogador", nullable = false)
    private Personagem jogador;

    @ManyToOne
    @JoinColumn(name = "id_monstro", nullable = false)
    private Personagem oponente;

    @Enumerated(EnumType.STRING)
    private SituacaoBatalhaEnum situacao;

    @Enumerated(EnumType.STRING)
    private AtacanteEnum iniciativa;

    @OneToMany(mappedBy = "batalha", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Turno> turnos;
}
