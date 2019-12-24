package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.School;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface SchoolMapper
{
    @Insert("INSERT INTO school ( name, year) VALUES ( #{name}, #{year})")
    @Options(useGeneratedKeys = true)
    Integer insert(School school);

    @Select("SELECT id, name, year FROM school WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "groups", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.GroupMapper.getBySchool", fetchType = FetchType.LAZY))
    })
    School getById(int id);


    @Select({"UPDATE school SET name = #{name}, year = #{year}"})
    void update(School school);

    @Select("SELECT id, name, year FROM school")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "groups", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.GroupMapper.getBySchool", fetchType = FetchType.LAZY))
    })
    List<School> getAllLazy();

    @Delete("DELETE FROM school WHERE id = #{school.id}")
    void delete(@Param("school") School school);

    @Delete("DELETE FROM school")
    void deleteAll();
}
