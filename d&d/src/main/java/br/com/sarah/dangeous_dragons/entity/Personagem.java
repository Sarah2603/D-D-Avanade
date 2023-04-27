package br.com.sarah.dangeous_dragons.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table
public class Personagem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @OneToOne
    @JoinColumn(name = "classe_personagem_id")
    private ClassePersonagem classePersonagem;
}
