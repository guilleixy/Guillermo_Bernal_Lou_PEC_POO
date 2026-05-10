import java.util.List;
import java.util.ArrayList;

/**
 * Clase abstracta que define las propiedades comunes de cualquier vehículo de la fábrica.
 * Almacena datos físicos y el estado de su montaje.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public abstract class Vehiculo
{
    private String color;
    private int numPlazas;
    private double tara;
    private double pesoMaximo;
    private String tipo;
    private List<ComponenteTipo> componentesInstalados;
    
    public Vehiculo(String color, int numPlazas, double tara, double pesoMaximo, String tipo){
        this.color = color;
        this.numPlazas = numPlazas;
        this.tara = tara;
        this.pesoMaximo = pesoMaximo;
        this.tipo = tipo;
        this.componentesInstalados = new ArrayList<>();
    }
    
    /**
     * Añade un componente al listado de piezas ensambladas.
     * @param tipo El tipo de componente instalado.
     */
    public void instalarComponente(ComponenteTipo tipo) {
        if (!componentesInstalados.contains(tipo)) {
            componentesInstalados.add(tipo);
        }
    }
    
    /**
     * Verifica si el vehículo tiene todos los componentes necesarios instalados.
     * @return true si el montaje ha finalizado.
     */
    public boolean estaTerminado(){
        return componentesInstalados.size() == ComponenteTipo.values().length;
    }
    
    public String obtenerColor() { return color; }
    public void establecerColor(String color) { this.color = color; }

    public int obtenerNumPlazas() { return numPlazas; }
    public void establecerNumPlazas(int numPlazas) { this.numPlazas = numPlazas; }

    public double obtenerTara() { return tara; }
    public void establecerTara(double tara) { this.tara = tara; }

    public double obtenerPesoMaximo() { return pesoMaximo; }
    public void establecerPesoMaximo(double pesoMaximo) { this.pesoMaximo = pesoMaximo; }

    public String obtenerTipo(){ return tipo; } 
}