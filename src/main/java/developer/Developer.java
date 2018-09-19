package developer;

import java.util.Currency;


public class Developer {
    private String firsName;

    private String lastName;

    private String specialty;

    private int salary;

    public Developer() {
    }

    public Developer(String firsName, String lastName, String specialty, int salary) {
        this.firsName = firsName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.salary = salary;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Firs Name: " + firsName +
                "\nLast Name: " + lastName +
                "\nSpecialty:" + specialty +
                "\nSalary: $" + salary / 100 + "." + salary % 100;
    }
}