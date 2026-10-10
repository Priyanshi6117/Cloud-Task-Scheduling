package CloudProject;
public class FitnessCalculator {
    public static double calculateFitness(Chromosome chromosome) {
        int[] genes = chromosome.getGenes();
        int numberOfVms = 0;
        for (int gene : genes) {
            if (gene + 1 > numberOfVms) {
                numberOfVms = gene + 1;
            }
        }
        int[] vmLoad = new int[numberOfVms];
        for (int gene : genes) {
            vmLoad[gene]++;
        }
        int maxLoad = vmLoad[0];
        for (int i = 1; i < numberOfVms; i++) {
            if (vmLoad[i] > maxLoad) {
                maxLoad = vmLoad[i];
            }
        }
        double fitness = 1.0 / maxLoad;
        chromosome.setFitness(fitness);
        return fitness;
    }
}