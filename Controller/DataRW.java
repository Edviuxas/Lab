package Controller;

import ds.FinanceManagementIS;

import java.io.*;
import java.util.Scanner;

public class DataRW {
    public static FinanceManagementIS loadFinanceManagementSystemFromFile(FinanceManagementIS fmis) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("fmis.file"));
            fmis = (FinanceManagementIS) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed with class recognition.");
        } catch (IOException e) {
            System.out.println("Failed to open file.");
        }
        return fmis;
    }

    public static void writeFinanceManagementSystemToFile(BufferedReader br, FinanceManagementIS fmis) {
        try {
            System.out.println("Enter file name:\n");
            String fileName = br.readLine();
            if (fileName.isEmpty()) {
                fileName = "fmis";
                System.out.println("Using default name for saving...");
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName + ".file"));
            out.writeObject(fmis);
            out.close();
        } catch (IOException e) {
            System.out.println("Failed to write.\n");
        }
    }
}
