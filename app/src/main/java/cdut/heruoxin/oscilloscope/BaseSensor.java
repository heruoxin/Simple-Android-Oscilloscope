package cdut.heruoxin.oscilloscope;

/**
 * Created by heruoxin on 15/5/28.
 */
public class BaseSensor {

    protected boolean isPaused = true;

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public interface Callback {
        void onValueChanged(double value);
    }

}
