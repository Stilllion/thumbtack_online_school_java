package net.thumbtack.school.database.jdbc;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcService
{
    // Добавляет Trainee в базу данных.
    public static void insertTrainee(Trainee trainee) throws SQLException
	{	
		String insertTraineeQuery = "insert into trainee values(?,?,?,?)";
        Connection con = JdbcUtils.getConnection();

        try (PreparedStatement stmtInsertTrainee = con.prepareStatement(insertTraineeQuery, Statement.RETURN_GENERATED_KEYS);) {			
		
            stmtInsertTrainee.setNull(1, java.sql.Types.INTEGER);
            stmtInsertTrainee.setString(2, trainee.getFirstName());
            stmtInsertTrainee.setString(3, trainee.getLastName());
            stmtInsertTrainee.setInt(4, trainee.getRating());

            stmtInsertTrainee.executeUpdate();
			
            ResultSet generatedKeys = stmtInsertTrainee.getGeneratedKeys();
			
            int idTrainee = 0;
			
            if (generatedKeys.next()) {
                idTrainee = generatedKeys.getInt((1));
				trainee.setId(idTrainee);
            } else {
                throw new SQLException("Creating Trainee failed, no ID obtained.");
            }
			
        } catch (SQLException e) {
            throw e;
        }
	}

    // Изменяет ранее записанный Trainee в базе данных. В случае ошибки выбрасывает SQLException.
    public static void updateTrainee(Trainee trainee) throws SQLException
	{
		String updateQuery = "update trainee set firstname = ?, lastname = ?, rating = ?";
        Connection con = JdbcUtils.getConnection();

        try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {				
		
            stmt.setString(1, trainee.getFirstName());
            stmt.setString(2, trainee.getLastName());
			stmt.setInt(3, trainee.getRating());
            stmt.executeUpdate();
			
        } catch (SQLException e) {
            throw e;
        }
	}

    // Получает Trainee  из базы данных по его ID, используя метод получения “по именам полей”. 
    public static Trainee getTraineeByIdUsingColNames(int traineeId) throws SQLException
	{
        String selectQuery = "select * from trainee where id = ?";
        Connection con = JdbcUtils.getConnection();
        
        Trainee retTrainee = null;
		
		try (PreparedStatement stmt = con.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);) {
		
			stmt.setInt(1, traineeId);
		
			ResultSet rs = stmt.executeQuery();
			
            if(rs.next()){
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int rating = rs.getInt("rating");

                retTrainee = new Trainee(id, firstName, lastName, rating);
            }

        } catch (SQLException e) {
            throw e;
        }

        return retTrainee;
	}

    // Получает Trainee  из базы данных по его ID, используя метод получения “по номерам полей”.
    public static Trainee getTraineeByIdUsingColNumbers(int traineeId) throws SQLException
	{
		String selectQuery = "select * from trainee where id = ?";
        Connection con = JdbcUtils.getConnection();
        Trainee retTrainee = null;

		try (PreparedStatement stmt = con.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);) {
		
			stmt.setInt(1, traineeId);
		
			ResultSet rs = stmt.executeQuery();
			
            if(rs.next()){
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                int rating = rs.getInt(4);

                retTrainee = new Trainee(id, firstName, lastName, rating);
            }
        } catch (SQLException e) {
            throw e;
        }

        return retTrainee;
	}

    // Получает все Trainee  из базы данных, используя метод получения “по именам полей”.
    public static List<Trainee> getTraineesUsingColNames() throws SQLException
	{		
		List<Trainee> retTrainees = new ArrayList<>();
		
		String selectQuery = "select * from trainee";
        Connection con = JdbcUtils.getConnection();
		
    	try (PreparedStatement stmt = con.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);) {
		
			
			ResultSet rs = stmt.executeQuery();
			
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int rating = rs.getInt("rating");
				
                Trainee trainee = new Trainee(id, firstName, lastName, rating);
				retTrainees.add(trainee);
            }
        } catch (SQLException e) {
            throw e;
        }
		
		return retTrainees;
	}

    // Получает все Trainee  из базы данных, используя метод получения “по номерам полей”. 
    public static List<Trainee> getTraineesUsingColNumbers() throws SQLException
	{
		List<Trainee> retTrainees = new ArrayList<>();
		
		String selectQuery = "select * from trainee";
        Connection con = JdbcUtils.getConnection();
		
      	try (PreparedStatement stmt = con.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);) {
		
			ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                int rating = rs.getInt(4);
				
                Trainee trainee = new Trainee(id, firstName, lastName, rating);
				retTrainees.add(trainee);
				
            }
        } catch (SQLException e) {
            throw e;
        }
		
		return retTrainees;
	}

    // Удаляет Trainee из базы данных.
    public static void deleteTrainee(Trainee trainee) throws SQLException
	{
		String deleteQuery = "delete from trainee where id = ?";
        Connection con = JdbcUtils.getConnection();

        try (PreparedStatement stmt = con.prepareStatement(deleteQuery)) {
			
            stmt.setInt(1, trainee.getId());
            stmt.executeUpdate();
			
        } catch (SQLException e) {
            throw e;
        }
	}

    // Удаляет все Trainee из базы данных
    public static void deleteTrainees() throws SQLException
	{
		String deleteQuery = "delete from trainee";
        Connection con = JdbcUtils.getConnection();

		try (PreparedStatement stmt = con.prepareStatement(deleteQuery)) {
			
			stmt.executeUpdate();
			
        } catch (SQLException e) {
            throw e;
        }	
	}

    // Добавляет Subject в базу данных
    public static void insertSubject(Subject subject) throws SQLException
	{
		String insertQuery = "insert into subject values(?,?)";
        Connection con = JdbcUtils.getConnection();

        try (PreparedStatement stmtInsertTrainee = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);) {

            stmtInsertTrainee.setNull(1, java.sql.Types.INTEGER);
            stmtInsertTrainee.setString(2, subject.getName());
            
            stmtInsertTrainee.executeUpdate();
			
            ResultSet generatedKeys = stmtInsertTrainee.getGeneratedKeys();
			
            int idSubject = 0;
			
            if (generatedKeys.next()) {
                idSubject = generatedKeys.getInt((1));
				subject.setId(idSubject);
            } else {
                throw new SQLException("Creating Subject failed, no ID obtained.");
            }
			
        } catch (SQLException e) {
            throw e;
        }
	}

    // Получает Subject  из базы данных по его ID, используя метод получения “по именам полей”.
    public static Subject getSubjectByIdUsingColNames(int subjectId) throws SQLException
	{
		String selectQuery = "select * from subject where id = ?";
        Connection con = JdbcUtils.getConnection();
        
        Subject subject = null;

      		try (PreparedStatement stmt = con.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);) {
		
			stmt.setInt(1, subjectId);
		
			ResultSet rs = stmt.executeQuery();
			
            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
               
                subject = new Subject(id, name);
            }

        } catch (SQLException e) {
            throw e;
        }

        return subject;
	}

    // Получает Subject  из базы данных по его ID, используя метод получения “по номерам полей”.
    public static Subject getSubjectByIdUsingColNumbers(int subjectId) throws SQLException
	{
		String selectQuery = "select * from subject where id = ?";
        Connection con = JdbcUtils.getConnection();
        
        Subject subject = null;

		try (PreparedStatement stmt = con.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);) {
		
			stmt.setInt(1, subjectId);
		
			ResultSet rs = stmt.executeQuery();
			
            if(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
               
                subject = new Subject(id, name);
            }

        } catch (SQLException e) {
            throw e;
        }

        return subject;
	}

    // Удаляет все Subject из базы данных.
    public static void deleteSubjects() throws SQLException
	{
		String deleteQuery = "delete from subject";
        Connection con = JdbcUtils.getConnection();

        try (PreparedStatement stmt = con.prepareStatement(deleteQuery, Statement.RETURN_GENERATED_KEYS);) {
			stmt.executeUpdate(deleteQuery);
			
        } catch (SQLException e) {
            throw e;
        }
	}

    // Добавляет School в базу данных
    public static void insertSchool(School school) throws SQLException
	{
		String insertQuery = "insert into school values(?,?,?)";
        Connection con = JdbcUtils.getConnection();

        try (PreparedStatement stmtInsert = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);) {
			
            stmtInsert.setNull(1, java.sql.Types.INTEGER);
            stmtInsert.setString(2, school.getName());
            stmtInsert.setInt(3, school.getYear());
            
            stmtInsert.executeUpdate();
			
            ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
			
            int idSchool = 0;
			
            if (generatedKeys.next()) {
                idSchool = generatedKeys.getInt((1));
				school.setId(idSchool);
            } else {
                throw new SQLException("Creating Subject failed, no ID obtained.");
            }
			
        } catch (SQLException e) {
            throw e;
        }	
	}

    // Получает School  из базы данных по ее ID, используя метод получения “по именам полей”.
    public static School getSchoolByIdUsingColNames(int schoolId) throws SQLException
	{
		String selectQuery = "select * from school where id = ?";
        Connection con = JdbcUtils.getConnection();
        
        School school = null;

        try (PreparedStatement stmtSelect = con.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);) {
			
			stmtSelect.setInt(1, schoolId);
			
			ResultSet rs = stmtSelect.executeQuery();
			
            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int date = rs.getInt("year");
               
                school = new School(id, name, date);
            }

        } catch (SQLException e) {
            throw e;
        }

        return school;
	}

    // Получает School  из базы данных по ее ID, используя метод получения “по номерам полей”. 
	// Если School с таким ID нет, возвращает null.
    public static School getSchoolByIdUsingColNumbers(int schoolId) throws SQLException
	{
		String selectQuery = "select * from school where id = ?";
        Connection con = JdbcUtils.getConnection();
        
        School school = null;

        try (PreparedStatement stmt = con.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);) {
		
			stmt.setInt(1, schoolId);
		
			ResultSet rs = stmt.executeQuery();
			
            if(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int date = rs.getInt(3);
               
                school = new School(id, name, date);
            }

        } catch (SQLException e) {
            throw e;
        }

        return school;
	}

    // Удаляет все School из базы данных. Если список Group в School не пуст, удаляет все Group для каждой School.
    public static void deleteSchools() throws SQLException
	{
		String deleteQuery = "delete from school";
        Connection con = JdbcUtils.getConnection();

        try (PreparedStatement stmt = con.prepareStatement(deleteQuery)) {
			stmt.executeUpdate();
			
        } catch (SQLException e) {
            throw e;
        }
	}
	
    // Добавляет Group в базу данных, устанавливая ее принадлежность к школе School.
    public static void insertGroup(School school, Group group) throws SQLException
	{
		String insertGroupQuery = "insert into grouptable values(?,?,?,?)";
        Connection con = JdbcUtils.getConnection();

        try  {
            PreparedStatement stmtInsert = con.prepareStatement(insertGroupQuery, Statement.RETURN_GENERATED_KEYS);
			
            stmtInsert.setInt(1, group.getId());
            stmtInsert.setString(2, group.getName());
            stmtInsert.setString(3, group.getRoom());
            stmtInsert.setInt(4, school.getId());
			
			stmtInsert.executeUpdate();		

            ResultSet generatedKeys = stmtInsert.getGeneratedKeys();

            int idGroup;

            if (generatedKeys.next()) {
                idGroup = generatedKeys.getInt((1));
                group.setId(idGroup);
            } else {
                throw new SQLException("Creating Subject failed, no ID obtained.");
            }
			
			
        } catch (SQLException e) {
            throw e;
        }	
	}

    // Получает School по ее ID вместе со всеми ее Group из базы данных. 
    public static School getSchoolByIdWithGroups(int id) throws SQLException
	{
        Connection con = JdbcUtils.getConnection();
		String selectQuery = "SELECT * FROM grouptable JOIN school ON schoolid = school.id";
        
        School school = null;
		Group group = null;

		try( PreparedStatement stmtSelect = con.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);)
        {
			ResultSet rs = stmtSelect.executeQuery();
			
			while(rs.next()){
				
				if(school == null){
					
					String schoolName = rs.getString("school.name");
					int year = rs.getInt("year");
					school = new School(id, schoolName, year);
				}
				
				int groupId = rs.getInt("grouptable.id");
				String groupName = rs.getString("grouptable.name");
				String room = rs.getString("room");
				
				group = new Group(groupId, groupName, room);
				
				school.getGroups().add(group);
			}
          
        } catch (SQLException e) {
            throw e;
        }

        return school;
	}

    // Получает список всех School вместе со всеми их Group из базы данных.
    public static List<School> getSchoolsWithGroups() throws SQLException
	{
        Connection con = JdbcUtils.getConnection();
		String selectQuery = "SELECT * FROM grouptable JOIN school ON schoolid = school.id";
		
		List<School> retList = new ArrayList<>();
        
        School school = null;
		Group group = null;

		try( PreparedStatement stmtSelect = con.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);)
        {
  			ResultSet rs = stmtSelect.executeQuery();
			
			while(rs.next()){
				
				int schoolId = rs.getInt("school.id");
				
				if(school == null || schoolId != school.getId()){
					String schoolName = rs.getString("school.name");
					int year = rs.getInt("year");
					school = new School(schoolId, schoolName, year);
					
					retList.add(school);
				}

				int groupId = rs.getInt("grouptable.id");
				String groupName = rs.getString("grouptable.name");
				String room = rs.getString("room");
				
				group = new Group(groupId, groupName, room);
				
				retList.get(retList.size() - 1).getGroups().add(group);
			}
			
        } catch (SQLException e) {
            throw e;
        }

		return retList;
	}
}