package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.SchoolDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SchoolDaoImpl extends DaoImplBase implements SchoolDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolDaoImpl.class);
    // вставляет School в базу данных.
    public School insert(School school)
    {
        LOGGER.debug("DAO insert School {}", school);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).insert(school);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert School {}, {}", school, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return school;
    }

    // получает School по его ID. Если такого ID нет, метод должен возвращать null
    public School getById(int id)
    {
        LOGGER.debug("DAO get School by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getSchoolMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get School by Id {} {}", id, ex);
            throw ex;
        }
    }

    // получает список всех School вместе с Group, Subject, и Trainee, используя LAZY подход.
    // Если БД не содержит ни одной School, метод должен возвращать пустой список
    public List<School> getAllLazy()
    {
        LOGGER.debug("DAO get all schools lazy ");
        try (SqlSession sqlSession = getSession()) {
            return getSchoolMapper(sqlSession).getAllLazy();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all schools lazy {}", ex);
            throw ex;
        }
    }

    // получает список всех School вместе с Group, Subject, и Trainee, используя JOIN подход.
    // Если БД не содержит ни одной School, метод должен возвращать пустой список
    public List<School> getAllUsingJoin()
    {
        LOGGER.debug("DAO get schools with groups, subjects and trainees using join ");
        try (SqlSession sqlSession = getSession()) {
            return sqlSession.selectList("net.thumbtack.school.database.mybatis.mappers.SchoolMapper.getAllUsingJoin");
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get schools with groups, subjects and trainees using join {}", ex);
            throw ex;
        }
    }

    // изменяет School  в базе данных
    public void update(School school)
    {
        LOGGER.debug("DAO update School {}", school);
        try (SqlSession sqlSession = getSession()) {
            getSchoolMapper(sqlSession).update(school);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't update School {} {}", school, ex);
            throw ex;
        }
    }

    // удаляет School  в базе данных, при этом удаляются все Group
    public void delete(School school)
    {
        LOGGER.debug("DAO delete School {} ", school);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).delete(school);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete School {} {}", school, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    // удаляет все School  в базе данных, при этом удаляются все Group для каждой School
    public void deleteAll()
    {
        LOGGER.debug("DAO delete all from school {} ");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all from school {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    // трансакционно вставляет School вместе с ее Group, со всеми Trainee этих Group, и привязывает все Subject для этих Group
    // предполагается, что сами Subject были вставлены раньше
    public School insertSchoolTransactional(School school2018)
    {
        LOGGER.debug("Transactional DAO Insert School {} ", school2018);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).insert(school2018);
                for (Group group : school2018.getGroups()) {
					
                    group.setSchoolId(school2018.getId());
                    getGroupMapper(sqlSession).insert(group);

                    for(Subject subject : group.getSubjects()){
                        getGroupMapper(sqlSession).addSubjectToGroup(subject, group);
                    }

                    for (Trainee trainee : group.getTrainees()) {
						trainee.setGroupid(group.getId());
                        getTraineeMapper(sqlSession).insert(trainee);
                    }
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't do transactional insert School {} {} ", school2018, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return school2018;
    }
}