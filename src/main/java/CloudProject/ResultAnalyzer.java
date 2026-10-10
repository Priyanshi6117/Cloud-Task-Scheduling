package CloudProject;
public class ResultAnalyzer {
    public static void printSchedule(int[] schedule) {
        System.out.println("\n========== TASK TO VM SCHEDULE ==========");
        for (int i = 0; i < schedule.length; i++) {
            System.out.println(
                    "Task " + i + " -> VM " + schedule[i]
            );
        }
    }
    public static void printSummary(
            int[] schedule,
            double fitness) {
        int numberOfVMs = getRequiredVmCount(schedule);
        printSummary(schedule, fitness, numberOfVMs);
    }
    public static void printSummary(
            int[] schedule,
            double fitness,
            int numberOfVMs) {
        System.out.println(
                "\n========== PERFORMANCE SUMMARY =========="
        );
        System.out.println(
                "Total Tasks: " + schedule.length
        );
        System.out.println(
                "Total VMs: " + numberOfVMs
        );
        System.out.println(
                "Best Fitness: " + fitness
        );
        int[] vmTaskCount = new int[numberOfVMs];
        for (int vm : schedule) {
            if (vm >= 0 && vm < numberOfVMs) {
                vmTaskCount[vm]++;
            }
        }
        System.out.println("\nTask Distribution:");
        for (int i = 0; i < numberOfVMs; i++) {
            System.out.println(
                    "VM " + i + " -> " +
                            vmTaskCount[i] + " Task(s)"
            );
        }
        System.out.println(
                "\n========================================="
        );
    }
    private static int getRequiredVmCount(int[] schedule) {
        int maxVm = 0;
        for (int vm : schedule) {
            if (vm > maxVm) {
                maxVm = vm;
            }
        }
        return maxVm + 1;
    }
}