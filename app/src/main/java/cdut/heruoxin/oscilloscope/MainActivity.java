package cdut.heruoxin.oscilloscope;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
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

        oscilloscopeStack = new OscilloscopeStack(mCubicValueLineChart, 20);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
