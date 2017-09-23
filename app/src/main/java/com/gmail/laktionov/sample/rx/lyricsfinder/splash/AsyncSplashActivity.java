package com.gmail.laktionov.sample.rx.lyricsfinder.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.laktionov.sample.rx.lyricsfinder.R;
import com.gmail.laktionov.sample.rx.lyricsfinder.search.MainActivity;

public class AsyncSplashActivity extends AppCompatActivity implements FingerprintHandler.AuthListener {

    private TextView mFingerLabelMessage;
    private TextView mFingerTextMessage;
    private ImageView mFingerImage;

    @Override
    protected void onStart() {
        super.onStart();
        new AsyncFingerprintChecker().execute();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();

    }

    private void initViews() {
        mFingerLabelMessage = findViewById(R.id.auth_label);
        mFingerTextMessage = findViewById(R.id.auth_message);
        mFingerImage = findViewById(R.id.fingerprint_image);
    }

    private void showViews() {
        mFingerLabelMessage.setVisibility(View.VISIBLE);
        mFingerTextMessage.setVisibility(View.VISIBLE);
        mFingerImage.setVisibility(View.VISIBLE);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAuthSuccess() {
        startMainActivity();
    }

    @SuppressLint("StaticFieldLeak")
    class AsyncFingerprintChecker extends AsyncTask<Void, Void, FingerprintHelper.SensorState> {

        @Override
        protected FingerprintHelper.SensorState doInBackground(Void... voids) {
            FingerprintHelper.SensorState currentFingerprintState = FingerprintHelper.checkSensorState(AsyncSplashActivity.this);

            if (currentFingerprintState == FingerprintHelper.SensorState.READY) {
                try {
                    FingerprintHelper.generateKey();
                } catch (RxSplashActivity.FingerprintException e) {
                    e.printStackTrace();
                }
                if (FingerprintHelper.initCipher()) {
                    publishProgress();
                    FingerprintHelper.startAuth(AsyncSplashActivity.this);
                }
            }
            return currentFingerprintState;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            SplashAnimator.startAnimation(mFingerLabelMessage, mFingerTextMessage, mFingerImage);
        }

        @Override
        protected void onPostExecute(FingerprintHelper.SensorState sensorState) {
            if (sensorState != FingerprintHelper.SensorState.READY) {
                showWarningMessage(sensorState);
                startMainActivity();
            }
        }

        private void showWarningMessage(FingerprintHelper.SensorState state) {
            String message = null;
            switch (state) {
                case NOT_SUPPORTED:
                    message = "Fingerprint not supported";
                    break;
                case NO_FINGERPRINT:
                    message = "No current fingerprint found";
                    break;
                case NOT_BLOCKED:
                    message = "Device not blocked via pin";
                    break;
            }
            if (message != null) {
                Toast.makeText(AsyncSplashActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
