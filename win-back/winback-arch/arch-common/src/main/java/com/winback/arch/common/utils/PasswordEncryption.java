package com.winback.arch.common.utils;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.exception.BaseException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordEncryption {

    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * 盐的长度
     */
    private static final int SALT_BYTE_SIZE = 20 / 2;

    /**
     * 生成密文的长度
     */
    private static final int HASH_BIT_SIZE = 64 * 4;

    /**
     * 迭代次数
     */
    private static final int PBKDF2_ITERATIONS = 50000;

    /**
     * 对输入的密码进行验证
     *
     * @param attemptedPassword 待验证的密码
     * @param encryptedPassword 密文
     * @return 是否验证成功
     * @Throws NoSuchAlgorithmException
     * @Throws InvalidKeySpecException
     */
    public static boolean authenticate(String attemptedPassword, String encryptedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 用相同的盐值对用户输入的密码进行加密
        String salt = decry(encryptedPassword);
        if (StringUtils.isBlank(salt)) {
            throw new BaseException(ApiCallResult.SIGNERROR.getCode(), ApiCallResult.SIGNERROR.getDesc());
        }
        String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
        return encryptedAttemptedPassword.equals(encryptedPassword);
    }

    private static Pattern compile = Pattern.compile("\\$(.*?)\\$");

    public static String decry(String encryptedPassword) {
        Matcher matcher = compile.matcher(encryptedPassword);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    /**
     * 生成密文
     *
     * @param password 明文密码
     * @return String
     * @Throws NoSuchAlgorithmException
     * @Throws InvalidKeySpecException
     */
    public static String getEncryptedPassword(String password) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        char[] chars = password.toCharArray();
        String salt = generateSalt();
        PBEKeySpec spec = new PBEKeySpec(chars, salt.getBytes(), PBKDF2_ITERATIONS, HASH_BIT_SIZE);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return "pbkdf2:sha256:" + PBKDF2_ITERATIONS + "$" + salt + "$" + toHex(hash);
    }

    /**
     * 加密
     *
     * @param salt 盐值
     */
    public static String getEncryptedPassword(String password, String salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        char[] chars = password.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(chars, salt.getBytes(), PBKDF2_ITERATIONS, HASH_BIT_SIZE);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return "pbkdf2:sha256:" + PBKDF2_ITERATIONS + "$" + salt + "$" + toHex(hash);
    }

    /**
     * 通过提供加密的强随机数生成器 生成盐
     *
     * @return string
     */
    public static String generateSalt() {
        return RandomStringUtils.randomAlphanumeric(SALT_BYTE_SIZE);
    }

    /**
     * 十六进制字符串转二进制字符串
     *
     * @return the hex string decoded into a byte array      
     * @Param hex        
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    /**
     * 二进制字符串转十六进制字符串
     *
     * @return a length*2 character string encoding the byte array      
     * @Param array
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
       // String decry = decry("pbkdf2:sha256:50000$VhatHOSqjI$db9cc2a3b84d12788f584d3aefdc9e7dbcb86ee6db259959d67905bb405aa9af");
        boolean authenticate = authenticate("ruqing1234567", "pbkdf2:sha256:50000$pdJMMRE8pS$9136287bb90e8c8b58fcd3340fe0d5062353ef7770c173b7404d1a0a16895d6d");

        System.out.println("authenticate = " + authenticate);
//        String password = "ruqing1234567";
//        String encPassword = getEncryptedPassword(password);
//        System.out.println(encPassword);
    }
}
