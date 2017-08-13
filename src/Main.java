import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        TelematicsService telServ = new TelematicsService();
        VehicleInfo vehInfo = new VehicleInfo();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter VIN#:\r");
        String inputVIN = scanner.next();
        vehInfo.setVIN(Integer.parseInt(inputVIN));

        System.out.println("Please enter Current Odometer:\r");
        String inputOdometer = scanner.next();
        vehInfo.setOdometer(Double.parseDouble(inputOdometer));

        System.out.println("Please enter Gas Consumption:\r");
        String inputConsumption = scanner.next();
        vehInfo.setConsumption(Double.parseDouble(inputConsumption));

        System.out.println("Please enter mileage at last Oil Change:\r");
        String inputOilChange = scanner.next();
        vehInfo.setOilChange(Double.parseDouble(inputOilChange));

        System.out.println("Please enter Engine Liters:\r");
        String inputEngLiters = scanner.next();
        vehInfo.setEngine(Double.parseDouble(inputEngLiters));

        telServ.report(vehInfo);

    }

}

