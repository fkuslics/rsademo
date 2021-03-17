package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Application {

    public static void main(String[] args) throws IOException {

        String message = getMessage();

        RSAInitializer rsaInitializer = new RSAInitializer();

        BigInteger p = rsaInitializer.getP();
        BigInteger q = rsaInitializer.getQ(p);

        // convention is p > q, swap if needed!
        if (p.compareTo(q) < 0) {
            BigInteger tmp = p;
            p = q;
            q = tmp;
        }

        print(p, "P:");
        print(q, "Q:");

        BigInteger n = rsaInitializer.getN(p, q); // N = P*Q
        print(n, "N (P*Q):");

        BigInteger phiN = rsaInitializer.getPhiN(p, q);
        print(phiN, "ϕ(N):");

        BigInteger e = rsaInitializer.getE();
        print(e, "E:");

        BigInteger d = rsaInitializer.getD(e, phiN);
        print(d, "D:");

        System.out.println("Now, we have everything to create an encoder and a decoder");
        System.in.read();

        RSAEncoder encoder = new RSAEncoder(e, n);

        BigInteger encryptedMessage = encoder.encrypt(message);

        System.out.println("Passing ecrypted message from encoder to decoder...");
        System.in.read();

        RSADecoder decoder = new RSADecoder(d, n);

        String decryptedMessage = decoder.decryptToString(encryptedMessage);

        System.out.println("Decrypted message: \"" + decryptedMessage + "\"");
    }

    private static String getMessage() throws IOException {
        System.out.println("Enter message:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String message;
        do {
             message = bufferedReader.readLine();
        } while (message.trim().isEmpty());
        System.out.println("Message: " + message);
        System.out.println();
        return message;
    }

    private static void print(BigInteger p, String text) throws IOException {
        System.out.println(text);
        System.out.println(p);
        System.out.println(p.toString(2));
        System.in.read();
    }
}
