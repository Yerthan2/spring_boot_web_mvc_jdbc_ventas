package org.iesvdm.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class PedidoDAOImplements implements PedidoDAO{
    private final JdbcTemplate jdbcTemplate;

    public PedidoDAOImplements(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Pedido> findByIdComercial(Integer id_comercial) {
        String sql = """
        SELECT 
            p.id, p.total, p.fecha,
            c.id AS c_id, c.nombre AS c_nombre, c.apellido1 AS c_apellido1, c.apellido2 AS c_apellido2,
            c.ciudad, c.categoría, c.email as c_email,
            com.id AS com_id, com.nombre AS com_nombre, com.apellido1 AS com_apellido1,
            com.apellido2 AS com_apellido2, com.comisión
        FROM ventas.pedido p
        JOIN ventas.cliente c ON p.id_cliente = c.id
        JOIN ventas.comercial com ON p.id_comercial = com.id
        WHERE com.id = ?
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Cliente cliente = new Cliente(
                    rs.getInt("c_id"),
                    rs.getString("c_nombre"),
                    rs.getString("c_apellido1"),
                    rs.getString("c_apellido2"),
                    rs.getString("ciudad"),
                    rs.getInt("categoría"),
                    rs.getString("c_email")
            );

            Comercial comercial = new Comercial(
                    rs.getInt("com_id"),
                    rs.getString("com_nombre"),
                    rs.getString("com_apellido1"),
                    rs.getString("com_apellido2"),
                    rs.getBigDecimal("comisión")
            );

            Pedido pedido = new Pedido();
            pedido.setId(rs.getInt("id"));
            pedido.setTotal(rs.getDouble("total"));
            pedido.setFecha(rs.getDate("fecha").toLocalDate());

            pedido.setId_cliente(cliente);
            pedido.setId_comercial(comercial);

            return pedido;

        }, id_comercial);
    }
}
