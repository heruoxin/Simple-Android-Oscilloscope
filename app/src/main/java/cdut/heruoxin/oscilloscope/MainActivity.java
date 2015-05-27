package cdut.heruoxin.oscilloscope;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.eazegraph.lib.charts.ValueLineChart;


public class MainActivity extends ActionBarActivity {

    private OscilloscopeStack oscilloscopeStack;
    private AudioRecorder recorder;
    private Button mStart;
    private Button mStop;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);
        mStart = (Button) findViewById(R.id.button_start);
        mStop = (Button) findViewById(R.id.button_stop);

        oscilloscopeStack = new OscilloscopeStack(
                mCubicValueLineChart,
                getResources().getColor(R.color.accent),
                20);

        recorder = new AudioRecorder(20, new AudioRecorder.Callback() {
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

    public void onStartClick(View view) {
        mStart.setEnabled(false);
        mStop.setEnabled(true);
        recorder.resume();
    }

    public void onStopClick(View view) {
        mStart.setEnabled(true);
        mStop.setEnabled(false);
        recorder.pause();
    }

}
