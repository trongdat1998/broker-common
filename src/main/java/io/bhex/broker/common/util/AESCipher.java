package io.bhex.broker.common.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.stream.CryptoInputStream;
import org.apache.commons.crypto.stream.CryptoOutputStream;
import org.apache.commons.crypto.utils.Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * AES加解密工具类
 * 密钥使用256位的Key
 * 目前密钥生成的算法是使用 md5(srcKey+salt) 产生256位的key
 */
@Slf4j
public class AESCipher {

    private static final String BROKER_AES_KEY_SALT = "bluehelix";
    private static final String BROKER_DEFAULT_AES_KEY_IV = "2019080520190805";

    private static final String TRANSFORM = "AES/CBC/PKCS5Padding";
    private static final int BUFFER_SIZE = 1024;
    private static Properties properties;

    static {
        properties = new Properties();
        properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CryptoCipherFactory.CipherProvider.JCE.getClassName());
    }

    public static String encryptString(String inputString, String keyString) {
        return encryptString(inputString, keyString, null);
    }

    public static String encryptString(String inputString, String keyString, byte[] ivBytes) {
        final SecretKeySpec key = getFinalKey(keyString);
        final IvParameterSpec iv = getFinalIv(ivBytes);

        try (CryptoCipher encipher = Utils.getCipherInstance(TRANSFORM, properties)){
            ByteBuffer inBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
            ByteBuffer outBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

            inBuffer.put(getUTF8Bytes(inputString));
            inBuffer.flip();

            encipher.init(Cipher.ENCRYPT_MODE, key, iv);
            int updateBytes = encipher.update(inBuffer, outBuffer);
            int finalBytes = encipher.doFinal(inBuffer, outBuffer);

            outBuffer.flip();
            byte[] encoded = new byte[updateBytes + finalBytes];
            outBuffer.duplicate().get(encoded);
            return Hex.encodeHexString(encoded);
        } catch (Exception e) {
            log.error("AES encrypt error", e);
            throw new BrokerException(BrokerErrorCode.AES_CIPHER_ERROR);
        }
    }

    public static void encryptStream(InputStream input, OutputStream output, String keyString) {
        encryptStream(input, output, keyString, null);
    }

    public static void encryptStream(InputStream input, OutputStream output, String keyString, byte[] ivBytes) {
        final SecretKeySpec key = getFinalKey(keyString);
        final IvParameterSpec iv = getFinalIv(ivBytes);

        try (CryptoOutputStream cos = new CryptoOutputStream(TRANSFORM, properties, output, key, iv)) {
            int n;
            byte[] buffer = new byte[4096];
            while (-1 != (n = input.read(buffer))) {
                cos.write(buffer, 0, n);
            }
            cos.flush();
        } catch (IOException e) {
            log.error("AES stream encrypt error", e);
            throw new BrokerException(BrokerErrorCode.AES_CIPHER_ERROR);
        }
    }

    public static void decryptStream(InputStream input, OutputStream output, String keyString) {
        decryptStream(input, output, keyString, null);
    }

    public static void decryptStream(InputStream input, OutputStream output, String keyString, byte[] ivBytes) {
        final SecretKeySpec key = getFinalKey(keyString);
        final IvParameterSpec iv = getFinalIv(ivBytes);

        try (CryptoInputStream cis = new CryptoInputStream(TRANSFORM, properties, input, key, iv)) {
            byte[] buffer = new byte[4096];
            int n;
            while (-1 != (n = cis.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }  catch (IOException e) {
            log.error("AES stream decrypt error", e);
            throw new BrokerException(BrokerErrorCode.AES_CIPHER_ERROR);
        }
    }

    public static String decryptString(String encryptedString, String keyString) {
        return decryptString(encryptedString, keyString, null);
    }

    public static String decryptString(String encryptedString, String keyString, byte[] ivBytes) {
        final SecretKeySpec key = getFinalKey(keyString);
        final IvParameterSpec iv = getFinalIv(ivBytes);

        try (CryptoCipher decipher = Utils.getCipherInstance(TRANSFORM, properties)) {
            decipher.init(Cipher.DECRYPT_MODE, key, iv);

            ByteBuffer encodedBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
            ByteBuffer decodedBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

            encodedBuffer.put(Hex.decodeHex(encryptedString.toCharArray()));
            encodedBuffer.flip();

            decipher.update(encodedBuffer, decodedBuffer);
            decipher.doFinal(encodedBuffer, decodedBuffer);
            decodedBuffer.flip();

            return asString(decodedBuffer);
        } catch (Exception e) {
            log.error("AES encrypt error", e);
            throw new BrokerException(BrokerErrorCode.AES_CIPHER_ERROR);
        }
    }

    private static byte[] getUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

    private static String asString(ByteBuffer buffer) {
        final ByteBuffer copy = buffer.duplicate();
        final byte[] bytes = new byte[copy.remaining()];
        copy.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static SecretKeySpec getFinalKey(String keyString) {
        if (Strings.isNullOrEmpty(keyString)) {
            throw new NullPointerException("getFinalKey keyString is null");
        }
        String finalKeyString = DigestUtils.md5Hex(String.format("%s%s", keyString, BROKER_AES_KEY_SALT));
        return new SecretKeySpec(getUTF8Bytes(finalKeyString), "AES");
    }

    private static IvParameterSpec getFinalIv(byte[] ivBytes) {
        byte[] iv = ivBytes;
        if (ivBytes == null) {
            iv = getUTF8Bytes(BROKER_DEFAULT_AES_KEY_IV);
        }
        return new IvParameterSpec(iv);
    }
}