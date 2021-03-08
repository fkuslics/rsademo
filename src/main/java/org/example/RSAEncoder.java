package org.example;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigInteger;

@RequiredArgsConstructor
public class RSAEncoder {

    private final BigInteger e;
    private final BigInteger n;

    public BigInteger encrypt(String message) throws IOException {
        BigInteger bigIntegerMessage = new BigInteger(message.getBytes());
        System.out.println("Converted message from String : \"" + message + "\" to:");
        System.out.println(bigIntegerMessage);
        System.out.println(bigIntegerMessage.toString(2));
        System.in.read();
        return encrypt(bigIntegerMessage);
    }

    public BigInteger encrypt(BigInteger message) throws IOException {
        BigInteger encrypted = message.modPow(e, n); //((message^e) mod n)
        System.out.println("Encrypted message from: " + message + " to:");
        System.out.println(encrypted);
        System.out.println(encrypted.toString(2));
        System.in.read();
        return encrypted;
    }
}
