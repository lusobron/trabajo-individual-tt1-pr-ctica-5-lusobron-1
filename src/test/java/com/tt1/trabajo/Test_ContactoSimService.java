package com.tt1.trabajo;

import com.tt1.trabajo.client.api.ResultadosApi;
import com.tt1.trabajo.client.api.SolicitudApi;
import com.tt1.trabajo.client.model.ResultsResponse;
import com.tt1.trabajo.client.model.SolicitudResponse;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicios.ContactoSimService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Test_ContactoSimService {

    private ContactoSimService servicio;
    private ResultadosApi resultadosApiMock;
    private SolicitudApi solicitudApiMock;

    @BeforeEach
    void setUp() {
        resultadosApiMock = mock(ResultadosApi.class);
        solicitudApiMock = mock(SolicitudApi.class);
        servicio = new ContactoSimService(resultadosApiMock, solicitudApiMock);
    }

    @Test
    void testGetEntitiesNoEstaVacia() {
        assertFalse(servicio.getEntities().isEmpty());
    }

    @Test
    void testGetEntitiesTieneNombre() {
        Entidad primera = servicio.getEntities().get(0);
        assertNotNull(primera.getName());
        assertFalse(primera.getName().isEmpty());
    }

    @Test
    void testIsValidEntityIdConIdExistente() {
        int idValido = servicio.getEntities().get(0).getId();
        assertTrue(servicio.isValidEntityId(idValido));
    }

    @Test
    void testIsValidEntityIdConIdInexistente() {
        assertFalse(servicio.isValidEntityId(99999));
    }

    @Test
    void testSolicitarSimulationDevuelveTokenPositivo() {
        SolicitudResponse respuesta = new SolicitudResponse();
        respuesta.setDone(true);
        respuesta.setTokenSolicitud(12345);

        when(solicitudApiMock.solicitudSolicitarPost(anyString(), any())).thenReturn(respuesta);

        Map<Integer, Integer> nums = new HashMap<>();
        int idValido = servicio.getEntities().get(0).getId();
        nums.put(idValido, 5);
        DatosSolicitud sol = new DatosSolicitud(nums);

        int token = servicio.solicitarSimulation(sol);

        assertEquals(12345, token);
    }

    @Test
    void testSolicitarSimulationDevuelveMenosUnoSiFalla() {
        SolicitudResponse respuesta = new SolicitudResponse();
        respuesta.setDone(false);
        respuesta.setErrorMessage("Error simulado");

        when(solicitudApiMock.solicitudSolicitarPost(anyString(), any())).thenReturn(respuesta);

        Map<Integer, Integer> nums = new HashMap<>();
        DatosSolicitud sol = new DatosSolicitud(nums);

        int token = servicio.solicitarSimulation(sol);

        assertEquals(-1, token);
    }

    @Test
    void testDescargarDatosDevuelveObjeto() {
        ResultsResponse respuesta = new ResultsResponse();
        respuesta.setDone(true);
        respuesta.setData("2\n0,0,0,red\n0,1,0,blue");

        when(resultadosApiMock.resultadosPost(anyString(), anyInt())).thenReturn(respuesta);

        DatosSimulation resultado = servicio.descargarDatos(12345);

        assertNotNull(resultado);
        assertEquals(2, resultado.getAnchoTablero());
    }

    @Test
    void testDescargarDatosLanzaErrorSiTokenInvalido() {
        when(resultadosApiMock.resultadosPost(anyString(), anyInt()))
                .thenThrow(org.springframework.web.client.HttpClientErrorException.BadRequest.class);

        assertThrows(org.springframework.web.client.HttpClientErrorException.class,
                () -> servicio.descargarDatos(99999));
    }
}