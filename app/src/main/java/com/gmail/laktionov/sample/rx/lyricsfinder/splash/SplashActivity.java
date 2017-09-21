package com.gmail.laktionov.sample.rx.lyricsfinder.splash;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.laktionov.sample.rx.lyricsfinder.R;
import com.gmail.laktionov.sample.rx.lyricsfinder.search.MainActivity;

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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    public static final int MARSHMALLOW = Build.VERSION_CODES.M;
    public static final String KEY_NAME = "androidHive";

    public static final int ERROR_DEFICE_NOT_SUPPORT = 701;
    public static final int ERROR_NO_FINGERPRINT_CONFIGURE = 702;
    public static final int ERROR_LOCKSCREEN_DISABLED = 703;

    public static final int PERMISSION_CODE = 10001;

    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private Cipher mCipher;
    private FingerprintManager.CryptoObject mCryptoObject;
    private FingerprintManager mFingerprintManager;

    private TextView mFingerLabelMessage;
    private TextView mFingerTextMessage;
    private ImageView mFingerImage;

    private int mErrorCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();

        Observable<String> fingerPrintObservable = createFingerPrintObservable();
        fingerPrintObservable
                .doOnNext(s -> getFingerPrint())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fingerPrintApiObserver);
    }

    private void initViews() {
        mFingerLabelMessage = findViewById(R.id.auth_label);
        mFingerTextMessage = findViewById(R.id.auth_message);
        mFingerImage = findViewById(R.id.fingerprint_image);
    }

    private Observable<String> createFingerPrintObservable() {
        return Observable.create((ObservableEmitter<String> emitter) -> {
            if (isFingerPrintExist() && isNoErrorDetected()) {
                if (isPermissionGranted()) {
                    emitter.onNext(null);
                } else {
                    requestPermission();
                }
            } else {
                emitter.onError(new Throwable());
            }
        });
    }

    private Observer<String> fingerPrintApiObserver = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            showViews();
            startAuth();
        }

        @Override
        public void onError(Throwable e) {
            switch (mErrorCode) {
                case ERROR_DEFICE_NOT_SUPPORT:
                    break;
                case ERROR_NO_FINGERPRINT_CONFIGURE:
                    break;
                case ERROR_LOCKSCREEN_DISABLED:
                    break;
            }
            startMainActivity();
        }

        @Override
        public void onComplete() {

        }
    };

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isFingerPrintExist() {
        if (Build.VERSION.SDK_INT >= MARSHMALLOW) {
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            mFingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            if (mFingerprintManager != null) {
                //if has sensor
                if (!mFingerprintManager.isHardwareDetected()) {
                    mErrorCode = 701;
                    //if has at least one fingerPrint
                } else if (!mFingerprintManager.hasEnrolledFingerprints()) {
                    mErrorCode = 702;
                    // if has enabled lockscreen
                } else if (keyguardManager != null && !keyguardManager.isKeyguardSecure()) {
                    mErrorCode = 703;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void showViews() {
        mFingerLabelMessage.setVisibility(View.VISIBLE);
        mFingerTextMessage.setVisibility(View.VISIBLE);
        mFingerImage.setVisibility(View.VISIBLE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getFingerPrint() {
        try {
            generateKey();
        } catch (FingerprintException e) {
            e.printStackTrace();
        }
        if (initCipher()) {
            mCryptoObject = new FingerprintManager.CryptoObject(mCipher);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void startAuth() {
        FingerprintHandler handler = new FingerprintHandler(this);
        handler.setListener(this::startMainActivity);
        handler.startAuth(mFingerprintManager, mCryptoObject);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() throws FingerprintException {
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            mKeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            mKeyStore.load(null);

            KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec
                    .Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build();

            mKeyGenerator.init(keyGenParameterSpec);
            mKeyGenerator.generateKey();
        } catch (KeyStoreException | NoSuchAlgorithmException |
                NoSuchProviderException | IOException |
                CertificateException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean initCipher() {
        try {
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" +
                    KeyProperties.BLOCK_MODE_CBC + "/" +
                    KeyProperties.ENCRYPTION_PADDING_PKCS7);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get cipher", e);
        }

        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyStoreException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isPermissionGranted() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            getFingerPrint();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, PERMISSION_CODE);
    }

    private class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private boolean isNoErrorDetected() {
        return mErrorCode == 0;
    }
}
