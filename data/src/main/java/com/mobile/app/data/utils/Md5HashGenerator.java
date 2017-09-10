package com.mobile.app.data.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class Md5HashGenerator {
    @Nullable
    public static String generateHash(final long timestamp,
                                      @NonNull final String privateKey,
                                      @NonNull final String publicKey) {
        try {
            final String textToHash = timestamp + privateKey.concat(publicKey);
            return md5Hash(textToHash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String md5Hash(@NonNull final String string) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(string.getBytes());

        final byte messageDigest[] = digest.digest();
        final BigInteger bigInteger = new BigInteger(1, messageDigest);
        String hashText = bigInteger.toString(16);

        while (hashText.length() < 32) {
            hashText = "0".concat(hashText);
        }
        return hashText;
    }
}
