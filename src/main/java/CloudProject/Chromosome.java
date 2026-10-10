package CloudProject;
import java.util.Random;
public class Chromosome {
    private int[] genes;
    private double fitness;
    public Chromosome(int numberOfTasks, int numberOfVMs) {
        genes = new int[numberOfTasks];
        Random random = new Random();
        for (int i = 0; i < numberOfTasks; i++) {
            genes[i] = random.nextInt(numberOfVMs);
        }
        fitness = 0;
    }
    public Chromosome(int[] genes) {
        this.genes = genes;
        this.fitness = 0;
    }
    public int[] getGenes() {
        return genes;
    }
    public void setGenes(int[] genes) {
        this.genes = genes;
    }
    public double getFitness() {
        return fitness;
    }
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    public Chromosome copy() {
        Chromosome copy = new Chromosome(genes.length, 1);
        int[] newGenes = new int[genes.length];
        for (int i = 0; i < genes.length; i++) {
            newGenes[i] = genes[i];
        }
        copy.setGenes(newGenes);
        copy.setFitness(fitness);
        return copy;
    }
    @Override
    public String toString() {
        return java.util.Arrays.toString(genes);
    }
}
