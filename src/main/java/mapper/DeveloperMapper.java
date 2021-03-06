package mapper;

import entity.Developer;
import entity.DeveloperExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

public interface DeveloperMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    long countByExample(DeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    int deleteByExample(DeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    int insert(Developer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    int insertSelective(Developer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    List<Developer> selectByExample(DeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    Developer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Developer record, @Param("example") DeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Developer record, @Param("example") DeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Developer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table developers
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Developer record);
}