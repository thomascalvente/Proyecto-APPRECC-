
package com.egg.AppRECC.repositorios;

import com.egg.AppRECC.entidades.Posteo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PosteoRepositorio extends JpaRepository<Posteo, Long>{
     
    @Query("SELECT n FROM Posteo n WHERE n.titulo = :titulo")
    public Posteo buscarPorTitulo(@Param("titulo") String titulo);
    
    @Query("SELECT n FROM Posteo n WHERE n.borrado!=true ORDER BY n.fecha desc")
    public List<Posteo> listarposteos();
     
    @Query("SELECT n FROM Posteo n WHERE n.id = :id")
    public Posteo buscarPorId(@Param("id") Long id);
    
    @Query("SELECT n FROM Posteo n WHERE n.borrado!=true and n.cuerpo LIKE %:frase% and n.titulo LIKE %:frase% ORDER BY n.fecha desc")
    public List<Posteo> buscarPosteos(@Param("frase") String frase);
    
    @Query("SELECT p FROM Posteo p WHERE p.borrado!=true and p.id_campania LIKE :campania ORDER BY p.fecha")
    public List<Posteo> mostrarPosteosXCampania(@Param("campania") Long campania);
    
    
}
