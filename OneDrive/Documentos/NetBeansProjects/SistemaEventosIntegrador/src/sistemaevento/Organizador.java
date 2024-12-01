/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaevento;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


/**
 *
 * @author jose1
 */
//Esta clase representa un rol de usuario, esta clase esxtienede de usuarios ademas de que implementa la interfaz de gestion
public class Organizador extends Usuario implements Gestion {

    private String empresa; //Atributos del organizador, por defecto inicia como: no especiifcada
    private List<Evento> eventosOrganizados; //declaro como tipo List, y su implementacioin es un arrayList

    //Constructor que inicaliza Org con solo 3 atributos
    //Este utilizo en la clase SisEvento, para cuando se quiera ver la info de un evento, solo muestre esos tres datos
    //de los organizadores, es decir, lo que una pers puede ver.
    public Organizador(String nombre, String apellido, String correo) {
        super(nombre, apellido, "", correo, Rol.ORGANIZADOR); //la contraseña esta vacia por defectoo
        this.empresa = "No especificada"; //empresa no asignada
        this.eventosOrganizados = new ArrayList<>(); //inicializo la lista vacia
    }

    //Constructor complrto que uso para crear un Org, inicializar con toda la informacion
    public Organizador(String empresa, List<Evento> eventosOrganizados, String nombre, String apellido, String contraseña, String correo, Rol rol) {
        super(nombre, apellido, contraseña, correo, rol);
        this.empresa = empresa;
        this.eventosOrganizados = new ArrayList<>();
    }

    //Estos son los tres mètodos que la clase sobreescribe de la interfaz gestion
    
    @Override
    //Agrego un nuevo evento a la lista de eventosOrg
    public void añadir(Object evento) { //el metodo añadir esta diseñado para recibir un parametro de tipo obj, y no directamente de tipo Event
        eventosOrganizados.add((Evento) evento); //Casteo de obj a su tipo original de dato, en este caso Evento
        System.out.println("Evento añadido al organizador");
    }

    @Override
    public void eliminar(Object evento) { //elimina un evento
        eventosOrganizados.remove((Evento) evento); //casteo de Object a el tipo especifico de dato, Evento
        System.out.println("Evento eliminado del organizador");
    }

    @Override
    //metodo de verificacion
    public void modificar(Object evento) { 
        if (evento instanceof Evento) { //verifica si es una instancia de la clase evento
            //si es positiva, llama a una version sobrecargada del metodo
            modificar((Evento) evento, "NuevoNombre", "NuevaInformacion", null, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 100.0, EstadoEvento.PROGRAMADO);
        } else { 
            System.out.println("Error");
        }
    }

    //mètodo sobrecargado con parametros
    public void modificar(Evento evento, String nuevoNombre, String nuevaInformacion, Ubicacion nuevaUbicacion, LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFin, double nuevoPrecio, EstadoEvento nuevoEstado) {
        //utiliza los metodos setter de la clase Evento
        evento.setNombre(nuevoNombre); //a setnombre le paso como parametro el nuevo nombre
        evento.setInformacion(nuevaInformacion);
        evento.setUbicacion(nuevaUbicacion);
        evento.setFechaInicio(nuevaFechaInicio);
        evento.setFechaFin(nuevaFechaFin);
        evento.setPrecio(nuevoPrecio);
        evento.setEstado(nuevoEstado);
        System.out.println("El evento ha sido modificado");
        
    }

    @Override //sobreescritura del metodo de la clase padre
    public void mostrarDatos() { //lo utilizo en la clase SsitemaEventos, en el metodo mostrarUsuarios()
        System.out.println("Organizador: " + nombre + " " + apellido + ", Empresa: " + empresa);
    }

    //se obtine una lista de los eventos organizados
    public List<Evento> getEventosOrganizados() { //se utiliza en el main para modificar un evento
        return eventosOrganizados;
    }

    //se obtiene la empresa, como obj
    public String getEmpresa() { //se utiliza en guardarEventos(), de la clase SistemaEvento
        return empresa;
    }

}
