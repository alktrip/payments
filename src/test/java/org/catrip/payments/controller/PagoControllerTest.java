package org.catrip.payments.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.catrip.payments.configuration.PaymentsExceptionHandler;
import org.catrip.payments.entity.PagoEntity;
import org.catrip.payments.model.Pago;
import org.catrip.payments.service.impl.PagoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PagoControllerTest {

    @InjectMocks
    PagoController pagoController;

    @Mock
    ResponseEntity responseEntity;

    @Mock
    PagoServiceImpl pagoServiceImpl;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(pagoController).setControllerAdvice(new PaymentsExceptionHandler()).build();
    }

    @Test
    public void creaPagoTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.POST, "/servicio/pagos/pagar")
                .content(objectMapper.writeValueAsString(getPagoExample()))
                .contentType("application/json");

        mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());

    }

    @Test
    public void consultaPagoTest() throws Exception {

        when(pagoServiceImpl.consultarPago(anyLong())).thenReturn(Pago.builder().estatusPago("TEST").monto(BigDecimal.valueOf(12.21)).id(1L).cantidadProductos(12L).cliente("TEST TEST TEST").vendedor("VTEST VTEST VTEST").build());

        MockHttpServletRequestBuilder requestBuilder =
                request(HttpMethod.GET,
                        "/servicio/pagos/consultar/{id}", 1L)
                .contentType("application/json");

        mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());

    }

    @Test
    public void actualizaPagoTest() throws Exception {

        when(pagoServiceImpl.actualizarEstatusPago(anyLong(), anyString())).thenReturn(Pago.builder().estatusPago("TEST").monto(BigDecimal.valueOf(12.21)).id(1L).cantidadProductos(12L).cliente("TEST TEST TEST").vendedor("VTEST VTEST VTEST").build());

        MockHttpServletRequestBuilder requestBuilder =
                request(HttpMethod.PUT,
                        "/servicio/pagos/actualizar/{id}", 1L)
                        .param("estatus", "PAGADO")
                        .contentType("application/json");

        mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());

    }

    private Pago getPagoExample() {
        return Pago.builder()
                .id(1L)
                .estatusPago("PAGADO")
                .monto(BigDecimal.valueOf(12.34))
                .cliente("PEDRO PEREZ PEREZ")
                .vendedor("SERGIO SUAREZ SUAREZ")
                .cantidadProductos(10L)
                .build();
    }
}
