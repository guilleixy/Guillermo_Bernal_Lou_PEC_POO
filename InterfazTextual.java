import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        double pesoMaximo = leerDecimal("Peso máximo: ");
        Vehiculo v = (tipo == 2)
            ? new VehiculoBiplaza(color, 2, tara, pesoMaximo)
            : (tipo == 3)
                ? new VehiculoFurgoneta(color, 3, tara, pesoMaximo)
                : new VehiculoTurismo(color, 5, tara, pesoMaximo);
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
        boolean volver = false;
        while (!volver) {
            Dashboard.imprimirCadena("\n--- LISTADOS Y ESTADÍSTICAS ---");
            Dashboard.imprimirCadena(" 1. Listado de operarios");
            Dashboard.imprimirCadena(" 2. Listado de vehículos ensamblados");
            Dashboard.imprimirCadena(" 3. Configuraciones con mayor tasa de ensamblaje");
            Dashboard.imprimirCadena(" 4. Cadenas de montaje por fecha");
            Dashboard.imprimirCadena(" 0. Volver");
            switch (leerEntero("Opción: ")) {
                case 1: listadoOperarios(); break;
                case 2: listadoVehiculos(); break;
                case 3: listadoTasaEnsamblaje(); break;
                case 4: listadoCadenasPorFecha(); break;
                case 0: volver = true; break;
                default: Dashboard.mostrarError("Opción no válida.");
            }
        }
    }

    /**
     * Listado 1: operarios con filtrado por productividad y ordenación alfabética.
     */
    private void listadoOperarios() {
        Dashboard.imprimirCadena("\n--- LISTADO DE OPERARIOS ---");
        Dashboard.imprimirCadena("Filtrar por nivel: 1.Todos  2.Estándar  3.Eficiente");
        int filtro = leerEntero("Selección: ");
        Dashboard.imprimirCadena("Ordenar: 1.Nombre A-Z  2.Nombre Z-A  3.Nº montajes desc.");
        int orden = leerEntero("Selección: ");

        List<TrabajadorOperario> lista = new ArrayList<>();
        for (Trabajador t : rrhh.obtenerEmpleados()) {
            if (!(t instanceof TrabajadorOperario)) continue;
            TrabajadorOperario op = (TrabajadorOperario) t;
            if (filtro == 2 && op.obtenerNivel() != TrabajadorNivelProductividad.ESTANDAR) continue;
            if (filtro == 3 && op.obtenerNivel() != TrabajadorNivelProductividad.EFICIENTE) continue;
            lista.add(op);
        }

        if (orden == 2) {
            Collections.sort(lista, new Comparator<TrabajadorOperario>() {
                public int compare(TrabajadorOperario a, TrabajadorOperario b) {
                    return b.obtenerNombre().compareToIgnoreCase(a.obtenerNombre());
                }
            });
        } else if (orden == 3) {
            Collections.sort(lista, new Comparator<TrabajadorOperario>() {
                public int compare(TrabajadorOperario a, TrabajadorOperario b) {
                    return b.obtenerNumeroMontajes() - a.obtenerNumeroMontajes();
                }
            });
        } else {
            Collections.sort(lista, new Comparator<TrabajadorOperario>() {
                public int compare(TrabajadorOperario a, TrabajadorOperario b) {
                    return a.obtenerNombre().compareToIgnoreCase(b.obtenerNombre());
                }
            });
        }

        if (lista.isEmpty()) {
            Dashboard.mostrarMensaje("No hay operarios con esos criterios.");
            return;
        }
        Dashboard.imprimirCadena(String.format("%-12s %-20s %-12s %s", "DNI", "Nombre", "Nivel", "Montajes"));
        Dashboard.imprimirCadena("------------------------------------------------------------");
        for (TrabajadorOperario op : lista) {
            Dashboard.imprimirCadena(String.format("%-12s %-20s %-12s %d",
                op.obtenerDni(), op.obtenerNombre(), op.obtenerNivel(), op.obtenerNumeroMontajes()));
        }
    }

    /**
     * Listado 2: vehículos ensamblados con filtrado por componente y ordenación alfabética.
     */
    private void listadoVehiculos() {
        Dashboard.imprimirCadena("\n--- LISTADO DE VEHÍCULOS ENSAMBLADOS ---");
        Dashboard.imprimirCadena("Filtrar por componente (vacío = todos): ");
        String filtroComp = leerTexto("Componente (CHASIS/MOTOR/TAPICERIA/RUEDAS, vacío=todos): ").trim().toUpperCase();
        Dashboard.imprimirCadena("Ordenar: 1.Tipo A-Z  2.Tipo Z-A  3.Color A-Z");
        int orden = leerEntero("Selección: ");

        List<Vehiculo> lista = new ArrayList<>();
        for (Vehiculo v : almacen.obtenerVehiculosTerminados()) {
            if (!filtroComp.isEmpty()) {
                boolean tieneComponente = false;
                for (ComponenteTipo ct : v.obtenerComponentesInstalados()) {
                    if (ct.toString().contains(filtroComp)) { tieneComponente = true; break; }
                }
                if (!tieneComponente) continue;
            }
            lista.add(v);
        }

        if (orden == 2) {
            Collections.sort(lista, new Comparator<Vehiculo>() {
                public int compare(Vehiculo a, Vehiculo b) {
                    return b.obtenerTipo().compareToIgnoreCase(a.obtenerTipo());
                }
            });
        } else if (orden == 3) {
            Collections.sort(lista, new Comparator<Vehiculo>() {
                public int compare(Vehiculo a, Vehiculo b) {
                    return a.obtenerColor().compareToIgnoreCase(b.obtenerColor());
                }
            });
        } else {
            Collections.sort(lista, new Comparator<Vehiculo>() {
                public int compare(Vehiculo a, Vehiculo b) {
                    return a.obtenerTipo().compareToIgnoreCase(b.obtenerTipo());
                }
            });
        }

        if (lista.isEmpty()) {
            Dashboard.mostrarMensaje("No hay vehículos con esos criterios.");
            return;
        }
        Dashboard.imprimirCadena(String.format("%-15s %-10s %-8s %-10s %s", "Tipo", "Color", "Plazas", "Tara", "Componentes"));
        Dashboard.imprimirCadena("-----------------------------------------------------------------------");
        for (Vehiculo v : lista) {
            Dashboard.imprimirCadena(String.format("%-15s %-10s %-8d %-10.1f %s",
                v.obtenerTipo(), v.obtenerColor(), v.obtenerNumPlazas(), v.obtenerTara(),
                v.obtenerComponentesInstalados().toString()));
        }
    }

    /**
     * Listado 3: configuraciones de vehículos con mayor tasa de ensamblaje (más producidas).
     */
    private void listadoTasaEnsamblaje() {
        Dashboard.imprimirCadena("\n--- CONFIGURACIONES CON MAYOR TASA DE ENSAMBLAJE ---");
        List<Vehiculo> terminados = almacen.obtenerVehiculosTerminados();
        if (terminados.isEmpty()) {
            Dashboard.mostrarMensaje("No hay vehículos producidos.");
            return;
        }

        // Agrupamos por tipo+color como "configuración"
        Map<String, Integer> conteo = new HashMap<>();
        for (Vehiculo v : terminados) {
            String clave = v.obtenerTipo() + " [" + v.obtenerColor() + "]";
            conteo.put(clave, conteo.getOrDefault(clave, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entradas = new ArrayList<>(conteo.entrySet());
        Collections.sort(entradas, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return b.getValue() - a.getValue();
            }
        });

        Dashboard.imprimirCadena(String.format("%-30s %s", "Configuración", "Unidades"));
        Dashboard.imprimirCadena("--------------------------------------");
        for (Map.Entry<String, Integer> e : entradas) {
            Dashboard.imprimirCadena(String.format("%-30s %d", e.getKey(), e.getValue()));
        }
    }

    /**
     * Listado 4: cadenas de montaje filtradas por fecha, con vehículos y sus componentes.
     */
    private void listadoCadenasPorFecha() {
        Dashboard.imprimirCadena("\n--- CADENAS DE MONTAJE POR FECHA ---");
        try {
            Date desde = SDF.parse(leerTexto("Fecha inicio (dd/MM/yyyy): "));
            Date hasta = SDF.parse(leerTexto("Fecha fin   (dd/MM/yyyy): "));

            List<RegistroMontaje> registros = almacen.consultarHistorialPorFecha(desde, hasta);
            if (registros.isEmpty()) {
                Dashboard.mostrarMensaje("No hay actividad en ese período.");
                return;
            }

            // Agrupamos los registros por cadena
            Map<String, List<RegistroMontaje>> porCadena = new LinkedHashMap<>();
            for (RegistroMontaje r : registros) {
                String cadena = r.obtenerIdentificadorCadena();
                if (!porCadena.containsKey(cadena)) porCadena.put(cadena, new ArrayList<>());
                porCadena.get(cadena).add(r);
            }

            for (Map.Entry<String, List<RegistroMontaje>> entrada : porCadena.entrySet()) {
                Dashboard.imprimirCadena("\nCadena: " + entrada.getKey());
                Dashboard.imprimirCadena("  Fecha                 | Vehículo       | Componente");
                Dashboard.imprimirCadena("  -------------------------------------------------------");
                for (RegistroMontaje r : entrada.getValue()) {
                    Dashboard.imprimirCadena(String.format("  %-22s | %-14s | %s",
                        SDF.format(r.obtenerFecha()), r.obtenerTipoVehiculo(), r.obtenerComponente()));
                }
            }
        } catch (ParseException e) {
            Dashboard.mostrarError("Fecha no válida. Use el formato dd/MM/yyyy.");
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
            Date diaDesde = SDF.parse(leerTexto("Fecha inicio (dd/MM/yyyy): "));
            Date diaHasta = SDF.parse(leerTexto("Fecha fin (dd/MM/yyyy): "));
            List<RegistroMontaje> logs = almacen.consultarHistorialPorFecha(
                diaDesde,
                diaHasta
            );
            for (RegistroMontaje r : logs)
                Dashboard.imprimirCadena(r.toString());
        } catch (ParseException e) {
            Dashboard.mostrarError("Fecha no válida.");
        }
    }
}
