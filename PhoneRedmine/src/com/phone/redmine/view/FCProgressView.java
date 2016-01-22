package com.phone.redmine.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.phone.redmine.util.DisplayUtil;

/**
 * 
 * 自定义的显示百分比的View
 */
public class FCProgressView extends AppCompatTextView
{

	protected Paint	paint	= new Paint();
	private int		fullColor;				//底色
	private int		progressColor;			//进度条颜色
	private int		progress;				//进度
	private int		progressHeight;			//进度条的高度
	private int		height;					//View的高度

	public FCProgressView (Context context)
	{
		this(context, null);
	}

	public FCProgressView (Context context, AttributeSet attrs)
	{
		super(context, attrs);
		int defaultWidth = DisplayUtil.dip2px(context, 100);
		setWidth(defaultWidth);
		progressHeight = DisplayUtil.dip2px(context, 3);
		fullColor = Color.rgb(153, 153, 153);
		progressColor = Color.rgb(0, 199, 229);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (height == 0)
		{
			height = getHeight();
		}
		paint.setAntiAlias(true);
		paint.setStrokeWidth(progressHeight);
		paint.setColor(fullColor);
		canvas.drawLine(0, height / 2, getWidth() / 3 * 2, height / 2, paint);
		paint.setColor(progressColor);
		canvas.drawLine(0, height / 2, getWidth() / 3 * 2 * progress / 100, height / 2, paint);
	}

	/**
	 * 设置进度
	 * 
	 * @param p		百分比
	 */
	public void setProgress(int p)
	{
		if (progress < 0)
		{
			progress = 0;
		} else if (progress > 100)
		{
			progress = 100;
		} else
		{
			progress = p;
		}
		setText(p + "%");
	}

}
