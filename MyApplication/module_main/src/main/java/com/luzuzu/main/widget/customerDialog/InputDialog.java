package com.luzuzu.main.widget.customerDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.luzuzu.emoji.easyemojo.CircleIndicator;
import com.luzuzu.emoji.easyemojo.EmoJiContainerAdapter;
import com.luzuzu.emoji.easyemojo.EmoJiHelper;
import com.luzuzu.emoji.easyemojo.EmotionInputDetector;
import com.luzuzu.library.utils.KeyboardUtils;
import com.luzuzu.library.utils.SystemUtils;
import com.luzuzu.library.widget.BaseDialog;
import com.luzuzu.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fula on 2019/5/22.
 */

public class InputDialog extends BaseDialog {

    @BindView(R.id.et_input)
    EditText mInputContainer;
    @BindView(R.id.bt_send)
    Button mSendButton;

    @BindView(R.id.cb_smile)
    CheckBox mEmoJiView;

    @BindView(R.id.ll_face_container)
    LinearLayout mEmoJiContainer;

    private EmotionInputDetector mDetector;

    @BindView(R.id.circleIndicator)
    CircleIndicator mCircleIndicator;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.emojicons_container)
    LinearLayout contentView;

    private boolean isShowEmoji;

    public InputDialog(@NonNull Context context, int themeResId, boolean isShowEmoji) {
        super(context, themeResId);
        this.isShowEmoji = isShowEmoji;
        setOnKeyListener(keyListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreateView = View.inflate(getContext(), R.layout.input_dialog_layout, null);
        setContentView(mCreateView);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(mInputContainer.getText().toString().trim());
                //onSendListener.send(mInputContainer.getText().toString().trim());
            }
        });

        mDetector = EmotionInputDetector.with(SystemUtils.getAppCompatActivity(mContext))
                .bindSendButton(mSendButton)
                .bindToEditText(mInputContainer)
                .bindToContent(contentView)
                .setEmotionView(mEmoJiContainer)
                .bindToEmotionButton(mEmoJiView);

        if (isShowEmoji) {

        } else {
            mInputContainer.requestFocus();
        }

        EmoJiHelper emojiHelper = new EmoJiHelper(1, mContext, mInputContainer);
        EmoJiContainerAdapter mAdapter = new EmoJiContainerAdapter(emojiHelper.getPagers());
        viewPager.setAdapter(mAdapter);
        mCircleIndicator.setViewPager(viewPager);
    }

    private DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog.dismiss();
                return false;
            } else if (keyCode == KeyEvent.KEYCODE_DEL) {//删除键
                //这里一定要返回false,否则无法使用删除键
                return false;
            } else {
                return true;
            }
        }
    };

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void dismiss() {
        if (!mDetector.interceptBackPress()) {
            super.dismiss();
        }
    }

    public interface onSendListener {
        void send(String content);
    }

    private onSendListener onSendListener;

    public void setOnSendListener(onSendListener onSendListener) {
        this.onSendListener = onSendListener;
    }
}
