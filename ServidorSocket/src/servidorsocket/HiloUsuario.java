/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 *
 * @author Manuel Ernesto Bonilla Muñoz <mail>mebonilla9@gmail.com</mail>
 */
public class HiloUsuario extends Thread {

  private final Usuario objUsuario;

  public HiloUsuario(Usuario objUsuario) {
    this.objUsuario = objUsuario;
  }

  @Override
  public void run() {
    try {
      BufferedReader lector = new BufferedReader(new InputStreamReader(objUsuario.getConexion().getInputStream()));
      while (Servicio.continuar) {
        String linea = lector.readLine();
        if (linea == null) {
          Servicio.continuar = false;
        }
        for (Usuario usuario : Servicio.LISTA_CLIENTES) {
          PrintStream out = new PrintStream(usuario.getConexion().getOutputStream());
          String mensaje = objUsuario.getNombre() + ": " + linea;
          System.out.println(mensaje);
          out.println(mensaje);
          out.flush();
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace(System.err);
    }
  }
}
