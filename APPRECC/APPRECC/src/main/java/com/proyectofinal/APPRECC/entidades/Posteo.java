package com.proyectofinal.APPRECC.entidades;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Post")
public class Posteo {

    @Id
    private Long rolId;
    private String rolNombre;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "Posteo")
    private Set <UsuarioPosteo> usuarioPost = new HashSet<>();

    public Posteo(){

    }

    public Posteo(Long rolId, String rolNombre) {
        this.rolId = rolId;
        this.rolNombre = rolNombre;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Set<UsuarioPosteo> getUsuarioRoles() {
        return usuarioPost;
    }

    public void setUsuarioRoles(Set<UsuarioPosteo> usuarioRoles) {
        this.usuarioPost = usuarioRoles;
    }
}
