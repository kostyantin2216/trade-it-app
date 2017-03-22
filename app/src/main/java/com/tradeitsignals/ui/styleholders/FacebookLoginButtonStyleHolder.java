package com.tradeitsignals.ui.styleholders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.facebook.login.widget.LoginButton;
import com.tradeitsignals.R;

/**
 * Created by Kostyantin on 6/4/2016.
 */
public class FacebookLoginButtonStyleHolder implements StyleHolder<LoginButton> {

    private Drawable drawable;
    private int drawablePadding;
    private int paddingLeft;
    private int paddingTop;
    private int paddingBottom;

    public FacebookLoginButtonStyleHolder(Context context) {
        Resources resources = context.getResources();

        float fbIconScale = 1.45F;
        drawable = resources.getDrawable(com.facebook.R.drawable.com_facebook_button_icon);
        drawable.setBounds(
                0,
                0,
                (int)(drawable.getIntrinsicWidth() * fbIconScale),
                (int)(drawable.getIntrinsicHeight() * fbIconScale)
        );

        drawablePadding = resources.getDimensionPixelSize(R.dimen.fb_margin_override_textpadding);
        paddingLeft = resources.getDimensionPixelSize(R.dimen.fb_margin_override_left);
        paddingTop = resources.getDimensionPixelSize(R.dimen.fb_margin_override_top);
        paddingBottom = resources.getDimensionPixelSize(R.dimen.fb_margin_override_bottom);
    }

    @Override
    public void applyStyle(LoginButton view) {
        view.setCompoundDrawables(drawable, null, null, null);
        view.setCompoundDrawablePadding(drawablePadding);
        view.setPadding(paddingLeft, paddingTop, 0, paddingBottom);
    }
}
