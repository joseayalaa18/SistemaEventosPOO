/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaevento;

/**
 *
 * @author jose1
 */
//
//Esta clase modela la ubicaciòn fìsica donde podrian realizarse eventos.

public class Ubicacion { //Tiene cuatro atributos, el nombre del lugar, la direccion, la cuidad y la capacidad de personas
    private String nombreLugar;
    private String direccion;
    private String ciudad;
    private int capacidad;

    public Ubicacion(String nombreLugar, String direccion, String ciudad, int capacidad) {
        this.nombreLugar = nombreLugar; //Constructor de la clase, que inicializa los atributos
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
    }
    
    public void mostrarInfo(){ //Metodo que genera un resumen de la informacion de la ubicacion, utilizando la clase StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("Informacion de la ubicacion: ")
                .append(nombreLugar)
                .append(", ")
                .append(direccion)
                .append(", ")
                .append(ciudad)
                .append(", ")
                .append(capacidad);
        
        System.out.println(sb.toString());
    }

    //metodos get para poder acceder a los atributos privados en otras clases
    public String getNombreLugar() { //utilizo para mostrar la informacion completa en la clase EVENTO
        return nombreLugar;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getCapacidad() {
        return capacidad;
    }

}
