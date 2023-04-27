package br.com.sarah.dangeous_dragons.entity;

import br.com.sarah.dangeous_dragons.enums.ClassePersonagemEnum;
import br.com.sarah.dangeous_dragons.enums.TipoPersonagemEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "classe_personagem")
public class ClassePersonagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ClassePersonagemEnum classe;

    @NotNull
    private int vida;

    @NotNull
    private int forca;

    @NotNull
    private int defesa;

    @NotNull
    private int agilidade;

    @NotNull
    private int quantidadeDeDados;

    @NotNull
    private int facesDoDado;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoPersonagemEnum tipoPersonagem;
}
