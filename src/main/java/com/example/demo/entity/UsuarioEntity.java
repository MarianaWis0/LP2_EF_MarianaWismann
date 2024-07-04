package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {
	
	
	@Id
	@Column(name = "correo_electronico" ,
	nullable = false ,
	length = 100,
	columnDefinition = "VARCHAR(100)",
	unique = true
	)
	private String correo;
	
	@Column(name = "password" ,
			nullable = false 
			)
	private String password;
	
	@Column(name = "nombre" ,
			nullable = false ,
			length = 100,
			columnDefinition = "VARCHAR(100)"
			)
	private String nombre;
	
	@Column(name = "apellido" ,
			nullable = false ,
			length = 100,
			columnDefinition = "VARCHAR(100)"
			)
	private String apellido;
	
	  @Column(name = "fecha_nacimiento", nullable = false, updatable = false)
	    @Temporal(TemporalType.DATE) 
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date fecha_naci;
	  
	  
	private String urlImagen;
}
