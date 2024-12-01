/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemalog;

import java.util.ArrayList;
import java.util.List;
import sistemaevento.Usuario;
import sistemaevento.Organizador;
import sistemaevento.Evento;
import sistemaevento.EstadoEvento;
import sistemaevento.*;
import sistemaevento.Comunidad;
import java.io.*; //se utiliza para op de entradas y salidas, como leer datos desdes arch o escribir datos en archivos
import java.time.LocalDateTime;

/**
 *
 * @author jose1
 */
//Esta clase actua como nucleo del sistema donde se gestionan los usuarios y los eventos del sistema, el mismo implemta la intefaz Gestion, el cual tiene tres metodos que seran sobreescrito
public class SistemaEvento implements Gestion {

    private List<Usuario> usuarios; //Lista de ususarios que estan logueados en el sistema
    private List<Evento> eventos; //Lista de org que entan logueados en el sistema

    //Ruta del archivo donde se guardan los datos de los usuarios, eventos y las inscripciones
    private final String USUARIOS= "C:/Users/jose1/OneDrive/Documentos/NetBeansProjects/SistemaEventosIntegrador/data/usuarios.txt";
    private final String EVENTOS = "C:/Users/jose1/OneDrive/Documentos/NetBeansProjects/SistemaEventosIntegrador/data/eventos.txt";
    private final String INSCRIPCIONES = "C:/Users/jose1/OneDrive/Documentos/NetBeansProjects/SistemaEventosIntegrador/data/inscriptos.txt";

    public SistemaEvento() { //contructor de la clase que inicaliza las listas vacias
        this.usuarios = new ArrayList<>();
        this.eventos = new ArrayList<>();
        inicializarArchivos(); //se encarga d everificar si los archivos de e, u e i existen o sino los crea
        cargarUsuarios(); //lee los datos de los usuarios desde el archivo y los  carga
        cargarEventos();
        cargarInscripciones();

    }

    //este metodo verifica si usuario y eventos existen en las ubicaciones especificadas
    public void inicializarArchivos() {
        try {
            File usuariosArchivo = new File(USUARIOS); //crea un obj File, que representa un archivo en el sistema de arch
            if (!usuariosArchivo.exists()) { //verifica si existe
                usuariosArchivo.getParentFile().mkdirs(); //o	Este método devuelve un objeto File que representa el directorio padre del archivo actual
                //mkdirs(): intenta crear el directorio representado por le obj File
                usuariosArchivo.createNewFile(); //si no existe crea un nuevo archivo, en la ruta especificada
            }

            File eventosArchivo = new File(EVENTOS);
            if (!eventosArchivo.exists()) {
                eventosArchivo.getParentFile().mkdirs();
                eventosArchivo.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar archivos: " + e.getMessage());
        }
    }
    
    //Tiene la funcion de guardar Informacion en los archivos de textos
    public void guardarUsuarios() {
        //FileWriter(usu): crea un obj FileW para escribir en el archivo
        try (BufferedWriter escritura = new BufferedWriter(new FileWriter(USUARIOS))) { //bufferWriter, perimine escriibir texto
            for (Usuario usuario : usuarios) {
                String tipo = usuario instanceof Organizador ? "Organizador" : "Comunidad";
                escritura.write(tipo + "," + usuario.getNombre() + "," + usuario.getApellido() + ","
                        + usuario.getCorreo() + "," + usuario.getContraseña());
                escritura.newLine(); //añade un salto de linea
            }
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    

    public void guardarEventos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTOS))) { //abre el archivo en modo escritura, si el arch ya existe sobreescribe el contenido
            for (Evento evento : eventos) {//ietera sobre la lista de eventos
                String ubicacion = (evento.getUbicacion() != null) //si el evento tiene una ubicacion se desglosan sus atributos
                        ? evento.getUbicacion().getNombreLugar() + ";" + evento.getUbicacion().getDireccion() + ";"
                        + evento.getUbicacion().getCiudad() + ";" + evento.getUbicacion().getCapacidad() //se concatenan en una sola cadena
                        : "Sin ubicacion;Sin direccion;Sin ciudad;0"; //operador ternario ..? - : -

                String organizador = (evento.getOrganizador() != null)
                        ? evento.getOrganizador().getNombre() + ";" + evento.getOrganizador().getApellido() + ";" + evento.getOrganizador().getEmpresa()
                        : "Sin nombre;Sin apellido;Sin empresa";

                writer.write(evento.getNombre() + "," + evento.getInformacion() + "," //escribe todo los datos en el aarchivo
                        + ubicacion + "," + evento.getFechaInicio() + "," + evento.getFechaFin() + ","
                        + organizador + "," + evento.getEstado() + "," + evento.getPrecio() + ","
                        + evento.getCapacidadMax() + "," + evento.getCapacidadMin());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar eventos: " + e.getMessage());
        }
    }
    
