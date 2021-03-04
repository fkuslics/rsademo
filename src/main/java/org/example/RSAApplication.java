package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;

import static org.example.RSAConfiguration.E_VALUE;
import static org.example.RSAConfiguration.KEY_SIZE;

public class RSAApplication {

    public static void main(String[] args) throws IOException {

        System.out.println("Enter message:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String message = bufferedReader.readLine();
        System.out.println("Message: " + message);
        System.out.println();


        BigInteger p = getP(); // P is a large prime number random generated
        print(p, "P (Generated prime nr.):");

        BigInteger q = getQ(); // Q is also a large prime number random generated
        BigInteger n = getN(p, q); // N = P*Q
        while (n.bitLength() < KEY_SIZE) {
            q = getQ();
            // convention is p > q, swap if needed!
            if (p.compareTo(q) < 0) {
                BigInteger tmp = p;
                p = q;
                q = tmp;
            }
            n = getN(p, q);
        }
        print(q, "Q (Generated prime nr.):");
        print(n, "N (P*Q):");

        BigInteger e = getE(); // E is also a prime number from configuration
        print(e, "E (Public key component):");

        BigInteger phiN = getPhiN(p, q); // ϕ(N)=(p-1)*(q-1)
        print(phiN, "ϕ(N) (Euler phi function, (P-1)*(Q-1)):");

        BigInteger d = e.modInverse(phiN);// d=((e^-1) mod ϕ(N))
        print(d, "D (Private key component ((E^-1) mod ϕ(N))):");

        RSAEncoder encoder = new RSAEncoder(e, n);

        BigInteger encryptedMessage = encoder.encrypt(message); //((message^e) mod n)

        System.out.println("==============================================");
        System.out.println("Sending ecrypted message: ");
        System.out.println(encryptedMessage);
        System.out.println(encryptedMessage.toString(2));
        System.out.println("==============================================");
        System.in.read();

        RSADecoder decoder = new RSADecoder(d, n);

        String decryptedMessage = decoder.decryptToString(encryptedMessage); //((encryptedMessage^d) mod n)

        System.out.println("Decrypted message: \"" + decryptedMessage + "\"");
    }

    private static void print(BigInteger p, String text) throws IOException {
        System.out.println(text);
        System.out.println(p);
        System.out.println(p.toString(2));
        System.in.read();
    }

    private static BigInteger getN(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }

    private static BigInteger getP() {
        int lp = (KEY_SIZE + 1) / 2;
        SecureRandom secureRandom = new SecureRandom();
        return BigInteger.probablePrime(lp, secureRandom);
    }

    private static BigInteger getQ() {
        SecureRandom secureRandom = new SecureRandom();
        int lq = KEY_SIZE - (KEY_SIZE + 1) / 2;
        return BigInteger.probablePrime(lq, secureRandom);
    }

    private static BigInteger getE() {
        return E_VALUE;
    }

    private static BigInteger getPhiN(BigInteger p, BigInteger q) {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

}
