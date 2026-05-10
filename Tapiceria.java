
/**
 * Clase que representa la tapicería de un vehículo dentro del sistema de la fábrica.
 * Almacena información sobre el material, color y cantidad de tela utilizada.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class Tapiceria extends ComponenteVehiculo
{
    private String color;
    private double metrosCuadradosTela;
    private TapiceriaTipoMaterial material;
    
    
    /**
     * Constructor para la clase Tapiceria.
     * @param color Tono cromático de la tapicería.
     * @param metrosCuadradosTela Cantidad de material necesaria.
     * @param material Tipo de tejido (TELA, CUERO o ALCANTARA).
     */
    public Tapiceria(String color, double metrosCuadradosTela, TapiceriaTipoMaterial material){
        this.color = color;
        this.metrosCuadradosTela = (metrosCuadradosTela > 0) ? metrosCuadradosTela : 0; // No se pueden tener metros de tela negativos
        this.material = material;
    }
    
    public String obtenerColor() { return color; }
    public double obtenerMetrosCuadradosTela() { return metrosCuadradosTela; }
    public TapiceriaTipoMaterial obtenerMaterial() { return material; }

    public void establecerMetrosCuadradosTela(double metrosCuadradosTela) {
        if (metrosCuadradosTela >= 0) {
            this.metrosCuadradosTela = metrosCuadradosTela;
        }
    }
    public void establecerColor(String color) { this.color = color; }
    public void establecerMaterial(TapiceriaTipoMaterial material) { this.material = material; }
    
    @Override
    public String obtenerDatosTecnicos(){
        return color + "color_" + metrosCuadradosTela + "metrosCuadradosTela_" + material + "material";
    }
    
    @Override
    public String obtenerIdentificador(){
        return "TAPICERIA_" + obtenerDatosTecnicos();
    }
}