package ds;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private String name = "--";
    private String description;
    private User creator = null;
    private ArrayList<User> responsiblePeople = new ArrayList<>();
    private ArrayList<Category> subCategories = new ArrayList<>();
    private Category parentCategory;
    private ArrayList<Income> incomes = new ArrayList<>();
    private ArrayList<Expense> expenses = new ArrayList<>();

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public ArrayList<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(ArrayList<Income> incomes) {
        this.incomes = incomes;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public Category(String name, String description, User creator, ArrayList<User> responsiblePeople, ArrayList<Category> subCategories, Category parentCategory) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.responsiblePeople = responsiblePeople;
        this.subCategories = subCategories;
        this.parentCategory = parentCategory;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<User> getResponsiblePeople() {
        return responsiblePeople;
    }

    public void setResponsiblePeople(ArrayList<User> responsiblePeople) {
        this.responsiblePeople = responsiblePeople;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public String toString() {
        return "Category " + this.getName();
    }

    public double calculateTotalIncome() {
        //TODO: REIKIA PEREITI PER VISAS SUBKATEGORIJAS IR APSKAICIUOTI PRIDETI PRIE BENDRO
        double total = 0.0;
        for (Income income : incomes) {
            total += income.count;
        }
        return total;
    }

    public Category(String name, String description, ArrayList<User> responsiblePeople, ArrayList<Category> subCategories, Category parentCategory) {
        this.name = name;
        this.description = description;
        this.responsiblePeople = responsiblePeople;
        this.subCategories = subCategories;
        this.parentCategory = parentCategory;
    }

    public double calculateTotalExpenses() {
        //TODO: REIKIA PEREITI PER VISAS SUBKATEGORIJAS IR APSKAICIUOTI PRIDETI PRIE BENDRO
        double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.count;
        }
        return total;
    }

    public double getTotal() {
        return calculateTotalIncome() - calculateTotalExpenses();
    }
}
