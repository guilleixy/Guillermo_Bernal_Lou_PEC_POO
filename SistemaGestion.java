
/**
 * Write a description of class SistemaGestion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SistemaGestion {
    private IAlmacen almacen;
    private RecursosHumanos rrhh;
    private Dashboard dashboard;
    private Planificador planificador;
    private final int operariosPorCadena = ComponenteTipo.values().length;;
    
    public SistemaGestion(IAlmacen almacen, RecursosHumanos rrhh, Dashboard dashboard, Planificador planificador) {
        this.almacen = almacen;
        this.rrhh = rrhh;
        this.dashboard = dashboard;
        this.planificador = planificador;
        dashboard.configurarVisualizador(new VisualizadorTerminal());
    }
    

}