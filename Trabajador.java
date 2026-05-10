import java.util.Date;

/**
 * Clase abstracta que define los atributos comunes de todo el personal de la fábrica.
 * Cumple con los requisitos de gestión de RRHH y nóminas.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public abstract class Trabajador
{
    private String dni; 
    private String nombre;
    private String apellidos;
    private String direccion;
    private int numSeguridadSocial;
    private double salario;
    private TrabajadorPuesto puesto;
    private Date fechaIngreso;
        
    public Trabajador(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, TrabajadorPuesto puesto, Date fechaIngreso){
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.numSeguridadSocial = numSeguridadSocial;
        this.salario = salario;
        this.puesto = puesto;
        this.fechaIngreso = fechaIngreso;
    }
    public String obtenerDni() { return dni; }
    public String obtenerNombre() { return nombre; }
    public String obtenerApellidos() { return apellidos; }
    public String obtenerDireccion() { return direccion; }
    public int obtenerNumSeguridadSocial() { return numSeguridadSocial; }
    public double obtenerSalario() { return salario; }
    public TrabajadorPuesto obtenerPuesto() { return puesto; }
    public Date obtenerFechaIngreso() { return fechaIngreso; }
    
    public void establecerDireccion(String direccion) { this.direccion = direccion; }
    public void establecerSalario(double salario) { this.salario = salario; }
}