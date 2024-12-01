/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemaevento;

/**
 *
 * @author jose1
 */

//Enumeraciòn del estado de inscripciòn, para que pueda estar inscripto un obj de tipo
//comunidad a una evento tiene que estar en esatdo CONFIIRMADO.
public enum EstadoInscripcion {
    PENDIENTE,
    CONFIRMADO,
    RECHAZADO;
}
