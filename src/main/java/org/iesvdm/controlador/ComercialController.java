package org.iesvdm.controlador;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping("/crear")
    public String crear(Model model){
        Comercial comercial = new Comercial();
        model.addAttribute("comercial", comercial);
        return "crear-comercial";
    }

    @PostMapping("/crear")
    public RedirectView submitCrear(@ModelAttribute("Comercial") Comercial comercial){
        comercialService.newComercial(comercial);
        return new RedirectView("/comerciales");
    }


    @GetMapping("/{id}")
    public String detalle(Model model, @PathVariable int id){
        Comercial comercial = comercialService.findById(id);
        List<Pedido> list = comercialService.obtenerPedidosPorComercial(id);

        model.addAttribute("list", list);
        model.addAttribute("comercial", comercial);
        return "detalle-comercial";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable Integer id){
        Comercial comercial = comercialService.findById(id);
        model.addAttribute("comercial", comercial);
        return "editar-comercial";
    }
    @PostMapping("/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("Comercial") Comercial comercial) {

        comercialService.replaceCliente(comercial);

        return new RedirectView("/comerciales");
    }

    @GetMapping("/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {
        comercialService.delete(id);

        return new RedirectView("/comerciales");
    }

}
