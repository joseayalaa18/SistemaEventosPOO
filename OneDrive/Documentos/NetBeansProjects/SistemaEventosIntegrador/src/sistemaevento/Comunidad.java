/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaevento;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 *
 * @author jose1
 */
//eSta clase representa a un particiapnte en el sistema de eventos
public class Comunidad extends Usuario { 

    //el unico atributo que tiene es una lista vacia de inscripciones
    private List<Inscripcion> inscripciones;

    //Constructor de la clase, ademas mediante el metodo super, se trae los atributos de la clase padre
    public Comunidad(String nombre, String apellido, String contraseña, String correo, Rol rol) {
        super(nombre, apellido, contraseña, correo, rol);
        this.inscripciones = new ArrayList<>(); //inicializa la lista vacia
    }

    //Metodo que registra un usuario en un evento
    public void inscribirse(Evento evento) {
        //defino una variable del tipo localDateTime - metodo estatico que devuelve una instancia 
        LocalDateTime fechaInscripcion = LocalDateTime.now(); //a traves de LocalDateTime.now, estoy obteniendo una instancia del obj LOcalDateTime
        
        EstadoInscripcion estado = EstadoInscripcion.PENDIENTE; //asigno una referencia a una constante, PENDIENTE
        
        double montoPagar = evento.getPrecio(); //Defino una varibale
        
        Inscripcion inscripcion = new Inscripcion(evento, fechaInscripcion, estado, montoPagar); //creo un obj de inscripcion
        
        inscripciones.add(inscripcion); // Agregar la inscripción al participante inmediatamente
        evento.agregarInscripcion(inscripcion);
        inscripcion.realizarPago();
        
        if (inscripcion.getEstadoInscripcion() == EstadoInscripcion.CONFIRMADO) {
            System.out.println("Inscripcin confirmada para el evento " + evento.getNombre());
        } else {
            System.out.println("Inscripción pendiente,, Realice el pago para confirmar");
        }

    }

    public void cancelarInscripcion(Evento evento) {
        // Utilizo un iterador para recorrer la lista d einscriociones y eliminar mientras itera
        Iterator<Inscripcion> iterator = inscripciones.iterator(); //declaro una variable, ademas asigno un valor que es el ieterardor
        boolean encontrada = false;

        while (iterator.hasNext()) { //verifica si hay mas elementos
            Inscripcion inscripcion = iterator.next(); //obtengo el siguinete elemento con el metodo nxt
            if (inscripcion.getEvento().equals(evento)) { // Verificar si la inscripción corresponde al evento
                iterator.remove(); // Eliminar inscripción de la lista
                System.out.println("Inscripcion cancelada para el evento " + evento.getNombre());
                encontrada = true;
                break; // Terminar el recorrido después de encontrar y eliminar
            }
        }

        if (!encontrada) {
            System.out.println("No se encontro una inscripcion para el evento " + evento.getNombre());
        }
    }

    //se utiliza para devolver una lista de eventos en los que el usuario tiene una inscripción pendiente
    public List<Evento> obtenerEventosInscriptos() { //se utiliza en el main para q el usuario cancele la inscripcion
        List<Evento> eventosInscriptos = new ArrayList<>();
        for (Inscripcion inscripcion : inscripciones) {
            eventosInscriptos.add(inscripcion.getEvento()); // Añadir todos los eventos asociados
        }
        return eventosInscriptos;
    }

    @Override //sobreescritura del metodo de la clase padre
    public void mostrarDatos() {
        System.out.println("Participante: " + nombre + " " + apellido);
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones; //S.E lo utilizo
    }

}
