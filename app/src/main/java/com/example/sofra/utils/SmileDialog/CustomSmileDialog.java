package com.example.sofra.utils.SmileDialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

public class CustomSmileDialog extends Dialog {
    public CustomSmileDialog(@NonNull Context context) {
        super(context);
        this.setCancelable(false);
    }

    public CustomSmileDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        this.dismiss();
    }
}
