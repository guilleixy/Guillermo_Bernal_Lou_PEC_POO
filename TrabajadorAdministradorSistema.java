import java.util.Date;

/**
 * Representa al personal encargado de la administración del sistema de la fábrica.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class TrabajadorAdministradorSistema extends Trabajador
{
    public TrabajadorAdministradorSistema(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, TrabajadorPuesto puesto, Date fechaIngreso){
        super(dni, nombre, apellidos, direccion, numSeguridadSocial, salario, puesto, fechaIngreso);
    }
}