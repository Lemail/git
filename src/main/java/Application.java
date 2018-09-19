import entity.Developer;
import entity.DeveloperExample;
import entity.User;
import entity.UserExample;
import mapper.DeveloperMapper;
import mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUserNameEqualTo("0000");
            //List<User> allRecords = userMapper.selectByExample(userExample);
            //sqlSession.commit();
            /*for (User user : allRecords) {
                System.out.println("user id: " + user.getId());
                System.out.println("user name: " + user.getUserName());

            }*/
            DeveloperMapper developerMapper = sqlSession.getMapper(DeveloperMapper.class);
            DeveloperExample developerExample = new DeveloperExample();
            developerExample.createCriteria().andSpecialtyEqualTo("Java");
            List<Developer> allRecords = developerMapper.selectByExample(developerExample);
            for (Developer developer : allRecords) {
                System.out.println("---------------------------------");
                System.out.println("Developer id: " + developer.getId());
                System.out.println("Developer name: " + developer.getName());
                System.out.println("Developer speciality: " + developer.getSpecialty());
                System.out.println("Developer salary: " + developer.getSalary());
            }

        } finally {
            sqlSession.close();
        }
    }

}
