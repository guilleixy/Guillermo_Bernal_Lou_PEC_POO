/**
 * Representa una línea de producción donde se ensamblan los vehículos fase a fase.
 * Requiere un equipo de operarios y gestiona el progreso del montaje.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class CadenaMontaje
{
    private String identificadorCadena;
    private TrabajadorOperario[] operarios;
    private int estadoActual; // 0: Espera, 1: Chasis, 2: Motor, 3: Tapicería, 4: Ruedas
    private Vehiculo vehiculoEnCurso;
    private boolean averiada = false;
    private int ticksEnFaseActual = 0;
    private int ticksPendientesReparacion = 0;
    private TrabajadorMecanicoCinta mecanicoAsignado = null;
    
    public CadenaMontaje(String id) {
        this.identificadorCadena = id;
        this.operarios = new TrabajadorOperario[4];
        this.estadoActual = 0; 
    }
    
    /**
     * Asigna el equipo de trabajo a la cadena.
     * @param nuevosOperarios Array de exactamente 4 operarios.
     */
    public void asignarOperarios(TrabajadorOperario[] nuevosOperarios) {
        if(nuevosOperarios.length == 4){
            this.operarios = nuevosOperarios;
            Dashboard.mostrarMensaje("Operarios de la cadena " + identificadorCadena + " asignados.");
        } else {
            Dashboard.mostrarError("La cadena " + identificadorCadena + " requiere exactamente 4 operarios.");
        }
    }
    
    /**
     * Inicia el montaje de un nuevo vehículo en la cadena.
     * @param v Vehículo a ensamblar.
     */
    public void iniciarMontaje(Vehiculo v) {
        if (this.operarios[0] == null) {
            Dashboard.mostrarError("No se puede iniciar: No hay operarios asignados.");
            return;
        }
        this.vehiculoEnCurso = v;
        this.estadoActual = 1;
        this.ticksEnFaseActual = 0;
        Dashboard.mostrarMensaje("Iniciado montaje de " + v.obtenerTipo() + " en " + identificadorCadena);
    }

    /**
     * Avanza el vehículo a la siguiente fase de montaje e incrementa la 
     * experiencia de los operarios asignados.
     */
    /**
     * Avanza un tick en la fase actual.
     * @return El ComponenteTipo completado en este tick, o null si la fase sigue en curso.
     */
    public ComponenteTipo avanzarFase() {
        if (vehiculoEnCurso == null) return null;

        ComponenteTipo[] fases = ComponenteTipo.values();
        if (estadoActual <= fases.length) {
            TrabajadorOperario operarioActual = operarios[estadoActual - 1];
            // eficiente 1 tick, estandar 3 ticks
            int ticksNecesarios = (operarioActual != null &&
                operarioActual.obtenerNivel() == TrabajadorNivelProductividad.EFICIENTE) ? 1 : 3;

            ticksEnFaseActual++;

            if (ticksEnFaseActual >= ticksNecesarios) {
                ComponenteTipo faseCompletada = fases[estadoActual - 1];
                vehiculoEnCurso.instalarComponente(faseCompletada);

                for (TrabajadorOperario op : operarios) {
                    if (op != null) op.registrarMontaje();
                }

                Dashboard.mostrarMensaje(identificadorCadena + ": Fase " + faseCompletada + " completada.");
                estadoActual++;
                ticksEnFaseActual = 0;
                return faseCompletada;
            } else {
                Dashboard.mostrarMensaje(identificadorCadena + ": Fase " + fases[estadoActual - 1] +
                    " en progreso (" + ticksEnFaseActual + "/" + ticksNecesarios + " ticks)");
            }
        }
        return null;
    }

    public void provocarAveria() {
        this.averiada = true;
        this.ticksPendientesReparacion = 0;
        this.mecanicoAsignado = null;
        Dashboard.mostrarError("¡AVERÍA CRÍTICA en " + identificadorCadena + "!");
    }

    /**
     * Inicia una reparación con tiempo variable según el nivel del mecánico.
     * @param mecanico  Mecánico asignado a la reparación.
     * @param ticks     Ticks necesarios para completar la reparación.
     */
    public void iniciarReparacion(TrabajadorMecanicoCinta mecanico, int ticks) {
        this.mecanicoAsignado = mecanico;
        this.ticksPendientesReparacion = ticks;
        Dashboard.mostrarMensaje(identificadorCadena + ": Reparación en curso (" + ticks + " ticks).");
    }

    /**
     * Procesa un tick de reparación. Cuando los ticks llegan a 0 completa la reparación.
     * @return true si la reparación ha finalizado en este tick.
     */
    public boolean procesarTickReparacion() {
        if (!averiada || ticksPendientesReparacion <= 0) return false;
        ticksPendientesReparacion--;
        if (ticksPendientesReparacion == 0) {
            averiada = false;
            if (mecanicoAsignado != null) {
                mecanicoAsignado.registrarReparacion();
                mecanicoAsignado = null;
            }
            Dashboard.mostrarMensaje("Reparación completada en " + identificadorCadena);
            return true;
        }
        return false;
    }

    public void reparar() {
        this.averiada = false;
        this.ticksPendientesReparacion = 0;
        this.mecanicoAsignado = null;
        Dashboard.mostrarMensaje("Reparación completada en " + identificadorCadena);
    }
    
    /**
     * Verifica si el vehículo actual ha pasado por todas las fases.
     * @return true si el vehículo está listo para salir de la cadena.
     */
    public boolean estaTrabajoFinalizado() {
        return vehiculoEnCurso != null && vehiculoEnCurso.estaTerminado();
    }
    
    public void liberarCadena() {
        this.vehiculoEnCurso = null;
        this.estadoActual = 0;
        this.ticksEnFaseActual = 0;
        this.ticksPendientesReparacion = 0;
        this.mecanicoAsignado = null;
        this.operarios = new TrabajadorOperario[4];
    }
    
    public boolean estaAveriada() { return averiada; }
    public int obtenerTicksPendientesReparacion() { return ticksPendientesReparacion; }
    public String obtenerIdentificadorCadena() { return identificadorCadena; }
    public int obtenerEstadoActual() { return estadoActual; }
    public Vehiculo obtenerVehiculoEnCurso() { return vehiculoEnCurso; }
    public TrabajadorOperario[] obtenerOperarios() { return operarios; }
}