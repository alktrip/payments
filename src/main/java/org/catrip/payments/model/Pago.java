package org.catrip.payments.model;

import lombok.*;
import org.catrip.payments.entity.PagoEntity;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Pago {

    private Long id;

    private String concepto;

    private Long cantidadProductos;

    private String cliente;

    private String vendedor;

    private BigDecimal monto;

    private String estatusPago;

    public Pago(PagoEntity pagoEntity){
        this.id = pagoEntity.getId();
        this.concepto = pagoEntity.getConcepto();
        this.cantidadProductos = pagoEntity.getCantidadProductos();
        this.cliente = pagoEntity.getCliente();
        this.vendedor = pagoEntity.getVendedor();
        this.monto = pagoEntity.getMonto();
        this.estatusPago = pagoEntity.getEstatusPago();
    }
}
