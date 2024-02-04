/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bruno.biblioteca.servicios;

import com.bruno.biblioteca.entidades.Autor;
import com.bruno.biblioteca.excepciones.MiException;
import com.bruno.biblioteca.repositorios.AutorRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    
    // @Transactional solo creara el objeto si no lanza errores. sino cancelara la creacion del objeto. haciendo un RollBack
    @Transactional
    public void crearAutor(String nombre) throws MiException{
        
        validar(nombre);
        
        Autor autor = new Autor();
       
        autor.setNombre(nombre);
        
        // persistimos en la base de datos
        autorRepositorio.save(autor);
    }
    
    public List<Autor> listarAutores() {

        List<Autor> listaAutores = new ArrayList();

        listaAutores = autorRepositorio.findAll();

        return listaAutores;

    }
    
   public void modificarAutor(String id, String nombre) throws MiException{
       
       validar(nombre);
       
       Optional<Autor> respuesta = autorRepositorio.findById(id);
       
       if(respuesta.isPresent()){
           Autor autor = respuesta.get();
           
           autor.setNombre(nombre);
           
           autorRepositorio.save(autor);
       }
   }
   
   public Autor getOne(String id){
       return autorRepositorio.getOne(id);
   }
   
   private void validar(String nombre) throws MiException{
       
       if(nombre == null || nombre.isEmpty()){
           throw new MiException("el nombre del autor no puede estar vacio");
       }
   }

}
