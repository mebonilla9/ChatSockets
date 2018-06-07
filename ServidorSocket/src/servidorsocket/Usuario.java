/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket;

import java.net.Socket;

/**
 *
 * @author Manuel Ernesto Bonilla Muñoz <mail>mebonilla9@gmail.com</mail>
 */
public class Usuario {

    private String nombre;
    private Socket conexion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Socket getConexion() {
        return conexion;
    }

    public void setConexion(Socket conexion) {
        this.conexion = conexion;
    }

    @Override
    public String toString() {
        return nombre + " " + conexion.getInetAddress();
    }

}
