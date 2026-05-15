import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Clase responsable de la coordinación temporal y operativa de la fábrica.
 * Gestiona el avance de las líneas de producción mediante un sistema de ticks (reloj).
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class Planificador {

    private int reloj;
    private int ticksBloqueoApagon;
    private List<CadenaMontaje> cadenasMontaje;
    private RecursosHumanos rrhh;
    private IAlmacen almacen;
    private Queue<Vehiculo> colaProduccion;

    public Planificador(
        List<CadenaMontaje> cadenasMontaje,
        RecursosHumanos rrhh,
        IAlmacen almacen
    ) {
        this.cadenasMontaje = cadenasMontaje;
        this.rrhh = rrhh;
        this.almacen = almacen;
        this.colaProduccion = new LinkedList<>();
        this.reloj = 0;
        this.ticksBloqueoApagon = 0;
    }

    /**
     * Añade un vehículo a la espera de ser fabricado.
     */
    public void añadirPedido(Vehiculo v) {
        colaProduccion.add(v);
        Dashboard.mostrarMensaje("Nuevo pedido en cola: " + v.obtenerTipo());
    }

    private void gestionarAsignaciones() {
        // Recopilar operarios ya ocupados en cadenas activas
        Set<TrabajadorOperario> ocupados = new HashSet<>();
        for (CadenaMontaje cadena : cadenasMontaje) {
            if (cadena.obtenerVehiculoEnCurso() != null) {
                for (TrabajadorOperario op : cadena.obtenerOperarios()) {
                    if (op != null) ocupados.add(op);
                }
            }
        }

        for (CadenaMontaje cadena : cadenasMontaje) {
            if (
                cadena.obtenerVehiculoEnCurso() == null &&
                !colaProduccion.isEmpty()
            ) {
                TrabajadorOperario[] equipo = rrhh.buscarOperariosDisponibles(
                    4,
                    ocupados
                );
                if (equipo.length == 4) {
                    Vehiculo v = colaProduccion.poll();
                    cadena.asignarOperarios(equipo);
                    cadena.iniciarMontaje(v);
                    // Marcar los recién asignados como ocupados para el resto del bucle
                    for (TrabajadorOperario op : equipo) ocupados.add(op);
                }
            }
        }
    }

    /**
     * Verifica que existan los trabajadores mínimos necesarios para el modo indicado.
     * @return true si la plantilla es suficiente, false si falta personal.
     */
    private boolean validarPlantilla(SimulacionModo modo) {
        // Contar operarios registrados
        int numOperarios = 0;
        for (Trabajador t : rrhh.obtenerEmpleados()) {
            if (t.obtenerPuesto() == TrabajadorPuesto.OPERARIO) numOperarios++;
        }
        // Se necesitan al menos 4 operarios por cadena que puede estar activa simultáneamente
        int cadenasActivas = cadenasMontaje.size();
        int operariosNecesarios = 4 * cadenasActivas;
        if (numOperarios < 4) {
            Dashboard.mostrarError(
                "Simulación cancelada: se necesitan al menos 4 operarios (hay " + numOperarios + ")."
            );
            return false;
        }
        if (numOperarios < operariosNecesarios) {
            Dashboard.mostrarMensaje(
                "Aviso: hay " + numOperarios + " operarios para " + cadenasActivas +
                " cadenas. Algunas cadenas pueden quedar inactivas."
            );
        }

        if (modo == SimulacionModo.COMPLEJA || modo == SimulacionModo.MUY_COMPLEJA) {
            boolean hayMecanico = false;
            for (Trabajador t : rrhh.obtenerEmpleados()) {
                if (t.obtenerPuesto() == TrabajadorPuesto.MECANICO_CINTA) { hayMecanico = true; break; }
            }
            if (!hayMecanico) {
                Dashboard.mostrarError(
                    "Simulación cancelada: el modo " + modo + " requiere al menos un Mecánico de Cinta."
                );
                return false;
            }
        }

        if (modo == SimulacionModo.MUY_COMPLEJA) {
            boolean hayGestor = false;
            boolean hayAdmin = false;
            for (Trabajador t : rrhh.obtenerEmpleados()) {
                if (t.obtenerPuesto() == TrabajadorPuesto.GESTOR_PLANTA) hayGestor = true;
                if (t.obtenerPuesto() == TrabajadorPuesto.ADMINISTRADOR_SISTEMAS) hayAdmin = true;
            }
            if (!hayGestor) {
                Dashboard.mostrarError(
                    "Simulación cancelada: el modo MUY_COMPLEJA requiere un Gestor de Planta."
                );
                return false;
            }
            if (!hayAdmin) {
                Dashboard.mostrarError(
                    "Simulación cancelada: el modo MUY_COMPLEJA requiere un Administrador de Sistemas."
                );
                return false;
            }
        }

        return true;
    }

    /**
     * Inicia el ciclo de simulación de la fábrica.
     * El proceso continúa hasta que todos los vehículos en curso han sido finalizados.
     */
    public void empezarSimulacion(SimulacionModo modo) {
        if (!validarPlantilla(modo)) return;
        if (colaProduccion.isEmpty()) {
            Dashboard.mostrarError("Simulación cancelada: no hay pedidos en cola.");
            return;
        }
        Dashboard.mostrarMensaje("Iniciando simulación en modo: " + modo);
        boolean trabajoPendiente = true;

        while (trabajoPendiente) {
            reloj++;
            trabajoPendiente = false;

            if (modo == SimulacionModo.MUY_COMPLEJA) {
                if (ticksBloqueoApagon > 0) {
                    ticksBloqueoApagon--;
                    trabajoPendiente = true;
                    continue;
                }
                if (Math.random() < 0.02) {
                    ejecutarApagon();
                    trabajoPendiente = true;
                    continue;
                }
            }

            gestionarAsignaciones();

            for (CadenaMontaje cadena : cadenasMontaje) {
                if (cadena.obtenerVehiculoEnCurso() == null) continue;

                if (cadena.estaTrabajoFinalizado()) {
                    // Vehículo terminado: registrar en almacén y liberar la cadena
                    Vehiculo terminado = cadena.obtenerVehiculoEnCurso();
                    almacen.añadirVehiculo(terminado);
                    Dashboard.mostrarMensaje(
                        "Vehículo " +
                            terminado.obtenerTipo() +
                            " completado y enviado al almacén."
                    );
                    cadena.liberarCadena();
                } else {
                    switch (modo) {
                        case SIMPLE:
                            ejecutarPasoSimple(cadena);
                            break;
                        case COMPLEJA:
                            ejecutarPasoComplejo(cadena);
                            break;
                        case MUY_COMPLEJA:
                            ejecutarPasoMuyComplejo(cadena);
                            break;
                    }
                    trabajoPendiente = true;
                }
            }

            if (!colaProduccion.isEmpty()) trabajoPendiente = true;
        }

        Dashboard.mostrarMensaje(
            "Simulación finalizada. Tiempo total: " + reloj + " unidades."
        );
    }

    private void ejecutarApagon() {
        Dashboard.mostrarError("¡APAGÓN GENERAL!");
        Trabajador t = rrhh.buscarTrabajadorPorPuesto(
            TrabajadorPuesto.ADMINISTRADOR_SISTEMAS
        );
        if (t instanceof TrabajadorAdministradorSistema) {
            this.ticksBloqueoApagon = (
                (TrabajadorAdministradorSistema) t
            ).restaurarSistema();
        }
    }

    private void ejecutarPasoSimple(CadenaMontaje cadena) {
        ejecutarComprobacionStock(cadena);
    }

    private void ejecutarPasoComplejo(CadenaMontaje cadena) {
        if (cadena.estaAveriada()) {
            gestionarReparacionDirecta(cadena);
            return;
        }
        if (Math.random() < 0.10) {
            Dashboard.mostrarError(
                "¡AVERÍA en " + cadena.obtenerIdentificadorCadena() + "!"
            );
            cadena.provocarAveria();
            return;
        }
        ejecutarComprobacionStock(cadena);
    }

    private void ejecutarPasoMuyComplejo(CadenaMontaje cadena) {
        if (cadena.estaAveriada()) {
            gestionarReparacionConGestor(cadena);
            return;
        }
        if (Math.random() < 0.10) {
            Dashboard.mostrarError(
                "¡AVERÍA en " + cadena.obtenerIdentificadorCadena() + "!"
            );
            cadena.provocarAveria();
            return;
        }
        ejecutarComprobacionStock(cadena);
    }

    private void ejecutarComprobacionStock(CadenaMontaje cadena) {
        String tipoPieza = ComponenteTipo.values()[cadena.obtenerEstadoActual() - 1].toString();
        // el stock solo se comprueba y reserva al inicio de cada fase, no en cada tick
        if (cadena.obtenerTicksEnFaseActual() == 0) {
            if (!almacen.hayPiezasSuficientes(tipoPieza)) {
                Dashboard.mostrarError("Cadena " + cadena.obtenerIdentificadorCadena() + " parada por falta de: " + tipoPieza);
                return;
            }
            almacen.quitarStockComponente(tipoPieza, 1);
        }
        ComponenteTipo completado = cadena.avanzarFase();
        registrarSiCompletado(completado, cadena);
    }

    private void registrarSiCompletado(
        ComponenteTipo completado,
        CadenaMontaje cadena
    ) {
        if (completado != null && cadena.obtenerVehiculoEnCurso() != null) {
            almacen.registrarMontaje(
                new RegistroMontaje(
                    new Date(),
                    completado,
                    cadena.obtenerVehiculoEnCurso().obtenerTipo(),
                    cadena.obtenerIdentificadorCadena()
                )
            );
        }
    }

    private void gestionarReparacionDirecta(CadenaMontaje cadena) {
        if (cadena.obtenerTicksPendientesReparacion() == 0) {
            Trabajador t = rrhh.buscarTrabajadorPorPuesto(
                TrabajadorPuesto.MECANICO_CINTA
            );
            if (t instanceof TrabajadorMecanicoCinta) {
                TrabajadorMecanicoCinta mecanico = (TrabajadorMecanicoCinta) t;
                int ticks = calcularTicksReparacion(mecanico);
                cadena.iniciarReparacion(mecanico, ticks);
                Dashboard.mostrarMensaje(
                    "Mecánico " +
                        mecanico.obtenerNombre() +
                        " reparando " +
                        cadena.obtenerIdentificadorCadena() +
                        " (" +
                        ticks +
                        " ticks)."
                );
            } else {
                Dashboard.mostrarError(
                    "No hay mecánicos disponibles para reparar la cadena."
                );
            }
        } else {
            cadena.procesarTickReparacion();
        }
    }

    private void gestionarReparacionConGestor(CadenaMontaje cadena) {
        if (cadena.obtenerTicksPendientesReparacion() == 0) {
            Trabajador t = rrhh.buscarTrabajadorPorPuesto(
                TrabajadorPuesto.GESTOR_PLANTA
            );
            if (t instanceof TrabajadorGestorPlanta) {
                TrabajadorGestorPlanta gestor = (TrabajadorGestorPlanta) t;
                TrabajadorMecanicoCinta mecanico = gestor.llamarMecanico(rrhh);
                if (mecanico != null) {
                    int ticks = calcularTicksReparacion(mecanico);
                    cadena.iniciarReparacion(mecanico, ticks);
                    Dashboard.mostrarMensaje(
                        "Mecánico " +
                            mecanico.obtenerNombre() +
                            " asignado por " +
                            gestor.obtenerNombre() +
                            " (" +
                            ticks +
                            " ticks)."
                    );
                } else {
                    Dashboard.mostrarError(
                        "Gestión: " +
                            gestor.obtenerNombre() +
                            " informa que no hay mecánicos disponibles."
                    );
                }
            } else {
                Dashboard.mostrarError(
                    "CRÍTICO: No hay Gestor de Planta para supervisar la avería."
                );
            }
        } else {
            cadena.procesarTickReparacion();
        }
    }

    // eficiente: 1 tick. estandar: 2-5 ticks aleatorios
    private int calcularTicksReparacion(TrabajadorMecanicoCinta mecanico) {
        if (mecanico.obtenerNivel() == TrabajadorNivelProductividad.EFICIENTE) {
            return 1;
        }
        return 2 + (int) (Math.random() * 4);
    }

    public int obtenerReloj() {
        return reloj;
    }

    public List<CadenaMontaje> obtenerCadenasMontaje() {
        return cadenasMontaje;
    }
}
