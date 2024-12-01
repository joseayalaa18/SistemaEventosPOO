/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaevento;

/**
 *
 * @author jose1
 */
//Clase abstracta que representa a un usuario general
public abstract class Usuario { //Atributos de la clase

    //atributos accesibles directamente desde las clases hijas, pero no desde fuera de paquete
    protected String nombre;
    protected String apellido;
    protected String contraseña;
    protected String correo;
    protected Rol rol;

    //Constructor de la clase, el cual utiliza un tipo de dato llamado Rol, que es un enum
    public Usuario(String nombre, String apellido, String contraseña, String correo, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.correo = correo;
        this.rol = rol;
    }

    //Mètodo abstracto que implemetaran las clases hijas, las mismas obligadamente deberan de hacer implementar este metodo
    public abstract void mostrarDatos();

    //Mètodos get necesarios
    public String getNombre() { //se utiliza en la clase Evento, en mostrartInfomracionCompleta()
        return nombre;          //y tambien se utuliza en sistemaEvento, en guardarEvento, iniciarSecion y modifcar
    }

    public String getApellido() {
        return apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public Rol getRol() { //se utiliza para verficar el tipo de usuario
        return rol;
    }

    //Mètodos set para modificar al usuario
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
