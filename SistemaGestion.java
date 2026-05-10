/**
 * Controlador central del sistema de la fábrica. 
 * Coordina la logística del almacén, la gestión de personal y la planificación.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class SistemaGestion {
    private IAlmacen almacen;
    private RecursosHumanos rrhh;
    private Dashboard dashboard;
    private Planificador planificador;
    private final int operariosPorCadena = ComponenteTipo.values().length;
    
    public SistemaGestion(IAlmacen almacen, RecursosHumanos rrhh, Dashboard dashboard, Planificador planificador) {
        if (almacen == null || rrhh == null || dashboard == null || planificador == null) {
            throw new IllegalArgumentException("Los subsistemas no pueden ser nulos.");
        }
        this.almacen = almacen;
        this.rrhh = rrhh;
        this.dashboard = dashboard;
        this.planificador = planificador;
        
        // Configuración por defecto del visualizador
        Dashboard.configurarVisualizador(new VisualizadorTerminal());
    }
    
    public IAlmacen obtenerAlmacen() { return almacen; }
    public RecursosHumanos obtenerRRHH() { return rrhh; }
}