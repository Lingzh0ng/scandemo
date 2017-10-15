package com.wearapay.scandemo.weight;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wearapay.base.utils.SimpleTextWatcher;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.utils.UIUtil;


/**
 * Created by Kindy on 2016-06-02.
 *
 * <br>support : iconLeft（提示小图标）、titleView、editText and iconRight（清空图标）
 * <br>
 * <br>{@link R.attr#cell_title} title 文字
 * <br>{@link R.attr#cell_title_size} title 字体大小
 * <br>{@link R.attr#cell_title_color} title 字体颜色
 * <br>{@link R.attr#cell_title_padding_left} icon 和 titleView 之间的空隙
 * <br>{@link R.attr#cell_title_width} titleView 宽度
 * <br>{@link R.attr#cell_edit_text_style} editText 的 appearance（包括大小，颜色）
 * <br>{@link R.attr#cell_hint} editText 的 hintText
 * <br>{@link R.attr#cell_text_margin_left} editText 的 marginLeft，默认20dp
 * <br>{@link R.attr#cell_text_margin_right} editText 的 marginRight，默认20dp
 * <br>{@link R.attr#cell_icon} iconLeft 资源id
 * <br>{@link R.attr#cell_image} iconRight 资源id
 * <br>{@link R.attr#cell_icon_size} iconLeft 大小
 * <br>{@link R.attr#cell_image_size} iconRight 大小
 * <br>{@link R.attr#cell_padding_left} cell 的 paddingLeft，默认20dp
 * <br>{@link R.attr#cell_padding_right} cell 的 paddingRight，默认20dp
 * <br>{@link R.attr#cell_password} 是否是密码形式，默认false
 * <br>{@link R.attr#cell_editable} 是否可编辑，默认true
 */
public class CustomEditCell extends LinearLayout {
  private ImageView iconLeft;
  private TextView titleView;
  private EditText editText;
  private ImageView iconRight;
  private boolean autoHideRightIcon;

  public CustomEditCell(Context context) {
    this(context, null);
  }

