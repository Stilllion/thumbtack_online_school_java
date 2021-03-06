package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SubjectMapper
{
    @Insert("INSERT INTO subject (name) VALUES ( #{name} )")
    @Options(useGeneratedKeys = true)
    Integer insert(Subject subject);

    @Select("SELECT id, name FROM subject WHERE id = #{id}")
    Subject getById(int id);


    @Select("SELECT * FROM subject WHERE id IN (SELECT subjectid FROM group_subject WHERE groupid = #{id});")
    List<Subject> getByGroup(Group group);

    @Select("SELECT id, name FROM subject")
    List<Subject> getAll();

    @Select({"UPDATE subject SET name = #{name}"})
    Subject update(Subject subject);

    @Delete("DELETE FROM subject WHERE id = #{id}")
    void delete(Subject subject);

    @Delete("DELETE FROM subject")
    void deleteAll();
}
