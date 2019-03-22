package com.jeremy.utils.factories;

import com.jeremy.constants.AnimationType;
import com.jeremy.constants.StringResources;
import com.jeremy.models.Animation;
import com.jeremy.utils.loggers.PuzzleLogger;
import com.vaadin.ui.Component;

public final class AnimationFactory {

    private static Animation animation;
    private static String class_name = "Animation Factory";

    public static Animation getAnimation(AnimationType animationType , Component[] components) {
        if (animation == null) animation = new Animation(animationType , components);
        if (animation.getAnimationType() != animationType) animation.setAnimationType(animationType);
        animation.setComponents(components);
        return animation;
    }

    public static void close() {
        PuzzleLogger.info(class_name , StringResources.closing);
        animation = null;
    }
}
