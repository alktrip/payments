package org.catrip.payments.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "pago")
@AllArgsConstructor
@Getter
@Builder
public class PagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "cantidad_productos")
    private Long cantidadProductos;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "vendedor")
    private String vendedor;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "estatus_pago")
    private String estatusPago;
}
