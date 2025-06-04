package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

	//JdbcTemplate se inyecta por el constructor de la clase automáticamente
	//
	private JdbcTemplate jdbcTemplate;

	/**
	 * Sirve para crear un nuevo comercial pasandole por parámetro, un comercial, y así obtenemos todos sus datos a
	 * la hora de ingresarlo en la BBDD
	 * @param comercial
	 */
	@Override
	public void create(Comercial comercial) {
		// TODO Auto-generated method stub


		String sqlInsert = """
							INSERT INTO ventas.comercial (nombre, apellido1, apellido2, comisión) 
							VALUES  (     ?,         ?,         ?,       ?)
						   """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Con recuperación de id generado
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
			int idx = 1;
			ps.setString(idx++, comercial.getNombre());
			ps.setString(idx++, comercial.getApellido1());
			ps.setString(idx++, comercial.getApellido2());
			ps.setDouble(idx++, comercial.getComision());
			return ps;
		},keyHolder);

		comercial.setId(keyHolder.getKey().intValue());
		log.info("Insertados {} registros.", rows);
	}

	/**
	 * Obtiene todos los Comerciales, que se encuentran en la base de datos
	 * @return
	 */
	@Override
	public List<Comercial> getAll() {
		
		List<Comercial> listComercial = jdbcTemplate.query(
                "SELECT * FROM ventas.comercial",
                (rs, rowNum) -> new Comercial(rs.getInt("id"), 
                							  rs.getString("nombre"), 
                							  rs.getString("apellido1"),
                							  rs.getString("apellido2"), 
                							  rs.getFloat("comisión"))
                						 	
        );
		
		log.info("Devueltos {} registros.", listComercial.size());
		
        return listComercial;
	}

	/**
	 * Función que devuelve un comercial, si devuelve null, devuelve null
	 * @param id
	 * @return
	 */
	@Override
	public Optional<Comercial> find(int id) {
		// TODO Auto-generated method stub
		Comercial fab =  jdbcTemplate
				.queryForObject("SELECT * FROM ventas.comercial WHERE id = ?"
						, (rs, rowNum) -> new Comercial(rs.getInt("id"),
								rs.getString("nombre"),
								rs.getString("apellido1"),
								rs.getString("apellido2"),
								rs.getFloat("comisión"))
						, id
				);

		if (fab != null) {
			return Optional.of(fab);}
		else {
			log.info("Comercial no encontrado.");
			return Optional.empty(); }
	}

	/**
	 * Función que update los valores de Comercial
	 * @param cliente
	 */
	@Override
	public void update(Comercial cliente) {
		// TODO Auto-generated method stub
		int rows = jdbcTemplate.update("""
										UPDATE ventas.comercial SET 
														nombre = ?, 
														apellido1 = ?, 
														apellido2 = ?,
														comisión = ?  
												WHERE id = ?
										""", cliente.getNombre()
				, cliente.getApellido1()
				, cliente.getApellido2()
				, cliente.getComision()
				, cliente.getId());

		log.info("Update de Comercial con {} registros actualizados.", rows);
	}

	/**
	 * Funcion que coge y borra según el id, primero cliente id de pedido
	 * y luego el de comercial
	 * @param id
	 */
	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		int row1 =jdbcTemplate.update("DELETE from ventas.pedido where id_comercial = ?", id );
		int rows = jdbcTemplate.update("DELETE FROM ventas.comercial WHERE id = ?", id);

		log.info("Delete de comercial con {} registros eliminados.", rows);
	}

}
