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

    /**
     * Modulo, common component of the keys.
     * N = P*Q
     *
     * @param p
     * @param q
     * @return (p - 1)*(q - 1)
     */
    public BigInteger getN(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }

    /**
     * Euler phi function, (P-1)*(Q-1)
     *
     * @param p
     * @param q
     * @return (p - 1)*(q - 1)
     */
    public BigInteger getPhiN(BigInteger p, BigInteger q) {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    /**
     * E is a prime number from configuration.
     * Need to be relative prime to the message.
     * No check is done for this, as we assume that an absolute prime number is set in the configuration.
     *
     * @return e number from configuration
     */
    public BigInteger getE() {
        return E_VALUE;
    }

    /**
     * multiplicative inverse of e mod ϕ(N)
     * <p>
     * find d such that e*d ≡ 1(mod phiN) -> e*d mod ϕ(N) = 1
     * <p>
     * (Extended Euclidean algorithm)
     *      ax+by=gcd(a,b) //gcd(e,ϕ(N))=1
     *      -> ex+φ(n)y=1 -> ex≡1(modφ(n)) // x=d
     *      -> ed≡1(modφ(n))
     *
     * @param e
     * @param phiN
     * @return d
     */
    public BigInteger getD(BigInteger e, BigInteger phiN) {
        return e.modInverse(phiN);
    }
}
