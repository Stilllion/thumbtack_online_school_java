package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface TraineeMapper
{
    @Insert("INSERT INTO trainee ( firstName, lastName, rating, groupid) VALUES "
            + "( #{firstName}, #{lastName}, #{rating}, #{groupid} )")
    @Options(useGeneratedKeys = true)
    Integer insert(Trainee trainee);

    @Insert({"<script>",
              "INSERT INTO trainee (firstName, lastName, rating) VALUES",
              "<foreach item='item' collection='list' separator=','>",
                 "( #{item.firstName}, #{item.lastName}, #{item.rating} )",
              "</foreach>",
             "</script>"})
    @Options(useGeneratedKeys = true)
    void batchInsert(@Param("list") List<Trainee> traineeList);

    @Select("SELECT id, firstName, lastName, rating, groupid FROM trainee WHERE id = #{id}")
    Trainee getById(int id);

    @Select("SELECT * FROM trainees WHERE groupid = #{group.id}")
    List<Trainee> getBySchool(Group group);

    @Select("SELECT * FROM trainee")
    List<Trainee> getAll();

    @Select({"UPDATE trainee SET firstName = #{firstName}, lastName = #{lastName}, " +
            "rating = #{rating}, groupid = #{groupid}"})
    Trainee update(Trainee trainee);


    @Select({"<script>",
                "SELECT id, firstname, lastname, rating FROM trainee",
                "<where>" ,
                    "<if test='firstName != null'> AND firstName like #{firstName}",
                    "</if>",
                    "<if test='lastName != null'> AND lastName like #{lastName}",
                    "</if>",
                    "<if test='rating != null'> AND rating like #{rating}",
                    "</if>",
                "</where>" ,
            "</script>"})
    List<Trainee> getAllWithParams(@Param("firstName")String firstName, @Param("lastName")String lastName, @Param("rating")Integer rating);

    @Select("SELECT * FROM trainee WHERE groupid = #{id}")
    List<Trainee> getByGroup(Group group);

    @Delete("DELETE FROM trainee WHERE id = #{trainee.id}")
    void delete(@Param("trainee") Trainee trainee);

    @Delete("DELETE FROM trainee")
    void deleteAll();
}