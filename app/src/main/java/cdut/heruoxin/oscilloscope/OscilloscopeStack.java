package cdut.heruoxin.oscilloscope;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by heruoxin on 15/5/27.
 */
public class OscilloscopeStack {
    private final int mColor;
    private final int mStackSize;
    private final ValueLineChart mCubicValueLineChart;
    private ArrayList<Float> decibelStack;

    public OscilloscopeStack(ValueLineChart mCubicValueLineChart, int color, int stackSize) {
        this.mColor = color;
        this.mStackSize = stackSize;
        this.mCubicValueLineChart = mCubicValueLineChart;
        this.decibelStack = new ArrayList<>();
        for (int i =0; i < stackSize; i++) {
            decibelStack.add(0f);
        }
    }

    public synchronized void addToStack(float decibel) {
        if (decibelStack.size() > mStackSize) {
            decibelStack.remove(0);
        }
        decibelStack.add(decibelStack.size(), decibel);

        refreshGraph();
    }

    private void refreshGraph() {
        mCubicValueLineChart.clearChart();
        final ValueLineSeries series = new ValueLineSeries();
        series.setColor(mColor);
        for (float i : decibelStack) {
            series.addPoint(new ValueLinePoint(String.valueOf(i), i));
        }
        mCubicValueLineChart.addSeries(series);
    }

}
