package servicios;

import com.tt1.trabajo.client.api.ResultadosApi;
import com.tt1.trabajo.client.api.SolicitudApi;
import com.tt1.trabajo.client.invoker.ApiClient;
import com.tt1.trabajo.client.model.ResultsResponse;
import com.tt1.trabajo.client.model.Solicitud;
import com.tt1.trabajo.client.model.SolicitudResponse;
import interfaces.InterfazContactoSim;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;
import modelo.Punto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactoSimService implements InterfazContactoSim {

    private static final String NOMBRE_USUARIO = "usuarioPrueba";

    private final List<Entidad> entidades;
    private final List<DatosSolicitud> solicitudesPendientes = new ArrayList<>();
    private final ResultadosApi resultadosApi;
    private final SolicitudApi solicitudApi;

    @Autowired
    public ContactoSimService(@Value("${servicio-consumible.base-url}") String baseUrl) {
        this(construirResultadosApi(baseUrl), construirSolicitudApi(baseUrl));
    }

    public ContactoSimService(ResultadosApi resultadosApi, SolicitudApi solicitudApi) {
        this.resultadosApi = resultadosApi;
        this.solicitudApi = solicitudApi;

        entidades = new ArrayList<>();

        String[][] datos = {
                {"1", "Lobo",     "Depredador del bosque"},
                {"2", "Ciervo",   "Herbívoro de pradera"},
                {"3", "Conejo",   "Pequeño herbívoro"},
                {"4", "Zorro",    "Omnívoro adaptable"},
                {"5", "Águila",   "Ave rapaz"},
        };

        for (String[] d : datos) {
            Entidad e = new Entidad();
            e.setId(Integer.parseInt(d[0]));
            e.setName(d[1]);
            e.setDescripcion(d[2]);
            entidades.add(e);
        }
    }

    private static ResultadosApi construirResultadosApi(String baseUrl) {
        RestClient restClient = ApiClient.buildRestClientBuilder().baseUrl(baseUrl).build();
        return new ResultadosApi(new ApiClient(restClient));
    }

    private static SolicitudApi construirSolicitudApi(String baseUrl) {
        RestClient restClient = ApiClient.buildRestClientBuilder().baseUrl(baseUrl).build();
        return new SolicitudApi(new ApiClient(restClient));
    }

    @Override
    public int solicitarSimulation(DatosSolicitud sol) {
        solicitudesPendientes.add(sol);

        List<Integer> cantidades = new ArrayList<>();
        List<String> nombres = new ArrayList<>();

        sol.getNums().forEach((entidadId, cantidad) ->
                entidades.stream()
                        .filter(e -> e.getId() == entidadId)
                        .findFirst()
                        .ifPresent(e -> {
                            nombres.add(e.getName());
                            cantidades.add(cantidad);
                        })
        );

        Solicitud solicitud = new Solicitud();
        solicitud.setCantidadesIniciales(cantidades);
        solicitud.setNombreEntidades(nombres);

        try {
            SolicitudResponse respuesta = solicitudApi.solicitudSolicitarPost(NOMBRE_USUARIO, solicitud);
            if (respuesta != null && Boolean.TRUE.equals(respuesta.getDone())) {
                return respuesta.getTokenSolicitud();
            }
            return -1;
        } catch (RestClientResponseException e) {
            return -1;
        }
    }

    @Override
    public DatosSimulation descargarDatos(int ticket) {
        ResultsResponse respuesta = resultadosApi.resultadosPost(NOMBRE_USUARIO, ticket);
        return parsearResultado(respuesta);
    }

    /**
     * Convierte el campo "data" de la respuesta
     * (formato: primera línea = ancho del tablero; resto = "tiempo,x,y,color")
     * en un DatosSimulation.
     */
    private DatosSimulation parsearResultado(ResultsResponse respuesta) {
        DatosSimulation ds = new DatosSimulation();
        Map<Integer, List<Punto>> puntos = new HashMap<>();

        if (respuesta == null || respuesta.getData() == null || respuesta.getData().isBlank()) {
            ds.setAnchoTablero(0);
            ds.setMaxSegundos(0);
            ds.setPuntos(puntos);
            return ds;
        }

        String[] lineas = respuesta.getData().split("\\R");
        int ancho = Integer.parseInt(lineas[0].trim());
        int maxTiempo = 0;

        for (int i = 1; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            if (linea.isEmpty()) continue;

            String[] campos = linea.split(",");
            int tiempo = Integer.parseInt(campos[0].trim());
            int x = Integer.parseInt(campos[1].trim());
            int y = Integer.parseInt(campos[2].trim());
            String color = campos[3].trim();

            Punto p = new Punto();
            p.setX(x);
            p.setY(y);
            p.setColor(color);

            puntos.computeIfAbsent(tiempo, k -> new ArrayList<>()).add(p);
            maxTiempo = Math.max(maxTiempo, tiempo + 1);
        }

        ds.setAnchoTablero(ancho);
        ds.setMaxSegundos(maxTiempo);
        ds.setPuntos(puntos);
        return ds;
    }

    @Override
    public List<Entidad> getEntities() {
        return entidades;
    }

    @Override
    public boolean isValidEntityId(int id) {
        return entidades.stream().anyMatch(e -> e.getId() == id);
    }
}