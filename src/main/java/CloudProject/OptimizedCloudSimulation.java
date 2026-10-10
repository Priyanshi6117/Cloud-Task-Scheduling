package CloudProject;
import org.cloudsimplus.brokers.DatacenterBrokerSimple;
import org.cloudsimplus.core.CloudSimPlus;
import org.cloudsimplus.datacenters.Datacenter;
import org.cloudsimplus.datacenters.DatacenterSimple;
import org.cloudsimplus.hosts.Host;
import org.cloudsimplus.hosts.HostSimple;
import org.cloudsimplus.resources.Pe;
import org.cloudsimplus.resources.PeSimple;
import org.cloudsimplus.vms.Vm;
import org.cloudsimplus.vms.VmSimple;
import org.cloudsimplus.cloudlets.Cloudlet;
import org.cloudsimplus.cloudlets.CloudletSimple;
import java.util.ArrayList;
import java.util.List;
public class OptimizedCloudSimulation {
    public static void main(String[] args) {
        System.out.println("GENETIC ALGORITHM CLOUD SCHEDULING");
        int[] bestSchedule = GeneticAlgorithmScheduler.getBestSchedule();
        CloudSimPlus simulation = new CloudSimPlus();
        Datacenter datacenter = createDatacenter(simulation);
        DatacenterBrokerSimple broker =
                new DatacenterBrokerSimple(simulation);
        List<Vm> vmList = createVMs();
        List<Cloudlet> cloudletList = createCloudlets();
        broker.submitVmList(vmList);
        broker.submitCloudletList(cloudletList);
        for (int i = 0; i < cloudletList.size(); i++) {
            int vmIndex = bestSchedule[i];
            broker.bindCloudletToVm(
                    cloudletList.get(i),
                    vmList.get(vmIndex)
            );
        }
        System.out.println("\nBest Task Schedule:");
        for (int i = 0; i < bestSchedule.length; i++) {
            System.out.println(
                    "Task " + i +
                            " -> VM " +
                            bestSchedule[i]
            );
        }
        simulation.start();
        System.out.println("\nSIMULATION COMPLETED");
        ResultAnalyzer.printSchedule(bestSchedule);
    }
    private static Datacenter createDatacenter(
            CloudSimPlus simulation) {
        List<Host> hostList = new ArrayList<>();
        List<Pe> peList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            peList.add(new PeSimple(1000));
        }
        Host host = new HostSimple(
                8192,
                10000,
                1000000,
                peList
        );
        hostList.add(host);
        return new DatacenterSimple(
                simulation,
                hostList
        );
    }
    private static List<Vm> createVMs() {
        List<Vm> vmList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Vm vm = new VmSimple(1000, 1);
            vm.setRam(2048)
                    .setBw(1000)
                    .setSize(10000);
            vmList.add(vm);
        }
        return vmList;
    }
    private static List<Cloudlet> createCloudlets() {
        List<Cloudlet> cloudletList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Cloudlet cloudlet =
                    new CloudletSimple(10000, 1);
            cloudlet.setSizes(1024);
            cloudletList.add(cloudlet);
        }
        return cloudletList;
    }
}