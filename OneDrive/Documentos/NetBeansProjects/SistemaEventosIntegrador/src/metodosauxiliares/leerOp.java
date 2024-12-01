/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metodosauxiliares;
import java.time.LocalDateTime; //se utiliza para representar y manipular valoresde fecha y hora
import java.time.format.DateTimeFormatter; // Proporciona una manera de especificar el formato de una fecha y hora
import java.time.format.DateTimeParseException; //Esta excepción se lanza cuando la cadena de fecha ingresada por el usuario no coincide con el formato esperado
import java.util.InputMismatchException; //Esta excepción se lanza cuando el tipo de entrada del usuario no coincide con el tipo esperado
import java.util.Scanner;//Permite la entrada de datos desde la consola

public class leerOp {

    //Tibe como proposito leer un numero entero ingresado, y garantizar que la entrada sea valida
    public static int leerOpcion(Scanner scanner) { //static: permite que sea accecible sin necesidad de craer una instancia de clase, 
        //como parametro tiene scanner que se usa para leer entrada desde la consola
        int opcion = -1; //declaro e inicialiizo opcion con un valor cua-quiera (-1) que es un valor temporal que se sobreescribirá 
        while (true) { //bucle infinito que se romperá cuandocuando se reciba una entrada valida
            try { //bloque try captura las posibles excepciones generadas por la entrada del usuario
                opcion = scanner.nextInt();  //Intenta leer un número entero ingresado por el usuario usando el meetodo nextInt() de Scanner.
                //si el usuario ingresa un num valido, este valor se le asigna a opcion, de lo contrario lanzara una excepcion, y el flujo pasara al bloque catch
                scanner.nextLine(); //evita cualquier caracter adicional
                break; //sale del bucle si se ha ingresado una up correcta
            } catch (InputMismatchException e) {
                System.out.println("Entrada incorrecta, ingrese un numero valido");
                scanner.nextLine(); 
            }
        }
        return opcion;
    }
    //este metodo asegura que el usuario ingrese un número entero dentro de un rango específico
    public static int leerOpcionEnRango(Scanner scanner, int min, int max) {
        int opcion; //declaro una variable de tipo int para almacenar la op ingresada por el usu
        while (true) {
            opcion = leerOpcion(scanner); //llamo al metodo leerOpcion, que lee un numero entero del usuario y se asegura de que sea valido.
            if (opcion >= min && opcion <= max) { //Verifica si el valor de opcion está dentro del rango especificado, es decir, 
                //si es mayor o igual a min y menor o igual a max
                break;//si op dentro del rango, sale del bucle
            } else {
                System.out.println("Opcion fuera de rango. Ingrese un número entre: " + min + " y " + max);
            }
        }
       
        return opcion;
    }
//tiene como objetivo leer un número entero ingresado por el usuario y asegurarse de que la entrada sea válida antes de devolver el valor
    public static int leerEntero(Scanner scanner) {
        int numero = -1;
        while (true) {
            try {
                numero = scanner.nextInt(); ////Intenta leer un número entero usando scanner nextInt y asigna este valor a numero
                scanner.nextLine(); 
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada incorrecta, ingrese un numero entero");
                scanner.nextLine();
            }
        }
        return numero;
    }

    public static double leerDouble(Scanner scanner) {
        double numero = -1;
        while (true) {
            try {
                numero = scanner.nextDouble(); 
                scanner.nextLine(); 
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada incorrecta, ingrese un num decimal");
                scanner.nextLine(); 
            }
        }
        return numero;
    }

    //lee una fecha y hora ingresada por el ususario, valida utilizandod un formato especifico y devuelve como obj de tipo LocalDateTime
    public static LocalDateTime leerFecha(Scanner scanner, DateTimeFormatter formato) {
        LocalDateTime fecha = null; //lo inicializo como null, ya que luego se va a ir actualizando este valor
        while (true) {
            try {
                String input = scanner.nextLine().trim(); //Lee la netrada del usuario como cadena usando Line, y elimina los espacio en blanco
                fecha = LocalDateTime.parse(input, formato); //convierte la cadena inpput en un objeto LocalDateTime utilizando el metodo parse de la clase LDT
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecta; tiene que ser en formato: anio, mes, dia y Hora");
            }
        }
        return fecha;
    }
    
    public static String leerPalabra(Scanner scanner) {
        String palabra;
        while (true) {
            palabra = scanner.nextLine().trim();
            if (palabra.matches("[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+")) { //Aquí se utiliza el método matches de la clase String, que verifica si la cadena palabra coincide con una expresión regular (regex
                //este permite toda clase de letras (+)significa que debe haber al menos un carácter que cumpla con la condición de la expresión regular
                break;
            } else {
                System.out.println("Entrada incorrecta, ingresa letras nomas");
            }
        }
        return palabra;
    }
    
}
