package br.com.sarah.dangeous_dragons.dto;

import br.com.sarah.dangeous_dragons.enums.ClassePersonagemEnum;
import br.com.sarah.dangeous_dragons.enums.TipoPersonagemEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class PersonagemRequest implements Serializable {

    private String nomePersonagem;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "^[A-Z]+$", locale = "pt-BR")
    private ClassePersonagemEnum classePersonagem;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "^[A-Z]+$", locale = "pt-BR")
    private TipoPersonagemEnum tipoPersonagem;
}
