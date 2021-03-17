package org.example;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigInteger;

@RequiredArgsConstructor
public class RSADecoder {

    private final BigInteger d;
    private final BigInteger n;

    /**
     * Decrypts the encrypted message
     * encryptedMessage^d mod n
     *
     * @param encryptedMessage RSA encrypted message
     * @return the decrypted message
     * @throws IOException
     */
    public String decryptToString(BigInteger encryptedMessage) throws IOException {
        return bigIntToString(decrypt(encryptedMessage));
    }

    private BigInteger decrypt(BigInteger message) throws IOException {
        BigInteger decrypted = message.modPow(d, n);
        System.out.println("Decrypted message from: " + message + " to: ");
        System.out.println(decrypted);
        System.out.println(decrypted.toString(2));
        System.in.read();
        return decrypted;
    }

    private String bigIntToString(BigInteger message) throws IOException {
        String messageString = new String(message.toByteArray());
        System.out.println("Converted message from number: " + message + " to String: \"" + messageString + "\"");
        System.in.read();
        return messageString;
    }
}
