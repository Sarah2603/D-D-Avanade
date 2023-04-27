package br.com.sarah.dangeous_dragons.component;

import br.com.sarah.dangeous_dragons.entity.Batalha;
import br.com.sarah.dangeous_dragons.exception.ResourceNotFoundException;
import br.com.sarah.dangeous_dragons.repository.BatalhaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class HistoricoBatalhaComponent {
    private final BatalhaRepository batalhaRepository;

    public List<Batalha> listaHistoricoBatalhas() {
        return batalhaRepository.findAll();
    }

    public Batalha listaHistoricoBatalhaPorID(Long id) {
        return batalhaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Historico de Batalha não encontrado")
        );
    }

}
