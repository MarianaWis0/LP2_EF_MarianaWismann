package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductoEntity;

@Service
public interface ProductoService {
	 List<ProductoEntity> obtenerProducto();
	    ProductoEntity buscarProductoPorId(Integer id);
}
