package com.bruno.biblioteca.controladores;

import com.bruno.biblioteca.entidades.Autor;
import com.bruno.biblioteca.excepciones.MiException;
import com.bruno.biblioteca.servicios.AutorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor") // localhost:8080/autor
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    // mapeamos con un GET 
    @GetMapping("/registrar") // pericion HTTP llamada GET 
    //localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro") // metodo post nos trae la informacion del formulario - la action es Registro
    public String registro(@RequestParam String nombre, ModelMap modelo) { // nombre viene de autor_form.html 
        System.out.println("nombre: " + nombre);
        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito","el autor fue creado con exito!");
        } catch (MiException ex) {
            modelo.put("error",ex.getMessage());
            //Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "autor_form.html";
        }
        
        return "index.html";
        
    }
    
       @GetMapping("/lista") // pericion HTTP llamada GET 
    //localhost:8080/autor/registrar
    public String listar(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
      
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }
    
     @GetMapping("/modificar/{id}")
     public String modificar(@PathVariable String id, ModelMap modelo){
           modelo.put("autor", autorServicio.getOne(id));
           
     return "autor_modificar.html";
}
    
     @PostMapping("/modificar/{id}")
     public String modificar(@PathVariable String id,String nombre, ModelMap modelo) throws MiException {
        try {
            autorServicio.modificarAutor(id, nombre);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
     }
        
       
    
}
