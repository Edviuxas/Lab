package Controller;

import ds.CompanyUser;
import ds.FinanceManagementIS;
import ds.IndividualPersonUser;
import ds.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class UserController {
    public static void manageUsers(BufferedReader br, FinanceManagementIS fmis) {
        String userInput = "";

        while (!userInput.equals("exit")) {
            System.out.println("Choose an action:\n"
                    + "\tadd - add a new user\n"
                    + "\tupd - update user data.\n"
                    + "\tdel - remove user from the system.\n"
                    + "\tprinta - print all users.\n"
                    + "\tprint - print specific user's info.\n"
                    + "\texit - return to main menu.\n");

            try {
                userInput = br.readLine();
            } catch (IOException exception) {
                System.out.println("Error occured");
            }
            switch (userInput) {
                case "add":
                    addUser(br, fmis);
                    break;
                case "upd":
                    updateUser(br, fmis);
                    break;
                case "printa":
                    printAllUsers(fmis);
                    break;
                case "print":
                    printUserInfo(br, fmis);
                    break;
                case "del":
                    deleteUser(br, fmis);
                    break;
                case "exit":
                    System.out.println("Exiting the menu");
                    break;
                default:
                    System.out.println("Read the options again.");
            }
        }
    }

    private static void updateUser(BufferedReader br, FinanceManagementIS fmis) {
        System.out.println("Enter userID of the user you would like to update");
        try {
            int userID = Integer.parseInt(br.readLine());
            User user = getUser(userID, fmis);
            if (user != null) {
                if (user instanceof IndividualPersonUser) {
                    System.out.println("Enter user's new data:{Username};{Password};{Name};{Surename};{Email address}");
                    String[] userDataInput = br.readLine().split(";");
                    int index = fmis.getAllUsers().indexOf(user);
                    fmis.getAllUsers().set(index, new IndividualPersonUser(userDataInput[0], userDataInput[1], user.getId(), userDataInput[2], userDataInput[3], userDataInput[4]));
                } else {
                    System.out.println("Enter user's new data:{Username};{Password};{Company name}");
                    String[] userDataInput = br.readLine().split(";");
                    int index = fmis.getAllUsers().indexOf(user);

                    System.out.println("Enter contact person's user ID");
                    int contactPersonID = Integer.parseInt(br.readLine());
                    User contactUser = getUser(contactPersonID, fmis);
                    if (contactUser instanceof IndividualPersonUser) {
                        if (contactUser != null) {
                            fmis.getAllUsers().set(index, new CompanyUser(userDataInput[0], userDataInput[1], user.getId(), userDataInput[2], (IndividualPersonUser) contactUser));
                        } else {
                            System.out.println("User not found");
                        }
                    } else {
                        System.out.println("You have to enter individual person's ID");
                    }
                }
            } else {
                System.out.println("User does not exist");
            }
        } catch (IOException exception) {
            System.out.println("Error occured");
        }
    }

    private static void printUserInfo(BufferedReader br, FinanceManagementIS fmis) {
        System.out.println("Enter user ID");
        try {
            int userID = Integer.parseInt(br.readLine());
            for (User user : fmis.getAllUsers()) {
                if (user.getId() == userID) {
                    System.out.println(user.toString());
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static void addUser(BufferedReader br, FinanceManagementIS fmis) {
        System.out.println("Enter COMPANY if you want to add company as a user or PERSON if you want to add a person");
        try {
            String input = br.readLine();
            System.out.println(input);
            if (input.equals("COMPANY")) {
                System.out.println("Enter user's data:{Username};{Password};{UserID};{Company name}");
                String[] userDataInput = br.readLine().split(";");
                if (getUser(Integer.parseInt(userDataInput[2]), fmis) == null) {
                    System.out.println("Enter contact person's user ID");
                    int contactPersonID = Integer.parseInt(br.readLine());
                    User contactUser = getUser(contactPersonID, fmis);
                    if (contactUser instanceof IndividualPersonUser) {
                        if (contactUser != null) {
                            fmis.getAllUsers().add(new CompanyUser(userDataInput[0], userDataInput[1], Integer.parseInt(userDataInput[2]), userDataInput[3], (IndividualPersonUser) contactUser));
                        } else {
                            System.out.println("User not found");
                        }
                    } else {
                        System.out.println("You have to enter individual person's ID");
                    }
                } else {
                    System.out.println("User with this ID already exists");
                }
            } else if (input.equals("PERSON")) {
                System.out.println("Enter user's data:{Username};{Password};{UserID};{Name};{Surename};{Email address}");
                String[] userDataInput = br.readLine().split(";");
                fmis.getAllUsers().add(new IndividualPersonUser(userDataInput[0], userDataInput[1], Integer.parseInt(userDataInput[2]), userDataInput[3], userDataInput[4], userDataInput[5]));
            } else {
                System.out.println("Entered an invalid option. You need to write PERSON or COMPANY");
            }
        } catch (IOException exception) {
            System.out.println("Error occured");
        }
    }

    private static void printAllUsers(FinanceManagementIS fmis) {
        for (User user : fmis.getAllUsers()) {
            System.out.println("User with username: " + user.getUserName() + ", userID: " + user.getId());
        }
    }

    public static User getUser(int id, FinanceManagementIS fmis) {
        for (User user : fmis.getAllUsers()) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    private static void deleteUser(BufferedReader br, FinanceManagementIS fmis) {
        int userID = -1;
        System.out.println("Enter id of the user you would like to remove from the system");
        try {
            userID = Integer.parseInt(br.readLine());
        } catch (IOException exception) {
            System.out.println("Error occured");
        }

        for (User user : fmis.getAllUsers()) {
            if (user.getId() == userID) {
                fmis.getAllUsers().remove(user);
                break;
                //TODO: panaikinti useri is visu kategoriju, uz kurias jis atsakingas???
            }
        }
    }
}
