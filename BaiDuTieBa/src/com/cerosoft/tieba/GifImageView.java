package com.cerosoft.tieba;

import java.io.InputStream;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**   
 * <p>项目名称：ImageViewGifDemo </p>  
 * <p>类名称：GifImageView </p>
 * <p>类描述：   </p>
 * <p>创建人：wuzq，zhongqianhit@163.com </p> 
 * <p>创建时间：2012-3-23 下午5:21:24 </p>	
 * <p>修改人：wuzq，zhongqianhit@163.com </p>  
 * <p>修改时间：2012-3-23 下午5:21:24 </p>
 * <p>修改备注：	</p>	
 * @version    
 **/

public class GifImageView extends ImageView {
	/**是否是gif图片*/
	private boolean isGif = false;
	
	/**播放类*/
	private Movie mMovie;  
	
	/**gif播放起始时间*/
	private long mMovieStart;  
	
	/** 
	 * <p>Title: </p> 
	 * <p>Description: </p> 
	 * @param context 
	**/ 
	public GifImageView(Context context) {
		super(context);
		mMovie=null;  
        mMovieStart=0;  
		// TODO Auto-generated constructor stub
	}
	
	/** 
	 * <p>Title: </p> 
	 * <p>Description: </p> 
	 * @param context
	 * @param attrs 
	**/ 
	public GifImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mMovie=null;  
        mMovieStart=0;  
		// TODO Auto-generated constructor stub
	}
	
	/** 
	 * <p>Title: </p> 
	 * <p>Description: </p> 
	 * @param context
	 * @param attrs
	 * @param defStyle 
	**/ 
	public GifImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mMovie=null;  
        mMovieStart=0;  
         
        //从描述文件中读出gif的值，创建出Movie实例  
        /*TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.GIFView, defStyle, 0);  
         
        int srcID=a.getResourceId(R.styleable.GIFView_gif, 0);  
        if(srcID>0){  
            InputStream is = context.getResources().openRawResource(srcID);  
            mMovie = Movie.decodeStream(is);  
        }  
         
        a.recycle();  
		*/
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Log.e("GifImageView", "start:"+isGif);
		if(isGif){
			//当前时间 
			long curTime = android.os.SystemClock.uptimeMillis();  
	        
			//如果第一帧，记录起始时间  
	        if(mMovieStart == 0){  
	            mMovieStart = curTime;  
	        }  
	        
	        if(mMovie != null){  
	        	//取出动画的时长  
	        	int duration = mMovie.duration();  
	
	            if(duration==0){
	            	duration = 1000;
	            }
	            
	            Log.e("GifImageView", "duration:"+duration);
	            
	            //算出需要显示第几帧  
	            int relTime = (int)((curTime - mMovieStart)% duration);  
	            
	            //设置要显示的帧，绘制
	            mMovie.setTime(relTime);  
	            mMovie.draw(canvas, 0, 0);  
	              
	            //强制重绘    
	            invalidate();  
	        }  
		}else{
			super.onDraw(canvas);
		}
	}

	public void setImageResource(Bitmap bitmap, boolean isGifPic,int gifResId) {
		// TODO Auto-generated method stub
		isGif = isGifPic;
		
		if(isGif){
			//以文件流的方式读取文件  
            mMovie = Movie.decodeStream(getResources().openRawResource(gifResId));  
            invalidate(); //强制刷新，其实就是执行以下onDraw方法
            return;
		}else{
			super.setImageBitmap(bitmap);
		}
	}
}
