package CloudProject;
public class AutoScalingTest {
    public static void main(String[] args) {
        int currentVms = 4;
        double[] utilizationLevels = {0.90, 0.60, 0.20, 0.85, 0.25};
        System.out.println("========== AUTOMATIC VM SCALING ==========");
        System.out.println("Initial VM Count: " + currentVms);
        for (double utilization : utilizationLevels) {
            int previousVms = currentVms;
            currentVms = AutoScaler.determineVmCount(utilization, currentVms);
            System.out.println(
                    "Utilization: " + (utilization * 100) + "%" +
                            " | VMs: " + previousVms +
                            " -> " + currentVms
            );
        }
        System.out.println("==========================================");
        System.out.println("Final VM Count: " + currentVms);
    }
}