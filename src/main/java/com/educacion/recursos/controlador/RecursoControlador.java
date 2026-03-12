package com.educacion.recursos.controlador;

import com.educacion.recursos.modelo.Recurso;
import com.educacion.recursos.repositorio.RecursoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recursos")
public class RecursoControlador {

    @Autowired
    private RecursoRepositorio repositorio;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("recursos", repositorio.findAll());
        return "recursos/recursos-lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("recurso", new Recurso());
        return "recursos/recursos-formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("recurso") Recurso recurso) {
        repositorio.save(recurso);
        return "redirect:/recursos";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Recurso recurso = repositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        model.addAttribute("recurso", recurso);
        return "recursos/recursos-formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
        return "redirect:/recursos";
    }

    // --- RUTA DE IMPRESIÓN CORREGIDA ---
    @GetMapping("/imprimir/{id}")
    public String imprimirFicha(@PathVariable Long id, Model model) {
        Recurso recurso = repositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        model.addAttribute("recurso", recurso);
        return "recursos/recursos-imprimir"; 
    }

    @GetMapping("/creditos")
    public String creditos() {
        return "recursos/recursos-creditos";
    }
}