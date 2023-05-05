package org.tensorflow.lite.examples.objectdetection.classes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import org.tensorflow.lite.examples.objectdetection.R;


public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context,String layout) {
        super(context);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View view = null;
        if(layout.equals("image_loading"))
            view = LayoutInflater.from(context).inflate(R.layout.image_loading,null);
        if(layout.equals("info_loading"))
            view = LayoutInflater.from(context).inflate(R.layout.info_loading,null);
//        if(layout.equals("image_loading"))
//            view = LayoutInflater.from(context).inflate(R.layout.image_loading,null);

        setContentView(view);

    }
}