package schuelerdb.gui;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class ProgressAnimator<T> implements Runnable {
    private final Future<T> subject;
    private final DoubleProperty progress;

    public ProgressAnimator(Future<T> subject, DoubleProperty progress) {
        this.subject = subject;
        this.progress = progress;
    }

    @Override
    public void run() {
        while (!subject.isDone()) {
            setProgressLater(getNewProgress(0.8, 10));
            sleepAndCatch(75, 200);
        }
        for (int i = 0; i < 6; i++) {
            setProgressLater(getNewProgress(1,40));
            sleepAndCatch(20,100);
        }
        setProgressLater(1);
    }

    private void setProgressLater(double newProgress) {
        Platform.runLater(() -> progress.set(newProgress));
    }

    private void sleepAndCatch(int min, int max) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(min, max));
        } catch (InterruptedException ignored) {
        }
    }

    private double getNewProgress(double max, double rate) {
        double currentProgress = progress.get();
        return currentProgress + (max - currentProgress) * rate / 100.0;
    }
}
