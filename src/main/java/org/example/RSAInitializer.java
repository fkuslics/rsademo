package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.function.Supplier;

import static org.example.RSAConfiguration.E_VALUE;
import static org.example.RSAConfiguration.KEY_SIZE;

public class RSAInitializer {

    private static final int LP = (KEY_SIZE + 1) / 2;
    private static final int LQ = KEY_SIZE - LP;

    private final SecureRandom secureRandom = new SecureRandom();

    public BigInteger getP() {
        return executeWithTimer(() -> BigInteger.probablePrime(LP, secureRandom), "getP");
    }

    public BigInteger getQ(BigInteger p) {
        return executeWithTimer(() -> {
            BigInteger q = BigInteger.probablePrime(LQ, secureRandom);
            BigInteger n = p.multiply(q);
            while (n.bitLength() < KEY_SIZE) {
                q = BigInteger.probablePrime(LQ, secureRandom);
                n = p.multiply(q);
            }
            return q;
        }, "getQ");
    }

    /**
     * Modulo, common component of the keys.
     * N = P*Q
     *
     * @param p
     * @param q
     * @return (p - 1)*(q - 1)
     */
    public BigInteger getN(BigInteger p, BigInteger q) {
        return executeWithTimer(() -> p.multiply(q), "getN");
    }

    /**
     * Euler phi function, (P-1)*(Q-1)
     *
     * @param p
     * @param q
     * @return (p - 1)*(q - 1)
     */
    public BigInteger getPhiN(BigInteger p, BigInteger q) {
        return executeWithTimer(() -> p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)), "getPhiN");
    }

    /**
     * E is a prime number from configuration.
     * Need to be relative prime to the message.
     * No check is done for this, as we assume that an absolute prime number is set in the configuration.
     *
     * @return e number from configuration
     */
    public BigInteger getE() {
        return executeWithTimer(() -> E_VALUE, "getE");
    }

    /**
     * d=((e^-1) mod ϕ(N))
     * multiplicative inverse of e mod ϕ(N)
     * (Extended Euclidean algorithm)
     *
     * @param e
     * @param phiN
     * @return d such that e*d ≡ 1(mod phiN).
     */
    public BigInteger getD(BigInteger e, BigInteger phiN) {
        return executeWithTimer(() -> e.modInverse(phiN), "getD");
    }

    private <T> T executeWithTimer(Supplier<T> supplier, String methodName) {
        long start = System.currentTimeMillis();
        T t = supplier.get();
//        System.out.println(methodName + " executed in " + (System.currentTimeMillis() - start) + " ms");
        return t;
    }
}
