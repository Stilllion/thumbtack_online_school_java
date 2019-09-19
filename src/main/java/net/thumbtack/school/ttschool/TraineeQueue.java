package net.thumbtack.school.ttschool;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class TraineeQueue
{
	Queue<Trainee> traineeQueue;
	// Создает TraineeQueue с пустой Queue. 
	public TraineeQueue()
	{
		traineeQueue = new LinkedList<Trainee>();
	}
	
	// Добавляет студента в очередь.
	public void addTrainee(Trainee trainee)
	{
		traineeQueue.add(trainee);
	}
	
	// Удаляет студента из очереди. Метод возвращает удаленного Trainee.
	// Если в очереди никого нет, выбрасывает TrainingException с TrainingErrorCode.EMPTY_TRAINEE_QUEUE.
	public Trainee removeTrainee() throws TrainingException
	{
		Trainee retTrainee = null;
		try{
			retTrainee = traineeQueue.remove();
		} catch (NoSuchElementException e){
			throw new TrainingException(TrainingErrorCode.EMPTY_TRAINEE_QUEUE);
		}

		return retTrainee;
	}
	
	// Возвращает true, если очередь пуста, иначе false
	public boolean isEmpty()
	{
		if (traineeQueue.peek() == null){
			return true;
		}
		
		return false;
	}
}
