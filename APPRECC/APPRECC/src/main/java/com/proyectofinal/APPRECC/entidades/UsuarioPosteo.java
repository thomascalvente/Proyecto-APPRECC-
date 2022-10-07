package com.proyectofinal.APPRECC.entidades;


import javax.persistence.*;

@Entity
public class UsuarioPosteo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioPosteoId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @ManyToOne
    private Posteo Posteo;

    public Long getUsuarioRolId() {
        return usuarioPosteoId;
    }

    public void setUsuarioRolId(Long usuarioRolId) {
        this.usuarioPosteoId = usuarioPosteoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Posteo getRol() {
        return Posteo;
    }

    public void setRol(Posteo Posteo) {
        this.Posteo = Posteo;
    }
}
