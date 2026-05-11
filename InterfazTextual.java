import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Interfaz textual del sistema. Gestiona la interacción con el usuario
 * mediante un menú jerárquico por consola.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class InterfazTextual
{
    private IAlmacen almacen;
    private RecursosHumanos rrhh;
    private Planificador planificador;
    private List<CadenaMontaje> cadenas;
    private Scanner scanner;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    public InterfazTextual(IAlmacen almacen, RecursosHumanos rrhh, Planificador planificador,
                           List<CadenaMontaje> cadenas) {
        this.almacen = almacen;
        this.rrhh = rrhh;
        this.planificador = planificador;
        this.cadenas = cadenas;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n========== SISTEMA DE GESTIÓN DE FÁBRICA ==========");
            System.out.println(" 1. Gestión de Almacén");
            System.out.println(" 2. Gestión de Trabajadores");
            System.out.println(" 3. Producción y Simulación");
            System.out.println(" 4. Listados y Estadísticas");
            System.out.println(" 0. Salir");
            System.out.println("====================================================");
            switch (leerEntero("Seleccione una opción: ")) {
                case 1: menuAlmacen();      break;
                case 2: menuTrabajadores(); break;
                case 3: menuProduccion();   break;
                case 4: menuListados();     break;
                case 0: salir = true;       break;
                default: System.out.println("Opción no válida.");
            }
        }
        System.out.println("Sistema cerrado.");
    }

    // ------------------------------------------------------------------ ALMACÉN
    private void menuAlmacen() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE ALMACÉN ---");
            System.out.println(" 1. Añadir stock de componente");
            System.out.println(" 2. Ver estado del almacén");
            System.out.println(" 3. Consultar historial de montaje por fecha");
            System.out.println(" 0. Volver");
            switch (leerEntero("Opción: ")) {
                case 1: añadirStockComponente();                     break;
                case 2: System.out.println(almacen.obtenerEstadoCompleto()); break;
                case 3: consultarHistorial();                        break;
                case 0: volver = true;                               break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void añadirStockComponente() {
        System.out.println("Tipo de componente:");
        System.out.println(" 1.Chasis  2.Motor Eléctrico  3.Motor Gasolina  4.Motor Híbrido");
        System.out.println(" 5.Tapicería  6.Ruedas");
        ComponenteVehiculo comp = crearComponente(leerEntero("Tipo: "));
        if (comp != null) {
            int cantidad = leerEntero("Cantidad: ");
            almacen.añadirStockComponente(comp, cantidad);
        }
    }

    private ComponenteVehiculo crearComponente(int tipo) {
        switch (tipo) {
            case 1: return new Chasis();
            case 2: return new MotorElectrico(leerEntero("Cilindrada: "), leerEntero("Potencia: "), leerEntero("Núm. cilindros: "));
            case 3: return new MotorGasolina(leerEntero("Cilindrada: "), leerEntero("Potencia: "), leerEntero("Núm. cilindros: "));
            case 4: return new MotorHibrido(leerEntero("Cilindrada: "), leerEntero("Potencia: "), leerEntero("Núm. cilindros: "));
            case 5: {
                String color = leerTexto("Color: ");
                double metros = leerDecimal("Metros cuadrados: ");
                System.out.println("Material: 1.Tela  2.Cuero  3.Alcántara");
                int m = leerEntero("Material: ");
                TapiceriaTipoMaterial mat = m == 2 ? TapiceriaTipoMaterial.CUERO :
                                             m == 3 ? TapiceriaTipoMaterial.ALCANTARA :
                                                      TapiceriaTipoMaterial.TELA;
                return new Tapiceria(color, metros, mat);
            }
            case 6: {
                double ancho = leerDecimal("Ancho (mm): ");
                double diam  = leerDecimal("Diámetro (pulgadas): ");
                double carga = leerDecimal("Índice de carga (kg): ");
                double vel   = leerDecimal("Código velocidad (km/h): ");
                System.out.println("Tipo: 1.Normal  2.Deportivo  3.Todoterreno");
                int t = leerEntero("Tipo: ");
                RuedasTipo rt = t == 2 ? RuedasTipo.DEPORTIVO :
                                t == 3 ? RuedasTipo.TODOTERRENO :
                                         RuedasTipo.NORMAL;
                return new Ruedas(ancho, diam, carga, vel, rt);
            }
            default: System.out.println("Tipo no válido."); return null;
        }
    }

    private void consultarHistorial() {
        try {
            Date desde = SDF.parse(leerTexto("Fecha desde (dd/MM/yyyy): "));
            Date hasta = SDF.parse(leerTexto("Fecha hasta (dd/MM/yyyy): "));
            List<RegistroMontaje> historial = almacen.consultarHistorialPorFecha(desde, hasta);
            if (historial.isEmpty()) { System.out.println("Sin registros en ese rango."); return; }
            for (RegistroMontaje r : historial) System.out.println(r);
        } catch (ParseException e) {
            System.out.println("Formato inválido. Use dd/MM/yyyy.");
        }
    }

    // --------------------------------------------------------------- TRABAJADORES
    private void menuTrabajadores() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE TRABAJADORES ---");
            System.out.println(" 1. Dar de alta trabajador");
            System.out.println(" 2. Buscar por DNI");
            System.out.println(" 3. Buscar por puesto");
            System.out.println(" 0. Volver");
            switch (leerEntero("Opción: ")) {
                case 1: altaTrabajador(); break;
                case 2: buscarPorDni();   break;
                case 3: buscarPorPuesto(); break;
                case 0: volver = true;    break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void altaTrabajador() {
        String dni       = leerTexto("DNI: ");
        String nombre    = leerTexto("Nombre: ");
        String apellidos = leerTexto("Apellidos: ");
        String direccion = leerTexto("Dirección: ");
        int    nss       = leerEntero("Núm. Seguridad Social: ");
        double salario   = leerDecimal("Salario: ");
        System.out.println("Puesto: 1.Operario  2.Gestor Planta  3.Admin Sistema  4.Mecánico Cinta");
        int p = leerEntero("Puesto: ");
        TrabajadorPuesto tp = p == 2 ? TrabajadorPuesto.GESTOR_PLANTA :
                               p == 3 ? TrabajadorPuesto.ADMINISTRADOR_SISTEMAS :
                               p == 4 ? TrabajadorPuesto.MECANICO_CINTA :
                                        TrabajadorPuesto.OPERARIO;
        Date hoy = new Date();
        switch (p) {
            case 1: rrhh.altaTrabajador(new TrabajadorOperario(
                        dni, nombre, apellidos, direccion, nss, salario, tp, hoy,
                        TrabajadorNivelProductividad.ESTANDAR)); break;
            case 2: rrhh.altaTrabajador(new TrabajadorGestorPlanta(
                        dni, nombre, apellidos, direccion, nss, salario, tp, hoy)); break;
            case 3: rrhh.altaTrabajador(new TrabajadorAdministradorSistema(
                        dni, nombre, apellidos, direccion, nss, salario, tp, hoy)); break;
            case 4: rrhh.altaTrabajador(new TrabajadorMecanicoCinta(
                        dni, nombre, apellidos, direccion, nss, salario, tp, hoy,
                        TrabajadorNivelProductividad.ESTANDAR)); break;
            default: System.out.println("Puesto no válido."); return;
        }
        System.out.println("Trabajador dado de alta correctamente.");
    }

    private void buscarPorDni() {
        Trabajador t = rrhh.buscarPorDni(leerTexto("DNI: "));
        System.out.println(t != null ? formatarTrabajador(t) : "No encontrado.");
    }

    private void buscarPorPuesto() {
        System.out.println("Puesto: 1.Operario  2.Gestor Planta  3.Admin Sistema  4.Mecánico Cinta");
        int p = leerEntero("Puesto: ");
        TrabajadorPuesto tp = p == 2 ? TrabajadorPuesto.GESTOR_PLANTA :
                               p == 3 ? TrabajadorPuesto.ADMINISTRADOR_SISTEMAS :
                               p == 4 ? TrabajadorPuesto.MECANICO_CINTA :
                                        TrabajadorPuesto.OPERARIO;
        Trabajador t = rrhh.buscarTrabajadorPorPuesto(tp);
        if (t != null) System.out.println(formatarTrabajador(t));
    }

    // ---------------------------------------------------------------- PRODUCCIÓN
    private void menuProduccion() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- PRODUCCIÓN Y SIMULACIÓN ---");
            System.out.println(" 1. Añadir pedido de vehículo");
            System.out.println(" 2. Iniciar simulación");
            System.out.println(" 0. Volver");
            switch (leerEntero("Opción: ")) {
                case 1: añadirPedido();      break;
                case 2: iniciarSimulacion(); break;
                case 0: volver = true;       break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void añadirPedido() {
        System.out.println("Tipo de vehículo: 1.Turismo  2.Biplaza  3.Furgoneta");
        int tipo     = leerEntero("Tipo: ");
        String color = leerTexto("Color: ");
        int plazas   = leerEntero("Núm. plazas: ");
        double tara  = leerDecimal("Tara (kg): ");
        double pMax  = leerDecimal("Peso máximo (kg): ");
        Vehiculo v;
        switch (tipo) {
            case 2:  v = new VehiculoBiplaza(color, plazas, tara, pMax);   break;
            case 3:  v = new VehiculoFurgoneta(color, plazas, tara, pMax); break;
            default: v = new VehiculoTurismo(color, plazas, tara, pMax);   break;
        }
        planificador.añadirPedido(v);
    }

    private void iniciarSimulacion() {
        System.out.println("Modo: 1.Simple  2.Compleja  3.Muy Compleja");
        int m = leerEntero("Modo: ");
        SimulacionModo modo = m == 2 ? SimulacionModo.COMPLEJA :
                               m == 3 ? SimulacionModo.MUY_COMPLEJA :
                                        SimulacionModo.SIMPLE;
        planificador.empezarSimulacionSimple(modo);
        Dashboard.mostrarEstado(almacen.obtenerEstadoCompleto(), obtenerEstadoCadenas());
    }

    // ------------------------------------------------------------------ LISTADOS
    private void menuListados() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- LISTADOS Y ESTADÍSTICAS ---");
            System.out.println(" 1. Operarios (filtrado por productividad, orden alfabético)");
            System.out.println(" 2. Vehículos ensamblados (filtrado por componente)");
            System.out.println(" 3. Configuraciones con mayor tasa de ensamblaje");
            System.out.println(" 4. Historial de cadenas por fecha");
            System.out.println(" 0. Volver");
            switch (leerEntero("Opción: ")) {
                case 1: listadoOperarios();         break;
                case 2: listadoVehiculos();         break;
                case 3: listadoTasaEnsamblaje();    break;
                case 4: listadoCadenasPorFecha();   break;
                case 0: volver = true;              break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void listadoOperarios() {
        System.out.println("Filtrar por nivel: 1.Todos  2.Eficiente  3.Estándar");
        int filtro = leerEntero("Filtro: ");
        List<TrabajadorOperario> lista = new ArrayList<>();
        for (Trabajador t : rrhh.obtenerEmpleados()) {
            if (t.obtenerPuesto() != TrabajadorPuesto.OPERARIO) continue;
            TrabajadorOperario op = (TrabajadorOperario) t;
            if (filtro == 2 && op.obtenerNivel() != TrabajadorNivelProductividad.EFICIENTE) continue;
            if (filtro == 3 && op.obtenerNivel() != TrabajadorNivelProductividad.ESTANDAR)  continue;
            lista.add(op);
        }
        lista.sort((a, b) -> a.obtenerNombre().compareToIgnoreCase(b.obtenerNombre()));
        if (lista.isEmpty()) { System.out.println("Sin resultados."); return; }
        System.out.printf("%-15s %-20s %-12s %-12s %s%n", "Nombre", "Apellidos", "DNI", "Nivel", "Montajes");
        System.out.println("-".repeat(75));
        for (TrabajadorOperario op : lista) {
            System.out.printf("%-15s %-20s %-12s %-12s %d%n",
                op.obtenerNombre(), op.obtenerApellidos(), op.obtenerDni(),
                op.obtenerNivel(), op.obtenerNumeroMontajes());
        }
    }

    private void listadoVehiculos() {
        System.out.println("Filtrar: 1.Todos  2.CHASIS  3.MOTOR  4.TAPICERIA  5.RUEDAS");
        int filtro = leerEntero("Filtro: ");
        ComponenteTipo filtroTipo = filtro == 2 ? ComponenteTipo.CHASIS :
                                     filtro == 3 ? ComponenteTipo.MOTOR :
                                     filtro == 4 ? ComponenteTipo.TAPICERIA :
                                     filtro == 5 ? ComponenteTipo.RUEDAS : null;
        List<Vehiculo> vehiculos = almacen.obtenerVehiculosTerminados();
        vehiculos.sort((a, b) -> a.obtenerTipo().compareToIgnoreCase(b.obtenerTipo()));
        if (vehiculos.isEmpty()) { System.out.println("Sin vehículos ensamblados."); return; }
        boolean hayResultados = false;
        for (Vehiculo v : vehiculos) {
            List<ComponenteTipo> comps = v.obtenerComponentesInstalados();
            if (filtroTipo == null || comps.contains(filtroTipo)) {
                System.out.println("Tipo: " + v.obtenerTipo() +
                    " | Color: " + v.obtenerColor() + " | Componentes: " + comps);
                hayResultados = true;
            }
        }
        if (!hayResultados) System.out.println("Sin resultados con ese filtro.");
    }

    private void listadoTasaEnsamblaje() {
        Map<String, Integer> conteo = new HashMap<>();
        for (Vehiculo v : almacen.obtenerVehiculosTerminados()) {
            String tipo = v.obtenerTipo();
            conteo.put(tipo, conteo.getOrDefault(tipo, 0) + 1);
        }
        if (conteo.isEmpty()) { System.out.println("Sin vehículos ensamblados."); return; }
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(conteo.entrySet());
        lista.sort((a, b) -> b.getValue() - a.getValue());
        System.out.printf("%-20s %s%n", "Tipo de vehículo", "Unidades producidas");
        System.out.println("-".repeat(40));
        for (Map.Entry<String, Integer> e : lista) {
            System.out.printf("%-20s %d%n", e.getKey(), e.getValue());
        }
    }

    private void listadoCadenasPorFecha() {
        try {
            Date desde = SDF.parse(leerTexto("Fecha desde (dd/MM/yyyy): "));
            Date hasta = SDF.parse(leerTexto("Fecha hasta (dd/MM/yyyy): "));
            List<RegistroMontaje> historial = almacen.consultarHistorialPorFecha(desde, hasta);
            if (historial.isEmpty()) { System.out.println("Sin registros en ese rango."); return; }
            Map<String, List<RegistroMontaje>> porCadena = new LinkedHashMap<>();
            for (RegistroMontaje r : historial) {
                String id = r.obtenerIdentificadorCadena();
                if (!porCadena.containsKey(id)) porCadena.put(id, new ArrayList<>());
                porCadena.get(id).add(r);
            }
            for (Map.Entry<String, List<RegistroMontaje>> e : porCadena.entrySet()) {
                System.out.println("\nCadena: " + e.getKey());
                for (RegistroMontaje r : e.getValue()) {
                    System.out.println("  " + SDF.format(r.obtenerFecha()) + " | " +
                        r.obtenerTipoVehiculo() + " | " + r.obtenerComponente());
                }
            }
        } catch (ParseException e) {
            System.out.println("Formato inválido. Use dd/MM/yyyy.");
        }
    }

    // ------------------------------------------------------------------- HELPERS
    private String obtenerEstadoCadenas() {
        StringBuilder sb = new StringBuilder();
        for (CadenaMontaje c : cadenas) {
            sb.append(c.obtenerIdentificadorCadena()).append(": ");
            Vehiculo v = c.obtenerVehiculoEnCurso();
            if (v != null) sb.append("Ensamblando ").append(v.obtenerTipo())
                             .append(" (fase ").append(c.obtenerEstadoActual()).append(")");
            else           sb.append("Libre");
            sb.append("\n");
        }
        return sb.toString();
    }

    private String formatarTrabajador(Trabajador t) {
        return t.obtenerNombre() + " " + t.obtenerApellidos() +
               " | DNI: " + t.obtenerDni() +
               " | Puesto: " + t.obtenerPuesto() +
               " | Salario: " + t.obtenerSalario();
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        try { return Double.parseDouble(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return 0.0; }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}
