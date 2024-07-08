package org.catrip.payments.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PagoResponse {

    private Long id;
    private String mensaje;
    private String estatus;
}
