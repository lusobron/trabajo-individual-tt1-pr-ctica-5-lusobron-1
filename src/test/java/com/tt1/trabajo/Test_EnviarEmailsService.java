package com.tt1.trabajo;

import com.tt1.mocks.LoggerFake;
import modelo.Destinatario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicios.EnviarEmailsService;

import static org.junit.jupiter.api.Assertions.*;

class Test_EnviarEmailsService {

    private EnviarEmailsService servicio;
    private LoggerFake loggerFake;

    @BeforeEach
    void setUp() {
        loggerFake = new LoggerFake();
        servicio = new EnviarEmailsService(loggerFake);
    }

    @Test
    void testEnvioCorrectamenteDevuelveTrue() {
        boolean resultado = servicio.enviarEmail(new Destinatario(), "Hola, esto es un test");
        assertTrue(resultado);
    }

    @Test
    void testEnvioLoggea() {
        servicio.enviarEmail(new Destinatario(), "Mensaje de prueba");
        assertFalse(loggerFake.mensajesInfo.isEmpty());
    }

    @Test
    void testDestinatarioNuloDevuelveFalse() {
        boolean resultado = servicio.enviarEmail(null, "Mensaje");
        assertFalse(resultado);
    }

    @Test
    void testMensajeVacioDevuelveFalse() {
        boolean resultado = servicio.enviarEmail(new Destinatario(), "");
        assertFalse(resultado);
    }

    @Test
    void testMensajeVacioLoggeaWarning() {
        servicio.enviarEmail(new Destinatario(), "");
        assertFalse(loggerFake.mensajesWarn.isEmpty());
    }

    @Test
    void testMensajeNuloDevuelveFalse() {
        boolean resultado = servicio.enviarEmail(new Destinatario(), null);
        assertFalse(resultado);
    }
}
