package CloudProject;
public class PerformanceComparison {
    public static void main(String[] args) {
        System.out.println("\n====================================================");
        System.out.println("   CLOUD SCHEDULING PERFORMANCE COMPARISON");
        System.out.println("====================================================");
        int[] basicSchedule = {0, 0, 0, 1, 1, 2, 2, 3};
        int currentVmCount = 4;
        double utilization = 1.0;
        int scaledVmCount = AutoScaler.determineVmCount(utilization, currentVmCount);
        System.out.println("\n========== AUTO SCALING ==========");
        System.out.println("Initial VMs: " + currentVmCount);
        System.out.println("Workload Utilization: " + (utilization * 100) + "%");
        System.out.println("Scaled VMs: " + scaledVmCount);
        System.out.println("\nRUNNING GENETIC ALGORITHM...");
        int[] optimizedSchedule = GeneticAlgorithmScheduler.getBestSchedule(scaledVmCount);
        double basicMakespan = PerformanceMetrics.calculateMakespan(basicSchedule, currentVmCount);
        double optimizedMakespan = PerformanceMetrics.calculateMakespan(optimizedSchedule, scaledVmCount);
        double basicImbalance = PerformanceMetrics.calculateLoadImbalance(basicSchedule, currentVmCount);
        double optimizedImbalance = PerformanceMetrics.calculateLoadImbalance(optimizedSchedule, scaledVmCount);
        System.out.println("\n================ BASIC SCHEDULING ================");
        System.out.println("Scheduling Method: FCFS");
        System.out.println("VM Count: " + currentVmCount);
        System.out.println("Task Schedule:");
        ResultAnalyzer.printSchedule(basicSchedule);
        PerformanceMetrics.printMetrics(basicSchedule, "Basic FCFS", currentVmCount);        System.out.println("\n========== AUTO SCALING + GENETIC ALGORITHM ==========");
        System.out.println("Scheduling Method: Genetic Algorithm");
        System.out.println("VM Count: " + scaledVmCount);
        System.out.println("Task Schedule:");
        ResultAnalyzer.printSchedule(optimizedSchedule);
        PerformanceMetrics.printMetrics(optimizedSchedule, "Auto Scaling + GA", scaledVmCount);        System.out.println("\n================ FINAL COMPARISON =================");
        System.out.printf("%-20s %-15s %-20s%n", "Metric", "FCFS", "Auto Scaling + GA");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-20s %-15d %-20d%n", "Total Tasks", basicSchedule.length, optimizedSchedule.length);
        System.out.printf("%-20s %-15d %-20d%n", "Total VMs", currentVmCount, scaledVmCount);
        System.out.printf("%-20s %-15.2f %-20.2f%n", "Makespan", basicMakespan, optimizedMakespan);
        System.out.printf("%-20s %-15.2f %-20.2f%n", "Load Imbalance", basicImbalance, optimizedImbalance);
        double makespanImprovement = ((basicMakespan - optimizedMakespan) / basicMakespan) * 100;
        double imbalanceImprovement;
        if (basicImbalance == 0) {
            imbalanceImprovement = 0;
        } else {
            imbalanceImprovement = ((basicImbalance - optimizedImbalance) / basicImbalance) * 100;
        }
        System.out.println("\n================ IMPROVEMENT RESULTS ================");
        System.out.printf("Makespan Improvement: %.2f%%%n", makespanImprovement);
        System.out.printf("Load Balance Improvement: %.2f%%%n", imbalanceImprovement);
        System.out.println("\n================ PROJECT CONCLUSION ================");
        if (makespanImprovement > 0) {
            System.out.println("The Auto Scaling + Genetic Algorithm approach reduced the makespan.");
        }
        if (optimizedImbalance < basicImbalance) {
            System.out.println("The Auto Scaling + Genetic Algorithm approach improved load distribution.");
        }
        System.out.println("Dynamic VM scaling combined with Genetic Algorithm scheduling");
        System.out.println("provides adaptive task allocation compared to traditional FCFS.");
        System.out.println("====================================================");
    }
}