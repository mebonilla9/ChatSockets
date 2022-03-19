/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientesocket;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author dev_manuel
 */
public class RsaUtil {

  private final PrivateKey privateKey;
  private final PublicKey publicKey;

  public RsaUtil() throws NoSuchAlgorithmException {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    SecureRandom random = new SecureRandom("Secure-key".getBytes());
    generator.initialize(1024, random);
    KeyPair pair = generator.generateKeyPair();
    privateKey = pair.getPrivate();
    publicKey = pair.getPublic();
  }

  public String encrypt(String message) throws NoSuchAlgorithmException,
          NoSuchPaddingException, InvalidKeyException,
          IllegalBlockSizeException, BadPaddingException {
    byte[] messageToBytes = message.getBytes();
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] encryptedBytes = cipher.doFinal(messageToBytes);
    return encode(encryptedBytes);
  }

  private String encode(byte[] data) {
    return Base64.getEncoder().encodeToString(data);
  }

  public String decrypt(String encryptedMessage) throws NoSuchAlgorithmException,
          NoSuchPaddingException, InvalidKeyException,
          IllegalBlockSizeException, BadPaddingException,
          UnsupportedEncodingException {
    byte[] encryptedBytes = decode(encryptedMessage);
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
    return new String(decryptedMessage, "UTF-8");
  }

  private byte[] decode(String data) {
    return Base64.getDecoder().decode(data);
  }

}
