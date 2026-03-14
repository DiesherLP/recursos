package com.educacion.recursos.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorInicio {

    @GetMapping("/")
    public String home() {
        return "redirect:/recursos";
    }
@GetMapping("/login")
public String login() {
    return "login"; // Esto busca el archivo login.html en templates
}
}
