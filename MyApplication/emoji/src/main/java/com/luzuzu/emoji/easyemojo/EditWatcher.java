package com.luzuzu.emoji.easyemojo;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * Created by fula on 2019/5/26.
 */

public class EditWatcher implements TextWatcher {
    private View view;

    public EditWatcher(View view) {
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count > 0 || s.length() > 0){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
