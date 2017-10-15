package com.wearapay.scandemo.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wearapay.scandemo.R;
import com.wearapay.scandemo.utils.UIUtil;

/**
 * Created by Kindy on 2016-05-08.
 *
 * <br>support : icon、titleView、subTitleView and forward
 * <br>
 * <br>{@link R.attr#cell_title} title 文字
 * <br>{@link R.attr#cell_sub_title} subTitle 文字
 * <br>{@link R.attr#forward_visible_status} forward 是否可见
 * <br>{@link R.attr#cell_title_color} titleView 颜色，如不设置则使用默认颜色
 * <br>{@link R.attr#cell_value_color} subTitleView 颜色，如不设置则使用默认颜色
 * <br>{@link R.attr#cell_title_size} titleView 字体大小
 * <br>{@link R.attr#cell_title_icon} icon 资源id
 * <br>{@link R.attr#cell_accessory_icon} forward 资源id
 * <br>{@link R.attr#cell_icon_size} icon 大小
 * <br>{@link R.attr#cell_title_padding_left} icon 和 titleView 之间的空隙
 * <br>{@link R.attr#cell_subtitle_padding_right} forward 和 subTitleView 之间的空隙
 * <br>{@link R.attr#cell_padding_left} cell 的 paddingLeft，默认20dp
 * <br>{@link R.attr#cell_padding_right} cell 的 paddingRight，默认0dp
 */
public class CustomLabelCell extends LinearLayout {

  private TextView titleView;
  private TextView subTitleView;
  private ImageView forward;
  private int paddingRightWithForward;
  private int paddingRight;
  private int paddingLeft;
  private View vDot;

  public CustomLabelCell(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PPFieldCell);

    String title = a.getString(R.styleable.PPFieldCell_cell_title);
    String subTitle = a.getString(R.styleable.PPFieldCell_cell_sub_title);
    int forwardVisibleStatus =
        a.getInt(R.styleable.PPFieldCell_forward_visible_status, VisibleStatus.VISIBLE.ordinal());
    int titleColor = a.getColor(R.styleable.PPFieldCell_cell_title_color, 0);
    int valueColor = a.getColor(R.styleable.PPFieldCell_cell_value_color, 0);
    int titleSize = a.getDimensionPixelSize(R.styleable.PPFieldCell_cell_title_size, 0);
    int valueSize = a.getDimensionPixelSize(R.styleable.PPFieldCell_cell_value_size, 0);
    int iconResId = a.getResourceId(R.styleable.PPFieldCell_cell_title_icon, 0);
    int forwardResId = a.getResourceId(R.styleable.PPFieldCell_cell_accessory_icon, 0);
    int iconSize = a.getDimensionPixelSize(R.styleable.PPFieldCell_cell_icon_size, 0);
    int titlePaddingLeft =
        a.getDimensionPixelOffset(R.styleable.PPFieldCell_cell_title_padding_left, 0);
    int subTitlePaddingRight =
        a.getDimensionPixelOffset(R.styleable.PPFieldCell_cell_subtitle_padding_right, 0);
    paddingLeft = UIUtil.dip2px(context, 20);
    paddingLeft = a.getDimensionPixelSize(R.styleable.PPFieldCell_cell_padding_left, paddingLeft);
    paddingRight = a.getDimensionPixelSize(R.styleable.PPFieldCell_cell_padding_right, 0);
    paddingRightWithForward = UIUtil.dip2px(context, 20);

    a.recycle();

    setOrientation(LinearLayout.HORIZONTAL);
    setGravity(Gravity.CENTER_VERTICAL);
    Drawable drawable = getBackground();
    if (drawable == null) {
      // 如果没有设置背景，则使用该颜色背景
      setBackgroundResource(R.drawable.bg_cell);
    }
    setPadding(paddingLeft, 0, paddingRight, 0);

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.custom_label_cell, this, true);

    ImageView icon = (ImageView) findViewById(R.id.ivLeft);
    this.titleView = (TextView) findViewById(R.id.tvTitle);
    this.subTitleView = (TextView) findViewById(R.id.tvSubTitle);
    vDot = findViewById(R.id.vDot);
    forward = (ImageView) findViewById(R.id.ivRight);

    this.setTitle(title);
    this.setSubTitle(subTitle);
    if (forwardVisibleStatus == VisibleStatus.VISIBLE.ordinal()) {
      forward.setVisibility(View.VISIBLE);
      if (forwardResId != 0) {
        forward.setImageResource(forwardResId);
      }
    } else if (forwardVisibleStatus == VisibleStatus.INVISIBLE.ordinal()) {
      forward.setVisibility(View.INVISIBLE);
    } else if (forwardVisibleStatus == VisibleStatus.GONE.ordinal()) {
      forward.setVisibility(View.GONE);
    }

    if (iconResId == 0) {
      icon.setVisibility(View.GONE);
    } else {
      if (iconSize > 0) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) icon.getLayoutParams();
        lp.width = lp.height = iconSize;
        icon.setLayoutParams(lp);
      }
      icon.setImageResource(iconResId);
    }

    setTitleIconSpace(titlePaddingLeft);
    setSubTitleForwardSpace(subTitlePaddingRight);
    UIUtil.setTextViewSize(context, this.titleView, titleSize);
    UIUtil.setTextViewColor(context, this.titleView, titleColor);
    UIUtil.setTextViewSize(context, this.subTitleView, valueSize);
    UIUtil.setTextViewColor(context, this.subTitleView, valueColor);
  }

  private void setTitleIconSpace(int pad) {
    if (pad > 0) {
      LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.titleView.getLayoutParams();
      lp.leftMargin = pad;
      this.titleView.setLayoutParams(lp);
    }
  }

  private void setSubTitleForwardSpace(int pad) {
    if (pad > 0) {
      LinearLayout.LayoutParams lp =
          (LinearLayout.LayoutParams) this.subTitleView.getLayoutParams();
      lp.rightMargin = pad;
      this.subTitleView.setLayoutParams(lp);
    }
  }

  public TextView getTitleTextView() {
    return titleView;
  }

  public TextView getSubTitleTextView() {
    return subTitleView;
  }

  public ImageView getForward() {
    return forward;
  }

  public void setTitle(CharSequence text) {
    this.titleView.setText(text);
  }

  public void setTitle(int resId) {
    this.titleView.setText(resId);
  }

  public void setSubTitle(CharSequence text) {
    this.subTitleView.setText(text);
  }

  public void setSubTitle(int resId) {
    this.subTitleView.setText(resId);
  }

  public void setForwardVisibility(boolean visibility) {
    if (visibility) {
      getForward().setVisibility(View.VISIBLE);
      setPadding(paddingLeft, 0, paddingRight, 0);
    } else {
      getForward().setVisibility(View.GONE);
      setPadding(paddingLeft, 0, paddingRightWithForward, 0);
    }
  }

  public void setDotState(Boolean visible, Boolean enabled) {
    if (visible != null) {
      vDot.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
    if (enabled != null) {
      vDot.setEnabled(enabled);
    }
  }
}
