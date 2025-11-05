package br.com.fiap.nextpark.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="USUARIOS")
public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="USERNAME", unique=true, nullable=false)
    private String username;

    @Column(name="PASSWORD", nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="ROLE", nullable=false)
    private Role role;


}
