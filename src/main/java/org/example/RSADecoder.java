package org.example;

import java.io.IOException;
import java.math.BigInteger;

public class RSADecoder {

    private final BigInteger d;
    private final BigInteger n;

    public RSADecoder(BigInteger d, BigInteger n) {
        this.d = d;
        this.n = n;
    }

    public BigInteger decrypt(BigInteger message) throws IOException {
        BigInteger decrypted = message.modPow(d, n);
        System.out.println("Decrypted message from: " + message + " to: ");
        System.out.println(decrypted);
        System.out.println(decrypted.toString(2));
        System.in.read();
        return decrypted;
    }

    public String decryptToString(BigInteger message) throws IOException {
        return bigIntToString(decrypt(message));
    }

    private String bigIntToString(BigInteger message) throws IOException {
        String messageString = new String(message.toByteArray());
        System.out.println("Converted message from byte array: " + message + " to String: \"" + messageString + "\"");
        System.in.read();
        return messageString;
    }
}
