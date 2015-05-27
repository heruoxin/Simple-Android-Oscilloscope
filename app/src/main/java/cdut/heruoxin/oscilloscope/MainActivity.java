package cdut.heruoxin.oscilloscope;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.eazegraph.lib.charts.ValueLineChart;


public class MainActivity extends ActionBarActivity {

    private OscilloscopeStack oscilloscopeStack;
    private AudioRecorder recorder;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);

        oscilloscopeStack = new OscilloscopeStack(
                mCubicValueLineChart,
                getResources().getColor(R.color.accent),
                18);

        recorder = new AudioRecorder(16, new AudioRecorder.Callback() {
            @Override
            public void onDecibelGot(final double decibel) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        oscilloscopeStack.addToStack((float) decibel);
                    }
                });
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                onAboutClick();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onAboutClick() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.action_about))
                .setMessage(getString(R.string.summary_about))
                .setPositiveButton(android.R.string.ok, null)
                .create().show();
    }

    public void mFabOnClick(final View view) {
        final boolean isPaused = recorder.isPaused();
        if (Build.VERSION.SDK_INT >= 12) {
            view.setRotation(0);
            view.animate().rotation(360).setDuration(600);
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((ImageButton) view).setImageResource(
                        isPaused ?
                                R.drawable.ic_action_av_pause :
                                R.drawable.ic_action_av_play
                );
            }
        }, 400);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isPaused) recorder.resume();
                else recorder.pause();
            }
        }, 200);
    }
}
