package br.com.start.meupet.agendamento.dto.servico;

import br.com.start.meupet.agendamento.model.ServicoPrestado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicoAtendimentoDTO {
    private Long id;
    private String name;

    public ServicoAtendimentoDTO(ServicoPrestado servicoPrestado) {
        this.id = servicoPrestado.getId();
        this.name = servicoPrestado.getName();
    }
}
