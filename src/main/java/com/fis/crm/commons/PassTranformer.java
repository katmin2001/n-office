package com.fis.crm.commons;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PassTranformer {

    public PassTranformer() {
    }

    private static byte[] _encrypt(byte arrByte[])
        throws Exception {
        return encoder.doFinal(arrByte);
    }

    private static byte[] _decrypt(byte arrByte[])
        throws Exception {
        return decoder.doFinal(arrByte);
    }

    public static String encrypt(String str)
        throws Exception {
        return toHexa(_encrypt(str.getBytes()));
    }

    public static String decrypt(String str)
        throws Exception {
        return new String(_decrypt(ByteUtils.stringToBytes(str)));
    }

    public static String toHexa(byte b[]) {
        StringBuilder s = new StringBuilder(2 * b.length);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            s.append((char) Hexhars[v >> 4]);
            s.append((char) Hexhars[v & 0xf]);
        }

        return s.toString();
    }

    public static void main(String args[])
        throws Exception {
        for (int i = 0; i < 1000; i++) {
            String encryptStr = encrypt((new StringBuilder()).append("xxx:").append(i).toString());
            System.out.println(encryptStr);
            System.out.println(decrypt(encryptStr));
        }

    }

    private static byte key[] = {
        -95, -29, -62, 25, 25, -83, -18, -85
    };
    private static String algorithm;
    private static SecretKeySpec keySpec;
    private static Cipher encoder;
    private static Cipher decoder;
    protected static final byte Hexhars[] = {
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
        97, 98, 99, 100, 101, 102
    };

    static {
        algorithm = "DES";
        keySpec = new SecretKeySpec(key, algorithm);
        try {
            encoder = Cipher.getInstance(algorithm);
            encoder.init(1, keySpec);
            decoder = Cipher.getInstance(algorithm);
            decoder.init(2, keySpec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

