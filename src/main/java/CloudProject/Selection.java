package CloudProject;
import java.util.List;
import java.util.Random;
public class Selection {
    private static final Random random = new Random();
    public static Chromosome selectParent(List<Chromosome> population) {
        Chromosome candidate1 =
                population.get(random.nextInt(population.size()));
        Chromosome candidate2 =
                population.get(random.nextInt(population.size()));
        if (candidate1.getFitness() >= candidate2.getFitness()) {
            return candidate1;
        } else {
            return candidate2;
        }
    }
}