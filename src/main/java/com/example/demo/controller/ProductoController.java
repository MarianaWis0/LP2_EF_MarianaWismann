package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;

@Controller
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	  @Autowired
	    private UsuarioService usuarioService;
	

	@GetMapping("/productos")
	public String showMenu(HttpSession session, Model model) {
	    if (session.getAttribute("usuario") == null) {
	        return "redirect:/";
	    }

	    String correo = session.getAttribute("usuario").toString();
	    UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
	    model.addAttribute("foto", usuarioEntity.getUrlImagen());
	    
	    String nombreCompleto = usuarioEntity.getNombre() + " " + usuarioEntity.getApellido();
	    model.addAttribute("nombreUsuario", nombreCompleto);
	    model.addAttribute("foto", usuarioEntity.getUrlImagen());

	    List<ProductoEntity> productos = productoService.obtenerProducto();
	    model.addAttribute("productos", productos);

	    return "mantenimiento";
	}

}   
