package com.yang.yunfan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yang.yunfan.R;


/**
 * 带图标，文字，详情箭头，红点，分割线的item
 * Created by yangjinxi on 2016/8/30.
 */

public class ImageTextItemView extends RelativeLayout {
    private static final int GONE = 0;
    private static final int SHOW = 1;

    //有红点
    public static final int HAS_RED_DOT = 0;

    //无红点
    public static final int NON_RED_DOT = 1;

    private static final String INSTANCE_STATE = "instance_state";

    private static final String STATE_RED_DOT = "state_red_dot";

    private int redDot = 1;

    private ImageView ivDot;

    public ImageTextItemView(Context context) {
        this(context, null);
    }

    public ImageTextItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_image_text_view, this, true);

        TextView tvItemName = (TextView) findViewById(R.id.tv_item_name);
        ivDot = (ImageView) findViewById(R.id.iv_dot);
        View line = findViewById(R.id.v_line);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTextItemView);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            if (attr == R.styleable.ImageTextItemView_itemText){
                String text = a.getString(attr);
                tvItemName.setText(text);
            }else if (attr == R.styleable.ImageTextItemView_bottomLine){
                if (GONE == a.getInt(attr, SHOW)){
                    line.setVisibility(View.GONE);
                }
            }else if (attr == R.styleable.ImageTextItemView_redDot){
                setItemHasRedDot(a.getInt(attr, NON_RED_DOT));
            }
        }
        a.recycle();
    }

    /**
     * 设置该项是否有红点
     * @param hasRedDot
     */
    public void setItemHasRedDot(int hasRedDot){
        redDot = hasRedDot;
        switch (hasRedDot){
            case HAS_RED_DOT:
                ivDot.setVisibility(VISIBLE);
                break;
            case NON_RED_DOT:
                ivDot.setVisibility(INVISIBLE);
                break;
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(STATE_RED_DOT, redDot);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            int i = bundle.getInt(STATE_RED_DOT);
            setItemHasRedDot(i);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
        } else {
            super.onRestoreInstanceState(state);
        }

    }

}
