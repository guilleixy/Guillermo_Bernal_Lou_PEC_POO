import java.util.Date;

/**
 * Write a description of class TrabajadorOperario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TrabajadorAdministradorSistema extends Trabajador
{
    public TrabajadorAdministradorSistema(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, TrabajadorPuesto puesto, Date fechaIngreso){
        super(dni, nombre, apellidos, direccion, numSeguridadSocial, salario, puesto, fechaIngreso);
    }
}