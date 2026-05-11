/**
 * Representa el chasis de un vehículo, siendo el componente base 
 * sobre el que se inicia el proceso de ensamblaje en la fábrica.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class Chasis extends ComponenteVehiculo
{
    public Chasis() {
    }

    @Override
    public String obtenerDatosTecnicos() {
        return "chasis_estándar";
    }

    @Override
    public String obtenerIdentificador() {
        return "CHASIS";
    }
}