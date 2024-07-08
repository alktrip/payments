package org.catrip.payments.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.catrip.payments.entity.PagoEntity;
import org.catrip.payments.exception.PaymentsException;
import org.catrip.payments.model.Pago;
import org.catrip.payments.model.PagoResponse;
import org.catrip.payments.repository.PagoRepository;
import org.catrip.payments.service.PagoService;
import org.catrip.payments.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.catrip.payments.constants.Constants.OPERATION_DONE;

@Service
@Slf4j
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private KafkaTemplate<String, Pago> kafkaTemplate;

    @Override
    public PagoResponse crearPago(Pago pago) {

        log.info("[PagoServiceImpl] -> crearPago -> iniciado");

        PagoEntity payment = pagoRepository.save(Objects.requireNonNull(Utils.getPagoEntity(pago)));

        Pago pagoDato = Utils.getPago(payment);

        log.info("[PagoServiceImpl] -> " + Objects.requireNonNull(pagoDato));

        log.info("[PagoServiceImpl] -> crearPago -> finalizado");

        return PagoResponse.builder()
                .id(Objects.requireNonNull(pagoDato).getId())
                .estatus(Objects.requireNonNull(pagoDato).getEstatusPago())
                .mensaje(OPERATION_DONE).build();
    }

    @Override
    public Pago consultarPago(Long id) throws PaymentsException {

        log.info("[PagoServiceImpl] -> consultarPago -> iniciado");

        PagoEntity pagoEntity = pagoRepository.findById(id).orElse(null);

        if(Objects.isNull(pagoEntity)){
            throw new PaymentsException("No se ha localizado pago correspondiente");
        }

        log.info("[PagoServiceImpl] -> consultarPago -> finalizado");

        return Utils.getPago(Objects.requireNonNull(pagoEntity));
    }

    @Override
    public Pago actualizarEstatusPago(Long id, String newStatus) throws PaymentsException {

        log.info("[PagoServiceImpl] -> actualizarEstatusPago -> iniciado");

        PagoEntity pagoEntity = pagoRepository.findById(id).orElse(null);

        Pago pago = Utils.getPago(pagoEntity);

        if (Objects.nonNull(pago)) {

            pago.setEstatusPago(newStatus);

            pagoRepository.save(Objects.requireNonNull(Utils.getPagoEntity(pago)));

            enviarMensajeKafka(pago);
        } else {
            throw new PaymentsException("No se ha localizado pago correspondiente");
        }

        log.info("[PagoServiceImpl] -> actualizarEstatusPago -> finalizado");

        return pago;
    }

    @Override
    public void enviarMensajeKafka(Pago pago) {

        log.info("[PagoServiceImpl] -> enviarMensajeKafka -> iniciado");

        kafkaTemplate.send("pagos-topic", pago);

        log.info("[PagoServiceImpl] -> enviarMensajeKafka -> finalizado");
    }
}

