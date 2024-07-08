package org.catrip.payments.controller;

import lombok.extern.slf4j.Slf4j;
import org.catrip.payments.exception.PaymentsException;
import org.catrip.payments.model.Pago;
import org.catrip.payments.model.PagoResponse;
import org.catrip.payments.service.impl.PagoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicio/pagos")
@Slf4j
public class PagoController {

    @Autowired
    private PagoServiceImpl pagoServiceImpl;

    @PostMapping("/pagar")
    public ResponseEntity<PagoResponse> crearPago(
            @RequestBody Pago pago) {

        log.info("[PagoController] -> crearPago -> iniciado");

        PagoResponse nuevoPago = pagoServiceImpl.crearPago(pago);

        log.info("[PagoController] -> crearPago -> finalizado");

        return ResponseEntity.ok(nuevoPago);
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Pago> consultarPago(
            @PathVariable("id") Long id) throws PaymentsException {

        log.info("[PagoController] -> consultarPago -> iniciado");

        Pago pago = pagoServiceImpl.consultarPago(id);

        log.info("[PagoController] -> consultarPago -> finalizado");

        return ResponseEntity.ok(pago);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Pago> actualizarEstatusPago(
            @PathVariable("id") Long id,
            @RequestParam("estatus") String nuevoEstatus) throws PaymentsException {

        log.info("[PagoController] -> actualizarEstatusPago -> iniciado");

        Pago pagoActualizado = pagoServiceImpl.actualizarEstatusPago(id, nuevoEstatus);

        log.info("[PagoController] -> actualizarEstatusPago -> finalizado");

        return ResponseEntity.ok(pagoActualizado);

    }
}
