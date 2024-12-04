package br.com.start.meupet.agendamento.model;

import br.com.start.meupet.agendamento.enums.ServicoType;
import br.com.start.meupet.partner.model.Partner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "servico")
@Getter
@Setter
public class ServicoPrestado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "O nome do serviço é obrigatório.")
    @Enumerated(EnumType.STRING)
    private ServicoType name;


    @NotNull(message = "O preço do serviço é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero.")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_partner", referencedColumnName = "id")
    @JsonIgnore
    private Partner partner;

    public ServicoPrestado() {
    }

    public ServicoPrestado(ServicoType name, BigDecimal price, Partner partner) {
        this.name = name;
        this.price = price;
        this.partner = partner;
    }
}
