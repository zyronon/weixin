package ttentau.weixin.ui.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RadioButton;

import ttentau.weixin.R;
import ttentau.weixin.uitls.UIUtils;


/**
 * Created by ttent on 2017/3/14.
 */

public class WxinRadioButton extends RadioButton {
	private static final int DEFAULT_ICON_WIDTH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30.0f,
			Resources.getSystem().getDisplayMetrics());

	private int iconHeight;
	private int mColor;
	private int iconWidth;
	private Drawable mDefocusDrawable;
	private Drawable mFocusDrawable;

	private Paint mFocusPaint;
	private Paint mTextPaint;
	private Paint mDefocusPaint;

	private int iconPadding;

	private Bitmap mDefocusBitmap;
	private Bitmap mFocusBitmap;
	private int mAlpha;
	private float mFontHeight;
	private float mTextWidth;

	public WxinRadioButton(Context context) {
		super(context);
		init(context, null);
	}

	public WxinRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public WxinRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		mFocusPaint = new Paint();
		mTextPaint = new Paint();
		mDefocusPaint = new Paint();
		mFocusPaint.setAntiAlias(true);
		mTextPaint.setAntiAlias(true);
		mDefocusPaint.setAntiAlias(true);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WxinRadioButton);
		mFocusDrawable = ta.getDrawable(R.styleable.WxinRadioButton_focus_icon);
		mDefocusDrawable = ta.getDrawable(R.styleable.WxinRadioButton_defocus_icon);
		iconWidth = ta.getDimensionPixelOffset(R.styleable.WxinRadioButton_icon_width, DEFAULT_ICON_WIDTH);
		iconHeight = ta.getDimensionPixelOffset(R.styleable.WxinRadioButton_icon_height, DEFAULT_ICON_WIDTH);
		mColor = ta.getColor(R.styleable.WxinRadioButton_focus_color, Color.BLUE);
		ta.recycle();

		setButtonDrawable(null);
		if (mDefocusDrawable != null) {
			setCompoundDrawablesWithIntrinsicBounds(null, mDefocusDrawable, null, null);
			mDefocusDrawable = getCompoundDrawables()[1];
		}
		if (mFocusDrawable == null || mDefocusDrawable == null) {
			throw new RuntimeException("'focus_icon' and 'defocus_icon' attribute should be defined");
		}

		mDefocusDrawable.setBounds(0, 0, iconWidth, iconHeight);
		mFocusDrawable.setBounds(0, 0, iconWidth, iconHeight);

		iconPadding = getCompoundDrawablePadding();

		Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
		mFontHeight = (float) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
		mTextWidth = StaticLayout.getDesiredWidth(getText(), getPaint());

		mDefocusBitmap = UIUtils.getBitmapFromDrawable(mDefocusDrawable,iconWidth, iconHeight);
		mFocusBitmap = UIUtils.getBitmapFromDrawable(mFocusDrawable,iconWidth, iconHeight);

		if (isChecked()) {
			mAlpha = 255;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawDefocusIcon(canvas);
		drawFocusIcon(canvas);
		drawDefocusText(canvas);
		drawFocusText(canvas);
	}

	private void drawDefocusIcon(Canvas canvas) {
		mDefocusPaint.setAlpha(255 - mAlpha);
		canvas.drawBitmap(mDefocusBitmap, (getWidth() - iconWidth) / 2, getPaddingTop(), mDefocusPaint);
	}

	private void drawFocusIcon(Canvas canvas) {
		mFocusPaint.setAlpha(mAlpha);
		canvas.drawBitmap(mFocusBitmap, (getWidth() - iconWidth) / 2, getPaddingTop(), mFocusPaint);
	}

	private void drawDefocusText(Canvas canvas) {
		mTextPaint.setColor(getCurrentTextColor());
		mTextPaint.setAlpha(255 - mAlpha);
		mTextPaint.setTextSize(getTextSize());
		canvas.drawText(getText().toString(), getWidth() / 2 - mTextWidth / 2,
				iconHeight + iconPadding + getPaddingTop() + mFontHeight, mTextPaint);
	}

	private void drawFocusText(Canvas canvas) {
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(mAlpha);
		canvas.drawText(getText().toString(), getWidth() / 2 - mTextWidth / 2,
				iconHeight + iconPadding + getPaddingTop() + mFontHeight, mTextPaint);
	}

	public void updateAlpha(float alpha) {
		mAlpha = (int) alpha;
		invalidate();
	}

	public void setRadioButtonChecked(boolean isChecked) {
		setChecked(isChecked);
		if (isChecked) {
			mAlpha = 255;
		} else {
			mAlpha = 0;
		}
		invalidate();
	}
}
