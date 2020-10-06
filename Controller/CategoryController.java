package Controller;

import ds.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoryController {
    public static void manageCategories(BufferedReader br, FinanceManagementIS fmis, User user) {
        String categoryInput = "";
        while (!categoryInput.equals("exit")) {
            System.out.println("Choose an action:\n"
                    + "\t add - add a category\n"
                    + "\t upd - update a category\n"
                    + "\t addPers - add a responsible person\n"
                    + "\t addExp - add expenses\n"
                    + "\t addInc - add income\n"
                    + "\t addSub - add a subcategory\n"
                    + "\t printa - print all categories\n"
                    + "\t print - print information of a single category\n"
                    + "\t del - delete a category\n"
                    + "\t exit - exit this menu");
            try {
                categoryInput = br.readLine();
            } catch (IOException exception) {
                System.out.println("Error occured");
            }

            switch (categoryInput) {
                case "add":
                    addCategory(br, fmis, user);
                    break;
                case "upd":
                    updateCategory(br, fmis);
                    break;
                case "addPers":
                    addResponsiblePerson(br, fmis, user);
                    break;
                case "addExp":
                    addExpenses(br, fmis, user);
                    break;
                case "addInc":
                    addIncomes(br, fmis, user);
                    break;
                case "addSub":
                    addSubCategory(br, fmis, user);
                    break;
                case "printa":
                    printAllCategories(fmis.getCategories());
                    break;
                case "print":
                    printCategoryInformation(br, fmis);
                    break;
                case "del":
                    deleteCategory(fmis, br, user);
                    break;
                case "exit":
                    System.out.println("Exiting categories menu");
                    break;
                default:
                    System.out.println("There is no such option");
            }
        }
    }

    private static void printCategoryInformation(BufferedReader br, FinanceManagementIS fmis) {
        System.out.println("Enter the name of the category");
        try {
            String categoryName = br.readLine();
            Category category = getCategory(fmis.getCategories(), categoryName);
            if (category != null) {
                System.out.println("Name: " + categoryName
                        + "\n Description: " + category.getDescription()
                        + "\n Parent category: " + category.getParentCategory().getName()
                        + "\n Total income: " + category.calculateTotalIncome()
                        + "\n Total expenses: " + category.calculateTotalExpenses()
                        + "\n Total: " + category.getTotal());
            } else {
                System.out.println("Such category does not exist");
            }
        } catch (IOException exception) {
            System.out.println("Error occured");
        }
    }

    private static void addExpenses(BufferedReader br, FinanceManagementIS fmis, User user) {
        String categoryName = "";
        System.out.println("Enter the name of the category which you want to add expenses to:");
        try {
            categoryName = br.readLine();
        } catch (IOException exception) {
            System.out.println("Error occured");
        }

        Category category = getCategory(fmis.getCategories(), categoryName);

        if (category != null) {
            System.out.println("Enter expense data: {Description};{Ammount}");
            try {
                String[] expenseData = br.readLine().split(";");
                category.getExpenses().add(new Expense(expenseData[0], Double.parseDouble(expenseData[1])));
            } catch (IOException exception) {
                System.out.println("Error occured");
            }
            //addExpensesForReal(fmis.getCategories(), categoryName, new Expense(expenseData[0],Double.parseDouble(expenseData[1])));
        } else {
            System.out.println("Category not found");
        }
    }

    private static void addIncomes(BufferedReader br, FinanceManagementIS fmis, User user) {
        System.out.println("Enter the name of the category which you want to add incomes to:");
        try {
            String categoryName = br.readLine();
            Category category = getCategory(fmis.getCategories(), categoryName);

            if (category != null) {
                if (category.getCreator().equals(user) || category.getResponsiblePeople().indexOf(user) != -1)
                    System.out.println("Enter income data: {Description};{Ammount}");
                try {
                    String[] incomeData = br.readLine().split(";");
                    category.getIncomes().add(new Income(incomeData[0], Double.parseDouble(incomeData[1])));
                } catch (IOException exception) {
                    System.out.println("Error occured");
                }
            } else {
                System.out.println("Category not found");
            }
        } catch (IOException exception) {
            System.out.println("Error occured");
        }
    }

    private static void addResponsiblePerson(BufferedReader br, FinanceManagementIS fmis, User user) {
        System.out.println("Enter the name of the category you want to add a responsible person to");
        try {
            String categoryName = br.readLine();
            Category category = getCategory(fmis.getCategories(), categoryName);
            if (category != null) {
                if (fmis.getCategories().indexOf(category) != -1) { // jeigu gavom auksciausios kategorijos pavadinima
                    if (category.getCreator().equals(user)) {
                        System.out.println("Enter the id of the user you would like to add");
                        int inputID = 0;
                        try {
                            inputID = Integer.parseInt(br.readLine());
                        } catch (IOException exception) {
                            System.out.println("Error occured");
                        }
                        User addingUser = UserController.getUser(inputID, fmis);
                        if (addingUser != null) {
                            category.getResponsiblePeople().add(addingUser);
                            addResponsiblePersonToSubcategories(addingUser, category.getSubCategories());
                        } else {
                            System.out.println("User not found");
                        }
                    }
                } else {
                    System.out.println("You can only add responsible people to the categories that are at the top of category tree");
                }
            } else {
                System.out.println("Category not found");
            }
        } catch (IOException exception) {
            System.out.println("Error occured");
        }
    }

    private static void addResponsiblePersonToSubcategories(User addingUser, ArrayList<Category> layerOfCategories) {
        for (Category category : layerOfCategories) {
            category.getResponsiblePeople().add(addingUser);
            if (!category.getSubCategories().isEmpty()) {
                addResponsiblePersonToSubcategories(addingUser, category.getSubCategories());
            }
        }
    }

    private static void updateCategory(BufferedReader br, FinanceManagementIS fmis) {
        System.out.println("Enter the name of the category\n");
        try {
            String categoryName = br.readLine();
            Category category = getCategory(fmis.getCategories(), categoryName);

            if (category != null) {
                System.out.println("Enter new data for this category: {name};{description}");
                String[] newData = br.readLine().split(";");
                if (getCategory(fmis.getCategories(), newData[0]) == null) {
                    category.setName(newData[0]);
                    category.setDescription(newData[1]);
                } else {
                    System.out.println("This category name is already in use");
                }
            } else {
                System.out.println("Category not found");
            }
        } catch (IOException exception) {
            System.out.println("Error occured");
        }
    }

    private static void deleteCategory(FinanceManagementIS fmis, BufferedReader br, User user) {
        System.out.println("Enter the name of the category\n");
        try {
            String categoryName = br.readLine();
            Category category = getCategory(fmis.getCategories(), categoryName);

            if (category != null) {
                if (category.getParentCategory() == null) {
                    fmis.getCategories().remove(category);
                } else {
                    category.getParentCategory().getSubCategories().remove(category);
                }
            } else {
                System.out.println("Category not found");
            }
        } catch (IOException exception) {
            System.out.println("Error occured");
        }
    }

    private static void printAllCategories(ArrayList<Category> layerOfCategories) {
        layerOfCategories.forEach(category -> {
            System.out.println(category + " has these subcategories:");
            printSubCategories(category);
            printAllCategories(category.getSubCategories());
        });
    }

    private static void printSubCategories(Category category) {
        if (category.getSubCategories().stream().count() == 0) {
            System.out.println("NONE");
        } else {
            category.getSubCategories().forEach(subCategory -> {
                System.out.println(subCategory.toString());
            });
        }
    }

    private static void addSubCategory(BufferedReader br, FinanceManagementIS fmis, User user) {
        //TODO: SUBKATEGORIJOMS IS VISO NEREIKIA ATSAKINGU ASMENU??
        System.out.println("Enter the name of the parent category\n");
        String parentName = null;
        try {
            parentName = br.readLine();
        } catch (IOException exception) {
            System.out.println("Error occured");
        }

        Category parentCategory = getCategory(fmis.getCategories(), parentName);
        if (parentCategory != null) {
            //TODO: jei sukurei auksciau esancia subkategorija, turetum galet pasiekti ir zemiau esancias
            if (parentCategory.getResponsiblePeople().indexOf(user) != -1 || parentCategory.getCreator().equals(user)) {
                System.out.println("Add category info {name};{description}");
                try {
                    String[] enteredValues = br.readLine().split(";");
                    if (getCategory(fmis.getCategories(), enteredValues[0]) == null) {
                        ArrayList<User> responsiblePeople = parentCategory.getResponsiblePeople();
                        if (parentCategory.getCreator() != null) {
                            responsiblePeople.add(parentCategory.getCreator());
                        }
                        parentCategory.getSubCategories().add(new Category(enteredValues[0], enteredValues[1], responsiblePeople, new ArrayList<Category>(), parentCategory));
                    } else {
                        System.out.println("Category with this name already exists");
                    }
                } catch (IOException exception) {
                    System.out.println("Error occured");
                }
            } else {
                System.out.println("Since you are not responsible for this category you can not create sub categories");
            }
        } else {
            System.out.println("Such category does not exist");
        }
    }

    private static void addCategory(BufferedReader br, FinanceManagementIS fmis, User user) {
        System.out.println("Add category info {name};{description}");
        try {
            String[] enteredValues = br.readLine().split(";");

            for (Category cat : fmis.getCategories()) {
                System.out.println(cat.toString());
            }

            if (getCategory(fmis.getCategories(), enteredValues[0]) == null) {
                fmis.getCategories().add(new Category(enteredValues[0], enteredValues[1], user, new ArrayList<User>(), new ArrayList<Category>(), null));
            } else {
                System.out.println("Category with this name already exists");
            }
        } catch (IOException exception) {
            System.out.println("Error occured");
        }
    }

    private static Category getCategory(ArrayList<Category> layerOfCategories, String categoryName) {
        Category result;
        for (Category category : layerOfCategories) {
            if (category.getName().equals(categoryName)) {
                result = category;
            } else {
                result = getCategory(category.getSubCategories(),categoryName);
            }
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
