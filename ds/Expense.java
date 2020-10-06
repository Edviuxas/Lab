package ds;

import java.io.Serializable;

public class Expense implements Serializable {
    String description;
    double count;

    public Expense(String description, double count) {
        this.description = description;
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
