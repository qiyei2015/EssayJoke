package com.qiyei.sdk.encrypt;

import java.security.Provider;

/**
 * @author Created by qiyei2015 on 2017/12/16.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class CryptoProvider extends Provider {

    public CryptoProvider() {
        super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
        put("SecureRandom.SHA1PRNG",
                "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
    }

}
