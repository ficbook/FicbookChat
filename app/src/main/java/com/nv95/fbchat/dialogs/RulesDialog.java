package com.nv95.fbchat.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.nv95.fbchat.R;
import com.nv95.fbchat.utils.ThemeUtils;

/**
 * Created by nv95 on 14.08.16.
 */

public class RulesDialog {

    public static void show(Context context, DialogInterface.OnClickListener clickListener) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setMessage(R.string.rules)
                .setPositiveButton(R.string.accept, clickListener)
                .setNegativeButton(R.string.dismiss, null)
                .create();
        dialog.setOnShowListener(new ThemeUtils.DialogPainter());
        dialog.show();
    }
}
