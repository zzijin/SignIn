package com.example.administrator.myapp.cls;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapp.R;

public class UnderlineRadioButton extends FrameLayout {

    private TextView tvText;
    private View underline;
    private int textColorChecked, textColorUnchecked;
    private int underlineColorChecked, underlineColorUnchecked;
    private boolean checked;

    public UnderlineRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UnderlineRadioButton);
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        LayoutParams llLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        llLayoutParams.gravity = Gravity.CENTER;
        tvText = new TextView(context);
        String text = typedArray.getString(R.styleable.UnderlineRadioButton_text);
        tvText.setText(text);
        LinearLayout.LayoutParams tvTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvTextLayoutParams.gravity = Gravity.CENTER;
        tvTextLayoutParams.topMargin = typedArray.getDimensionPixelSize(R.styleable.UnderlineRadioButton_textMarginTop, 0);
        tvTextLayoutParams.bottomMargin = typedArray.getDimensionPixelSize(R.styleable.UnderlineRadioButton_textMarginBottom, 0);
        ll.addView(tvText, tvTextLayoutParams);
        underline = new View(context);
        int underlineHeight = typedArray.getDimensionPixelSize(R.styleable.UnderlineRadioButton_underlineHeight, 0);
        LinearLayout.LayoutParams underlineLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, underlineHeight);
        ll.addView(underline, underlineLayoutParams);
        addView(ll, llLayoutParams);
        boolean checked = typedArray.getBoolean(R.styleable.UnderlineRadioButton_checked, false);
        textColorChecked = typedArray.getColor(R.styleable.UnderlineRadioButton_textColorChecked, 0);
        textColorUnchecked = typedArray.getColor(R.styleable.UnderlineRadioButton_textColorUnchecked, 0);
        underlineColorChecked = typedArray.getColor(R.styleable.UnderlineRadioButton_underlineColorChecked, 0);
        underlineColorUnchecked = typedArray.getColor(R.styleable.UnderlineRadioButton_underlineColorUnchecked, 0);
        setChecked(checked);
        typedArray.recycle();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            tvText.setTextColor(textColorChecked);
            underline.setBackgroundColor(underlineColorChecked);
        } else {
            tvText.setTextColor(textColorUnchecked);
            underline.setBackgroundColor(underlineColorUnchecked);
        }
    }

}
