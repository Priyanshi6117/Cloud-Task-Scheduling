package CloudProject;

public class AutoScaler {
    private static final double SCALE_UP_THRESHOLD = 0.80;
    private static final double SCALE_DOWN_THRESHOLD = 0.30;
    private static final int MIN_VMS = 2;
    private static final int MAX_VMS = 8;

    public static int determineVmCount(double utilization, int currentVmCount) {
        if (utilization > SCALE_UP_THRESHOLD && currentVmCount < MAX_VMS) {
            return currentVmCount + 1;
        }

        if (utilization < SCALE_DOWN_THRESHOLD && currentVmCount > MIN_VMS) {
            return currentVmCount - 1;
        }

        return currentVmCount;
    }
}