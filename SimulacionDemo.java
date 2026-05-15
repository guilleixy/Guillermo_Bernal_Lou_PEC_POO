import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase de demo que configura y ejecuta una simulación completa sin pasar por la interfaz textual.
 * Equivale a hacer manualmente todos los pasos del menú: stock, trabajadores, pedidos y simulación.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class SimulacionDemo
{
    public static void main(String[] args) {

        // --- Infraestructura ---
        IAlmacen almacen = new Almacen();
        RecursosHumanos rrhh = new RecursosHumanos();
        Dashboard.configurarVisualizador(new VisualizadorTerminal());

        List<CadenaMontaje> cadenas = new ArrayList<>();
        cadenas.add(new CadenaMontaje("Línea A - Turismo"));
        cadenas.add(new CadenaMontaje("Línea B - Biplaza"));
        cadenas.add(new CadenaMontaje("Línea C - Furgoneta"));

        Planificador planificador = new Planificador(cadenas, rrhh, almacen);
        new SistemaGestion(almacen, rrhh, new Dashboard(), planificador);

        // --- Stock de componentes ---
        almacen.añadirStockComponente(new Chasis(), 10);
        almacen.añadirStockComponente(new MotorElectrico(1600, 120, 4), 10);
        almacen.añadirStockComponente(new Tapiceria("Negro", 5.0, TapiceriaTipoMaterial.TELA), 10);
        almacen.añadirStockComponente(new Ruedas(205, 16, 91, 210, RuedasTipo.NORMAL), 10);

        // --- Trabajadores ---
        Date hoy = new Date();

        // 12 operarios (4 por cadena)
        for (int i = 1; i <= 12; i++) {
            rrhh.altaTrabajador(new TrabajadorOperario(
                "0000000" + i, "Operario" + i, "Apellido", "Dirección",
                10000 + i, 1500.0, TrabajadorPuesto.OPERARIO, hoy,
                TrabajadorNivelProductividad.ESTANDAR
            ));
        }

        // Mecánico (necesario para COMPLEJA y MUY_COMPLEJA)
        rrhh.altaTrabajador(new TrabajadorMecanicoCinta(
            "MEC00001", "MecánicoPedro", "Apellido", "Dirección",
            20001, 2000.0, TrabajadorPuesto.MECANICO_CINTA, hoy,
            TrabajadorNivelProductividad.ESTANDAR
        ));

        // Gestor de planta (necesario para MUY_COMPLEJA)
        rrhh.altaTrabajador(new TrabajadorGestorPlanta(
            "GES00001", "GestorLuis", "Apellido", "Dirección",
            30001, 2500.0, TrabajadorPuesto.GESTOR_PLANTA, hoy
        ));

        // Administrador de sistemas (necesario para MUY_COMPLEJA)
        rrhh.altaTrabajador(new TrabajadorAdministradorSistema(
            "ADM00001", "AdminMaria", "Apellido", "Dirección",
            40001, 3000.0, TrabajadorPuesto.ADMINISTRADOR_SISTEMAS, hoy
        ));

        // --- Pedidos ---
        planificador.añadirPedido(new VehiculoTurismo("Rojo", 5, 1200.0, 500.0));
        planificador.añadirPedido(new VehiculoBiplaza("Azul", 2, 900.0, 300.0));
        planificador.añadirPedido(new VehiculoFurgoneta("Blanco", 3, 2000.0, 1500.0));

        // --- Simulación ---
        // Cambia SimulacionModo.SIMPLE por COMPLEJA o MUY_COMPLEJA para probar otros modos
        planificador.empezarSimulacion(SimulacionModo.COMPLEJA);

        // --- Resultado final ---
        Dashboard.mostrarEstado(almacen.obtenerEstadoCompleto(), obtenerEstadoCadenas(cadenas));
    }

    private static String obtenerEstadoCadenas(List<CadenaMontaje> cadenas) {
        String s = "";
        for (CadenaMontaje c : cadenas) {
            s += c.obtenerIdentificadorCadena() + " -> ";
            Vehiculo v = c.obtenerVehiculoEnCurso();
            s += (v != null) ? "OCUPADA [" + v.obtenerTipo() + "]" : "LIBRE";
            s += "\n";
        }
        return s;
    }
}
