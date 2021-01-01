package com.example.administrator.myapp.cls;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.myapp.R;

public class RadioGroup extends LinearLayout {
    private int checkedId;
    private OnCheckedChangeListener onCheckedChangeListener;
    private static final String KEY_SUPER_PARCELABLE = "SuperParcelableKey";
    private static final String KEY_CHECKED_ID = "CheckedIdKey";

    public RadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadioGroup);
        checkedId = typedArray.getResourceId(R.styleable.RadioGroup_checkedButton, NO_ID);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (checkedId != NO_ID) {
            setCheckedStateForView(checkedId, true);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superParcelable = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_PARCELABLE, superParcelable);
        bundle.putInt(KEY_CHECKED_ID, checkedId);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_PARCELABLE));
        int id = bundle.getInt(KEY_CHECKED_ID);
        check(id);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        UnderlineRadioButton button = (UnderlineRadioButton) child;
        if (button.isChecked()) {
            int buttonId = button.getId();
            if (buttonId != checkedId) {
                if (checkedId != NO_ID) {
                    setCheckedStateForView(checkedId, false);
                }
                setCheckedId(buttonId);
            }
        }
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id != checkedId) {
                    check(id);
                }
            }
        });
        super.addView(child, index, params);
    }

    public void check(int id) {
        if (id != checkedId) {
            setCheckedStateForView(checkedId, false);
            setCheckedStateForView(id, true);
            setCheckedId(id);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        onCheckedChangeListener = listener;
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        UnderlineRadioButton button = findViewById(viewId);
        if (button != null) {
            button.setChecked(checked);
        }
    }

    private void setCheckedId(int id) {
        checkedId = id;
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(id);
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(int checkedId);
    }
}
