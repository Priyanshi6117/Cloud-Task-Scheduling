package CloudProject;
import java.util.List;
public class BestSolution {
    public static Chromosome findBest(
            List<Chromosome> population) {
        Chromosome best =
                population.get(0);
        for (Chromosome chromosome : population) {
            if (chromosome.getFitness() >
                    best.getFitness()) {
                best = chromosome;
            }
        }
        return best;
    }
}