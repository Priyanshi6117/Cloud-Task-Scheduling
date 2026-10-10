package CloudProject;
import java.util.ArrayList;
import java.util.List;
public class GeneticAlgorithmScheduler {
    private static final int NUMBER_OF_TASKS = 8;
    private static final int DEFAULT_VMS = 4;
    private static final int POPULATION_SIZE = 20;
    private static final int NUMBER_OF_GENERATIONS = 10;
    public static void main(String[] args) {
        int[] bestSchedule = getBestSchedule(DEFAULT_VMS);
        System.out.println(
                "\n========== FINAL BEST SOLUTION =========="
        );
        System.out.println(
                "Best Task Schedule: " +
                        java.util.Arrays.toString(bestSchedule)
        );
    }
    public static int[] getBestSchedule() {
        return getBestSchedule(DEFAULT_VMS);
    }
    public static int[] getBestSchedule(int numberOfVms) {
        List<Chromosome> population =
                createInitialPopulation(numberOfVms);
        System.out.println("INITIAL POPULATION");
        for (int i = 0; i < population.size(); i++) {
            Chromosome chromosome = population.get(i);
            double fitness =
                    FitnessCalculator.calculateFitness(chromosome);
            chromosome.setFitness(fitness);
            System.out.println(
                    "Chromosome " + i + ": " +
                            java.util.Arrays.toString(
                                    chromosome.getGenes()
                            ) +
                            " | Fitness: " + chromosome.getFitness()
            );
        }
        for (int generation = 1;
             generation <= NUMBER_OF_GENERATIONS;
             generation++) {
            System.out.println(
                    "\n========== GENERATION " +
                            generation +
                            " =========="
            );
            population =
                    GeneticAlgorithm.createNextGeneration(
                            population,
                            numberOfVms
                    );
            for (int i = 0; i < population.size(); i++) {
                Chromosome chromosome = population.get(i);
                System.out.println(
                        "Chromosome " + i + ": " +
                                java.util.Arrays.toString(
                                        chromosome.getGenes()
                                ) +
                                " | Fitness: " +
                                chromosome.getFitness()
                );
            }
        }
        Chromosome bestChromosome =
                GeneticAlgorithm.getBestChromosome(population);
        System.out.println(
                "\n========== FINAL BEST SOLUTION =========="
        );
        System.out.println(
                "Best Schedule: " +
                        java.util.Arrays.toString(
                                bestChromosome.getGenes()
                        )
        );
        System.out.println(
                "Best Fitness: " +
                        bestChromosome.getFitness()
        );
        ResultAnalyzer.printSchedule(
                bestChromosome.getGenes()
        );
        ResultAnalyzer.printSummary(
                bestChromosome.getGenes(),
                bestChromosome.getFitness(),
                numberOfVms
        );
        return bestChromosome.getGenes();
    }
    private static List<Chromosome> createInitialPopulation(int numberOfVms) {
        List<Chromosome> population =
                new ArrayList<>();
        for (int i = 0;
             i < POPULATION_SIZE;
             i++) {
            Chromosome chromosome =
                    new Chromosome(
                            NUMBER_OF_TASKS,
                            numberOfVms
                    );
            double fitness =
                    FitnessCalculator.calculateFitness(chromosome);
            chromosome.setFitness(fitness);
            population.add(chromosome);
        }
        return population;
    }
}