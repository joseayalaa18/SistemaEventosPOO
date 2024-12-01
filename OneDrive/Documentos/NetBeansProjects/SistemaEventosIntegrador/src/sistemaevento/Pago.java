/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaevento;

/**
 *
 * @author jose1
 */

public class Pago {
    private double monto;
    private String metodoPago;
    private boolean estado;

    public Pago(double monto, String metodoPago) {
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = false;
    }
    
    public void pagar(Inscripcion inscripcion) {
    if (!estado) {
        System.out.println("Procesando el pago de: " + monto + ", mediante: " + metodoPago);
        this.estado = true;
        inscripcion.setEstadoInscripcion(EstadoInscripcion.CONFIRMADO);
    } else {
        System.out.println("Pago completado");
      }
    
    }
    
    public void mostrarInformacion() {
        String estadoPago = estado ? "Completado" : "Pendiente";
        StringBuilder sb = new StringBuilder();
    
        sb.append("Monto: ").append(monto)
                .append(", Método de Pago: ").append(metodoPago)
                .append(", Estado: ").append(estadoPago);
        
        System.out.println(sb.toString());
    }

    public boolean isEstado() {
        return estado;
    }
    
    
}

