import java.util.Date;

/**
 * Write a description of class TrabajadorOperario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TrabajadorOperario extends Trabajador
{
    private int numeroMontajes;
    private TrabajadorNivelProductividad nivel;
    private final int montajesNecesarios = 10;
    
    public TrabajadorOperario(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, String puesto, Date fechaIngreso, TrabajadorNivelProductividad nivelInicial){
        super(dni, nombre, apellidos, direccion, numSeguridadSocial, salario, puesto, fechaIngreso);
        this.numeroMontajes = 0;
        this.nivel = nivelInicial;
    }
    
    public void registrarMontaje() {
        this.numeroMontajes++;
        if (this.numeroMontajes > montajesNecesarios && this.nivel == TrabajadorNivelProductividad.ESTANDAR) {
            this.nivel = TrabajadorNivelProductividad.EFICIENTE;

        }
    }
    
    public int obtenerNumeroMontajes() {
        return numeroMontajes;
    }
    
    public TrabajadorNivelProductividad obtenerNivel() {
        return nivel;
    }
}