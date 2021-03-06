package codes.knight.cellularautomata.Handler;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;

import codes.knight.cellularautomata.R;
import codes.knight.cellularautomata.View.LifeView;

/**
 * Created by Nathan on 11/23/2016.
 *  Handles touch events.
 */

public class InputHandler extends ScaleGestureDetector.SimpleOnScaleGestureListener implements GestureDetector.OnGestureListener,
        Button.OnClickListener, GestureDetector.OnDoubleTapListener {

//    VelocityTracker velocityTracker = null;
    private LifeView lifeView;
    private ScaleGestureDetector scaleGestureDetector;
    private final String LOG_TAG = this.getClass().getSimpleName();

    float lastTouchPositionY = 0;

    public InputHandler(final LifeView lifeView) {
        this.lifeView = lifeView;
        scaleGestureDetector = new ScaleGestureDetector(lifeView.getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                Log.d(LOG_TAG, "Scale factor: " + scaleGestureDetector.getScaleFactor());
                lifeView.field.setScale(1/scaleGestureDetector.getScaleFactor());
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                Log.d(LOG_TAG, "onScaleBegin");
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                Log.d(LOG_TAG, "onScaleEnd");
            }
        });
    }

    public boolean onRawTouch(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchPositionY = event.getY();
                Log.d(LOG_TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                lastTouchPositionY = 0;
                Log.d(LOG_TAG, "ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(LOG_TAG, "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float dx, float dy) {
        lifeView.field.adjustPanX(-dx);
        lifeView.field.adjustPanY(-dy);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(LOG_TAG, "onFling");
        return false;
    }

    @Override
    public void onClick(View view) {
        Log.d(LOG_TAG, "Button pressed");
        switch(view.getId()) {
            case R.id.buttonPausePlay:
                lifeView.lifeRunning = !lifeView.lifeRunning;
                break;
            case R.id.buttonFaster:
                lifeView.millisPerTick -= 100;
                if(lifeView.millisPerTick < 1) lifeView.millisPerTick = 1;
                break;
            case R.id.buttonSlower:
                lifeView.millisPerTick += 100;
                break;
            case R.id.buttonClear:
                lifeView.field.clear();
                break;
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        lifeView.field.toggleCell(motionEvent.getX(), motionEvent.getY());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        lifeView.lifeRunning = !lifeView.lifeRunning;
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }
}
