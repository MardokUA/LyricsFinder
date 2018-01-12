package com.gmail.laktionov.sample.rx.lyricsfinder.splash.fingerprint;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManagerCompat.AuthenticationCallback {

    private AuthListener mListener;
    private CancellationSignal mCancellationSignal;
    private Context mContext;

    public FingerprintHandler(Context context) {
        mContext = context;
    }

    public void startAuth(FingerprintManagerCompat.CryptoObject cryptoObject) {
        mCancellationSignal = new CancellationSignal();
        FingerprintManagerCompat manager = FingerprintManagerCompat.from(mContext);
        manager.authenticate(cryptoObject, 0, mCancellationSignal, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Toast.makeText(mContext, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(mContext, "Authentication failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(mContext, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        Toast.makeText(mContext, "Success!", Toast.LENGTH_LONG).show();
        if (mListener != null) {
            mListener.onAuthSuccess();
        }
    }

    public interface AuthListener {
        void onAuthSuccess();
    }

    public void setListener(AuthListener mListener) {
        this.mListener = mListener;
    }
}
