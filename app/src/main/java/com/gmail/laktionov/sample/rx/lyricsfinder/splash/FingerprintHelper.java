package com.gmail.laktionov.sample.rx.lyricsfinder.splash;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

class FingerprintHelper {

    private static KeyStore sKeyStore;
    private static KeyGenerator sKeyGenerator;
    private static Cipher sCipher;
    private static FingerprintManagerCompat.CryptoObject sCryptoObject;
    private static FingerprintManagerCompat sFingerprintManager;

    public enum SensorState {
        NOT_SUPPORTED,
        NOT_BLOCKED,
        NO_FINGERPRINT,
        READY,
    }

    static final String KEYSTORE = "AndroidKeyStore";
    static final String KEY_ALIAS = "KeyAlias";

    static SensorState checkSensorState(@NonNull Context context) {
        if (isFingerprintExist(context)) {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            sFingerprintManager = FingerprintManagerCompat.from(context);
            if (keyguardManager != null && !keyguardManager.isKeyguardSecure()) {
                return SensorState.NOT_BLOCKED;
            }
            if (!sFingerprintManager.hasEnrolledFingerprints()) {
                return SensorState.NO_FINGERPRINT;
            }
            return SensorState.READY;
        } else {
            return SensorState.NOT_SUPPORTED;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    static void generateKey() throws RxSplashActivity.FingerprintException {
        try {
            sKeyStore = KeyStore.getInstance(KEYSTORE);
            sKeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            sKeyStore.load(null);

            KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec
                    .Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build();

            sKeyGenerator.init(keyGenParameterSpec);
            sKeyGenerator.generateKey();
        } catch (KeyStoreException | NoSuchAlgorithmException |
                NoSuchProviderException | IOException |
                CertificateException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    static boolean initCipher() {
        try {
            sCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" +
                    KeyProperties.BLOCK_MODE_CBC + "/" +
                    KeyProperties.ENCRYPTION_PADDING_PKCS7);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get cipher", e);
        }

        try {
            sKeyStore.load(null);
            SecretKey key = (SecretKey) sKeyStore.getKey(KEY_ALIAS, null);
            sCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyStoreException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    static void startAuth(@NonNull Context context) {
        sCryptoObject = new FingerprintManagerCompat.CryptoObject(sCipher);
        FingerprintHandler handler = new FingerprintHandler(context);
        handler.setListener((FingerprintHandler.AuthListener) context);
        handler.startAuth(sCryptoObject);
    }

    private static boolean isFingerprintExist(@NonNull Context context) {
        return FingerprintManagerCompat.from(context).isHardwareDetected();
    }
}
