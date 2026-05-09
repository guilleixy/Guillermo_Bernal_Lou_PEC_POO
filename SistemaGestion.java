
/**
 * Write a description of class SistemaGestion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SistemaGestion {
    private IAlmacen almacen;
    private List<Trabajador> trabajadores;

    public SistemaGestion(IAlmacen almacen) {
        this.almacen = almacen;
        this.trabajadores = new ArrayList<>();
    }
    

}