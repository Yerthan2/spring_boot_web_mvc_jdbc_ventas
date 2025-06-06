package org.iesvdm.modelo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Comercial {

	private int id;

	@NotBlank
	@Size(max = 30)
	private String nombre;

	@NotBlank
	@Size(max = 30)
	private String apellido1;
	private String apellido2;

	@DecimalMin(value = "0.276", inclusive = true)
	@DecimalMax(value = "0.946", inclusive = true)
	private BigDecimal comision;


	public Comercial() {
	}
}
