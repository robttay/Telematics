import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class TelematicsService extends VehicleInfo {

    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public String toString() {
        return super.toString();
    }

    public void report(VehicleInfo vehicleInfo) {

        try {
            String vehicleInfoJson = mapper.writeValueAsString(vehicleInfo);
            try (FileWriter out = new FileWriter(vehicleInfo.getVIN() + ".json")) {
                out.write(vehicleInfoJson);
                out.flush();
            }
        } catch (JsonProcessingException e) {
            System.out.println("Caught JsonProcessingException " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught IOException " + e.getMessage());
        }


        List<VehicleInfo> viList = new ArrayList<>();

        File files = new File(".");

        for (File f : files.listFiles()) {
            if (f.getName().endsWith(".json")) {
                File file = new File(f.getName());
                try (Scanner scanner = new Scanner(file)) {
                    String vehicleInfoJson = scanner.nextLine();
                    vehicleInfo = mapper.readValue(vehicleInfoJson, VehicleInfo.class);
                    viList.add(vehicleInfo);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        double totalOdometer = 0;
        double totalConsumption = 0;
        double totalOilChange = 0;
        double totalEngine = 0;

        int k = 0;
        while (k < viList.size()) {
            totalOdometer = totalOdometer + viList.get(k).odometer;
            totalConsumption = totalConsumption + viList.get(k).consumption;
            totalOilChange = totalOilChange + viList.get(k).oilChange;
            totalEngine = totalEngine + viList.get(k).engine;
            System.out.println(
                    "\t" + viList.get(k).VIN + "\n" +
                            "\nTotal Odometer-" + k + ": " + totalOdometer);
            k++;

            double totalMpg = (totalOdometer - totalOilChange) / totalConsumption;

            int total = viList.size();
            String html = Dashboard.dashboardTop.replace("%count%", String.valueOf(total));
            html = html.replace("%odometer%", String.format("%.1f", totalOdometer / total));
            html = html.replace("%consumption%", String.format("%.1f", totalConsumption / total));
            html = html.replace("%lastoilchange%", String.format("%.1f", totalOilChange / total));
            html = html.replace("%enginesize%", String.format("%.1f", totalEngine / total));
            html = html.replace("%mpg%", String.format("%.1f", totalMpg / total));

            StringBuilder htmlPage = new StringBuilder(html);

            double eachMPG;

            for (VehicleInfo newVI : viList) {

                html = Dashboard.dashboardTable.replace("%vin%", String.valueOf(newVI.getVIN()));
                html = html.replace("%odometer%", String.valueOf(newVI.getOdometer()));
                html = html.replace("%consumption%", String.valueOf(newVI.getConsumption()));
                html = html.replace("%lastoilchange%", String.valueOf(newVI.getOilChange()));
                html = html.replace("%enginesize%", String.format("%.1f", Double.valueOf(newVI.getEngine())));
                eachMPG = (newVI.getOdometer() - newVI.getOilChange()) / newVI.getConsumption();
                html = html.replace("%mpg%", String.format("%.1f", eachMPG));

                htmlPage.append(html);
            }


            htmlPage.append(Dashboard.dashboardBottom);

            try (PrintWriter out = new PrintWriter("Dashboard.html")) {
                out.println(htmlPage.toString());
                out.flush();
            } catch (FileNotFoundException e) {
                System.out.println("Caught FileNotFoundException (could not write dashboard.html) " + e.getMessage());
            }

        }

    }
}

















