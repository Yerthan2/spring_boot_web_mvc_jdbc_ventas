package org.iesvdm.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class Pedido {

    private Integer id;
    private Double total;
    private LocalDate fecha;
    private Cliente id_cliente;
    private Comercial id_comercial;

    public Pedido() {
    }
}
