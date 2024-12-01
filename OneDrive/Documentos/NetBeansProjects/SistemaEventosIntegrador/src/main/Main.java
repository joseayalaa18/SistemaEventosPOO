/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import sistemaevento.*;
import sistemalog.SistemaEvento;
import static metodosauxiliares.leerOp.*;

public class Main {

    public static void main(String[] args) {
        SistemaEvento sistema = new SistemaEvento(); //creo una instancia de mi nucleo del sistema para poder gestionar los usuarios y eventos
        Scanner scanner = new Scanner(System.in); //creo un obejto de tipo Scanner para leer entradas
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"); //declaro una variable de tipo dateTimeFormatter que uso para el formato de fechas y horas
//DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"): Utiliza el método estático ofPattern de la clase DateTimeFormatter para crear un formato personalizado de fecha y hora

        System.out.println("--------{BIENVENIDX AL SISTEMA DE EVENTOS}--------");
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nSeleccione una opcion valida:");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesion");
            System.out.println("3. Ver total de usuarios y eventos");
            System.out.println("4. Mostrar datos de todos los usuarios");
            System.out.println("5. Mostrar informacion de todos los eventos");
            System.out.println("6. Recuperar Usuario");
            System.out.println("7. Eliminar un usuario"); // Nueva opción
            System.out.println("8. Salir");
            int opcion = leerOpcionEnRango(scanner, 1, 8);

