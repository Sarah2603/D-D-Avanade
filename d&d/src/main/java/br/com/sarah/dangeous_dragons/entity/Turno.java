package br.com.sarah.dangeous_dragons.entity;

import br.com.sarah.dangeous_dragons.enums.AtacanteEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dadoDano;

    private Integer dadoAtaque;

    private Integer dadoDefesaOponente;

    private AtacanteEnum ultimoAtacante;

    @ManyToOne
    @JoinColumn(name = "id_batalha", nullable = false)
    private Batalha batalha;

}
