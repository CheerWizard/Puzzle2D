package com.jeremy.ui;

import com.jeremy.models.Animation;

/**View implementation , which will contain only view logic*/
public interface IView {
    void render(Animation animation);
    void closeView();
}
