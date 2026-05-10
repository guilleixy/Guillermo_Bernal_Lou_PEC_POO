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
        this.estadoActual = 1; // Comenzamos con el Chasis
        Dashboard.mostrarMensaje("Iniciado montaje de " + v.obtenerTipo() + " en " + identificadorCadena);
    }

    /**
     * Avanza el vehículo a la siguiente fase de montaje e incrementa la 
     * experiencia de los operarios asignados.
     */
    public void avanzarFase() {
        if (vehiculoEnCurso == null) return;

        ComponenteTipo[] fases = ComponenteTipo.values();
        if (estadoActual <= fases.length) {
            ComponenteTipo faseCompletada = fases[estadoActual - 1];
            vehiculoEnCurso.instalarComponente(faseCompletada);
            
            // Incrementar estadísticas de los operarios por el trabajo realizado
            for (TrabajadorOperario op : operarios) {
                if (op != null) op.registrarMontaje();
            }

            Dashboard.mostrarMensaje(identificadorCadena + ": Fase " + faseCompletada + " completada.");
            estadoActual++;
        }
    }

    public String obtenerIdentificadorCadena() { return identificadorCadena; }
    public int obtenerEstadoActual() { return estadoActual; }
    public Vehiculo obtenerVehiculoEnCurso() { return vehiculoEnCurso; }
    
    /**
     * Verifica si el vehículo actual ha pasado por todas las fases.
     * @return true si el vehículo está listo para salir de la cadena.
     */
    public boolean estaTrabajoFinalizado() {
        return vehiculoEnCurso != null && vehiculoEnCurso.estaTerminado();
    }
}