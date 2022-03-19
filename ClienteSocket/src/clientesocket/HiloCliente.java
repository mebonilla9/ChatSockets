/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientesocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.regex.Pattern;
import javax.swing.JTextArea;

/**
 *
 * @author Manuel Ernesto Bonilla Muñoz <mail>mebonilla9@gmail.com</mail>
 */
public class HiloCliente extends Thread {

  private final JTextArea txtConversacion;
  private final Socket conexion;
  private final RsaUtil rsaUtil;
  private static boolean continuar = true;

  public HiloCliente(JTextArea txtConversacion, Socket conexion, RsaUtil rsaUtil) {
    this.txtConversacion = txtConversacion;
    this.conexion = conexion;
    this.rsaUtil = rsaUtil;
  }

  @Override
  public void run() {
    try {
      BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
      while (continuar) {
        String linea = lector.readLine();
        if (linea != null || linea.endsWith("null")) {
          String[] message = linea.split(Pattern.quote(": "));
          String decryptMessage = rsaUtil.decrypt(message[1]);
          txtConversacion.append(message[0] + ": " + decryptMessage + "\n");
        }
      }
      lector.close();
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

}
