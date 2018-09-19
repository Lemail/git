package developer;

public class DeveloperLogic {

    public int calculateHourRate(Developer developer) {
        return developer.getSalary() / 20 / 8;
    }

    public int calculateAnnualSalary(Developer developer) {
        return developer.getSalary() * 12;
    }
}