  public CustomEditCell(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomEditCell(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CustomEditCell(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  @Override public String toString() {
    return super.toString();
  }

  private void init(Context context, AttributeSet attrs) {
    if (isInEditMode()) {
      return;
    }
    autoHideRightIcon = true;
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PPCellWithEditTextIcon);

    String title = a.getString(R.styleable.PPCellWithEditTextIcon_cell_title);
    int titleSize = a.getDimensionPixelSize(R.styleable.PPCellWithEditTextIcon_cell_title_size, 0);
    int titleColor = a.getColor(R.styleable.PPCellWithEditTextIcon_cell_title_color, 0);
    int titlePaddingLeft =
        a.getDimensionPixelOffset(R.styleable.PPCellWithEditTextIcon_cell_title_padding_left, 0);
    int titleLayoutWidth =
        a.getDimensionPixelOffset(R.styleable.PPCellWithEditTextIcon_cell_title_width, 0);
    int style = a.getResourceId(R.styleable.PPCellWithEditTextIcon_cell_edit_text_style, 0);
    String hint = a.getString(R.styleable.PPCellWithEditTextIcon_cell_hint);
    int margin = UIUtil.dip2px(context, 20);
    int textMarginLeft =
        a.getDimensionPixelOffset(R.styleable.PPCellWithEditTextIcon_cell_text_margin_left, margin);
    int textMarginRight =
        a.getDimensionPixelOffset(R.styleable.PPCellWithEditTextIcon_cell_text_margin_right,
            margin);
    int iconLeft = a.getResourceId(R.styleable.PPCellWithEditTextIcon_cell_icon, 0);
    int iconRight = a.getResourceId(R.styleable.PPCellWithEditTextIcon_cell_image, 0);
    int size = context.getResources().getDimensionPixelSize(R.dimen.cell_height) / 2;
    int iconLeftSize =
        a.getDimensionPixelSize(R.styleable.PPCellWithEditTextIcon_cell_icon_size, size);
    int iconRightSize =
        a.getDimensionPixelSize(R.styleable.PPCellWithEditTextIcon_cell_image_size, size);
    int padding = UIUtil.dip2px(context, 20);
    int cellPaddingLeft =
        a.getDimensionPixelOffset(R.styleable.PPCellWithEditTextIcon_cell_padding_left, padding);
    int cellPaddingRight =
        a.getDimensionPixelOffset(R.styleable.PPCellWithEditTextIcon_cell_padding_right, padding);
    boolean isPassword = a.getBoolean(R.styleable.PPCellWithEditTextIcon_cell_password, false);
    boolean isEditable = a.getBoolean(R.styleable.PPCellWithEditTextIcon_cell_editable, true);
    a.recycle();

    setOrientation(LinearLayout.HORIZONTAL);
    setGravity(Gravity.CENTER_VERTICAL);
    Drawable drawable = getBackground();
    if (drawable == null) {
      // 如果没有设置背景，则使用该颜色背景
      setBackgroundColor(getResources().getColor(R.color.cell_bg_color));
    }
    setPadding(cellPaddingLeft, 0, cellPaddingRight, 0);

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.custom_edit_cell, this, true);

    this.iconLeft = (ImageView) getChildAt(0);
    this.titleView = (TextView) getChildAt(1);
    this.editText = (EditText) getChildAt(2);
    this.iconRight = (ImageView) getChildAt(3);

    this.titleView.setText(title);
    UIUtil.setTextViewSize(context, this.titleView, titleSize);
    UIUtil.setTextViewColor(context, this.titleView, titleColor);

    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.titleView.getLayoutParams();
    if (titleLayoutWidth > 0) {
      lp.width = titleLayoutWidth;
    }
    if (titlePaddingLeft > 0) {
      lp.leftMargin = titlePaddingLeft;
    }
    this.titleView.setLayoutParams(lp);

    lp = (LinearLayout.LayoutParams) editText.getLayoutParams();
    lp.leftMargin = textMarginLeft;
    lp.rightMargin = textMarginRight;
    editText.setLayoutParams(lp);
    editText.setHint(hint);
    editText.setEnabled(isEditable);
    if (isPassword) {
      editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
    setEditTextStyle(context, style);

    if (iconLeft == 0) { // 未设置，则隐藏
      this.iconLeft.setVisibility(View.GONE);
    } else {
      lp = (LinearLayout.LayoutParams) this.iconLeft.getLayoutParams();
      lp.width = lp.height = iconLeftSize;
      this.iconLeft.setLayoutParams(lp);
      this.iconLeft.setImageResource(iconLeft);
    }
    if (iconRight == 0) { // 未设置，则隐藏
      this.iconRight.setVisibility(View.GONE);
    } else {
      lp = (LinearLayout.LayoutParams) this.iconRight.getLayoutParams();
      lp.width = lp.height = iconRightSize;
      this.iconRight.setLayoutParams(lp);
      this.iconRight.setImageResource(iconRight);

      setImageViewListener(new OnClickListener() {
        @Override public void onClick(View v) {
          editText.setText("");
        }
      });
      editText.setOnFocusChangeListener(new OnFocusChangeListener() {
        @Override public void onFocusChange(View v, boolean hasFocus) {
          autoChangeRightIconVisibility();
        }
      });
      editText.addTextChangedListener(new SimpleTextWatcher() {
        @Override public void afterTextChanged(Editable s) {
          super.afterTextChanged(s);
          autoChangeRightIconVisibility();
        }
      });
      autoChangeRightIconVisibility();
    }
  }

  public void autoChangeRightIconVisibility() {
    if (autoHideRightIcon) {
      this.iconRight.setVisibility(
          editText.hasFocus() ? (editText.getText().length() == 0 ? GONE : VISIBLE) : GONE);
    } else {
      iconRight.setVisibility(View.VISIBLE);
    }
  }

  public void setAutoHideRightIcon(boolean autoHideRightIcon) {
    this.autoHideRightIcon = autoHideRightIcon;
    autoChangeRightIconVisibility();
  }

  /**
   * Set EditText default text.
   */
  public void setText(String text) {
    editText.setText(text);
  }

  public void setHint(String hint) {
    editText.setHint(hint);
  }

  /**
   * Get EditText current text.
   */
  public String getText() {
    return editText.getText().toString().trim();
  }

  /**
   * Set right icon click event.
   */
  public void setImageViewListener(View.OnClickListener listener) {
    iconRight.setOnClickListener(listener);
  }

  /**
   * Clear EditText current text.
   */
  public void clearText() {
    editText.setText("");
  }

  /**
   * Set EditText text watcher.
   */
  public void setTextWatcher(TextWatcher textWatcher) {
    editText.addTextChangedListener(textWatcher);
  }

  private void setEditTextStyle(Context context, int style) {
    if (style != 0) {
      editText.setTextAppearance(context, style);
    }
  }

  public void setInputFilters(InputFilter[] filters) {
    editText.setFilters(filters);
  }

  public ImageView getImageView() {
    return iconRight;
  }

  public EditText getEditText() {
    return editText;
  }
}
