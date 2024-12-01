/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaevento;

import java.time.LocalDateTime; //Uso para manejar fechas y horas
import java.util.ArrayList; //Utilizo para almanecar elementos de ArrayList y que sean mutables
import java.util.List; //Manejo de listas

/**
 *
 * @author jose1
 */
public class Evento { //Atributos de la clase evento

    private String nombre; //11
    private String informacion;
    private Ubicacion ubicacion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Organizador organizador;
    private EstadoEvento estado;
    private double precio;
    private int capacidadMax;
    private int capacidadMin;
    private List<Inscripcion> inscripciones;

    public Evento(String nombre, String informacion, Ubicacion ubicacion, LocalDateTime fechaInicio, LocalDateTime fechaFin, Organizador organizador, EstadoEvento estado, double precio, int capacidadMax, int capacidadMin) {

        this.nombre = nombre; //Constructor de la clase, ademas inicializo la lista vacìa
        this.informacion = informacion;
        this.ubicacion = ubicacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.organizador = organizador;
        this.estado = estado;
        this.precio = precio;
        this.capacidadMax = capacidadMax;
        this.capacidadMin = capacidadMin;
        this.inscripciones = new ArrayList<>();
    }

    public void mostrarInformacionCompleta() {
        System.out.println("Nombre del evento: " + nombre);
        System.out.println("Informacion: " + informacion);

        if (ubicacion != null) {
            System.out.println("Ubicacion: " + ubicacion.getNombreLugar() + ", Direccion: " + ubicacion.getDireccion() + ", Ciudad: " + ubicacion.getCiudad());
        } else {
            System.out.println("Ubicacion: No asignada");
        }

        System.out.println("Fecha de inicio: " + fechaInicio);
        System.out.println("Fecha de fin: " + fechaFin);

        if (organizador != null) {
            System.out.println("Organizador: " + organizador.getNombre() + " " + organizador.getApellido() + ", Empresa: " + organizador.getCorreo());
        } else {
            System.out.println("Organizador: No asignado");
        }

        System.out.println("Estado: " + estado);
        System.out.println("Precio: " + precio);
        System.out.println("Capacidad maxima: " + capacidadMax);
        System.out.println("Capacidad minima: " + capacidadMin);
        System.out.println("Inscriptos: " + getCantidadInscriptos());
        System.out.println();
    }

    // mètodos get necesarios, que se utilizan en ptras clases
    public int getCantidadInscriptos() {
        return inscripciones.size();

    }

    public String getInformacion() {
        return informacion;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }

    public int getCapacidadMin() {
        return capacidadMin;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    //Todos los mètodos set que se usan para modifcar caracterìsticas del evento
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCapacidadMax(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public void setCapacidadMin(int capacidadMin) {
        this.capacidadMin = capacidadMin;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    //agrego una inscripcion al evento
    public void agregarInscripcion(Inscripcion inscripcion) {
        if (inscripciones.size() < capacidadMax) {
            inscripciones.add(inscripcion);
            System.out.println("Inscripción añadida al evento " + nombre);
        } else {
            System.out.println("No se puede agregar mas inscripciones, Capacidad max alcanzada");
        }
    } //comunidad, cargarInscripcion

}
