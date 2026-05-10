/**
 * Representa un vehículo de tipo Biplaza Deportivo.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class VehiculoBiplaza extends Vehiculo
{
    public VehiculoBiplaza(String color, int numPlazas, double tara, double pesoMaximo){
        super(color, numPlazas, tara, pesoMaximo, "biplaza");
    }
}