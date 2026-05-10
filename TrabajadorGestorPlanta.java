import java.util.Date;

/**
 * Representa al personal responsable de la gestión de la planta.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class TrabajadorGestorPlanta extends Trabajador
{
    public TrabajadorGestorPlanta(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, TrabajadorPuesto puesto, Date fechaIngreso){
        super(dni, nombre, apellidos, direccion, numSeguridadSocial, salario, puesto, fechaIngreso);
    }
}