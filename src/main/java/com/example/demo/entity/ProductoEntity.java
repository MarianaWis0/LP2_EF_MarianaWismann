package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_producto")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Integer idPro;
	
	@Column(name = "nomPro", nullable = false, length = 100)
	private String nomPro;
	
	@Column(name = "precio", nullable = false, precision = 10, scale = 2)
	private BigDecimal  precio;
	
	@Column(name = "stock", nullable = false)
	private Integer stock;
	
	

	@ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false) 
    private CategoriaEntity categoria;
	
}
