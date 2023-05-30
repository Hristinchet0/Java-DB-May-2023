import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private Scanner scanner;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        System.out.println("Select exercise number:");
        int exerciseNumber = 0;

        try {
            exerciseNumber = Integer.parseInt(scanner.nextLine());

            switch (exerciseNumber) {
                case 2:
                    exerciseTwoChangeCasing();
                    break;
                case 3:
                    exerciseThreeContainsEmployee();
                    break;
                case 4:
                    exerciseFourEmployeesWithSalaryOver50000();
                    break;
                case 5:
                    exerciseFiveEmployeesFromDepartment();
                    break;
                case 6:
                    exerciseSixAddingAndUpdate();
                    break;
                case 7:
                    exerciseSevenAddressWithEmployeeCount();
                    break;
                case 8:
                    exerciseEightGetEmployeeWithProject();
                    break;
                case 9:
                    exerciseNineFindLast10Projects();
                    break;
                case 10:
                    exerciseTenIncreaseSalaries();
                    break;
                case 11:
                    exerciseElevenFindEmployeesFirstName();
                    break;
                case 12:
                    exerciseTwelveFindEmployeesMaximumSalaries();
                    break;
                case 13:
                    exerciseThirteenRemoveTowns();
                    break;

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void exerciseElevenFindEmployeesFirstName() {
        System.out.println("Enter pattern:");
        System.out.println("Example: SA");
        String pattern = scanner.nextLine();

        List<Employee> employees = entityManager.createQuery(
                "SELECT e FROM Employee e " +
                        "WHERE e.firstName LIKE :p_pattern", Employee.class)
                .setParameter("p_pattern", pattern.concat("%"))
                .getResultList();

        for (Employee employee : employees) {
            System.out.printf("%s %s - %s - ($%.2f)%n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getJobTitle(),
                    employee.getSalary());
        }
    }

    private void exerciseNineFindLast10Projects() {
        List<Project> projects = entityManager.createQuery(
                "SELECT p FROM Project p " +
                        "ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();

        projects.stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    System.out.printf("Project name: %s%n", p.getName());
                    System.out.printf(" \tProject Description: %s%n", p.getDescription());
                    System.out.printf(" \tProject Start Date:%s%n", p.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s")));
                    System.out.printf(" \tProject End Date:%s%n", p.getEndDate() == null ? "null" : p.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s")));
                });
    }

    private void exerciseEightGetEmployeeWithProject() {
        System.out.println("Enter employee ID:");
        int employeeId = Integer.parseInt(scanner.nextLine());

        Employee employee = entityManager.find(Employee.class, employeeId);

        System.out.printf("%s %s - %s%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());

        employee.getProjects().stream()
                .map(Project::getName)
                .sorted(String::compareTo)
                .forEach(p -> System.out.printf("\t%s%n", p));
    }

    private void exerciseThirteenRemoveTowns() {
        System.out.println("Enter town name:");
        String townName = scanner.nextLine();

        Town town = entityManager.createQuery(
                        "SELECT t FROM Town t WHERE t.name = :t_name",
                        Town.class)
                .setParameter("t_name", townName)
                .getSingleResult();

        setEmployeeAddressToNull(townName);

        entityManager.getTransaction().begin();

        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a " +
                        "WHERE a.town.id = :p_id", Address.class)
                .setParameter("p_id", town.getId())
                .getResultList();

        addresses.forEach(entityManager::remove);

        entityManager.remove(town);
        entityManager.getTransaction().commit();

        System.out.printf("%d addresses in %s is deleted", addresses.size(), townName);

    }

    private void setEmployeeAddressToNull(String townName) {
        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.address.town.name = '" + townName +"'")
                .getResultList();

        entityManager.getTransaction().begin();

        for(Employee employee : employees) {
            employee.setAddress(null);
            entityManager.persist(employee);
        }
        entityManager.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    private void exerciseTwelveFindEmployeesMaximumSalaries() {
        entityManager.createQuery("SELECT e.department.name, MAX(e.salary) FROM Employee e " +
                        "GROUP BY e.department.id " +
                        "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000", Object[].class)
                .getResultList()
                .forEach(e -> System.out.println(e[0] + "  " + e[1]));

    }

    private void exerciseTenIncreaseSalaries() {

        entityManager.getTransaction().begin();

        int effectedRows = entityManager.createQuery(
                        "UPDATE Employee e " +
                                "SET e.salary = e.salary * 1.2 " +
                                "WHERE e.department.id IN :ids")
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();

        entityManager.getTransaction().commit();

        System.out.println(effectedRows);

    }

    private void exerciseSevenAddressWithEmployeeCount() {

        List<Address> addresses = entityManager.createQuery(
                        "SELECT a FROM Address a " +
                                "ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses.forEach(address -> {
            System.out.printf("%s, %s - %d employees%n",
                    address.getText(),
                    address.getTown() == null ? "Unknown" : address.getTown().getName(),
                    address.getEmployees().size());
        });

    }


    private void exerciseSixAddingAndUpdate() {
        System.out.println("Enter employee last name:");
        String lastName = scanner.nextLine();

        Employee employee = entityManager.createQuery(
                        "SELECT e FROM Employee e " +
                                "WHERE e.lastName = :l_name", Employee.class)
                .setParameter("l_name", lastName)
                .getSingleResult();

        Address address = createAddress("Vitoshka 15");

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();
    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();

        entityManager.persist(address);

        entityManager.getTransaction().commit();

        return address;
    }

    private void exerciseFiveEmployeesFromDepartment() {

        entityManager.createQuery(
                        "SELECT e FROM Employee e " +
                                "WHERE e.department.name = :d_name " +
                                "ORDER BY e.salary, e.id", Employee.class)
                .setParameter("d_name", "Research and Development")
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s from %s - $%.2f%n",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getDepartment().getName(),
                            employee.getSalary());
                });
    }

    private void exerciseFourEmployeesWithSalaryOver50000() {

        entityManager.createQuery(
                        "SELECT e FROM Employee e " +
                                "WHERE e.salary > :min_salary", Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000L))
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);

    }

    private void exerciseThreeContainsEmployee() {
        System.out.println("Enter Full Name:");

        String[] fullName = scanner.nextLine().split("\\s+");
        String firstName = fullName[0];
        String lastName = fullName[1];

        Long singleResult = entityManager.createQuery("SELECT count(e) FROM Employee e " +
                        " WHERE e.firstName = :f_name AND e.lastName = :l_name", Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();

        System.out.println(singleResult == 0 ? "No" : "Yes");

    }

    private void exerciseTwoChangeCasing() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                "UPDATE Town AS t " +
                        "SET t.name = upper(t.name) " +
                        "WHERE length(t.name) <= 5 ");

        System.out.println(query.executeUpdate());

        entityManager.getTransaction().commit();
    }
}
