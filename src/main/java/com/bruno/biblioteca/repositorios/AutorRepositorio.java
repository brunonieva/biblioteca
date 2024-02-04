
package com.bruno.biblioteca.repositorios;

import com.bruno.biblioteca.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
   // faltan metodos primero hacer las clases service
    
}
