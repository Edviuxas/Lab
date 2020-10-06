import Controller.CategoryController;
import Controller.DataRW;
import Controller.UserController;
import ds.Category;
import ds.FinanceManagementIS;
import ds.IndividualPersonUser;
import ds.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Scanner;

public class StartProgram {
    public static void main(String[] args) {

        FinanceManagementIS fmis = null;
        //FinanceManagementIS fmis = new FinanceManagementIS("Blablabla", LocalDate.now(), "V1.0.1");
        User person = new IndividualPersonUser();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //Scanner scanner = new Scanner(System.in);
        String input = "";

        fmis = DataRW.loadFinanceManagementSystemFromFile(fmis);

        if (fmis == null) {
            System.out.println("Enter finance management system data: {Name};{Version}");
            String[] fmisData;
            try {
                fmisData = br.readLine().split(";");
                fmis = new FinanceManagementIS(fmisData[0], LocalDate.now(), fmisData[1]);
            } catch (IOException exception) {
                System.out.println("Error occured");
            }
            //System.out.println(fmisData[1]);
        }

        while (!input.equals("exit")) {
            System.out.println("Choose your area of interest:\n"
                    + "\t cat - manage categories\n"
                    + "\t user - manage users\n"
                    + "\t save - save this system to a file\n"
                    + "\t exit - exit the system\n");
            try {
                input = br.readLine();
            } catch (IOException exception) {
                System.out.println("Error occured");
            }

            switch (input) {
                case "cat":
                    CategoryController.manageCategories(br, fmis, person);
                    break;
                case "user":
                    UserController.manageUsers(br, fmis);
                    break;
                case "save":
                    DataRW.writeFinanceManagementSystemToFile(br,fmis);
                    break;
                case "exit":
                    System.out.println("exiting");
                    break;
                default:
            }
        }
    }
}
