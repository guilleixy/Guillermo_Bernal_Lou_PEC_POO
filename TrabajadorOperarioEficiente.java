import java.util.Date;

/**
 * Write a description of class TrabajadorOperarioEficiente here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TrabajadorOperarioEficiente extends TrabajadorOperario
{
    public TrabajadorOperarioEficiente(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, String puesto, Date fechaIngreso){
        super(dni, nombre, apellidos, direccion, numSeguridadSocial, salario, puesto, fechaIngreso);
    }
}