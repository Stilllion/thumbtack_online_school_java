package net.thumbtack.school.ttschool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TraineeMap
{
	Map<Trainee, String> traineeMap;
	
	// Создает TraineeMap с пустым Map. 
	public TraineeMap()
	{
		traineeMap = new HashMap<Trainee, String>();
	}

	// Добавляет пару Trainee - String в Map. Если Map уже содержит информацию об этом Trainee, выбрасывает TrainingException с TrainingErrorCode.DUPLICATE_TRAINEE. 
	public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException
	{
		if (traineeMap.put(trainee, institute) != null) {
			throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
		}
	}

	// Если в Map уже есть информация о данном Trainee, заменяет пару Trainee - String в Map на новую пару, иначе выбрасывает TrainingException с TrainingErrorCode.TRAINEE_NOT_FOUND. 
	public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException
	{
		if (traineeMap.replace(trainee, institute) == null){
			throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
		}
	}

	// Удаляет информацию о Trainee из Map. Если Map не содержит информации о таком Trainee, выбрасывает TrainingException с TrainingErrorCode.TRAINEE_NOT_FOUND.
	public void removeTraineeInfo(Trainee trainee) throws TrainingException
	{
		if (traineeMap.remove(trainee) == null) {
			throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
		}
	}

	// Возвращает количество элементов в Map, иными словами, количество студентов.
	public int getTraineesCount()
	{
		return traineeMap.size();
	}

	// Возвращает институт, в котором учится данный Trainee.
	// Если Map не содержит информации о таком Trainee, выбрасывает TrainingException с TrainingErrorCode.TRAINEE_NOT_FOUND
	public String getInstituteByTrainee(Trainee trainee) throws TrainingException
	{
		String institute;
		
		institute = traineeMap.get(trainee);
		
		if (institute != null){
			return institute;
		} else {
			throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
		}
	}

	// Возвращает Set из всех имеющихся в Map Trainee.
	public Set<Trainee> getAllTrainees()
	{
		return traineeMap.keySet();
	}

	// Возвращает Set из всех институтов.
	public Set<String> getAllInstitutes()
	{
		return new HashSet<String>(traineeMap.values());
	}

	// Возвращает true, если хоть один студент учится в этом institute, иначе false.
	public boolean isAnyFromInstitute(String institute)
	{
		return traineeMap.containsValue(institute);
	}
}
