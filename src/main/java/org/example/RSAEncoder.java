package org.example;

import java.io.IOException;
import java.math.BigInteger;

public class RSAEncoder {

    private final BigInteger e;
    private final BigInteger n;

    public RSAEncoder(BigInteger e, BigInteger n) {
        this.e = e;
        this.n = n;
    }

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

//
//    public byte[] encrypt(String message) {
//        byte[] bytes = message.getBytes();
//        System.out.println("Converted message from String : \"" + message + "\" to byte array: " + Arrays.toString(bytes));
//        return encrypt(bytes);
//
//    }
//
//    public byte[] encrypt(byte[] message) {
//        byte[] encrypted = new BigInteger(message).modPow(e, n).toByteArray();
//        System.out.println("Encrypted message from: " + Arrays.toString(message) + " to: " + Arrays.toString(encrypted));
//        return encrypted;
//    }
}
