package org.iesvdm.controlador;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/comerciales")
public class ComercialController {

    private ComercialService comercialService;

    @Autowired
    public ComercialController(ComercialService comercialService){
        this.comercialService = comercialService;
    }

    @GetMapping({"", "/"})
    public String listar(Model model){
        List<Comercial> listComercial = comercialService.listAll();
        model.addAttribute("listaComercial", listComercial);
        return "comerciales";
    }
}
