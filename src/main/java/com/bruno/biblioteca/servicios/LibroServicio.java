/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bruno.biblioteca.servicios;

import com.bruno.biblioteca.entidades.Autor;
import com.bruno.biblioteca.entidades.Editorial;
import com.bruno.biblioteca.entidades.Libro;
import com.bruno.biblioteca.excepciones.MiException;
import com.bruno.biblioteca.repositorios.AutorRepositorio;
import com.bruno.biblioteca.repositorios.EditorialRepositorio;
import com.bruno.biblioteca.repositorios.LibroRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    // lleva a cabo toda la logica del negocio

    // instanciamos el repositorio de libro. poner @autowired.
    @Autowired // a esto se lo llama inyeccion de dependencias
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    // CREAMOS UN LIBRO
    @Transactional
    public void crearLibro(Long ISBN, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        validar(ISBN,titulo,ejemplares,idAutor,idEditorial);
        // creo un autor llamando al repositorio Autor 
        Autor autor = autorRepositorio.findById(idAutor).get();
        
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        // creo un libro
        Libro libro = new Libro();

        libro.setIsbn(ISBN);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date()); 
        libro.setAutor(autor); // seteamos el autor encontrado desde el repositorio
        libro.setEditorial(editorial);
        //llamar al repositorio para persistir en la base de datos
        libroRepositorio.save(libro);

    }

    //MOSTRAMOS UNA LISTA DE LIBROS
    public List<Libro> listarLibros() {

        List<Libro> listaLibros = new ArrayList();

        listaLibros = libroRepositorio.findAll();

        return listaLibros;

    }
    
    // MODIFICAMOS UN LIBRO
     public void modificarLibro(Long ISBN, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        
          validar(ISBN,titulo,ejemplares,idAutor,idEditorial);
         // el optional devuelve una respuesta no nula. si se obtiene una respuesta se la persiste.
         Optional<Libro> respuesta = libroRepositorio.findById(ISBN);
         
         Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
         
         Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
         
         Autor autor = new Autor();
         
         Editorial editorial = new Editorial();
         
         if(respuestaAutor.isPresent()){
             autor = respuestaAutor.get();
         }
         
         if(respuestaEditorial.isPresent()){
             editorial = respuestaEditorial.get();
         }
          
         if(respuesta.isPresent()){
             Libro libro = respuesta.get();
             libro.setTitulo(titulo); 
             libro.setAutor(autor);
             libro.setEjemplares(ejemplares);
             libro.setEditorial(editorial);
             
             libroRepositorio.save(libro);
         }
         
         
    }

     private void validar(Long ISBN, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        
         if(ISBN == null){
             throw new MiException("el isbn no puede ser nulo");
         }
         if (titulo.isEmpty() || titulo==null){
             throw new MiException("el titulo no puede estar vacio");
         }
         if (ejemplares == null){
              throw new MiException("los ejemplares no pueden estar vacios");
         }
         if (idAutor.isEmpty() || idAutor == null){
             throw new MiException("el autor no pueden estar vacio");
         }
         if (idEditorial.isEmpty() || idEditorial==null) {
              throw new MiException("la editorial no pueden estar vacia");
         }
         
     }
}
