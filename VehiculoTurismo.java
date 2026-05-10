/**
 * Representa un vehículo de tipo Turismo.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class VehiculoTurismo extends Vehiculo
{
    public VehiculoTurismo(String color, int numPlazas, double tara, double pesoMaximo){
        super(color, numPlazas, tara, pesoMaximo, "turismo");
    }
}