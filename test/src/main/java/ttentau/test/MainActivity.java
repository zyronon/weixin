package ttentau.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private android.support.v4.view.ViewPager vp;
    private android.widget.RadioGroup rgroot;
    private android.widget.LinearLayout activitymain;
    private ImageView mIv1;
    private ImageView mIv2;
    private float mFirst=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mIv1 = (ImageView) findViewById(R.id.iv_1);
        mIv2 = (ImageView) findViewById(R.id.iv_2);
     /*   AlphaAnimation s=new AlphaAnimation(0,0.5f);
        s.setDuration(1);
        s.setFillAfter(true);
        s.start();
        mIv2.setAnimation(s);*/
//        mIv2.getBackground().setAlpha((int) 0.1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float density = getResources().getDisplayMetrics().widthPixels;
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float v = x / density;
                mIv2.setAlpha(v);
                break;

        }
        return super.onTouchEvent(event);
    }

}
