import java.util.Date;

/**
 * Representa al personal encargado del ensamblaje de componentes.
 * Su nivel de productividad aumenta tras completar un número determinado de montajes.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class TrabajadorOperario extends Trabajador
{
    private int numeroMontajes;
    private TrabajadorNivelProductividad nivel;
    private final int MONTAJES_PARA_EFICIENTE= 10;
    
    public TrabajadorOperario(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, TrabajadorPuesto puesto, Date fechaIngreso, TrabajadorNivelProductividad nivelInicial){
        super(dni, nombre, apellidos, direccion, numSeguridadSocial, salario, puesto, fechaIngreso);
        this.numeroMontajes = 0;
        this.nivel = nivelInicial;
    }
    
    public void registrarMontaje() {
        this.numeroMontajes++;
        if (this.numeroMontajes >= MONTAJES_PARA_EFICIENTE && this.nivel == TrabajadorNivelProductividad.ESTANDAR) {
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