/**
 * Representa un vehículo de tipo Furgoneta.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class VehiculoFurgoneta extends Vehiculo
{
    public VehiculoFurgoneta(String color, int numPlazas, double tara, double pesoMaximo){
        super(color, numPlazas, tara, pesoMaximo, "furgoneta");
    }
}