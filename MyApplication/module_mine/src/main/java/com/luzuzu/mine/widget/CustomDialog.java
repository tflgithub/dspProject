package com.luzuzu.mine.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.AppCompatTextView;
import com.luzuzu.mine.R;

/**
 * Created by fula on 2019/5/28.
 */

public class CustomDialog extends AppCompatDialog {

    private AppCompatTextView tv_title, tv_content;

    private Button btn_left, btn_right;

    private String title, content, leftTxt, rightTxt;

    public CustomDialog(Context context, String[] strings, onButtonListener onButtonListener) {
        super(context);
        init(strings);
        this.onButtonListener = onButtonListener;
    }

    private void init(String[] str) {
        title = str[0] == null ? "提示" : str[0];
        content = str[1] == null ? "" : str[1];
        leftTxt = str[2] == null ? "取消" : str[2];
        rightTxt = str[3] == null ? "确定" : str[3];
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        tv_title = findViewById(R.id.custom_dialog_title);
        tv_content = findViewById(R.id.custom_dialog_content);
        btn_left = findViewById(R.id.custom_dialog_left_btn);
        btn_right = findViewById(R.id.custom_dialog_right_btn);
        tv_title.setText(title);
        tv_content.setText(content);
        btn_left.setText(leftTxt);
        btn_right.setText(rightTxt);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonListener.onCancel();
                dismiss();
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onButtonListener.onOk();
            }
        });
    }

    public onButtonListener onButtonListener;

    public interface onButtonListener {

        void onCancel();

        void onOk();
    }
}
