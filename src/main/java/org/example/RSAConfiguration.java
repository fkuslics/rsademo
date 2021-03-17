package org.example;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RSAConfiguration {

    public static final int KEY_SIZE = 2048;

    public static final BigInteger E_VALUE = BigInteger.valueOf(65537); // F=(n)=(2^(2^n))+1 -> n=4, 4th and largest Fermat prime

}
