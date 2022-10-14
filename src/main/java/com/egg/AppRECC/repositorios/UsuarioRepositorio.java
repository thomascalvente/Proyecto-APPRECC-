package com.egg.AppRECC.repositorios;

import com.egg.AppRECC.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
    
    @Query("SELECT u FROM  Usuario u WHERE u.activo >= 1")
     public List<Usuario> listarActivos();
     
     @Query("SELECT u FROM Usuario u WHERE u.id = :id")
     public Usuario encontrarUsuarioPorId(@Param ("id") String id );
     
     @Query("SELECT u FROM Usuario u WHERE u.email = :email")
     public Usuario encontrarUsuarioPorEmail(@Param("email") String email);

    
}
