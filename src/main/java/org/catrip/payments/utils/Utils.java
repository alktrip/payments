package org.catrip.payments.utils;

import org.catrip.payments.entity.PagoEntity;
import org.catrip.payments.model.Pago;

import java.util.Objects;

public class Utils {

    public static PagoEntity getPagoEntity(Pago pago) {

        return Objects.isNull(pago) ? null : PagoEntity.builder()
                .monto(pago.getMonto())
                .cantidadProductos(pago.getCantidadProductos())
                .cliente(pago.getCliente())
                .vendedor(pago.getVendedor())
                .concepto(pago.getConcepto())
                .estatusPago(pago.getEstatusPago())
                .build();
    }

    public static Pago getPago(PagoEntity pagoEntity) {

        return Objects.isNull(pagoEntity) ? null : Pago.builder()
                .id(pagoEntity.getId())
                .monto(pagoEntity.getMonto())
                .cantidadProductos(pagoEntity.getCantidadProductos())
                .cliente(pagoEntity.getCliente())
                .vendedor(pagoEntity.getVendedor())
                .concepto(pagoEntity.getConcepto())
                .estatusPago(pagoEntity.getEstatusPago())
                .build();
    }
}
