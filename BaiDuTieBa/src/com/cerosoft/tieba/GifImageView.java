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
 * <p>��Ŀ���ƣ�ImageViewGifDemo </p>  
 * <p>�����ƣ�GifImageView </p>
 * <p>��������   </p>
 * <p>�����ˣ�wuzq��zhongqianhit@163.com </p> 
 * <p>����ʱ�䣺2012-3-23 ����5:21:24 </p>	
 * <p>�޸��ˣ�wuzq��zhongqianhit@163.com </p>  
 * <p>�޸�ʱ�䣺2012-3-23 ����5:21:24 </p>
 * <p>�޸ı�ע��	</p>	
 * @version    
 **/

public class GifImageView extends ImageView {
	/**�Ƿ���gifͼƬ*/
	private boolean isGif = false;
	
	/**������*/
	private Movie mMovie;  
	
	/**gif������ʼʱ��*/
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
         
        //�������ļ��ж���gif��ֵ��������Movieʵ��  
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
			//��ǰʱ�� 
			long curTime = android.os.SystemClock.uptimeMillis();  
	        
			//�����һ֡����¼��ʼʱ��  
	        if(mMovieStart == 0){  
	            mMovieStart = curTime;  
	        }  
	        
	        if(mMovie != null){  
	        	//ȡ��������ʱ��  
	        	int duration = mMovie.duration();  
	
	            if(duration==0){
	            	duration = 1000;
	            }
	            
	            Log.e("GifImageView", "duration:"+duration);
	            
	            //�����Ҫ��ʾ�ڼ�֡  
	            int relTime = (int)((curTime - mMovieStart)% duration);  
	            
	            //����Ҫ��ʾ��֡������
	            mMovie.setTime(relTime);  
	            mMovie.draw(canvas, 0, 0);  
	              
	            //ǿ���ػ�    
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
			//���ļ����ķ�ʽ��ȡ�ļ�  
            mMovie = Movie.decodeStream(getResources().openRawResource(gifResId));  
            invalidate(); //ǿ��ˢ�£���ʵ����ִ������onDraw����
            return;
		}else{
			super.setImageBitmap(bitmap);
		}
	}
}
