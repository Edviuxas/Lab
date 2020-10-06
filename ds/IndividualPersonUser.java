package ds;

import java.io.Serializable;

public class IndividualPersonUser extends User implements Serializable {
    private String name;
    private String surename;
    private String emailAddress;

    public IndividualPersonUser() {

    }

    @Override
    public String toString() {
        return "PERSON: Vardas='" + name + '\'' +
                ", pavarde='" + surename + '\'' +
                ", elPastas='" + emailAddress + '\'' +
                ", id=" + id;
    }

    public IndividualPersonUser(String userName, String password, int id, String vardas, String pavarde, String elPastas) {
        super(userName, password, id);
        this.name = vardas;
        this.surename = pavarde;
        this.emailAddress = elPastas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
