package br.com.fiap.nextpark.model.entity;

import br.com.fiap.nextpark.model.enums.StatusMoto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="MOTOS")
public class Moto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="PLACA", unique=true, nullable=false)
    private String placa;

    @Column(name="MODELO", nullable=false)
    private String modelo;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS", nullable=false)
    private StatusMoto status;

    @Column(name="OWNER_USER_ID", nullable=false)
    private Long ownerUserId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="VAGA_ID")
    private Vaga vaga;


}
