package CloudProject;
import java.util.Random;
public class Crossover {
    private static final Random random = new Random();
    public static Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        int[] parent1Genes = parent1.getGenes();
        int[] parent2Genes = parent2.getGenes();
        int[] childGenes = new int[parent1Genes.length];
        int crossoverPoint = random.nextInt(parent1Genes.length);
        for (int i = 0; i < parent1Genes.length; i++) {
            if (i < crossoverPoint) {
                childGenes[i] = parent1Genes[i];
            } else {
                childGenes[i] = parent2Genes[i];
            }
        }
        return new Chromosome(childGenes);
    }
}