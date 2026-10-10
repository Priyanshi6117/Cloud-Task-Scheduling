package CloudProject;
import java.util.ArrayList;
import java.util.List;
public class GeneticAlgorithm {
    public static List<Chromosome> createNextGeneration(
            List<Chromosome> population,
            int numberOfVMs) {
        List<Chromosome> newPopulation =
                new ArrayList<>();
        while (newPopulation.size() < population.size()) {
            Chromosome parent1 =
                    Selection.selectParent(population);
            Chromosome parent2 =
                    Selection.selectParent(population);
            Chromosome child =
                    Crossover.crossover(parent1, parent2);
            Mutation.mutate(child, numberOfVMs);
            double fitness =
                    FitnessCalculator.calculateFitness(child);
            child.setFitness(fitness);
            newPopulation.add(child);
        }
        return newPopulation;
    }
    public static Chromosome getBestChromosome(
            List<Chromosome> population
    ) {
        Chromosome best =
                population.get(0);
        for (Chromosome chromosome :
                population) {
            if (chromosome.getFitness() >
                    best.getFitness()) {
                best = chromosome;
            }
        }
        return best;
    }
}