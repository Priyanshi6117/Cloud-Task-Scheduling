package CloudProject;
public class PerformanceMetrics {
    private static final int DEFAULT_VMS = 4;
    public static void printMetrics(int[] schedule, String method) {
        printMetrics(schedule, method, getRequiredVmCount(schedule));
    }
    public static void printMetrics(int[] schedule, String method, int numberOfVms) {
        int[] vmLoad = calculateVmLoad(schedule, numberOfVms);
        int maxLoad = 0;
        int minLoad = schedule.length;
        int totalLoad = 0;
        for (int load : vmLoad) {
            totalLoad += load;
            if (load > maxLoad) {
                maxLoad = load;
            }
            if (load < minLoad) {
                minLoad = load;
            }
        }
        double averageLoad = (double) totalLoad / numberOfVms;
        double imbalance = calculateLoadImbalance(schedule, numberOfVms);
        double makespan = calculateMakespan(schedule, numberOfVms);
        System.out.println("\n========== " + method + " PERFORMANCE METRICS ==========");
        System.out.println("Average VM Load: " + averageLoad);
        System.out.println("Maximum VM Load: " + maxLoad);
        System.out.println("Minimum VM Load: " + minLoad);
        System.out.println("Load Imbalance: " + imbalance);
        System.out.println("Estimated Makespan: " + makespan);
        System.out.println("VM Load Distribution:");
        for (int i = 0; i < numberOfVms; i++) {
            System.out.println("VM " + i + " -> " + vmLoad[i] + " Task(s)");
        }
        System.out.println("================================================");
    }
    public static int[] calculateVmLoad(int[] schedule) {
        return calculateVmLoad(schedule, getRequiredVmCount(schedule));
    }
    public static int[] calculateVmLoad(int[] schedule, int numberOfVms) {
        int[] vmLoad = new int[numberOfVms];
        for (int vm : schedule) {
            if (vm >= 0 && vm < numberOfVms) {
                vmLoad[vm]++;
            }
        }
        return vmLoad;
    }
    public static double calculateLoadImbalance(int[] schedule) {
        return calculateLoadImbalance(schedule, getRequiredVmCount(schedule));
    }
    public static double calculateLoadImbalance(int[] schedule, int numberOfVms) {
        int[] vmLoad = calculateVmLoad(schedule, numberOfVms);
        double total = 0;
        for (int load : vmLoad) {
            total += load;
        }
        double average = total / numberOfVms;
        double imbalance = 0;
        for (int load : vmLoad) {
            imbalance += Math.abs(load - average);
        }
        return imbalance / numberOfVms;
    }
    public static double calculateMakespan(int[] schedule) {
        return calculateMakespan(schedule, getRequiredVmCount(schedule));
    }
    public static double calculateMakespan(int[] schedule, int numberOfVms) {
        int[] vmLoad = calculateVmLoad(schedule, numberOfVms);
        int maxLoad = 0;
        for (int load : vmLoad) {
            if (load > maxLoad) {
                maxLoad = load;
            }
        }
        return maxLoad * 10.0;
    }
    private static int getRequiredVmCount(int[] schedule) {
        int maxVm = DEFAULT_VMS - 1;
        for (int vm : schedule) {
            if (vm > maxVm) {
                maxVm = vm;
            }
        }
        return maxVm + 1;
    }
}