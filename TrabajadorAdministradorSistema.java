import java.util.Date;

/**
 * Representa al personal encargado de la administración del sistema de la fábrica.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class TrabajadorAdministradorSistema extends Trabajador
{
    // 2 seg para el sistema de gestión + 3 seg para las cadenas de montaje (enunciado sec. 3d)
    private static final int TICKS_RESTAURAR = 5;

    public TrabajadorAdministradorSistema(String dni, String nombre, String apellidos, String direccion, int numSeguridadSocial, double salario, TrabajadorPuesto puesto, Date fechaIngreso){
        super(dni, nombre, apellidos, direccion, numSeguridadSocial, salario, puesto, fechaIngreso);
    }

    /**
     * Restaura el sistema tras un apagón.
     * @return número de ticks que la fábrica permanece bloqueada durante la restauración.
     */
    public int restaurarSistema() {
        Dashboard.mostrarMensaje("Administrador " + obtenerNombre() +
            ": Restaurando sistema... (" + TICKS_RESTAURAR + " segundos)");
        return TICKS_RESTAURAR;
    }
}