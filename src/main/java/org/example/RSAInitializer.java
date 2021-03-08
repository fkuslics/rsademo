package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.example.RSAConfiguration.E_VALUE;
import static org.example.RSAConfiguration.KEY_SIZE;

public class RSAInitializer {

    private static final int LP = (KEY_SIZE + 1) / 2;
    private static final int LQ = KEY_SIZE - LP;

    private final SecureRandom secureRandom = new SecureRandom();

    public BigInteger getP() {
        return BigInteger.probablePrime(LP, secureRandom);
    }

    public BigInteger getQ(BigInteger p) {
        BigInteger q = BigInteger.probablePrime(LQ, secureRandom);
        BigInteger n = p.multiply(q);
        while (n.bitLength() < KEY_SIZE) {
            q = BigInteger.probablePrime(LQ, secureRandom);
            n = p.multiply(q);
        }
        return q;
    }

    public BigInteger getN(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }

    public BigInteger getPhiN(BigInteger p, BigInteger q) {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    public BigInteger getE() {
        return E_VALUE;
    }

    public BigInteger getD(BigInteger e, BigInteger phiN) {
        return e.modInverse(phiN);
    }
}
