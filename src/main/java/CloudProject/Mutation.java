package CloudProject;
import java.util.Random;
public class Mutation {
    public static void mutate(Chromosome chromosome, int numberOfVMs) {
        Random random = new Random();
        int[] genes = chromosome.getGenes();
        int mutationPoint = random.nextInt(genes.length);
        genes[mutationPoint] = random.nextInt(numberOfVMs);
        chromosome.setGenes(genes);
    }
}