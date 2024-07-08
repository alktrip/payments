package org.catrip.payments.service.impl;

import org.catrip.payments.configuration.PaymentsExceptionHandler;
import org.catrip.payments.entity.PagoEntity;
import org.catrip.payments.exception.PaymentsException;
import org.catrip.payments.model.Pago;
import org.catrip.payments.model.PagoResponse;
import org.catrip.payments.repository.PagoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import org.springframework.http.ProblemDetail;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PagoServiceImplTest {

    @InjectMocks
    PagoServiceImpl pagoServiceImpl;

    @Mock
    PagoRepository pagoRepository;

    @Mock
    KafkaTemplate<String, Pago> kafkaTemplateMock;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void creaPagoTest(){
        when(pagoRepository.save(any())).thenReturn(getPagoEntityTest());

        PagoResponse pagoResponse = pagoServiceImpl.crearPago(getPagoTest());

        Assertions.assertNotNull(pagoResponse);
        assertEquals(pagoResponse.getEstatus(), "PAGADO");
        Assertions.assertNotNull(pagoResponse.getId());
        Assertions.assertNotNull(pagoResponse.getMensaje());
    }

    @Test
    public void consultaPagoTest() throws PaymentsException {
        when(pagoRepository.findById(anyLong())).thenReturn(Optional.ofNullable(getPagoEntityTest()));

        Pago pago = pagoServiceImpl.consultarPago(1L);

        Assertions.assertNotNull(pago);
        assertEquals(pago.getCliente(), "TEST TEST TEST");
    }

    @Test
    public void consultaPagoTestNull() throws PaymentsException {
        when(pagoRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(PaymentsException.class, () -> pagoServiceImpl.consultarPago(1L));
    }

    @Test
    public void actualizarPagoTest() throws PaymentsException {

        when(pagoRepository.findById(anyLong())).thenReturn(Optional.ofNullable(getPagoEntityTest()));
        when(pagoRepository.save(any())).thenReturn(getPagoEntityTest());
        when(kafkaTemplateMock.send(anyString(), any())).thenReturn(getKafKaData());

        Pago pago = pagoServiceImpl.actualizarEstatusPago(1L, "TEST");

        Assertions.assertNotNull(pago);
    }

    @Test
    public void actualizarPagoTestNull() throws PaymentsException {

        when(pagoRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(PaymentsException.class, () -> pagoServiceImpl.actualizarEstatusPago(1L, "TEST"));
    }

    @Test
    public void testNullPointerException() throws Exception {
        PaymentsExceptionHandler paymentsExceptionHandler = new PaymentsExceptionHandler();

        NullPointerException exception = mock(NullPointerException.class);

        ProblemDetail response = paymentsExceptionHandler.handleConflict(exception, null);

        assertNotNull(response);
    }

    private CompletableFuture<SendResult<String, Pago>> getKafKaData() {
        CompletableFuture<SendResult<String, Pago>> future = new CompletableFuture<>();
        return future;
    }

    private Pago getPagoTest() {
        return new Pago(getPagoEntityTest());
    }

    private PagoEntity getPagoEntityTest() {
        return PagoEntity.builder()
                .id(1L)
                .estatusPago("PAGADO")
                .monto(BigDecimal.valueOf(123.321))
                .cliente("TEST TEST TEST")
                .vendedor("VTEST VTEST VTEST")
                .cantidadProductos(10L)
                .concepto("concepto test")
                .build();
    }
}
