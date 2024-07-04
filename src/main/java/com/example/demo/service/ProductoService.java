package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.entity.CategoriaEntity;
import com.example.demo.entity.ProductoEntity;

@Service
public interface ProductoService {
	 List<ProductoEntity> obtenerProducto();
	    ProductoEntity buscarProductoPorId(Integer id);
	    void crearProducto(ProductoEntity productoEntity, Model model);
	    List<CategoriaEntity> obtenerCategorias();
	    ProductoEntity actualizarProducto(ProductoEntity productoEntity);
	    void eliminarProducto(Integer id);
}
