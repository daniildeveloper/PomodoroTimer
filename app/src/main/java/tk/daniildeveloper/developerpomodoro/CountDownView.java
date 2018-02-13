package tk.daniildeveloper.developerpomodoro;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

public class CountDownView extends TextView {

    /**
     * Time left
     */
    private long mUntil;
    private Handler mHandler;
    private Runnable mCallBack;
    private boolean mPaused, mRunning;
    private long left;

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHandler = new Handler();
        mCallBack = new Runnable() {
            @Override
            public void run() {
                if (mRunning && !mPaused) {
                    CountDownView.this.tick();
                    mHandler.postDelayed(mCallBack,1000);
                }
            }
        };
    }

    /**
     * Start for it, when startTime is currentTime
     *
     * @param durationMilis - duration
     */
    public void start(long durationMilis) {
        this.start(durationMilis, 0);
    }

    /**
     * Start
     *
     * @param durationMilis - complete duration
     * @param startTime     - time to start at
     */
    public void start(long durationMilis, long startTime) {
        // if startTime is null call current time
        startTime = (startTime == 0) ? System.currentTimeMillis() : startTime;
        mUntil = startTime + durationMilis;
        mPaused = false; // stop pause
        mRunning = true; // run countdown
        mHandler.postDelayed(mCallBack, 0);
    }

    /**
     * Run countdown
     */
    protected void tick() {
        long left = mUntil - System.currentTimeMillis();
        long min = Math.abs((long) (left / 60000));
        long sec = Math.abs((long) ((left - min) / 1000) % 60);
        setText((left < 0 ? "-" : "") + min + " " + sec);
    }

    public void stop() {
        setText("0:0");
        mRunning = false;
    }

    public void pause () {
        mPaused = true;
    }

    public void unpause() {
        mPaused = true;
        mHandler.postDelayed(mCallBack, 0);
    }

}
