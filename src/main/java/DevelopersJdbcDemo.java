import java.sql.*;
import java.util.Scanner;

public class DevelopersJdbcDemo {
    /**
     * JDBC Driver and database url
     */
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    /**
     * User and Password
     */
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection;
        Statement statement;

        System.out.println("Registering JDBC driver...");

        Class.forName(JDBC_DRIVER);

        System.out.println("Creating database connection...");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

        connection.setAutoCommit(false);

        statement = connection.createStatement();

        String SQL;
        SQL = "SELECT * FROM developers";

        ResultSet resultSet = statement.executeQuery(SQL);

        System.out.println("Retrieving data from database...");
        System.out.println("\nDevelopers:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String specialty = resultSet.getString("specialty");
            int salary = resultSet.getInt("salary");

            System.out.println("\n================\n");
            System.out.println("id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Specialty: " + specialty);
            System.out.println("Salary: $" + salary);
        }
        System.out.println("Creating savepoint...");
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");

        String batchStatus;
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Do you wish to add new developer into database?(y/n):");
        batchStatus = reader.nextLine();


        while (batchStatus.equals("y")){
            try {
                System.out.println("Input id:");
                int id = reader.nextInt();
                reader.nextLine();
                System.out.println("Input name:");
                String name = reader.nextLine();
                System.out.println("Input salary:");
                int salary = reader.nextInt();
                reader.nextLine();
                System.out.println("Input speciality:");
                String specialty = reader.nextLine();

                SQL = "INSERT INTO developers VALUES ("+id+", '"+name+"', '"+specialty+"', "+salary+")";
                statement.addBatch(SQL);

                System.out.println("Do you wish to add another developer into database?(y/n):");
                batchStatus = reader.nextLine();
            } catch (SQLException e) {
                System.out.println("SQLException. Executing rollback to savepoint...");
                connection.rollback(savepointOne);
            }
        }
        try{
            statement.executeBatch();
            connection.commit();
        }
        catch (SQLException e){
            System.out.println("SQLException. Executing rollback to savepoint...");
            connection.rollback(savepointOne);
        }

        SQL = "SELECT * FROM developers";
        resultSet = statement.executeQuery(SQL);
        System.out.println("Retrieving data from database...");
        System.out.println("\nDevelopers:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String specialty = resultSet.getString("specialty");
            int salary = resultSet.getInt("salary");

            System.out.println("id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Specialty: " + specialty);
            System.out.println("Salary: $" + salary);
            System.out.println("\n================\n");
        }

        System.out.println("Closing connection and releasing resources...");
        reader.close();
        resultSet.close();
        statement.close();
        connection.close();
    }
}

