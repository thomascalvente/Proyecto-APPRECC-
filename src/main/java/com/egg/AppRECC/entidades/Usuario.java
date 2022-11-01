package com.egg.AppRECC.entidades;

import com.egg.AppRECC.enumeraciones.Rol;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Data
public class Usuario {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String email;
    private String password;
    private Integer activo;
    
    @Column(columnDefinition = "MEDIUMTEXT")
    private String imagenPerfil;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
