package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.entity.CategoriaEntity;
import com.example.demo.entity.ProductoEntity;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.ProductoService;

@Service
public class ProductoServiceImlp implements ProductoService{

	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public List<ProductoEntity> obtenerProducto() {
		 return productoRepository.findAll();
		
	}

	@Override
	public ProductoEntity buscarProductoPorId(Integer id) {

		return productoRepository.findById(id.intValue()).get();
	}

	

	 @Override
	    public void crearProducto(ProductoEntity productoEntity, Model model) {
	        productoRepository.save(productoEntity);
	        
	        List<CategoriaEntity> listaCategoria = categoriaRepository.findAll();
	        model.addAttribute("listaCategoria", listaCategoria);
	        model.addAttribute("producto", new ProductoEntity());
	        model.addAttribute("registroCorrecto", "Producto agregado");
	    }

	    @Override
	    public List<CategoriaEntity> obtenerCategorias() {
	        return categoriaRepository.findAll();
	    }

		@Override
		public ProductoEntity actualizarProducto(ProductoEntity productoEntity) {
			
			
			ProductoEntity buscarProductoPorId = buscarProductoPorId(productoEntity.getIdPro());
			if(buscarProductoPorId != null) { 
				buscarProductoPorId.setNomPro(productoEntity.getNomPro());
				buscarProductoPorId.setPrecio(productoEntity.getPrecio());
				buscarProductoPorId.setStock(productoEntity.getStock());
				buscarProductoPorId.setCategoria(productoEntity.getCategoria());
				
				return productoRepository.save(buscarProductoPorId);
				
				
			}
			return null;
		
		}
		
		@Override
		public void eliminarProducto(Integer id) {
			productoRepository.deleteById(id);
			
		}
}
