package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.mybatis.dao.SubjectDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class SubjectDaoImpl extends DaoImplBase implements SubjectDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDaoImpl.class);

    // вставляет Subject в базу данных.
    public Subject insert(Subject subject)
    {
        LOGGER.debug("DAO insert Subject {}", subject);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).insert(subject);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Subject {}, {}", subject, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return subject;
    }

    // получает Subject по его ID. Если такого ID нет, метод возвращает null
    public Subject getById(int id)
    {
        LOGGER.debug("DAO get Subject by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getSubjectMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Subject {}", ex);
            throw ex;
        }
    }

    public List<Subject> getByGroup(Group group)
    {
        LOGGER.debug("DAO get subjects by Group {}", group);
        try (SqlSession sqlSession = getSession()) {
            return getSubjectMapper(sqlSession).getByGroup(group);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get subjects by Group {} {}", group, ex);
            throw ex;
        }
    }

    // получает список всех Subject. Если БД не содержит ни одного Subject, метод возвращает пустой список
    public List<Subject> getAll()
    {
        LOGGER.debug("DAO get all Subjects {}");
        try (SqlSession sqlSession = getSession()) {
            return getSubjectMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all Subjects {}", ex);
            throw ex;
        }
    }

    // изменяет Subject  в базе данных
    public Subject update(Subject subject)
    {
        LOGGER.debug("DAO update Subject {}", subject);
        try (SqlSession sqlSession = getSession()) {
            return getSubjectMapper(sqlSession).update(subject);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't update Subject {}", ex);
            throw ex;
        }
    }

    // удаляет Subject из базы данных
    public void delete(Subject subject)
    {
        LOGGER.debug("DAO delete Subject {} ", subject);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).delete(subject);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Subject {} {}", subject, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    // удаляет все Subject из базы данных
    public void deleteAll()
    {
        LOGGER.debug("DAO delete all subjects {} ");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all Subjects", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
