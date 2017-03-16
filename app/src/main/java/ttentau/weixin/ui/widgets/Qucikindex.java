package ttentau.weixin.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import ttentau.weixin.R;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/8.
 */

public class Qucikindex extends View {
	private String[] indexArr = {"↑","☆","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"};
	private Paint mPaint;
    private float mTextY;
	private onTextChangedLisenter mLisenter;
	private float mWidth;

	public Qucikindex(Context context) {
		super(context);
		initView();
	}

	public Qucikindex(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public Qucikindex(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}
	private void initView() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextSize(UIUtils.dip2px(13));
		mPaint.setColor(Color.BLACK);
		mPaint.setTextAlign(Paint.Align.CENTER);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mWidth = getMeasuredWidth();
		mTextY = getMeasuredHeight() *1f/ indexArr.length;
		for (int i = 0; i < indexArr.length; i++) {
			float x = mWidth/2;
			float y = mTextY + mTextY * i ;
			canvas.drawText(indexArr[i], x, y, mPaint);
		}
	}
	private int getTextHeight(String text) {
		// 获取文本的高度
		Rect bounds = new Rect();
		mPaint.getTextBounds(text, 0, text.length(), bounds);
		return bounds.height();
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
		float y ;
		int v ;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
				setBackgroundColor(getResources().getColor(R.color.transparent_ban1));
				 y = event.getY();
				 v = (int)(y /mTextY) ;
				if (mLisenter!=null&&v>=0&&v<indexArr.length){
					mLisenter.onDown(indexArr[v]);
				}
                break;
            case MotionEvent.ACTION_MOVE:
                 y = event.getY();
                 v = (int) (y/mTextY);
//                Log.e("M","this is y=="+y+"这是字母-----"+indexArr[v]);
				if (mLisenter!=null&&v>=0&&v<indexArr.length){
					mLisenter.onTextChanged(indexArr[v]);
				}
                break;
            case MotionEvent.ACTION_UP:
				setBackgroundColor(Color.TRANSPARENT);
				if (mLisenter!=null){
					mLisenter.onUP();
				}
			case MotionEvent.ACTION_CANCEL:
				setBackgroundColor(Color.TRANSPARENT);
				if (mLisenter!=null){
					mLisenter.onUP();
				}
                break;
        }
        return true;
    }
	public interface onTextChangedLisenter{
		public void  onTextChanged(String value);
		public void onDown(String value);
		public void onUP();
	}
	public void setOnTextChangedLisenter(onTextChangedLisenter lisenter){
		mLisenter = lisenter;
	}
}
