package com.qiyei.sdk.encrypt;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Created by qiyei2015 on 2017/12/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class AES {

    private static final String TAG = AES.class.getSimpleName();


    /**
     * 生成SecretKey
     * @param rules 生成规则
     * @return
     */
    private static SecretKeySpec generateSecretKey(String rules){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据规则初始化密钥生成器  生成一个128位的随机源,根据传入的字节数组
            // 注意生成随机数的方式，否则在Android系统中可能会报错
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", new CryptoProvider());
            secureRandom.setSeed(rules.getBytes("utf-8"));
            keygen.init(128,secureRandom);
            //3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw = originalKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密
     * @param rules 规则
     * @param plainText 明文
     * @return 加密后的密文
     */
    public static byte[] encrypt(String rules,String plainText){
        if (TextUtils.isEmpty(rules)){
            return null;
        }
        if (plainText == null || plainText.length() == 0){
            return null;
        }
        try {
            SecretKeySpec key = generateSecretKey(rules);
            if (key != null){
                //1.根据指定算法AES自成密码器
                Cipher cipher = Cipher.getInstance("AES");
                //2.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
                cipher.init(Cipher.ENCRYPT_MODE, key);
                //3. 加密
                byte [] byteEncode = cipher.doFinal(plainText.getBytes("utf-8"));
                return byteEncode;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     * @param rules 规则
     * @param encryptText 密文
     * @return 解密后的明文
     */
    public static byte[] decrypt(String rules,byte[] encryptText){
        if (TextUtils.isEmpty(rules)){
            return null;
        }
        if (encryptText == null || encryptText.length == 0){
            return null;
        }

        try {
            SecretKeySpec key = generateSecretKey(rules);
            if (key != null){
                //6.根据指定算法AES自成密码器
                Cipher cipher = Cipher.getInstance("AES");
                //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
                cipher.init(Cipher.DECRYPT_MODE, key);
                //8. 解密
                byte [] byteDecode = cipher.doFinal(encryptText);
                return byteDecode;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将二进制转换成16进制
     * @param buffer 二进制数组
     * @return 十六进制字符串
     */
    public static String parseByte2HexStr(byte buffer[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buffer.length; i++) {
            String hex = Integer.toHexString(buffer[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * @param hexStr 十六进制字符串
     * @return 二进制数组
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1){
            return null;
        }
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
