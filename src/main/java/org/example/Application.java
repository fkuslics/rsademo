package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Application {

    public static void main(String[] args) throws IOException {

        String message = getMessage();

        RSAInitializer rsaInitializer = new RSAInitializer();

        BigInteger p = rsaInitializer.getP(); // P is a large prime number random generated
        BigInteger q = rsaInitializer.getQ(p); // Q is also a large prime number random generated
        // convention is p > q, swap if needed!
        if (p.compareTo(q) < 0) {
            BigInteger tmp = p;
            p = q;
            q = tmp;
        }
        print(p, "P (Generated prime nr.):");
        print(q, "Q (Generated prime nr.):");

        BigInteger n = rsaInitializer.getN(p, q); // N = P*Q
        print(n, "N (P*Q):");

        BigInteger e = rsaInitializer.getE(); // E is also a prime number from configuration
        print(e, "E (Public key component):");

        BigInteger phiN = rsaInitializer.getPhiN(p, q); // ϕ(N)=(p-1)*(q-1)
        print(phiN, "ϕ(N) (Euler phi function, (P-1)*(Q-1)):");

        BigInteger d = rsaInitializer.getD(e, phiN);// d=((e^-1) mod ϕ(N))
        print(d, "D (Private key component ((E^-1) mod ϕ(N))):");

        System.out.println("Now, we have everything to create an encoder and a decoder");

        RSAEncoder encoder = new RSAEncoder(e, n);

        BigInteger encryptedMessage = encoder.encrypt(message); //((message^e) mod n)

        System.out.println("Passing ecrypted message from encoder to decoder...");
        System.out.println();

        RSADecoder decoder = new RSADecoder(d, n);

        String decryptedMessage = decoder.decryptToString(encryptedMessage); //((encryptedMessage^d) mod n)

        System.out.println("Decrypted message: \"" + decryptedMessage + "\"");
    }

    private static String getMessage() throws IOException {
        System.out.println("Enter message:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String message = bufferedReader.readLine();
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
