package com.example.locateme;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class NoticeDialog extends Dialog implements View.OnClickListener {

    private Button btnCancle;
    private Button btnOk;
    private TextView tvNotice;

    public NoticeDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.notice_dialog);

        btnCancle = findViewById(R.id.btn_cancle);
        btnOk = findViewById(R.id.btn_ok);
        tvNotice = findViewById(R.id.tv_notice);
    }

    public void setNotification(String notification, String textOk, String textCancle, View.OnClickListener onClickListener) {
        btnOk.setText(textOk);
        btnCancle.setText(textCancle);
        tvNotice.setText(notification);

        btnOk.setOnClickListener(onClickListener);
        btnCancle.setOnClickListener(onClickListener);

        if (textCancle == null) btnCancle.setVisibility(View.GONE);
        if (onClickListener == null) btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
