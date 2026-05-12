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

    /**
     * Genera una cadena con los datos técnicos genéricos de un chasis.
     * @return "chasis_estándar".
     */
    @Override
    public String obtenerDatosTecnicos() {
        return "chasis_estándar";
    }
    
    /**
     * Genera un identificador único basado en el tipo de componente y sus datos técnicos.
     * @return Identificador genérico de chasis.
     */
    @Override
    public String obtenerIdentificador() {
        return "CHASIS";
    }
}