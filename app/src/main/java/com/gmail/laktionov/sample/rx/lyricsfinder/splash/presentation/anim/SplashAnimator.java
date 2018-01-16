package com.gmail.laktionov.sample.rx.lyricsfinder.splash.presentation.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

class SplashAnimator {

    private SplashAnimator() {

    }

    static void startAnimation(TextView labelView, TextView textView, ImageView fingerprintView) {

        ObjectAnimator labelAnimator = ObjectAnimator.ofFloat(labelView, View.SCALE_X, 0, labelView.getScaleX());
        ObjectAnimator textAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_X, 0, textView.getScaleX());
        ObjectAnimator fingerptintAnimator = ObjectAnimator.ofFloat(fingerprintView, View.SCALE_X, 0, fingerprintView.getScaleX());

        labelAnimator.setDuration(250);
        textAnimator.setDuration(250);
        fingerptintAnimator.setDuration(250);

        labelAnimator.addListener(getStartAimationAdapter(labelView));
        textAnimator.addListener(getStartAimationAdapter(textView));
        fingerptintAnimator.addListener(getStartAimationAdapter(fingerprintView));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(labelAnimator, fingerptintAnimator, textAnimator);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    private static AnimatorListenerAdapter getStartAimationAdapter(View view) {
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }
        };
    }
}
