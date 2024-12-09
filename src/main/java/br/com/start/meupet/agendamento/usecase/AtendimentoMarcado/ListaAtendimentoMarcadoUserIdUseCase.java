package br.com.start.meupet.agendamento.usecase.AtendimentoMarcado;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoDTO;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.repository.AtendimentoMarcadoRepository;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListaAtendimentoMarcadoUserIdUseCase {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AtendimentoMarcadoRepository atendimentoMarcadoRepository;
    public List<AtendimentoMarcadoDTO> execute(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Busca os atendimentos marcados associados ao usuário
        List<AtendimentoMarcado> atendimentosMarcados = atendimentoMarcadoRepository.findByUser(user);

        // Converte os atendimentos marcados para DTOs
        List<AtendimentoMarcadoDTO> atendimentosDTO = atendimentosMarcados.stream()
                .map(atendimento -> new AtendimentoMarcadoDTO(
                        atendimento,
                        atendimento.getPartner(),
                        atendimento.getUser(),
                        atendimento.getServicoPrestado(),
                        atendimento.getAnimal()
                ))
                .toList();
        return atendimentosDTO;
    }
}
