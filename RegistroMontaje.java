import java.util.Date;

/**
 * Representa un evento de instalación de un componente durante el proceso de ensamblaje.
 * Permite consultar el historial de producción filtrado por fecha.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class RegistroMontaje
{
    private Date fecha;
    private ComponenteTipo componente;
    private String tipoVehiculo;
    private String identificadorCadena;

    public RegistroMontaje(Date fecha, ComponenteTipo componente, String tipoVehiculo, String identificadorCadena) {
        this.fecha = fecha;
        this.componente = componente;
        this.tipoVehiculo = tipoVehiculo;
        this.identificadorCadena = identificadorCadena;
    }

    public Date obtenerFecha() { return fecha; }
    public ComponenteTipo obtenerComponente() { return componente; }
    public String obtenerTipoVehiculo() { return tipoVehiculo; }
    public String obtenerIdentificadorCadena() { return identificadorCadena; }

    @Override
    public String toString() {
        return fecha + " | " + identificadorCadena + " | " + tipoVehiculo + " | " + componente;
    }
}
