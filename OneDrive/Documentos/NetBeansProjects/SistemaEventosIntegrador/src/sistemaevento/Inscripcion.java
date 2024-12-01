/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaevento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; //permite formatear las fechas

/**
 *
 * @author jose1
 */

public class Inscripcion {

    private Evento evento; //atributos
    private LocalDateTime fechaInscripcion;
    private EstadoInscripcion estadoInscripcion;
    private double montoPagar;
    private Pago pago;

    public Inscripcion(Evento evento, LocalDateTime fechaInscripcion, EstadoInscripcion estadoInscripcion, double montoPagar) {
        this.evento = evento;
        this.fechaInscripcion = LocalDateTime.now();
        this.estadoInscripcion = estadoInscripcion;
        this.montoPagar = montoPagar;
        this.pago = new Pago(montoPagar, "Tarjeta de credito"); 
    }

    public void realizarPago() { 
        if (estadoInscripcion == EstadoInscripcion.PENDIENTE && pago != null) {
            pago.pagar(this); //invoco al metodo pagar del obj pago, this trae el onj de inscripcion
        } else {
            System.out.println("La inscripcion ya ha sido confirmada");
        }
    }

    public void generarInformacion() { //no llamo en ningun lado
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();

        sb.append("Informacion de la inscripcion en el evento: ").append(evento.getNombre()).append("\n")
                .append("Fecha de inscripcion: ").append(fechaInscripcion.format(formato)).append("\n")
                .append("Estado de inscripcion: ").append(estadoInscripcion).append("\n")
                .append("Monto a pagar: ").append(montoPagar).append("\n");

        if (pago != null) {
            sb.append("Estado del pago: ");
            if (pago.isEstado()) {
                sb.append("Completado");
            } else {
                sb.append("Pendiente");
            }
           
        }

        System.out.println(sb.toString());

    }

    public Evento getEvento() { //obtengo el evento
        return evento;
    }

    public EstadoInscripcion getEstadoInscripcion() {
        return estadoInscripcion; //obtengo el estado de inscrpcion
    }

    public void setEstadoInscripcion(EstadoInscripcion estadoInscripcion) {
        this.estadoInscripcion = estadoInscripcion; //set para modifcar la inscripcion
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public double getMontoPagar() {
        return montoPagar;
    }

    public Pago getPago() {
        return pago;
    }

}
