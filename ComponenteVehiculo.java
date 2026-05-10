/**
 * Clase abstracta base que define la estructura común para todos los 
 * componentes que pueden ser ensamblados en un vehículo dentro de la fábrica.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public abstract class ComponenteVehiculo
{
    /**
     * Devuelve una cadena de texto única que identifica de forma inequívoca 
     * al componente.
     * @return Identificador único del componente.
     */
    public abstract String obtenerIdentificador();
    
    /**
     * Devuelve una representación detallada de las especificaciones técnicas 
     * del componente (cilindrada, color, dimensiones, etc.).
     * @return Cadena con los datos técnicos formateados.
     */
    public abstract String obtenerDatosTecnicos();
}