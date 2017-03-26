package com.sugary.searchviewtest;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ethan on 17/3/21.
 */

public class ChangeColor extends Transition {

    private static final String PROPNAME_BACKGROUND = "ChangeColor_background";

    private long changeDuration = 1000;

    public ChangeColor() {
    }

    public ChangeColor(long changeDuration) {
        this.changeDuration = changeDuration;
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view.getBackground() instanceof ColorDrawable) {
            View view = transitionValues.view;
            transitionValues.values.put(PROPNAME_BACKGROUND, ((ColorDrawable) (view.getBackground())).getColor());
        }
    }

    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        if(startValues == null || endValues == null){
            return null;
        }

        int startBackground = (Integer) startValues.values.get(PROPNAME_BACKGROUND);
        int endBackground = (Integer) endValues.values.get(PROPNAME_BACKGROUND);
        if(startBackground != endBackground) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofObject(endValues.view, "backgroundColor", new ArgbEvaluator(), startBackground, endBackground);
            objectAnimator.setDuration(changeDuration);
            return objectAnimator;
        }

        return null;
    }

    @Nullable
    @Override
    public String[] getTransitionProperties() {
        return new String[]{PROPNAME_BACKGROUND};
    }
}
