package com.luzuzu.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.luzuzu.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fula on 2019/3/11.
 */

public class ObserverButton extends android.support.v7.widget.AppCompatTextView {

    private List<EditText> editTextList = new ArrayList<>();

    private boolean canPress;
    private int defaultBg = Color.GRAY;
    private int pressBg = Color.BLUE;
    private int defaultTextColor = Color.WHITE;
    private int pressTextColor = Color.WHITE;
    private int defaultBgRes;
    private int pressBgRes;

    public ObserverButton(Context context) {
        this(context, null);
    }

    public ObserverButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObserverButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ObserverButton);
        defaultBg = a.getColor(R.styleable.ObserverButton_defaultBgColor, defaultBg);
        pressBg = a.getColor(R.styleable.ObserverButton_pressBgColor, pressBg);
        defaultTextColor = a.getColor(R.styleable.ObserverButton_defaultTextColor, defaultTextColor);
        pressTextColor = a.getColor(R.styleable.ObserverButton_pressTextColor, pressTextColor);
        defaultBgRes = a.getResourceId(R.styleable.ObserverButton_defaultBgResource, 0);
        pressBgRes = a.getResourceId(R.styleable.ObserverButton_pressBgResource, 0);
        a.recycle();
        initBtn();
    }


    public void observer(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(textWatcher);
            editTextList.add(editText);
        }
    }

    private void initBtn() {
        if (canPress) {
            setTextColor(pressTextColor);
            if (pressBgRes != 0) {
                setBackgroundResource(pressBgRes);
            } else {
                setBackgroundColor(pressBg);
            }
            setEnabled(true);
        } else {
            setTextColor(defaultTextColor);
            setBackgroundColor(defaultBg);
            if (defaultBgRes != 0) {
                setBackgroundResource(defaultBgRes);
            } else {
                setBackgroundColor(defaultBg);
            }
            setEnabled(false);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkEditText();
        }
    };

    /**
     * 检查输入框输入
     */
    private void checkEditText() {
        canPress = false;
        for (EditText et : editTextList) {
            if (et.getText().toString().trim().length() == 11) {
                canPress = true;
                break;
            }
        }
        initBtn();
    }
}
