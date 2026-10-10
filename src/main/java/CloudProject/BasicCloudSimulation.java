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
public class BasicCloudSimulation {
    public static void main(String[] args) {
        System.out.println(
                "========== BASIC CLOUD SCHEDULING =========="
        );
        CloudSimPlus simulation = new CloudSimPlus();
        Datacenter datacenter =
                createDatacenter(simulation);
        DatacenterBrokerSimple broker =
                new DatacenterBrokerSimple(simulation);
        int currentVmCount = 4;
        List<Cloudlet> cloudletList = createCloudlets();
        double utilization = calculateWorkloadUtilization(cloudletList, currentVmCount);
        int numberOfVms = AutoScaler.determineVmCount(utilization, currentVmCount);
        System.out.println("Current VMs: " + currentVmCount);
        System.out.println("Workload Utilization: " + utilization);
        System.out.println("Required VMs: " + numberOfVms);
        List<Vm> vmList = createVMs(numberOfVms);
        int[] bestSchedule = GeneticAlgorithmScheduler.getBestSchedule(numberOfVms);
        broker.submitVmList(vmList);
        for (int i = 0; i < cloudletList.size(); i++) {
            int vmIndex = bestSchedule[i];
            broker.bindCloudletToVm(cloudletList.get(i), vmList.get(vmIndex));
        }
        System.out.println("\n========== GA TASK MAPPING ==========");
        for (int i = 0; i < cloudletList.size(); i++) {
            System.out.println(
                    "Task " + i +
                            " -> VM " + bestSchedule[i]
            );
        }
        broker.submitCloudletList(cloudletList);
        simulation.start();
        System.out.println(
                "\n========== BASIC SIMULATION COMPLETED =========="
        );
        System.out.println(
                "Total Tasks: " +
                        cloudletList.size()
        );
        System.out.println(
                "Total VMs: " +
                        vmList.size()
        );
        System.out.println(
                "Cloudlets Completed: " +
                        broker.getCloudletFinishedList().size()
        );
    }
    private static Datacenter createDatacenter(CloudSimPlus simulation) {
        List<Host> hostList = new ArrayList<>();
        List<Pe> peList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            peList.add(new PeSimple(1000));
        }
        Host host = new HostSimple(
                16384,
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
    private static List<Vm> createVMs(int numberOfVms) {
        List<Vm> vmList = new ArrayList<>();
        for (int i = 0; i < numberOfVms; i++) {
            Vm vm = new VmSimple(1000, 1);
            vm.setRam(2048)
                    .setBw(1000)
                    .setSize(10000);
            vmList.add(vm);
        }
        return vmList;
    }
    private static List<Cloudlet> createCloudlets() {
        List<Cloudlet> cloudletList =
                new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Cloudlet cloudlet =
                    new CloudletSimple(
                            10000,
                            1
                    );
            cloudlet.setSizes(1024);
            cloudletList.add(cloudlet);
        }
        return cloudletList;
    }
    private static double calculateWorkloadUtilization(List<Cloudlet> cloudletList, int currentVmCount) {
        double totalWorkload = 0;
        for (Cloudlet cloudlet : cloudletList) {
            totalWorkload += cloudlet.getLength();
        }
        double capacityPerVm = 20000;
        double totalCapacity = currentVmCount * capacityPerVm;
        double utilization = totalWorkload / totalCapacity;
        return Math.min(utilization, 1.0);
    }
}