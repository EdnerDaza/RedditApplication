/**
 * Copyright (c) 2011, 2012 Sentaca Communications Ltd.
 */
package com.ednerdaza.codigoriginal.redditapplication.classes.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ednerdaza.codigoriginal.redditapplication.R;
import com.ednerdaza.codigoriginal.redditapplication.classes.utilities.Config;

import java.util.concurrent.atomic.AtomicBoolean;

public class ToggleImageLabeledButton extends ImageView {

  private int imageOn;
  private int imageOff;
  private AtomicBoolean on = new AtomicBoolean(false);

  public ToggleImageLabeledButton(Context context, AttributeSet attrs) {
      super(context, attrs);

      Log.v(Config.LOG_TAG, "// ToggleImageLabeledButton (context : "+context+
              "\nattrs : "+attrs+") //\n...");

    if (attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.image_labeled_button, 0, 0);
      imageOn = a.getResourceId(R.styleable.image_labeled_button_icon_resource, 0);
      a = context.obtainStyledAttributes(attrs, R.styleable.toggle_image_labeled_button, 0, 0);
      imageOff = a.getResourceId(R.styleable.toggle_image_labeled_button_icon_resource_off, 0);
      setImageResource(imageOff);
    }

  }

  private void handleNewState(boolean newState) {
    Log.v(Config.LOG_TAG, "// handleNewState (newState : "+newState+") //\n...");
    if (newState) {
      setImageResource(imageOn);
    } else {
      setImageResource(imageOff);
    }
  }

  @Override
  public void setOnClickListener(final OnClickListener l) {
    OnClickListener wrappingListener = new OnClickListener() {

      public void onClick(View v) {
        boolean newState = !on.get();
        Log.v(Config.LOG_TAG, "// onClick (View : "+v+") //\n...");
        Log.v(Config.LOG_TAG, "// newState : "+newState+" //\n...");
        on.set(newState);
        handleNewState(newState);
        l.onClick(v);
      }

    };

    super.setOnClickListener(wrappingListener);
  }

  public void setState(boolean b) {
    Log.v(Config.LOG_TAG, "// setState (b : "+b+") //\n...");
    on.set(b);
    handleNewState(b);
  }

}
