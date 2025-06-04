package org.iesvdm.controlador;

import java.util.List;

import ch.qos.logback.core.net.server.Client;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
@RequestMapping("/clientes")
public class ClienteController {
	
	private ClienteService clienteService;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	//@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping({"", "/"}) //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
	public String listar(Model model) {
		
		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);
				
		return "clientes";
		
	}

	/**
	 * nos lleva a la ruta que le especificamos, en este caso será a crear-cliente
	 * @param model
	 * @return
	 */
	@GetMapping("/crear")
	public String crear(Model model){
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		return "crear-cliente";
	}

	/*
	* Sirve para cuando mandemos la info desde el html, insertarla en la BBDD
	 */
	@PostMapping("/crear")
	public RedirectView submitCrear(@ModelAttribute("cliente") Cliente cliente){
		clienteService.newCliente(cliente);
		return new RedirectView("/clientes");
	}

	/**
	 * Le pasamos la id y nos redirecciona a la página con el usuario, ya que utilizamos el findById
	 * @param model
	 * @param id
	 * @return
	 */

	@GetMapping("/{id}")
	public String detalle(Model model, @PathVariable int id){
		Cliente cliente = clienteService.findById(id);
		model.addAttribute("cliente", cliente);
		return "detalle-cliente";
	}

	/**
	 * NOs redirecciona a editar, con el usuario, del que le hemos pasado la id
	 * @param model
	 * @param id
	 * @return
	 */

	@GetMapping("/editar/{id}")
	public String editar(Model model, @PathVariable Integer id){
		Cliente cliente = clienteService.findById(id);
		model.addAttribute("cliente", cliente);
		return "editar-cliente";
	}

	/**
	 * Cuando le demos al botón guardará la nueva info en la BBDD
	 * @param cliente
	 * @return
	 */
	@PostMapping("/editar/{id}")
	public RedirectView submitEditar(@ModelAttribute("Cliente") Cliente cliente) {

		clienteService.replaceCliente(cliente);

		return new RedirectView("/clientes");
	}

	/**
	 * Borrará el registro de la BBDD y se queda en la misma página no cambia
	 * @param id
	 * @return
	 */
	@GetMapping("/borrar/{id}")
	public RedirectView submitBorrar(@PathVariable Integer id) {
		clienteService.delete(id);
		return new RedirectView("/clientes");
	}
	

}
