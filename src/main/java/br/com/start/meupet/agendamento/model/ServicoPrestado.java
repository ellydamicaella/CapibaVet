package br.com.start.meupet.agendamento.model;

import br.com.start.meupet.agendamento.enums.ServicoType;
import br.com.start.meupet.partner.model.Partner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
    private String price;

    @ManyToOne
    @JoinColumn(name = "id_partner", referencedColumnName = "id")
    @JsonIgnore
    private Partner partner;

    public ServicoPrestado() {
    }

    public ServicoPrestado(ServicoType name, String price, Partner partner) {
        this.name = name;
        this.price = price;
        this.partner = partner;
    }

    @Override
    public String toString() {
        return "ServicoPrestado{" +
                "id=" + id +
                ", name=" + name.getServicoType() +
                ", price=" + price +
                ", partner=" + partner.getName() +
                '}';
    }
}
