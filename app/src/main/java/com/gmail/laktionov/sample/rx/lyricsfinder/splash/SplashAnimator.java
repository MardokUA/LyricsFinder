package com.gmail.laktionov.sample.rx.lyricsfinder.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAnimator {

    private SplashAnimator() {

    }

    public static void startAnimation(TextView labelView, TextView textView, ImageView fingerprintView) {
        getLabelViewAnimation(labelView).start();
        getFingerprintAnimation(fingerprintView).start();
        getTextViewAnimation(textView).start();
    }

    private static ObjectAnimator getLabelViewAnimation(View view) {
        ObjectAnimator positionAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, view.getTranslationY());
        positionAnimator.setDuration(150);
        positionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        return positionAnimator;
    }

    private static ObjectAnimator getFingerprintAnimation(View view) {
        ObjectAnimator positionAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, view.getTranslationY());
        positionAnimator.setDuration(150);
        positionAnimator.setStartDelay(150);
        positionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        return positionAnimator;
    }

    private static ObjectAnimator getTextViewAnimation(View view) {
        ObjectAnimator positionAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, view.getTranslationY());
        positionAnimator.setDuration(150);
        positionAnimator.setStartDelay(300);
        positionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        return positionAnimator;
    }
}
