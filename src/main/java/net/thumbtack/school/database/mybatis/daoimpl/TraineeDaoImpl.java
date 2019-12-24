package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.TraineeDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TraineeDaoImpl extends DaoImplBase implements TraineeDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Override
    public Trainee insert(Group group, Trainee trainee) {
        LOGGER.debug("DAO insert Trainee {}", trainee);
        try (SqlSession sqlSession = getSession()) {
            try {
                if(group != null){
                    trainee.setGroupid(group.getId());
                }
                getTraineeMapper(sqlSession).insert(trainee);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Trainee {}, {}", trainee, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return trainee;
    }

    // получает Trainee по его ID. Если такого ID нет, метод возвращает null
    @Override
    public Trainee getById(int id)
    {
        LOGGER.debug("DAO get Trainee by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Trainee {}", ex);
            throw ex;
        }
    }

    public List<Trainee> getByGroup(Group group)
    {
        LOGGER.debug("DAO get trainees by Group {}", group);
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).getByGroup(group);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get trainees by Group {} {}", group , ex);
            throw ex;
        }
    }

    // получает список всех Trainee, независимо от их Group.
    // Если БД не содержит ни одного Trainee, метод возвращает пустой список
    @Override
    public List<Trainee> getAll()
    {
        LOGGER.debug("DAO get all Trainees ");
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get All Trainees", ex);
            throw ex;
        }
    }

    // изменяет Trainee в базе данных. Метод не изменяет принадлежности Trainee к Group
    public Trainee update(Trainee trainee)
    {
        LOGGER.debug("DAO update Trainee {}", trainee);
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).update(trainee);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't update Trainee {}", ex);
            throw ex;
        }
    }

    // получает список всех Trainee при условиях
    // если firstName не равен null - только имеющих это имя
    // если lastName не равен null - только имеющих эту фамилию
    // если rating не равен null - только имеющих эту оценку
    public List<Trainee> getAllWithParams(String firstName, String lastName, Integer rating)
    {
        LOGGER.debug("DAO get all Trainees with params {} {}", firstName, lastName, rating);
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).getAllWithParams(firstName, lastName, rating);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all Trainees with params {}", ex);
            throw ex;
        }
    }

    // вставляет список из Trainee в базу данных. Вставленные Trainee не принадлежат никакой группе
    public void batchInsert(List<Trainee> trainees)
    {
        LOGGER.debug("DAO batch insert trainees {}");
        try (SqlSession sqlSession = getSession()) {
                try {
                    getTraineeMapper(sqlSession).batchInsert(trainees);
                } catch (RuntimeException ex) {
                    LOGGER.info("Can't batch insert trainees {} {}", trainees, ex);
                    sqlSession.rollback();
                    throw ex;
                }
                sqlSession.commit();
            }
    }

    // удаляет Trainee из базы данных
    public void delete(Trainee trainee)
    {
        LOGGER.debug("DAO delete Trainee {} ", trainee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).delete(trainee);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Trainee {} {}", trainee, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    // удаляет все Trainee из базы данных
    public void deleteAll()
    {
        LOGGER.debug("DAO delete all trainees {} ");
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all trainees", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}