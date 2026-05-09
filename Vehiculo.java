
/**
 * Write a description of class vehiculo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Vehiculo
{
    private String color;
    private int numPlazas;
    private double tara;
    private double pesoMaximo;
    private String tipo;
    
    public Vehiculo(String color, int numPlazas, double tara, double pesoMaximo, String tipo){
        this.color = color;
        this.numPlazas = numPlazas;
        this.tara = tara;
        this.pesoMaximo = pesoMaximo;
        this.tipo = tipo;
    }
    public String obtenerTipo(){
        return tipo;
    }
}