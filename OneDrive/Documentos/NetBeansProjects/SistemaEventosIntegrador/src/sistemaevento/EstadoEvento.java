/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaevento;

/**
 *
 * @author jose1
 */

//Enumeraciòn del estado de un evento, para que el evento pueda desarrollarse tiene que estar
//en un estado de PROGRAMADO, y se cancela cuando el evento es eliminado
public enum EstadoEvento {
    PROGRAMADO,
    DESARROLLANDOSE,
    FINALIZADO,
    CANCELADO;
    
}
