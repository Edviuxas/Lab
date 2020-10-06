package ds;

import java.io.Serializable;

public class CompanyUser extends User implements Serializable {
    private String name;
    private IndividualPersonUser contactPerson; // ???

    public CompanyUser() {

    }

    @Override
    public String toString() {
        return "COMPANY: Name='" + name + '\'' +
                ", id=" + id +
                ", contact person: " + contactPerson.getName();
    }

    public CompanyUser(String userName, String password, int id, String name, IndividualPersonUser contactPerson) {
        super(userName, password, id);
        this.name = name;
        this.contactPerson = contactPerson;
    }
}
