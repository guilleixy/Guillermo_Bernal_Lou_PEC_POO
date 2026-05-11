import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Clase responsable de la coordinación temporal y operativa de la fábrica.
 * Gestiona el avance de las líneas de producción mediante un sistema de ticks (reloj).
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class Planificador
{
    private int reloj;
    private int ticksBloqueoApagon;
    private List<CadenaMontaje> cadenasMontaje;
    private RecursosHumanos rrhh;
    private IAlmacen almacen;
    private Queue<Vehiculo> colaProduccion;

    public Planificador(List<CadenaMontaje> cadenasMontaje, RecursosHumanos rrhh, IAlmacen almacen){
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
        for (CadenaMontaje cadena : cadenasMontaje) {
            if (cadena.obtenerVehiculoEnCurso() == null && !colaProduccion.isEmpty()) {
                // Buscamos operarios disponibles
                TrabajadorOperario[] equipo = rrhh.buscarOperariosAleatorios(4);
                if (equipo.length == 4) {
                    Vehiculo v = colaProduccion.poll();
                    cadena.asignarOperarios(equipo);
                    cadena.iniciarMontaje(v);
                }
            }
        }
    }

    /**
     * Inicia el ciclo de simulación de la fábrica.
     * El proceso continúa hasta que todos los vehículos en curso han sido finalizados.
     */
    public void empezarSimulacionSimple(SimulacionModo modo){
        Dashboard.mostrarMensaje("Iniciando simulación en modo: " + modo);
        boolean trabajoPendiente = true;

        while(trabajoPendiente){
            reloj++;
            trabajoPendiente = false; // Se asume falso hasta encontrar una cadena activa

            if (modo == SimulacionModo.MUY_COMPLEJA) {
                if (ticksBloqueoApagon > 0) {
                    ticksBloqueoApagon--;
                    trabajoPendiente = true;
                    continue; 
                }
                if (Math.random() < 0.02) { // Probabilidad de apagón
                    ejecutarApagon();
                    trabajoPendiente = true;
                    continue;
                }
            }
            
            for(CadenaMontaje cadena : cadenasMontaje){
                // Si la cadena tiene un vehículo y no ha terminado todas las fases
                if (cadena.obtenerVehiculoEnCurso() != null && !cadena.estaTrabajoFinalizado()) {
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
        }

        Dashboard.mostrarMensaje("Simulación finalizada. Tiempo total: " + reloj + " unidades.");
    }


    private void ejecutarApagon() {
        Dashboard.mostrarError("¡APAGÓN GENERAL!");
        Trabajador t = rrhh.buscarTrabajadorPorPuesto(TrabajadorPuesto.ADMINISTRADOR_SISTEMAS);
        if (t instanceof TrabajadorAdministradorSistema) {
            this.ticksBloqueoApagon = ((TrabajadorAdministradorSistema) t).restaurarSistema();
        }
    }
    
    private void ejecutarPasoSimple(CadenaMontaje cadena) {
        cadena.avanzarFase();
    }


    private void ejecutarPasoComplejo(CadenaMontaje cadena) {
        // En modo complejo, verificamos stock antes de cada fase
        ComponenteTipo siguientePieza = ComponenteTipo.values()[cadena.obtenerEstadoActual() - 1];
        ComponenteVehiculo pieza = determinarPiezaParaFase(siguientePieza, cadena.obtenerVehiculoEnCurso());
        // Simulamos la búsqueda de una pieza específica en el almacén
        if (almacen.hayPiezasSuficientes(siguientePieza.toString())) {
            cadena.avanzarFase();
            almacen.quitarStockComponente(pieza, 1);
        } else {
            Dashboard.mostrarError("Cadena " + cadena.obtenerIdentificadorCadena() + " parada por falta de: " + siguientePieza);
        }
    }

    private void ejecutarPasoMuyComplejo(CadenaMontaje cadena) {
        if (cadena.estaAveriada()) {
            // 1. Localizamos al Gestor de Planta a través de RRHH
            Trabajador t = rrhh.buscarTrabajadorPorPuesto(TrabajadorPuesto.GESTOR_PLANTA);

            if (t instanceof TrabajadorGestorPlanta) {
                TrabajadorGestorPlanta gestor = (TrabajadorGestorPlanta) t;
                TrabajadorMecanicoCinta mecanico = gestor.llamarMecanico(rrhh);

                if (mecanico != null) {
                    cadena.reparar();
                    mecanico.registrarReparacion();
                    Dashboard.mostrarMensaje("Acción de Gestión: El mecánico " + mecanico.obtenerNombre() +
                                             " ha reparado la cadena bajo supervisión de " + gestor.obtenerNombre());
                } else {
                    Dashboard.mostrarError("Gestión: " + gestor.obtenerNombre() +
                                           " informa que no hay mecánicos disponibles.");
                }
            } else {
                Dashboard.mostrarError("CRÍTICO: No hay Gestor de Planta para supervisar la avería.");
            }
            return; // El turno se consume en la gestión/reparación
        }

        if (Math.random() < 0.10) {
            Dashboard.mostrarError("¡AVERÍA en " + cadena.obtenerIdentificadorCadena() + "!");
            Dashboard.mostrarMensaje("Llamando a un Mecánico de Cinta...");
            cadena.provocarAveria();
        } else {
            ejecutarPasoComplejo(cadena);
        }
    }

    public int obtenerReloj() { return reloj; }
}
