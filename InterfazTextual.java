import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Interfaz textual del sistema que gestiona la interacción con el usuario.
 *
 * @author Guillermo Bernal
 * @version 12 de mayo de 2026
 */
public class InterfazTextual {

    private IAlmacen almacen;
    private RecursosHumanos rrhh;
    private Planificador planificador;
    private List<CadenaMontaje> cadenas;
    private static final SimpleDateFormat SDF = new SimpleDateFormat(
        "dd/MM/yyyy"
    );

    public InterfazTextual(
        IAlmacen almacen,
        RecursosHumanos rrhh,
        Planificador planificador,
        List<CadenaMontaje> cadenas
    ) {
        this.almacen = almacen;
        this.rrhh = rrhh;
        this.planificador = planificador;
        this.cadenas = cadenas;
    }

    /**
     * Lanza el bucle principal del menú de la aplicación.
     */
    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            Dashboard.imprimirCadena(
                "\n========== SISTEMA DE GESTIÓN DE FÁBRICA =========="
            );
            Dashboard.imprimirCadena(" 1. Gestión de Almacén");
            Dashboard.imprimirCadena(" 2. Gestión de Trabajadores");
            Dashboard.imprimirCadena(" 3. Producción y Simulación");
            Dashboard.imprimirCadena(" 4. Listados y Estadísticas");
            Dashboard.imprimirCadena(" 0. Salir");
            Dashboard.imprimirCadena(
                "===================================================="
            );

            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    menuAlmacen();
                    break;
                case 2:
                    menuTrabajadores();
                    break;
                case 3:
                    menuProduccion();
                    break;
                case 4:
                    menuListados();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    Dashboard.mostrarError("Opción no válida.");
            }
        }
        Dashboard.mostrarMensaje("Cerrando el sistema de forma segura...");
    }

    private void menuAlmacen() {
        boolean volver = false;
        while (!volver) {
            Dashboard.imprimirCadena("\n--- GESTIÓN DE ALMACÉN ---");
            Dashboard.imprimirCadena(" 1. Añadir stock de componente");
            Dashboard.imprimirCadena(" 2. Ver estado del almacén");
            Dashboard.imprimirCadena(" 3. Consultar historial por fecha");
            Dashboard.imprimirCadena(" 0. Volver");

            switch (leerEntero("Opción: ")) {
                case 1:
                    añadirStockComponente();
                    break;
                case 2:
                    Dashboard.imprimirCadena(almacen.obtenerEstadoCompleto());
                    break;
                case 3:
                    consultarHistorial();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    Dashboard.mostrarError("Opción no válida.");
            }
        }
    }

    private void añadirStockComponente() {
        Dashboard.imprimirCadena(
            "Tipo: 1.Chasis 2.MotorE 3.MotorG 4.MotorH 5.Tapicería 6.Ruedas"
        );
        ComponenteVehiculo comp = crearComponente(leerEntero("Selección: "));
        if (comp != null) {
            int cantidad = leerEntero("Cantidad a ingresar: ");
            almacen.añadirStockComponente(comp, cantidad);
            Dashboard.mostrarMensaje("Inventario actualizado correctamente.");
        }
    }

    private ComponenteVehiculo crearComponente(int tipo) {
        switch (tipo) {
            case 1:
                return new Chasis();
            case 2:
                return new MotorElectrico(
                    leerEntero("Cil: "),
                    leerEntero("Pot: "),
                    leerEntero("NumC: ")
                );
            case 3:
                return new MotorGasolina(
                    leerEntero("Cil: "),
                    leerEntero("Pot: "),
                    leerEntero("NumC: ")
                );
            case 4:
                return new MotorHibrido(
                    leerEntero("Cil: "),
                    leerEntero("Pot: "),
                    leerEntero("NumC: ")
                );
            case 5:
                String color = leerTexto("Color: ");
                double m2 = leerDecimal("Metros cuadrados: ");
                int matIdx = leerEntero(
                    "Material (1.Tela 2.Cuero 3.Alcántara): "
                );
                TapiceriaTipoMaterial mat =
                    matIdx == 2
                        ? TapiceriaTipoMaterial.CUERO
                        : matIdx == 3
                            ? TapiceriaTipoMaterial.ALCANTARA
                            : TapiceriaTipoMaterial.TELA;
                return new Tapiceria(color, m2, mat);
            case 6:
                double ancho = leerDecimal("Ancho: ");
                double diam = leerDecimal("Diámetro: ");
                double carga = leerDecimal("Carga: ");
                double vel = leerDecimal("Velocidad: ");
                int rIdx = leerEntero(
                    "Tipo (1.Normal 2.Deportivo 3.Todoterreno): "
                );
                RuedasTipo rt =
                    rIdx == 2
                        ? RuedasTipo.DEPORTIVO
                        : rIdx == 3
                            ? RuedasTipo.TODOTERRENO
                            : RuedasTipo.NORMAL;
                return new Ruedas(ancho, diam, carga, vel, rt);
            default:
                Dashboard.mostrarError("Componente no reconocido.");
                return null;
        }
    }

    private void menuTrabajadores() {
        boolean volver = false;
        while (!volver) {
            Dashboard.imprimirCadena("\n--- GESTIÓN DE TRABAJADORES ---");
            Dashboard.imprimirCadena(" 1. Alta trabajador");
            Dashboard.imprimirCadena(" 2. Buscar por DNI");
            Dashboard.imprimirCadena(" 0. Volver");
            switch (leerEntero("Opción: ")) {
                case 1:
                    altaTrabajador();
                    break;
                case 2:
                    buscarPorDni();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    Dashboard.mostrarError("Opción no válida.");
            }
        }
    }

    private void altaTrabajador() {
        String dni = leerTexto("DNI: ");
        String nombre = leerTexto("Nombre: ");
        double sueldo = leerDecimal("Salario base: ");
        Dashboard.imprimirCadena(
            "Puesto: 1.Operario 2.Gestor 3.Admin 4.Mecánico"
        );
        int p = leerEntero("Selección: ");

        TrabajadorPuesto tp = (p == 2)
            ? TrabajadorPuesto.GESTOR_PLANTA
            : (p == 3)
                ? TrabajadorPuesto.ADMINISTRADOR_SISTEMAS
                : (p == 4)
                    ? TrabajadorPuesto.MECANICO_CINTA
                    : TrabajadorPuesto.OPERARIO;

        Date hoy = new Date();
        rrhh.altaTrabajador(
            new TrabajadorOperario(
                dni,
                nombre,
                "Apellidos",
                "Dirección",
                12345,
                sueldo,
                tp,
                hoy,
                TrabajadorNivelProductividad.ESTANDAR
            )
        );
        Dashboard.mostrarMensaje("Trabajador registrado.");
    }

    private void buscarPorDni() {
        Trabajador t = rrhh.buscarPorDni(leerTexto("Introduzca DNI: "));
        Dashboard.imprimirCadena(
            t != null ? formatearTrabajador(t) : "Empleado no encontrado."
        );
    }

    private void menuProduccion() {
        boolean volver = false;
        while (!volver) {
            Dashboard.imprimirCadena("\n--- PRODUCCIÓN ---");
            Dashboard.imprimirCadena(" 1. Crear Pedido");
            Dashboard.imprimirCadena(" 2. Ejecutar Simulación");
            Dashboard.imprimirCadena(" 0. Volver");
            switch (leerEntero("Opción: ")) {
                case 1:
                    añadirPedido();
                    break;
                case 2:
                    iniciarSimulacion();
                    break;
                case 0:
                    volver = true;
                    break;
            }
        }
    }

    private void añadirPedido() {
        int tipo = leerEntero("Vehículo (1.Turismo 2.Biplaza 3.Furgoneta): ");
        String color = leerTexto("Color: ");
        double tara = leerDecimal("Tara: ");
        Vehiculo v = (tipo == 2)
            ? new VehiculoBiplaza(color, 2, tara, tara + 500)
            : (tipo == 3)
                ? new VehiculoFurgoneta(color, 3, tara, tara + 1500)
                : new VehiculoTurismo(color, 5, tara, tara + 800);
        planificador.añadirPedido(v);
        Dashboard.mostrarMensaje("Pedido encolado.");
    }

    private void iniciarSimulacion() {
        Dashboard.imprimirCadena("Modo: 1.Simple 2.Compleja 3.Muy Compleja");
        int m = leerEntero("Modo: ");
        SimulacionModo modo = (m == 2)
            ? SimulacionModo.COMPLEJA
            : (m == 3)
                ? SimulacionModo.MUY_COMPLEJA
                : SimulacionModo.SIMPLE;

        planificador.empezarSimulacion(modo);

        Dashboard.mostrarEstado(
            almacen.obtenerEstadoCompleto(),
            obtenerEstadoCadenas()
        );
    }

    private void menuListados() {
        Dashboard.imprimirCadena("\n--- LISTADOS ---");
        List<Vehiculo> terminados = almacen.obtenerVehiculosTerminados();
        if (terminados.isEmpty()) {
            Dashboard.mostrarMensaje("No hay datos de producción acumulados.");
        } else {
            for (Vehiculo v : terminados) {
                Dashboard.imprimirCadena(
                    "Vehículo: " +
                        v.obtenerTipo() +
                        " [" +
                        v.obtenerColor() +
                        "]"
                );
            }
        }
    }

    /**
     * Genera un reporte de las cadenas de montaje.
     */
    private String obtenerEstadoCadenas() {
        String s = "";
        for (CadenaMontaje c : cadenas) {
            s += c.obtenerIdentificadorCadena() + " -> ";
            Vehiculo v = c.obtenerVehiculoEnCurso();
            s += (v != null)
                ? "OCUPADA [" +
                  v.obtenerTipo() +
                  " Fase: " +
                  c.obtenerEstadoActual() +
                  "]"
                : "LIBRE";
            s += "\n";
        }
        return s;
    }

    private String formatearTrabajador(Trabajador t) {
        return (
            "ID: " +
            t.obtenerDni() +
            " | Nombre: " +
            t.obtenerNombre() +
            " | Puesto: " +
            t.obtenerPuesto() +
            " | Salario: " +
            t.obtenerSalario() +
            "€"
        );
    }

    private int leerEntero(String mensaje) {
        try {
            return Integer.parseInt(Dashboard.leerTexto(mensaje));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double leerDecimal(String mensaje) {
        try {
            return Double.parseDouble(Dashboard.leerTexto(mensaje));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private String leerTexto(String mensaje) {
        return Dashboard.leerTexto(mensaje);
    }

    private void consultarHistorial() {
        try {
            Date d = SDF.parse(leerTexto("Fecha inicio (dd/MM/yyyy): "));
            Date h = SDF.parse(leerTexto("Fecha fin (dd/MM/yyyy): "));
            List<RegistroMontaje> logs = almacen.consultarHistorialPorFecha(
                d,
                h
            );
            for (RegistroMontaje r : logs)
                Dashboard.imprimirCadena(r.toString());
        } catch (ParseException e) {
            Dashboard.mostrarError("Fecha no válida.");
        }
    }
}
