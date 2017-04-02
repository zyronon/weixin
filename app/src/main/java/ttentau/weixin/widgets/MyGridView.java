package ttentau.weixin.widgets;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.bean.PhotoInfoMy;
import ttentau.weixin.uitls.UIUtils;


/**
 *
 * Created by ttent on 2017/3/4.
 */

public class MyGridView extends LinearLayout {
	private static String TAG = "MyGridView";

	private OnItemClickListener mOnItemClickListener;
	public static int MAX_WIDTH = 0;
	/**
	 * 长度 单位为Pixel
	 **/
	private int pxOneMaxWandH; // 单张图最大允许宽高
	private int pxMoreWandH = 0;// 多张图的宽高
	private int pxImagePadding = UIUtils.dip2px(3);// 图片间的间距
	private LayoutParams onePicPara;
	private LayoutParams morePara, moreParaColumnFirst;
	private LayoutParams rowPara;
	// 照片的Url列表
	private List<PhotoInfoMy> imagesList;
	private int MAX_PER_ROW_COUNT = 3;// 每行显示最大数

	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (MAX_WIDTH == 0) {
			int width = measureWidth(widthMeasureSpec);
			// int width = getMeasuredWidth();
			// Log.e(TAG,"width==="+width+">>>>>measureWidth===="+measuredWidth);
			if (width > 0) {
				MAX_WIDTH = width;
				if (imagesList != null && imagesList.size() > 0) {
					setList(imagesList);
				}
			}
		}
		// 这里也可以把super.omMeasure放在前面,然后再调用getMeasureWidth();得出宽度
		// 如果不把super.onMeasure放前面就调用getMeasureWidth()的话。第一次得不到宽度，会导致在listview中
		// （程序首次加载的时候）有些条目加载不出来，得向下滑才能加载出来
		// 这次的情况是（程序首次加载的时候）屏幕中除了第一条之外其他都不能加载出来。花了我半天时间
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// Log.e(TAG,"this is onMeasure++++"+MAX_WIDTH);
	}

	public void setList(List<PhotoInfoMy> lists) throws IllegalArgumentException {
		if (lists == null) {
			throw new IllegalArgumentException("imageList is null...");
		}
		imagesList = lists;

		if (MAX_WIDTH > 0) {
			pxMoreWandH = (MAX_WIDTH - pxImagePadding * 2) / 3; // 解决右侧图片和内容对不齐问题
			pxOneMaxWandH = MAX_WIDTH * 2 / 3;
			initImageLayoutParams();
		}
		// Log.e(TAG,"this is setList++++"+MAX_WIDTH);
		initView();
	}

	private void initView() {
		// Log.e(TAG,"this is initView++++"+MAX_WIDTH);
		setOrientation(VERTICAL);
		removeAllViews();
		if (MAX_WIDTH == 0) {
			// 为了触发onMeasure()来测量MultiImageView的最大宽度，MultiImageView的宽设置为match_parent
			addView(new View(getContext()));
			return;
		}
		if (imagesList == null || imagesList.size() == 0) {
			return;
		}
		if (imagesList.size() == 1) {
			addView(createImageView(0, false));
		} else {
			int allCount = imagesList.size();
			if (allCount == 4) {
				MAX_PER_ROW_COUNT = 2;
			} else {
				MAX_PER_ROW_COUNT = 3;
			}
			int rowCount = allCount / MAX_PER_ROW_COUNT + (allCount % MAX_PER_ROW_COUNT > 0 ? 1 : 0);// 行数
			for (int rowCursor = 0; rowCursor < rowCount; rowCursor++) {
				LinearLayout rowLayout = new LinearLayout(getContext());
				rowLayout.setOrientation(LinearLayout.HORIZONTAL);

				rowLayout.setLayoutParams(rowPara);
				if (rowCursor != 0) {
					rowLayout.setPadding(0, pxImagePadding, 0, 0);
				}

				int columnCount = allCount % MAX_PER_ROW_COUNT == 0 ? MAX_PER_ROW_COUNT : allCount % MAX_PER_ROW_COUNT;// 每行的列数

				if (rowCursor != rowCount - 1) {
					columnCount = MAX_PER_ROW_COUNT;
				}
				addView(rowLayout);

				int rowOffset = rowCursor * MAX_PER_ROW_COUNT;// 行偏移
				for (int columnCursor = 0; columnCursor < columnCount; columnCursor++) {
					int position = columnCursor + rowOffset;
					rowLayout.addView(createImageView(position, true));
				}
			}
		}
	}

	private ImageView createImageView(int position, final boolean isMultiImage) {
		PhotoInfoMy photoInfo = imagesList.get(position);
		ImageView iv = new ColorFilterImageView(getContext());
		iv.setOnClickListener(new ImageOnClickListener(position));
		iv.setBackgroundColor(getResources().getColor(R.color.im_font_color_text_hint));
		if (isMultiImage) {
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setLayoutParams(position % MAX_PER_ROW_COUNT == 0 ? moreParaColumnFirst : morePara);
			RequestManager with = Glide.with(getContext());
			if (UIUtils.isEmpty(photoInfo.path)) {
				with.load(photoInfo.url).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
			} else {
				with.load(photoInfo.path).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
			}
			return iv;
		} else {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(photoInfo.path, options);
			int width = options.outWidth;
			int height = options.outHeight;
			iv.setAdjustViewBounds(true);
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			float scale = (float) width / (float) height;
			width=MAX_WIDTH*2/3;
			height=(int) (width / scale);
			iv.setLayoutParams(new LayoutParams(width, height));
			Glide.with(getContext()).load(photoInfo.path).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
			return iv;
		}
	}

	private void initImageLayoutParams() {
		int wrap = LayoutParams.WRAP_CONTENT;
		int match = LayoutParams.MATCH_PARENT;

		onePicPara = new LayoutParams(wrap, wrap);

		moreParaColumnFirst = new LayoutParams(pxMoreWandH, pxMoreWandH);
		morePara = new LayoutParams(pxMoreWandH, pxMoreWandH);
		morePara.setMargins(pxImagePadding, 0, 0, 0);

		rowPara = new LayoutParams(match, wrap);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			// result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
			// + getPaddingRight();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}
	private class ImageOnClickListener implements OnClickListener {

		private int position;

		public ImageOnClickListener(int position) {
			this.position = position;
		}
		@Override
		public void onClick(View view) {
			if (mOnItemClickListener != null) {
				mOnItemClickListener.onItemClick(view, position);
			}
		}

	}
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}
}
