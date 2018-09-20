import entity.Developer;
import entity.DeveloperExample;
import mapper.DeveloperMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            DeveloperMapper developerMapper = sqlSession.getMapper(DeveloperMapper.class);
            DeveloperExample developerExample = new DeveloperExample();
            developerExample.createCriteria().andIdGreaterThan(0);
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Do you wish to add new developer into database?(y/n):");
            String operationStatus = reader.nextLine();
            while (operationStatus.equals("y")){
                Developer test = new Developer();
                System.out.println("Input id:");
                test.setId(reader.nextInt());
                reader.nextLine();
                System.out.println("Input name:");
                test.setName(reader.nextLine());
                System.out.println("Input speciality:");
                test.setSpecialty(reader.nextLine());
                System.out.println("Input salary:");
                test.setSalary(reader.nextInt());
                reader.nextLine();
                sqlSession.insert("mapper.DeveloperMapper.insert", test);
                System.out.println("Do you wish to add another developer into database?(y/n):");
                operationStatus = reader.nextLine();

            }
            /*Developer test = new Developer();
            test.setName("Michael");
            test.setSalary(2000);
            test.setSpecialty("Java");
            developerMapper.insertAutoID(test);*/
            List<Developer> allRecords = developerMapper.selectByExample(developerExample);
            for (Developer developer : allRecords) {
                System.out.println("---------------------------------");
                System.out.println("Developer id: " + developer.getId());
                System.out.println("Developer name: " + developer.getName());
                System.out.println("Developer speciality: " + developer.getSpecialty());
                System.out.println("Developer salary: " + developer.getSalary());
            }
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}
