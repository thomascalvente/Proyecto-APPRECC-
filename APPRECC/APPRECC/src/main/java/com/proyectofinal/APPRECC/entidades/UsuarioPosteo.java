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

    public Long getUsuarioPosteoId() {
        return usuarioPosteoId;
    }

    public void setUsuarioPosteoId(Long usuarioPosteoId) {
        this.usuarioPosteoId = usuarioPosteoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Posteo getPosteo() {
        return Posteo;
    }

    public void setPosteo(Posteo Posteo) {
        this.Posteo = Posteo;
    }
}
