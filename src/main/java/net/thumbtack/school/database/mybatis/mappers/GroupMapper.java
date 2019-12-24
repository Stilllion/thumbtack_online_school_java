package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface GroupMapper
{
    @Insert("INSERT INTO grouptable (name, room, schoolid) VALUES "
            + "( #{name}, #{room}, #{schoolId} )")
    @Options(useGeneratedKeys = true)
    Integer insert(Group group);

    @Select("SELECT id, name, room, schoolid FROM grouptable WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getByGroup", fetchType = FetchType.LAZY)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getByGroup", fetchType = FetchType.LAZY))
    })
    Group getById(int id);

    @Select("SELECT * FROM grouptable WHERE schoolid = #{school.id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getByGroup", fetchType = FetchType.LAZY)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getByGroup", fetchType = FetchType.LAZY))
    })
    List<Group> getBySchool(@Param("school") School school);

    @Select("SELECT id, name, room, schoolid FROM grouptable")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getByGroup", fetchType = FetchType.LAZY)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getByGroup", fetchType = FetchType.LAZY))
    })
    List<Group> getAll();

    @Select({"UPDATE grouptable SET name = #{name}, room = #{room}, schoolid = #{schoolId} WHERE id = #{id}"})
    Group update(Group group);

    @Select({"UPDATE trainee SET groupid = #{group.id} where id = #{trainee.id}"})
    Trainee moveTraineeToGroup(@Param("trainee") Trainee trainee, @Param("group") Group group);

    @Select({"UPDATE trainee SET groupid = NULL where id = #{trainee.id}"})
    Trainee moveTraineeFromGroup(@Param("trainee") Trainee trainee);

    @Select({"INSERT into group_subject (groupid, subjectid) VALUES (#{group.id}, #{subject.id})"})
    void addSubjectToGroup(@Param("subject") Subject subject, @Param("group") Group group);

    @Delete("DELETE FROM grouptable WHERE id = #{group.id}")
    void delete(@Param("group") Group group);

    @Delete("DELETE FROM grouptable")
    void deleteAll();
}
