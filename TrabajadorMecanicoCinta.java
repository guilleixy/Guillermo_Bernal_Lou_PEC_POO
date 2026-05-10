import java.util.Date;

/**
 * Write a description of class TrabajadorOperario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TrabajadorMecanicoCinta extends Trabajador
{
    private int numeroReparaciones;
    private TrabajadorNivelProductividad nivel;
    private final int reparacionesNecesarias = 20;
    
    public TrabajadorMecanicoCinta(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, TrabajadorPuesto puesto, Date fechaIngreso, TrabajadorNivelProductividad nivelInicial){
        super(dni, nombre, apellidos, direccion, numSeguridadSocial, salario, puesto, fechaIngreso);
        this.numeroReparaciones = 0;
        this.nivel = nivelInicial;
    }
    public void registrarReparacion() {
        this.numeroReparaciones++;
        if (this.numeroReparaciones > reparacionesNecesarias && this.nivel == TrabajadorNivelProductividad.ESTANDAR) {
            this.nivel = TrabajadorNivelProductividad.EFICIENTE;
        }
    }
    
    public int obtenerNumeroReparaciones() {
        return numeroReparaciones;
    }
    
    public TrabajadorNivelProductividad obtenerNivel() {
        return nivel;
    }
}