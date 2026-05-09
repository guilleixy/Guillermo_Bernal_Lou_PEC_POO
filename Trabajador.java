import java.util.Date;

/**
 * Write a description of class Trabajador here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Trabajador
{
    private String dni; 
    private String nombre;
    private String apellidos;
    private String direccion;
    private int numSeguridadSocial;
    private double salario;
    private String puesto;
    private Date fechaIngreso;
        
    public Trabajador(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, String puesto, Date fechaIngreso){
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.numSeguridadSocial = numSeguridadSocial;
        this.salario = salario;
        this.puesto = puesto;
        this.fechaIngreso = fechaIngreso;
    }
}