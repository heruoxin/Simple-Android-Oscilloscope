package cdut.heruoxin.oscilloscope;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;

/**
 * Created by heruoxin on 15/5/27.
 */
public class OscilloscopeStack {
    private final int size;
    private final ValueLineChart mCubicValueLineChart;
    private ArrayList<Float> decibelStack;

    public OscilloscopeStack(ValueLineChart mCubicValueLineChart, int size) {
        decibelStack = new ArrayList<>();
        this.size = size;
        this.mCubicValueLineChart = mCubicValueLineChart;
        final ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
    }

    public ArrayList<Float> getDecibelStack() {
        return decibelStack;
    }

    public synchronized void addToStack(float decibel) {
        if (decibelStack.size() > size) {
            decibelStack.remove(0);
        }
        decibelStack.add(decibelStack.size(), decibel);

        refreshGraph();
    }

    private void refreshGraph() {
        mCubicValueLineChart.clearChart();
        final ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        for (float i : decibelStack) {
            series.addPoint(new ValueLinePoint(String.valueOf(i), i));
        }
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }

}
