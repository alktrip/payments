package org.catrip.payments.service;

import org.catrip.payments.exception.PaymentsException;
import org.catrip.payments.model.Pago;
import org.catrip.payments.model.PagoResponse;

public interface PagoService {
    PagoResponse crearPago(Pago pago);

    Pago consultarPago(Long id) throws PaymentsException;

    Pago actualizarEstatusPago(Long id, String newStatus) throws PaymentsException;

    void enviarMensajeKafka(Pago pago);
}