    public void guardarInscripciones() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INSCRIPCIONES))) {

            for (Usuario usuario : usuarios) {
                if (usuario instanceof Comunidad) {
                    Comunidad comunidad = (Comunidad) usuario;
                    for (Inscripcion inscripcion : comunidad.getInscripciones()) {
                        Evento evento = inscripcion.getEvento();
                        writer.write(comunidad.getCorreo() + "," // Identificador del usuario
                                + evento.getNombre() + "," // Identificador del evento
                                + inscripcion.getFechaInscripcion() + ","
                                + inscripcion.getEstadoInscripcion() + ","
                                + inscripcion.getMontoPagar());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar inscripciones: " + e.getMessage());
        }
    }

    //funcion de leer el archivo de texto que contiene info previamente guardada, y reconstruir la lista de usu a partir de esos datos
    public void cargarUsuarios() {
        //FileReader: abre el archivo de usu en modo lectura
        try (BufferedReader reader = new BufferedReader(new FileReader(USUARIOS))) { 
            String linea;
            while ((linea = reader.readLine()) != null) { //lee linea x linea almacenando el contenido de cada linea en la variable
                //cuando alcanza el fin del archivo redLine retorna null
                String[] datos = linea.split(","); //divide la linea leida en un arrglo mediante ,
                String tipo = datos[0]; //se extraen y se asignan cada posicion del arrgelo a las variables
                String nombre = datos[1];
                String apellido = datos[2];
                String correo = datos[3];
                String contraseña = datos[4];

                if ("Organizador".equals(tipo)) { //si el tipo de usu es org, se crea un nuevo obj Org y se le agrega a la lista de usuarios
                    usuarios.add(new Organizador("", new ArrayList<>(), nombre, apellido, contraseña, correo, Rol.ORGANIZADOR));
                } else {
                    usuarios.add(new Comunidad(nombre, apellido, contraseña, correo, Rol.PARTICIPANTE));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    //lee el archivo de eventos y recontruye los eventos para el sistema
    public void cargarEventos() {
        try (BufferedReader leer = new BufferedReader(new FileReader(EVENTOS))) { //abre el archivo de eventos en modo lectura
            String linea;
            while ((linea = leer.readLine()) != null) { //se lee cada linea del archivo y almacena en la variable linea
                //el bucle continua hasta quqe se almacenen todas las lineas en el archivo
                String[] datos = linea.split(","); //creo un arreglo de datos
                String nombre = datos[0]; //extraigo los primeros dos valores del obj evento
                String informacion = datos[1];

                
                String[] ubicacionDatos = datos[2].split(";"); //divido el tercer campo en subatributos
                Ubicacion ubicacion = new Ubicacion( //creo el onjeto ubicacion
                        ubicacionDatos[0], //nombre
                        ubicacionDatos[1], //direccion
                        ubicacionDatos[2], //ciudad
                        Integer.parseInt(ubicacionDatos[3]) //convierto el valor de capacidad de cadena a entero
                );

                LocalDateTime fechaInicio = LocalDateTime.parse(datos[3]);
                LocalDateTime fechaFin = LocalDateTime.parse(datos[4]);

               
                String[] organizadorDatos = datos[5].split(";");
                Organizador organizador = new Organizador( //aca se usa el metodo constructor sobrecargado
                        organizadorDatos[0], //nombre
                        organizadorDatos[1], //apellido
                        organizadorDatos[2] //corrreo
                );

                EstadoEvento estado = EstadoEvento.valueOf(datos[6]);
                double precio = Double.parseDouble(datos[7]);
                int capacidadMax = Integer.parseInt(datos[8]);
                int capacidadMin = Integer.parseInt(datos[9]);

                //utilizo el constructor de la clase evento para crear un nuevo obj y aññadir a la lista de eventos
                eventos.add(new Evento(nombre, informacion, ubicacion, fechaInicio, fechaFin, organizador, estado, precio, capacidadMax, capacidadMin));
            }
        } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Error al cargar eventos: " + e.getMessage());
        } //ArrayIndexOutOfBoundsException; captura si la linea tiene menos datos de los esperados
    }

    
//lee el archivo de inscripciones y reconstruye las inscripciones asociadas a cada usuario
    public void cargarInscripciones() {
        try (BufferedReader leer = new BufferedReader(new FileReader(INSCRIPCIONES))) { //abre el archivo de lectura utilizando BuffeRreader
            String linea;
            while ((linea = leer.readLine()) != null) { //lee cada linea del archivo y divide a cada atributos con ,
                String[] datos = linea.split(","); //divide la linea en campos
                if (datos.length < 5) { //si la linea no tiene almenos 5 campos, se ignora
                    continue; // Saltar líneas inválidas
                }
                Comunidad comunidad = (Comunidad) usuarios.stream() //uso steam para buscar en lista de usuarios un onj de tipo comunidad
                        .filter(u -> u instanceof Comunidad && u.getCorreo().equals(datos[0])) //compara el correo que tiene que coincidir con el primer campo
                        .findFirst()
                        .orElse(null);

                Evento evento = eventos.stream() //biusca en la lista de eventos uno cuyo nombre conicida con el segundo campo
                        
                        //El metodo filter, toma un lambda como parametro (e -> e.getNombre().equals(datos[1])
                        //lambda evalua una condicon para cada elemento de la lista:
                        //aca se compara si el nombre del evento es igual al valor de datos[1]
                        .filter(e -> e.getNombre().equals(datos[1])) //si lo encuenta guarda del valor en la variable, solo los que cumplen se van al sig paso del Stream
                        .findFirst() //obtiene el primer elemento del stream filtrado, si no enceuntra devuelve un valor vacio
                        .orElse(null); //provee un valor predeterminado si no se encuentra ningun elemento, null

                if (comunidad != null && evento != null) { // si existen, crea un obj de tipo inscripcion y asocia con los atributos
                    Inscripcion inscripcion = new Inscripcion(evento, LocalDateTime.parse(datos[2]), EstadoInscripcion.valueOf(datos[3]), Double.parseDouble(datos[4]));
              
                    comunidad.getInscripciones().add(inscripcion); //añade la inscripcion a la lista de inscipciones del usuario Comunidad
                    evento.agregarInscripcion(inscripcion); //eVENTO
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo de inscripciones");
        }
    }

    // Cargar todos los datos
    public boolean iniciarSesion(String correo, String contraseña) {
        for (Usuario usuario : usuarios) { //blucle for each
            if (usuario.getCorreo().equals(correo) && usuario.getContraseña().equals(contraseña)) {
                System.out.println("Ingresaste como: " + usuario.getNombre() + " " + usuario.getApellido());
                return true;
            }//1. Comparo el correo guardado en el array con el que se ingresa en la terminal (iniciar Sesion)
        }
        System.out.println("Correo o contreseña incorrectos");
        return false;

    }

    @Override
    public void añadir(Object usuario) {
        if (usuario instanceof Usuario) { //verifica si es una instancia de Usu
            if (!usuarios.contains(usuario)) { //verifica si el usuario esta dentro de la lista de usuarios
                usuarios.add((Usuario) usuario); //en caso de que no esté añade el objeto usuario de tipo Usuario, pero llo casteo
                System.out.println("Usuario añadido al sistema");
                guardarUsuarios();
            } else {
                System.out.println("El usuario ya esta registrado");
            }
        } else {
            System.out.println("Error");
        }

    }

    @Override
    public void eliminar(Object usuario) {
        if (usuario instanceof Usuario && usuarios.contains(usuario)) {
            usuarios.remove(usuario);
            System.out.println("Usuario eliminado del sistema");
            guardarUsuarios(); // Actualiza el archivo de usuarios
        } else {
            System.out.println("El usuario no está registrado en el sistema.");
        }
    }


    @Override
    public void modificar(Object usuario) {
        if (usuario instanceof Usuario usuarioModificado) {
            for (Usuario u : usuarios) {
                if (u.getCorreo().equals(usuarioModificado.getCorreo())) {
                    u.setNombre(usuarioModificado.getNombre());
                    u.setApellido(usuarioModificado.getApellido());
                    u.setContraseña(usuarioModificado.getContraseña());
                    System.out.println("Usuario modificado");
                    guardarUsuarios();
                    return;
                }
            }
            System.out.println("Usuario no encontrado para modificar.");
        }
    }

    public Usuario recuperarUsuario(String correo) { //mediante el correo va a recuperar el usuario
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equals(correo)) { //compara el correo almacenado en la lista con el correo indroducido en la entrada
                System.out.println("-----");
                return usuario;
            }
        }
        System.out.println("------");
        return null;
    }

    public int contarUsuarios() {
        return usuarios.size(); //devuelve el numero total de   usuarios en el sistema
    }

    public int contarEventos() {
        return eventos.size(); //devuelve el numero total de eventos en el sistema
    }

    public static int obtenerTotalUsuarios(SistemaEvento sistema) { //toma una instancia de SistemaEvento como parametro
        return sistema.contarUsuarios(); //llama a contar para obtener el numero total de usuarios
    }

    public static int obtenerTotalEventos(SistemaEvento sistema) {
        return sistema.contarEventos();
    }//Permite contar los usuarios sin tener que llamar a contarUsuarios() directamente desde una instancia,
    //lo cual es útil en ciertos contextos donde se necesita contar desde un contexto estático o independiente de una instancia 

    //Este método devuelve una lista de eventos que están en estado PROGRAMADO
    public List<Evento> obtenerEventosDisponibles() {
        List<Evento> eventosDisponibles = new ArrayList<>(); //creé una lista vacia 
        for (Evento evento : eventos) { //recorre la lista
            if (evento.getEstado() == EstadoEvento.PROGRAMADO) { // si el estado del evento es PROGRAMADO, lo agrega a la lista que cree
                eventosDisponibles.add(evento); //añado a la lista
            }
        }
        return eventosDisponibles; //Utiliza el main para q el usuario vea los eventos disponibles
    }

    //Permite añadir un nuevo evento a la lista de eventos si este no está registrado
    public void añadirEvento(Evento evento) {
        if (!eventos.contains(evento)) { //comprueba si el evento no estan en la lista
            eventos.add(evento); //si no está añade al la lista creada 

            System.out.println("Evento añadido al sistema");
            guardarEventos();
        } else {
            System.out.println("El evento ya esta registrado en el sistema");
        }
    }

    //ermite eliminar un evento existente de la lista de eventos
    public void eliminarEvento(Evento evento) {
        if (eventos.contains(evento)) {
            eventos.remove(evento);
            System.out.println("Evento eliminado del sistema");
            guardarEventos();
        } else {
            System.out.println("El evento no se encuentra en el sistema");
        }
    }

    public void mostrarDatosUsuarios() { //Muestra infromacioin de los usuarios
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Organizador) {
                Organizador organizador = (Organizador) usuario;//casteoo de usuario de tipo obj a tipo Organizador
                System.out.println("-----");
                organizador.mostrarDatos();
            } else if (usuario instanceof Comunidad) {
                Comunidad participante = (Comunidad) usuario;
                System.out.println("-----");
                participante.mostrarDatos();
            }
        }
    }

    public void mostrarInformacionEventos() {
        if (eventos.isEmpty()) { //verifica si la lista esta vacia mediante el metodo isEmpty()
            System.out.println("No hay eventos registrados o cargados");
        } else {
            for (Evento evento : eventos) {
                evento.mostrarInformacionCompleta();
            }
        }
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}
