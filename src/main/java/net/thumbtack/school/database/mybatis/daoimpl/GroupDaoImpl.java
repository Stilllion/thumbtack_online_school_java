package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.GroupDao;
import net.thumbtack.school.database.mybatis.dao.SubjectDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl extends DaoImplBase implements GroupDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDaoImpl.class);

    // вставляет Group в базу данных, привязывая ее к School.
    public Group insert(School school, Group group)
    {
        LOGGER.debug("DAO insert Group {}", group);
        try (SqlSession sqlSession = getSession()) {
            try {
                if(school.getId() == 0){
                    throw new RuntimeException();
                }

                group.setSchoolId(school.getId());
                getGroupMapper(sqlSession).insert(group);

            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Group {}, {}", group, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return group;
    }

    // изменяет Group  в базе данных, принадлежность к School не меняется
    public Group update(Group group)
    {
        LOGGER.debug("DAO update Group {}", group);
        try (SqlSession sqlSession = getSession()) {
            return getGroupMapper(sqlSession).update(group);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't update Group {}", group, ex);
            throw ex;
        }
    }

    public List<Group> getBySchool(School school)
    {
        LOGGER.debug("DAO get groups by School {}", school);
        try (SqlSession sqlSession = getSession()) {
            return getGroupMapper(sqlSession).getBySchool(school);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get groups by School {} {}", school, ex);
            throw ex;
        }
    }

    // получает список всех Group, независимо от School
    public List<Group> getAll()
    {
        LOGGER.debug("DAO get all groups ");
        try (SqlSession sqlSession = getSession()) {
            return getGroupMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all groups {}", ex);
            throw ex;
        }
    }

    // удаляет Group  в базе данных, при этом все Trainee оказываеются не принадлежащими никакой Group
    public void delete(Group group)
    {
        LOGGER.debug("DAO delete Group {} ", group);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).delete(group);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Group {} {}", group, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    // переводит Trainee в Group. Если Trainee не принадлежал никакой Group, добавляет его в Group
    public Trainee moveTraineeToGroup(Group group, Trainee trainee)
    {
        LOGGER.debug("DAO move Trainee {} ", trainee, " to Group {}", group);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).moveTraineeToGroup(trainee, group);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't move Trainee {} ", trainee, " to Group {}", group, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }

        return trainee;
    }

    // удаляет Trainee из Group, после этого Trainee не принадлежит никакой Group
    public void deleteTraineeFromGroup(Trainee trainee)
    {
        LOGGER.debug("DAO delete from group Trainee {} ", trainee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).moveTraineeFromGroup(trainee);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete from group Trainee {} ", trainee, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    // добавляет Subject к Group
    public void addSubjectToGroup(Group group, Subject subject)
    {
        LOGGER.debug("DAO add Subject {} ", subject, " to Group {}", group);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).addSubjectToGroup(subject, group);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't move Trainee {} ", subject, " to Group {}", group, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}