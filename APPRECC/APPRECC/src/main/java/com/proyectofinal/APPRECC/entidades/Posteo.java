package com.proyectofinal.APPRECC.entidades;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Post")
public class Posteo {

    @Id
    private Long PostId;
    private String PostNombre;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "Posteo")
    private Set <UsuarioPosteo> usuarioPost = new HashSet<>();

    public Posteo(){

    }

    public Posteo(Long PostId, String PostNombre) {
        this.PostId = PostId;
        this.PostNombre = PostNombre;
    }

    public Long getPostId() {
        return PostId;
    }

    public void setPostId(Long PostId) {
        this.PostId = PostId;
    }

    public String getRolNombre() {
        return PostNombre;
    }

    public void setPostNombre(String PostNombre) {
        this.PostNombre = PostNombre;
    }

    public Set<UsuarioPosteo> getUsuarioPost() {
        return usuarioPost;
    }

    public void setUsuarioPost(Set<UsuarioPosteo> usuarioPost) {
        this.usuarioPost = usuarioPost;
    }
}
