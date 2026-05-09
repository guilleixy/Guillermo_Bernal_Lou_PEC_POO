
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
    
    public SistemaGestion(IAlmacen almacen, RecursosHumanos rrhh) {
        this.almacen = almacen;
        this.rrhh = rrhh;
        Dashboard.configurarVisualizador(new VisualizadorTerminal());
    }
    

}