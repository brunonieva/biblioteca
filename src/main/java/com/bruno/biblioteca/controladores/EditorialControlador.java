/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bruno.biblioteca.controladores;

import com.bruno.biblioteca.excepciones.MiException;
import com.bruno.biblioteca.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
   
    @Autowired
    private EditorialServicio editorialServicio;

    // mapeamos con un GET 
    @GetMapping("/registrar") // pericion HTTP llamada GET 
    //localhost:8080/autor/registrar
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro") // metodo post nos trae la informacion del formulario - la action es Registro
    public String registro(@RequestParam String nombre, ModelMap modelo) { // nombre viene de autor_form.html 
        System.out.println("nombre: " + nombre);
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito","la editoral fue creada con exito!");
        } catch (MiException ex) {
            modelo.put("error",ex.getMessage());
            //Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "Editorial_form.html";
        }
        
        return "index.html";
        
    }
}
