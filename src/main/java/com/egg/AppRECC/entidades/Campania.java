/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.entidades;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author PC
 */

@Entity
@Data
public class Campania {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String titulo;
    
    private String description;
    
    private Date fechaFin;
    
    private boolean borrado = false;
    
    @Column(columnDefinition = "MEDIUMTEXT")
    private String imagen;
    
    private LocalDate fecha;
    
}
