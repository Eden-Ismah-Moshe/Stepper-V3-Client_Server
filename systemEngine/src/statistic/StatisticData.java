package statistic;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class StatisticData {
    private String name;
    private int timesRun;
    private Duration totalTime;

    public StatisticData(String name) {
        this.name = name;
        this.timesRun = 0 ;
        this.totalTime = Duration.ZERO;
    }

    public String getName() {
        return name;
    }

    public void incrementTimesRun() {
        timesRun++;
    }

    public void addToTotalTime(Duration time) {
        totalTime = totalTime.plus(time);
    }

    public int getTimesRun() {
        return timesRun;
    }

    public double getAverageTime() {
        return timesRun == 0 ? 0 : totalTime.toMillis() / timesRun;
    }
}