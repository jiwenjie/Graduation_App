package com.example.root.graduation_app.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/20
 * desc:
 * version:1.0
 */
public class MyEditText extends AppCompatEditText {

   private long lastTime = 0;

   public MyEditText(Context context) {
      super(context);
   }

   public MyEditText(Context context, AttributeSet attrs) {
      super(context, attrs);
   }

   public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   @Override
   protected void onSelectionChanged(int selStart, int selEnd) {
      super.onSelectionChanged(selStart, selEnd);
      this.setSelection(this.getText().length());
   }

   @Override
   public boolean onTouchEvent(MotionEvent event) {
      switch (event.getAction()) {
         case MotionEvent.ACTION_DOWN:
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime < 500) {
               lastTime = currentTime;
               return true;
            } else {
               lastTime = currentTime;
            }
            break;
      }
      return super.onTouchEvent(event);
   }
}
