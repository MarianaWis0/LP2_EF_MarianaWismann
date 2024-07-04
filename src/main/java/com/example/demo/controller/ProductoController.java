package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.CategoriaEntity;
import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.impl.PdfService;

@Controller
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	  @Autowired
	    private UsuarioService usuarioService;
	  @Autowired
		private CategoriaRepository categoriaRepository;
	  
	  @Autowired
	    private PdfService pdfService;


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
	
	
	@GetMapping("/detalle/{id}")
	public String verProducto(HttpSession session,  Model model, @PathVariable("id") Integer id) {
		
		if (session.getAttribute("usuario") == null) {
	        return "redirect:/";
	    }
		
		String correo = session.getAttribute("usuario").toString();
	    UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
	    model.addAttribute("foto", usuarioEntity.getUrlImagen());
	    
	    String nombreCompleto = usuarioEntity.getNombre() + " " + usuarioEntity.getApellido();
	    model.addAttribute("nombreUsuario", nombreCompleto);
	    model.addAttribute("foto", usuarioEntity.getUrlImagen());
		
	    ProductoEntity producto = productoService.buscarProductoPorId(id);
	    model.addAttribute("producto", producto);
	    return "detalle"; 
	}
	
	
	@GetMapping("/nuevo_producto")
    public String crearProducto(HttpSession session, Model model) {
		
	     if (session.getAttribute("usuario") == null) {
	            return "redirect:/";
	        }

	        String correo = session.getAttribute("usuario").toString();
	        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
	        model.addAttribute("foto", usuarioEntity.getUrlImagen());
	        
	        String nombreCompleto = usuarioEntity.getNombre() + " " + usuarioEntity.getApellido();
	        model.addAttribute("nombreUsuario", nombreCompleto);
	        model.addAttribute("foto", usuarioEntity.getUrlImagen());
	        
	        List<CategoriaEntity> listaCategoria = productoService.obtenerCategorias();
	        model.addAttribute("listaCategoria", listaCategoria);
	        model.addAttribute("producto", new ProductoEntity());
	        return "registrarProducto";
	    }

	    @PostMapping("/nuevo_producto")
	    public String crearProducto(@ModelAttribute ProductoEntity productoEntity, Model model, HttpSession session) {
	    	
	    	 if (session.getAttribute("usuario") == null) {
		            return "redirect:/";
		        }

		        String correo = session.getAttribute("usuario").toString();
		        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		        model.addAttribute("foto", usuarioEntity.getUrlImagen());
		        
		        String nombreCompleto = usuarioEntity.getNombre() + " " + usuarioEntity.getApellido();
		        model.addAttribute("nombreUsuario", nombreCompleto);
		        model.addAttribute("foto", usuarioEntity.getUrlImagen());
	    	
	    	
	        productoService.crearProducto(productoEntity, model);
	        return "registrarProducto"; 
	    }
	    
	    
	    
	    @GetMapping("/editar_producto/{id}")
	    public String showEditarProducto(@PathVariable("id") Integer id, Model model, HttpSession session) {
	        if (session.getAttribute("usuario") == null) {
	            return "redirect:/";
	        }

	        String correo = session.getAttribute("usuario").toString();
	        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
	        model.addAttribute("nombreUsuario", usuarioEntity.getNombre() + " " + usuarioEntity.getApellido());
	        model.addAttribute("foto", usuarioEntity.getUrlImagen());

	        ProductoEntity productoEditar = productoService.buscarProductoPorId(id);
	        if (productoEditar == null) {
	           
	            return "redirect:/productos";
	        }

	        model.addAttribute("producto", productoEditar);

	        List<CategoriaEntity> listaCategorias = productoService.obtenerCategorias();
	        model.addAttribute("listaCategoria", listaCategorias);

	        return "editarProducto";
	    }

	    @PostMapping("/editar_producto")
	    public String actualizarProducto(Model model, @ModelAttribute("producto") ProductoEntity productoEntity, HttpSession session) {
	        if (session.getAttribute("usuario") == null) {
	            return "redirect:/"; 
	        }

	        String correo = session.getAttribute("usuario").toString();
	        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
	        model.addAttribute("nombreUsuario", usuarioEntity.getNombre() + " " + usuarioEntity.getApellido());
	        model.addAttribute("foto", usuarioEntity.getUrlImagen());

	      
	        if (productoEntity == null || productoEntity.getIdPro() == null) {
	            return "redirect:/productos"; 
	        }

	        productoService.actualizarProducto(productoEntity);

	        return "redirect:/productos";
	    }
	    
	    
	    @GetMapping("/eliminar/{id}")
	    public String eliminarProducto(@PathVariable("id") Integer id, ProductoEntity productoEntity) {
	    	productoService.eliminarProducto(id);
	        return "redirect:/productos";
	    }
	    
	    
	    @GetMapping("/generar_pdf_productos")
	    public ResponseEntity<InputStreamResource> generarPdf(HttpSession session) throws IOException {
	        String correo = session.getAttribute("usuario").toString();
	        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);

	        List<ProductoEntity> productos = productoService.obtenerProducto(); 

	        BigDecimal total = BigDecimal.ZERO;
	        for (ProductoEntity producto : productos) {
	            total = total.add(producto.getPrecio().multiply(new BigDecimal(producto.getStock())));
	        }

	        Map<String, Object> datosPdf = new HashMap<>();
	        datosPdf.put("productos", productos);
	        datosPdf.put("usuario", usuarioEntity.getNombre() + " " + usuarioEntity.getApellido());

	        ByteArrayInputStream pdfBytes = pdfService.generarPdfDeHtml("exportarProductos", datosPdf);

	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.add("Content-Disposition", "inline; filename=productos.pdf");

	        return ResponseEntity.ok()
	                .headers(httpHeaders)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(pdfBytes));
	    }


}   
