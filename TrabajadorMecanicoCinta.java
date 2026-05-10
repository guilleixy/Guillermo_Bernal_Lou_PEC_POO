import java.util.Date;

/**
 * Representa al personal técnico encargado del mantenimiento y reparación en la cinta.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class TrabajadorMecanicoCinta extends Trabajador
{
    private int numeroReparaciones;
    private TrabajadorNivelProductividad nivel;
    private final int REPARACIONES_PARA_EFICIENTE = 20;
    
    public TrabajadorMecanicoCinta(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, TrabajadorPuesto puesto, Date fechaIngreso, TrabajadorNivelProductividad nivelInicial){
        super(dni, nombre, apellidos, direccion, numSeguridadSocial, salario, puesto, fechaIngreso);
        this.numeroReparaciones = 0;
        this.nivel = nivelInicial;
    }
    
    public void registrarReparacion() {
        this.numeroReparaciones++;
        if (this.numeroReparaciones >= REPARACIONES_PARA_EFICIENTE && this.nivel == TrabajadorNivelProductividad.ESTANDAR) {
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