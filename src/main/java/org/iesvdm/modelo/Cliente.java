package org.iesvdm.modelo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

//La anotación @Data de lombok proporcionará el código de:
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	
	private long id;

	@NotBlank
	@Size(max = 30)
	private String nombre;

	@NotBlank
	@Size(max = 30)
	private String apellido1;

	//Al ser opcional, no le ponemos nada de notblank
	//si no sería obligatorio
	private String apellido2;

	@NotBlank
	@Size(max = 50)
	private String ciudad;

	@Min(100)
	@Max(1000)
	private int categoria;

	@NotBlank
	@Email
	private String email;
}
