
/**
 * Write a description of class Tapiceria here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tapiceria extends ComponenteVehiculo
{
    private String color;
    private double metrosCuadradosTela;
    private TapiceriaTipoMaterial material;
    
    public Tapiceria(String color, double metrosCuadradosTela, TapiceriaTipoMaterial material){
        this.color = color;
        this.metrosCuadradosTela = metrosCuadradosTela;
        this.material = material;
    }
    
    @Override
    public String obtenerDatosTecnicos(){
        return color + "color_" + metrosCuadradosTela + "metrosCuadradosTela_" + material + "material";
    }
    
    @Override
    public String obtenerIdentificador(){
        return "TAPICERIA_" + obtenerDatosTecnicos();
    }
}