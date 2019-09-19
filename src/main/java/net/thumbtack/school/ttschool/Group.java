package net.thumbtack.school.ttschool;

import java.util.*;

public class Group
{
	String name;
	String room;
	List<Trainee> traineeList;
	
	// Создает Group с указанными значениями полей и пустым списком студентов.
	// Для недопустимых значений входных параметров выбрасывает TrainingException с соответствующим TrainingErrorCode
	public Group(String name, String room) throws TrainingException
	{
		setName(name);
		setRoom(room);
		traineeList = new ArrayList<>();
	}
	
	// Возвращает имя группы
	public String getName()
	{
		return name;
	}
	
	// Устанавливает имя группы. Для недопустимого значения входного параметра выбрасывает TrainingException с TrainingErrorCode.GROUP_WRONG_NAME (здесь и далее добавляйте новые коды ошибок в TrainingErrorCode)
	public void setName(String name) throws TrainingException
	{
		if(name == null || name.equals("")){
			throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
		}
		this.name = name;
	}
	
	// Возвращает название  аудитории
	public String getRoom()
	{
		return room;
	}
	
	// Устанавливает название  аудитории. Для недопустимого значения входного параметра выбрасывает TrainingException с TrainingErrorCode.GROUP_WRONG_ROOM
	public void setRoom(String room) throws TrainingException
	{
		if (room == null || room.equals("")) {
			throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
		}
		this.room = room;
	}
	
	// Возвращает список учащихся.
	public List<Trainee> getTrainees()
	{
		return traineeList;
	}

	// Добавляет Trainee в группу.
	public void  addTrainee(Trainee trainee)
	{
		traineeList.add(trainee);
	}
	
	// Удаляет Trainee из группы. Если такого Trainee в группе нет, выбрасывает TrainingException
	// с TrainingErrorCode.TRAINEE_NOT_FOUND
	public void  removeTrainee(Trainee trainee) throws TrainingException
	{
		if (!traineeList.contains(trainee)) {
			throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
		}

		traineeList.remove(trainee);
	}
	
	// Удаляет Trainee с данным номером в списке из группы.
	// Если номер не является допустимым, выбрасывает TrainingException с TrainingErrorCode.TRAINEE_NOT_FOUND 
	public void  removeTrainee(int index) throws TrainingException
	{
		if(index >= traineeList.size()){
			throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
		}

		traineeList.remove(index);
	}
	
	// Возвращает первый найденный в списке группы Trainee, у которого имя равно firstName.
	// Если такого Trainee в группе нет, выбрасывает TrainingException с TrainingErrorCode.TRAINEE_NOT_FOUND 
	public Trainee  getTraineeByFirstName(String firstName) throws TrainingException
	{
		for(Trainee t : traineeList){
			if (t.getFirstName().equals(firstName)){
				return t;
			}
		}
		
		throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
	}
	
	// Возвращает первый найденный в списке группы Trainee, у которого полное имя равно fullName. Если такого Trainee в группе нет, выбрасывает TrainingException с TrainingErrorCode.TRAINEE_NOT_FOUND 
	public Trainee  getTraineeByFullName(String fullName) throws TrainingException
	{
		for(Trainee t : traineeList){
			if (t.getFullName().equals(fullName)){
				return t;
			}
		}
		
		throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
	}
	
	// Сортирует список Trainee группы, упорядочивая его по возрастанию имени Trainee.
	public void  sortTraineeListByFirstNameAscendant()
	{
		Collections.sort(traineeList, new Comparator<Trainee>() {
			@Override
			public int compare(Trainee t1, Trainee t2) {
				return t1.getFirstName().compareTo(t2.getFirstName());
			}
		});
	}
	
	// Сортирует список Trainee группы, упорядочивая его по убыванию оценки Trainee.
	public void  sortTraineeListByRatingDescendant()
	{
		Collections.sort(traineeList, new Comparator<Trainee>() {
			@Override
			public int compare(Trainee t1, Trainee t2) {
				return Integer.toString(t2.getRating()).compareTo(Integer.toString(t1.getRating()));
			}
		});	
	}
	
	// Переворачивает список Trainee группы, то есть последний элемент списка становится начальным,
	// предпоследний - следующим за начальным и т.д..
	public void  reverseTraineeList()
	{
		Collections.reverse(traineeList);
	}
	
	// Циклически сдвигает список Trainee группы на указанное число позиций.
	// Для положительного значения positions сдвигает вправо, для отрицательного - влево на модуль значения positions.
	public void  rotateTraineeList(int positions)
	{
		Collections.rotate(traineeList, positions);
	}
	
	// Возвращает список тех Trainee группы , которые имеют наивысшую оценку.
	// Иными словами, если в группе есть Trainee с оценкой 5, возвращает список получивших оценку 5,
	// если же таких нет, но есть Trainee с оценкой 4, возвращает список получивших оценку 4 и т.д.
	// Для пустого списка выбрасывает TrainingException с TrainingErrorCode.TRAINEE_NOT_FOUND 
	public List<Trainee>  getTraineesWithMaxRating() throws TrainingException
	{
		List<Trainee> maxRatingList = new ArrayList<>();
		int highestRating = 0;

		for(Trainee t : traineeList){
			if (t.getRating() > highestRating){
				highestRating = t.getRating();
			}
		}

		for(Trainee t : traineeList){
			if (t.getRating() == highestRating){
				maxRatingList.add(t);
			}
		}

		if(maxRatingList.isEmpty()){
			throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
		}

		return maxRatingList;
	}
	
	// Проверяет, есть ли в группе хотя бы одна пара Trainee, для которых совпадают имя, фамилия и оценка.
	public boolean  hasDuplicates()
	{
		Set<Trainee> traineeSet = new HashSet<>(traineeList);

		return traineeSet.size() != traineeList.size();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Group group = (Group) o;
		return Objects.equals(name, group.name) &&
				Objects.equals(room, group.room) &&
				Objects.equals(traineeList, group.traineeList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, room, traineeList);
	}
}