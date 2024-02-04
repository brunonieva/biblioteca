/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bruno.biblioteca.servicios;

import com.bruno.biblioteca.entidades.Editorial;
import com.bruno.biblioteca.excepciones.MiException;
import com.bruno.biblioteca.repositorios.EditorialRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    
        @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    
    // @Transactional solo creara el objeto si no lanza errores. sino cancelara la creacion del objeto. haciendo un RollBack
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
        validar(nombre);
        
        Editorial editorial = new Editorial();
       
        editorial.setNombre(nombre);
        
        // persistimos en la base de datos
        editorialRepositorio.save(editorial);
    }
    
     public List<Editorial> listarEditorial() {

        List<Editorial> listaEditoriales = new ArrayList();

        listaEditoriales = editorialRepositorio.findAll();

        return listaEditoriales;

    }
    
   public void modificarAutor(String nombre, String id) throws MiException{
       
       validar(nombre);
       
       Optional<Editorial> respuesta = editorialRepositorio.findById(id);
       
       if(respuesta.isPresent()){
           Editorial editorial = respuesta.get();
           
           editorial.setNombre(nombre);
           
           editorialRepositorio.save(editorial);
       }
   }
    
      private void validar(String nombre) throws MiException{
       if(nombre == null || nombre.isEmpty()){
           throw new MiException("el nombre de la editorial no puede estar vacio");
       }
   }
}

