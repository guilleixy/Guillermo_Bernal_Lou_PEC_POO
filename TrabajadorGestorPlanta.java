import java.util.Date;

/**
 * Representa al personal responsable de la gestión de la planta.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class TrabajadorGestorPlanta extends Trabajador {

    public TrabajadorGestorPlanta(
        String dni,
        String nombre,
        String apellidos,
        String direccion,
        int numSeguridadSocial,
        double salario,
        TrabajadorPuesto puesto,
        Date fechaIngreso
    ) {
        super(
            dni,
            nombre,
            apellidos,
            direccion,
            numSeguridadSocial,
            salario,
            puesto,
            fechaIngreso
        );
    }

    /**
     * Simula la llamada a un mecánico cuando el gestor detecta un error en el Dashboard.
     * @param rrhh Referencia para localizar un mecánico disponible.
     * @return El mecánico localizado por el gestor.
     */
    public TrabajadorMecanicoCinta llamarMecanico(RecursosHumanos rrhh) {
        Dashboard.mostrarMensaje(
            "Gestor de Planta " +
                obtenerNombre() +
                ": Detectado error en Dashboard. Solicitando técnico..."
        );

        Trabajador t = rrhh.buscarTrabajadorPorPuesto(
            TrabajadorPuesto.MECANICO_CINTA
        );

        if (t instanceof TrabajadorMecanicoCinta) {
            return (TrabajadorMecanicoCinta) t;
        }

        return null;
    }

    /**
     * El gestor define qué identificador de componente se usará en la producción.
     */
    public String configurarComponenteParaCadena(
        ComponenteTipo tipo,
        String especificacion
    ) {
        return tipo.toString() + "_" + especificacion;
    }
}
