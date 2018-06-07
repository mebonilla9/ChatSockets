/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manuel Ernesto Bonilla Muñoz <mail>mebonilla9@gmail.com</mail>
 */
public class Servicio extends Thread {

    public static final List<Usuario> LISTA_CLIENTES = new ArrayList<>();
    public static boolean continuar = true;
    private final int puerto;
    private final Configuracion objConfiguracion;

    public Servicio(int puerto, Configuracion objConfiguracion) {
        this.puerto = puerto;
        this.objConfiguracion = objConfiguracion;
    }

    @Override
    public void run() {
        ServerSocket servidor;
        try {
            servidor = new ServerSocket(puerto);
        } catch (IOException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        while (continuar) {
            System.out.println("Esperando cliente...");
            try {
                Socket conexion = servidor.accept();
                BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String nombre = lector.readLine();
                Usuario objUsuario = new Usuario();
                objUsuario.setConexion(conexion);
                objUsuario.setNombre(nombre);
                LISTA_CLIENTES.add(objUsuario);
                new HiloUsuario(objUsuario).start();
                objConfiguracion.actualizar();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

}