            switch (opcion) {
                case 1:

                    System.out.println("Seleccione su ROL:");
                    System.out.println("1. Organizador de eventos");
                    System.out.println("2. Participante");
                    int rolSeleccionado = leerOpcionEnRango(scanner, 1, 2);

                    Rol rol = (rolSeleccionado == 1) ? Rol.ORGANIZADOR : Rol.PARTICIPANTE;
                    //condición ? valor si verdadero : valor si falso

                    System.out.println("Ingrese su nombre:");
                    String nombre = leerPalabra(scanner);
                    System.out.println("Ingrese su apellido:");
                    String apellido = leerPalabra(scanner);
                    System.out.println("Ingrese su correo:");
                    String correo = scanner.nextLine();
                    System.out.println("Ingrese su contraseña:");
                    String contraseña = scanner.nextLine();

                    if (rol == Rol.ORGANIZADOR) {
                        System.out.println("Ingrese el nombre de la empresa:");
                        String empresa = leerPalabra(scanner);
                        Organizador organizador = new Organizador(empresa, null, nombre, apellido, contraseña, correo, rol);
                        sistema.añadir(organizador);
                    } else {
                        Comunidad participante = new Comunidad(nombre, apellido, contraseña, correo, rol);
                        sistema.añadir(participante);
                    }
                    System.out.println("El registro ha sido completado");
                    break;

                case 2:
                    System.out.println("Ingrese su correo:");
                    String correoLogin = scanner.nextLine();
                    System.out.println("Ingrese su contraseña:");
                    String contraseñaLogin = scanner.nextLine();

                    if (sistema.iniciarSesion(correoLogin, contraseñaLogin)) {
                        Usuario usuario = sistema.recuperarUsuario(correoLogin);

                        if (usuario instanceof Organizador) {
                            Organizador organizador = (Organizador) usuario;
                            boolean menuOrganizador = true;

                            while (menuOrganizador) {
                                System.out.println("\nOpciones para Organizador:");
                                System.out.println("1. Crear evento");
                                System.out.println("2. Modificar evento");
                                System.out.println("3. Cancelar evento");
                                System.out.println("4. Cerrar sesión");
                                int opcionOrganizador = leerOpcionEnRango(scanner, 1, 4);

                                switch (opcionOrganizador) {
                                    case 1:
                                        System.out.println("Ingrese el nombre del evento:");
                                        String nombreEvento = leerPalabra(scanner);
                                        System.out.println("Ingrese información del evento:");
                                        String informacion = leerPalabra(scanner);
                                        System.out.println("Ingrese nombre del lugar:");
                                        String lugar = leerPalabra(scanner);
                                        System.out.println("Ingrese direccion del lugar:");
                                        String direccion = leerPalabra(scanner);
                                        System.out.println("Ingrese ciudad:");
                                        String ciudad = leerPalabra(scanner);

                                        System.out.println("Ingrese capacidad del lugar:");
                                        int capacidad = leerEntero(scanner);

                                        Ubicacion ubicacion = new Ubicacion(lugar, direccion, ciudad, capacidad);

                                        System.out.println("Ingrese fecha de inicio formato(anio, mes ,dia, hora): ");
                                        LocalDateTime inicio = leerFecha(scanner, formato);

                                        System.out.println("Ingrese fecha de fin:");
                                        LocalDateTime fin = leerFecha(scanner, formato);

                                        System.out.println("Ingrese el precio del evento:");
                                        double precio = leerDouble(scanner);

                                        int capacidadMax;
                                        int capacidadMin;
                                        do {
                                            System.out.println("Ingrese capacidad maxima:");
                                            capacidadMax = leerEntero(scanner);
                                            System.out.println("Ingrese capacidad minima:");
                                            capacidadMin = leerEntero(scanner);

                                            if (capacidadMax < capacidadMin) {
                                                System.out.println("Capacidad maxima debe ser mayor o igual a capacidad minima");
                                            }
                                        } while (capacidadMax < capacidadMin);

                                        Evento evento = new Evento(nombreEvento, informacion, ubicacion, inicio, fin, organizador, EstadoEvento.PROGRAMADO, precio, capacidadMax, capacidadMin);
                                        organizador.añadir(evento);
                                        sistema.añadirEvento(evento);
                                        break;

                                    case 2:
                                        System.out.println("Seleccione el Evento a modificar:");
                                        List<Evento> eventos = organizador.getEventosOrganizados(); 
                                        for (int i = 0; i < eventos.size(); i++) {
                                            System.out.println((i + 1) + ". " + eventos.get(i).getNombre());
                                        }
                                        int eventoModificar = leerOpcion(scanner) - 1; //Se resta 1 a la opción ingresada para que coincida con el índice de la lista por ej si el usuario selecciona 1, esto apunta al índice 0 de la lista que es donde comienza
                                        if (eventoModificar >= 0 && eventoModificar < eventos.size()) { //verifica que eventoModificar estee dentro de los límites de la lista para evitar erro
                                            Evento eventoMod = eventos.get(eventoModificar); //accede al evento específico que el usuario ha 
                                            //seleccionado para modificar y lo almacena en la variable eventoMod
                                            boolean salir = false;

                                            while (!salir) {
                                                System.out.println("\n¿Que atributo deseas modificar?");
                                                System.out.println("1. Nombre");
                                                System.out.println("2. Informacion");
                                                System.out.println("3. Ubicacion");
                                                System.out.println("4. Fecha de inicio");
                                                System.out.println("5. Fecha de fin");
                                                System.out.println("6. Organizador");
                                                System.out.println("7. Estado");
                                                System.out.println("8. Precio");
                                                System.out.println("9. Capacidad maxima");
                                                System.out.println("10. Capacidad minima");
                                                System.out.println("11. Salir");

                                                int opcionModificacion = leerOpcionEnRango(scanner, 1, 11);

                                                switch (opcionModificacion) {
                                                    case 1:
                                                        System.out.println("Ingrese el nuevo nombre del evento:");
                                                        String nuevoNombre = leerPalabra(scanner);
                                                        eventoMod.setNombre(nuevoNombre);
                                                        break;

                                                    case 2:
                                                        System.out.println("Ingrese la nueva informacion del evento:");
                                                        String nuevaInformacion = leerPalabra(scanner);
                                                        eventoMod.setInformacion(nuevaInformacion);
                                                        break;

                                                    case 3:
                                                        System.out.println("Ingrese el nuevo nombre de la ubicacion:");
                                                        String nuevoNombreLugar = leerPalabra(scanner);
                                                        System.out.println("Ingrese la nueva dirección de la ubicacion:");
                                                        String nuevaDireccion = leerPalabra(scanner);
                                                        System.out.println("Ingrese la nueva ciudad de la ubicacion:");
                                                        String nuevaCiudad = leerPalabra(scanner);

                                                        System.out.println("Ingrese la capacidad de la ubicacion:");
                                                        capacidad = leerEntero(scanner);
                                                        Ubicacion nuevaUbicacion = new Ubicacion(nuevoNombreLugar, nuevaDireccion, nuevaCiudad, capacidad);
                                                        eventoMod.setUbicacion(nuevaUbicacion);
                                                        break;

                                                    case 4:
                                                        System.out.println("Ingrese la nueva fecha de inicio. formato(anio, mes ,dia, hora):");
                                                        String fechaInicioStr = leerPalabra(scanner);
                                                        LocalDateTime nuevaFechaInicio = LocalDateTime.parse(fechaInicioStr);
                                                        eventoMod.setFechaInicio(nuevaFechaInicio);
                                                        break;

                                                    case 5:
                                                        System.out.println("Ingrese la nueva fecha de fin. formato(anio, mes ,dia, hora):");
                                                        String fechaFinStr = leerPalabra(scanner);
                                                        LocalDateTime nuevaFechaFin = LocalDateTime.parse(fechaFinStr);
                                                        eventoMod.setFechaFin(nuevaFechaFin);
                                                        break;

                                                    case 6:
                                                        System.out.println("Ingrese el nuevo nombre del organizador:");
                                                        String nuevoNombreOrganizador = leerPalabra(scanner);
                                                        System.out.println("Ingrese el nuevo apellido del organizador:");
                                                        String nuevoApellidoOrganizador = leerPalabra(scanner);
                                                        System.out.println("Ingrese la nueva empresa del organizador:");
                                                        String nuevaEmpresaOrganizador = leerPalabra(scanner);
                                                        System.out.println("Ingrese la nueva contraseña del organizador:");
                                                        String nuevaContraseñaOrganizador = leerPalabra(scanner);
                                                        System.out.println("Ingrese el nuevo correo del organizador:");
                                                        String nuevoCorreoOrganizador = leerPalabra(scanner);
                                                        System.out.println("Ingrese el nuevo rol del organizador (1 para ORGANIZADOR, 2 para PARTICIPANTE):");
                                                        Rol nuevoRol = leerOpcionEnRango(scanner, 1, 2) == 1 ? Rol.ORGANIZADOR : Rol.PARTICIPANTE;
                                                        Organizador nuevoOrganizador = new Organizador(nuevaEmpresaOrganizador, null, nuevoNombreOrganizador, nuevoApellidoOrganizador, nuevaContraseñaOrganizador, nuevoCorreoOrganizador, nuevoRol);

                                                        break;

                                                    case 7:
                                                        System.out.println("Seleccione el nuevo estado del evento (1. PROGRAMADO, 2. CANCELADO, 3. FINALIZADO):");
                                                        int estadoOpcion = leerOpcion(scanner);
                                                        EstadoEvento nuevoEstado = EstadoEvento.values()[estadoOpcion - 1];
                                                        eventoMod.setEstado(nuevoEstado);
                                                        break;

                                                    case 8:
                                                        System.out.println("Ingrese el nuevo precio del evento:");
                                                        double nuevoPrecio = leerDouble(scanner);
                                                        eventoMod.setPrecio(nuevoPrecio);
                                                        break;

                                                    case 9:
                                                        System.out.println("Ingrese la nueva capacidad maxima:");
                                                        int nuevaCapacidadMax = leerEntero(scanner);
                                                        eventoMod.setCapacidadMax(nuevaCapacidadMax);
                                                        break;

                                                    case 10:
                                                        System.out.println("Ingrese la nueva capacidad minima:");
                                                        int nuevaCapacidadMin = leerEntero(scanner);
                                                        eventoMod.setCapacidadMin(nuevaCapacidadMin);
                                                        break;

                                                    case 11:
                                                        salir = true;
                                                        break;

                                                    default:
                                                        System.out.println("Opcion no valida");
                                                }
                                            }
                                            System.out.println("El evento ha sido modificado.");
                                            
                                        } else {
                                            System.out.println("Evento no encontrado");
                                        }
                                        break;

                                    case 3:
                                        System.out.println("Seleccione el evento a cancelar:");
                                        eventos = organizador.getEventosOrganizados();
                                        for (int i = 0; i < eventos.size(); i++) {
                                            System.out.println((i + 1) + ": " + eventos.get(i).getNombre());
                                        }
                                        int eventoCancelar = leerOpcion(scanner) - 1;

                                        if (eventoCancelar >= 0 && eventoCancelar < eventos.size()) {
                                            Evento eventoCanc = eventos.get(eventoCancelar);
                                            organizador.eliminar(eventoCanc);
                                            sistema.eliminarEvento(eventoCanc);
                                            System.out.println("El evento ha sido cancelado");
                                        } else {
                                            System.out.println("Evento no encontrado");
                                        }
                                        break;

                                    case 4:
                                        menuOrganizador = false;
                                        System.out.println("Cerrando sesion de Organizador...");
                                        break;

                                    default:
                                        System.out.println("Op no valida");
                                }
                            }
                        } else if (usuario instanceof Comunidad) {
                            Comunidad participante = (Comunidad) usuario;
                            boolean menuParticipante = true;

                            while (menuParticipante) {
                                System.out.println("\nOpciones para Participante:");
                                System.out.println("1. Ver eventos disponibles");
                                System.out.println("2. Inscribirse a un evento");
                                System.out.println("3. Cancelar inscripcion a un evento");
                                System.out.println("4. Cerrar sesion");
                                int opcionParticipante = leerOpcionEnRango(scanner, 1, 4);

                                switch (opcionParticipante) {
                                    case 1:
                                        List<Evento> eventosDisponibles = sistema.obtenerEventosDisponibles();
                                        for (int i = 0; i < eventosDisponibles.size(); i++) {
                                            System.out.println((i + 1) + ". " + eventosDisponibles.get(i).getNombre());
                                        }
                                        break;

                                    case 2:
                                        System.out.println("Seleccione el evento al que desea inscribirse:");
                                        List<Evento> eventosParaInscribir = sistema.obtenerEventosDisponibles();
                                        if (eventosParaInscribir.isEmpty()) {
                                            System.out.println("No hay eventos disponibles para inscripcion");
                                            break;
                                        }
                                        for (int i = 0; i < eventosParaInscribir.size(); i++) {
                                            System.out.println((i + 1) + ". " + eventosParaInscribir.get(i).getNombre());
                                        }
                                        int eventoSeleccionado = leerOpcionEnRango(scanner, 1, eventosParaInscribir.size()) - 1;

                                        if (eventoSeleccionado >= 0 && eventoSeleccionado < eventosParaInscribir.size()) {
                                            Evento eventoInscribir = eventosParaInscribir.get(eventoSeleccionado);
                                            System.out.println("El precio del evento es: " + eventoInscribir.getPrecio());
                                            System.out.println("Seleccione el metodo de pago:");
                                            System.out.println("1. Tarjeta de credito\n2. Efectivo");
                                            int metodoPago = leerOpcionEnRango(scanner, 1, 2);

                                            String metodoSeleccionado = (metodoPago == 1) ? "Tarjeta de credito" : "Efectivo";
                                            Inscripcion nuevaInscripcion = new Inscripcion(eventoInscribir, LocalDateTime.now(), EstadoInscripcion.PENDIENTE, eventoInscribir.getPrecio());
                                            nuevaInscripcion.realizarPago();

                                            if (nuevaInscripcion.getEstadoInscripcion() == EstadoInscripcion.CONFIRMADO) {
                                                participante.inscribirse(eventoInscribir);
                                                sistema.guardarInscripciones();
                                                System.out.println("");
                                            } else {
                                                System.out.println("No se pudo completar la inscripcion verifica el pago");
                                            }
                                        } else {
                                            System.out.println("Evento no encontrado");
                                        }
                                        break;

                                    case 3:
                                        System.out.println("Seleccione el evento del cual desea cancelar su inscripcion:");
                                        List<Evento> eventosInscriptos = participante.obtenerEventosInscriptos();
                                        if (eventosInscriptos.isEmpty()) { //verifica si la lista esta vacia mediante el metodo empty
                                            System.out.println("No tienes inscripciones pendientes");
                                            break;
                                        }

                                        for (int i = 0; i < eventosInscriptos.size(); i++) {
                                            System.out.println((i + 1) + ". " + eventosInscriptos.get(i).getNombre());
                                        }
                                        int eventoCancelar = leerOpcionEnRango(scanner, 1, eventosInscriptos.size()) - 1;
                                        if (eventoCancelar >= 0 && eventoCancelar < eventosInscriptos.size()) {
                                            Evento eventoCancelarInscripcion = eventosInscriptos.get(eventoCancelar);
                                            participante.cancelarInscripcion(eventoCancelarInscripcion);
                                            sistema.guardarInscripciones();
                                        } else {
                                            System.out.println("Evento no encontrado");
                                        }
                                        break;
                                    case 4:
                                        menuParticipante = false;
                                        System.out.println("Cerrando sesion de Participante...");
                                        break;

                                    default:
                                        System.out.println("La opcion no es valida");
                                }
                            }
                        }
                    } else {
                        System.out.println("Entrada Incorrecta, no existe un usuario con esas credencialess");
                    }
                    break;

                case 3:
                    int totalUsuarios = SistemaEvento.obtenerTotalUsuarios(sistema);
                    int totalEventos = SistemaEvento.obtenerTotalEventos(sistema);
                    System.out.println("Total de usuarios registrados: " + totalUsuarios);
                    System.out.println("Total de eventos creados: " + totalEventos);
                    break;

                case 4:
                    sistema.mostrarDatosUsuarios();
                    break;

                case 5:
                    sistema.mostrarInformacionEventos();
                    break;

                case 6:
                    System.out.println("Ingrese el correo del usuario a recuperar:");
                    String correoRecuperar = scanner.nextLine();
                    Usuario usuarioRecuperado = sistema.recuperarUsuario(correoRecuperar);
                    if (usuarioRecuperado != null) {
                        System.out.println("Usuario encontrado: ");
                        usuarioRecuperado.mostrarDatos();
                    } else {
                        System.out.println("No se encontro el usuario con el correo proporcionado ");
                    }
                    break;

                case 7:
                    System.out.println("Ingrese el correo del usuario que desea eliminar:");
                    String correoAEliminar = scanner.nextLine().trim();

                    Usuario usuarioAEliminar = sistema.getUsuarios().stream() //busca en la lisita un obj de tipo usuario
                            .filter(usuario -> usuario.getCorreo().equals(correoAEliminar))
                            .findFirst()
                            .orElse(null);

                    if (usuarioAEliminar != null) {
                        sistema.eliminar(usuarioAEliminar);
                    } else {
                        System.out.println("No se encontro un usuario con el correo proporcionado");
                    }
                    break;

                case 8:
                    continuar = false;
                    System.out.println("Gracias por usar el sistema ");
                    break;

                default:
                    System.out.println("Opcion no valida");
            }
        }

        scanner.close();
    }
}
