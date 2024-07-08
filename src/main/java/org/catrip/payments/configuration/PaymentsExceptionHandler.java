package org.catrip.payments.configuration;

import org.catrip.payments.exception.PaymentsException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.catrip.payments.constants.Constants.OPERATION_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class PaymentsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = PaymentsException.class)
    public ProblemDetail handleConflict(Exception ex, WebRequest request) {

        return generaNotificacion(ex.getMessage());
    }

    private ProblemDetail generaNotificacion(String mensaje) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(NOT_FOUND, mensaje);
        problemDetail.setTitle(OPERATION_NOT_FOUND);
        return problemDetail;
    }
}
