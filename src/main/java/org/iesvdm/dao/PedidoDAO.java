package org.iesvdm.dao;

import org.iesvdm.modelo.Pedido;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoDAO {

    List<Pedido> findByIdComercial(Integer id_comercial);

}
