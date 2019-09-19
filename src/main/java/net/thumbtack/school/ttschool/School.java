package net.thumbtack.school.ttschool;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class School
{
	String name;
	int year;
	Set<Group> groups;
	
	// Создает School с указанными значениями полей и пустым множеством групп. 
	// Для недопустимых значений входных параметров выбрасывает TrainingException с соответствующим TrainingErrorCode
	public School(String name, int year) throws TrainingException
	{
		setName(name);
		setYear(year);
		groups = new HashSet<>();
	}

	// Возвращает название школы.
	public String getName()
	{
		return name;
	}

	// Устанавливает название школы. Для недопустимого значения входного параметра выбрасывает TrainingException с TrainingErrorCode.SCHOOL_WRONG_NAME
	public void setName(String name) throws TrainingException
	{
		if(name == null || name.equals("")){
			throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
		}
		this.name = name;
	}

	// Возвращает год начала обучения.
	public int getYear()
	{
		return year;
	}

	// Устанавливает год начала обучения.
	public void setYear(int year)
	{
		this.year = year;
	}

	// Возвращает список групп
	public Set<Group> getGroups()
	{
		return groups;
	}

	// Добавляет Group в школу.
	// Если группа с таким именем уже есть, выбрасывает TrainingException с  TrainingErrorCode.DUPLICATE_GROUP_NAME
	public void  addGroup(Group group) throws TrainingException
	{
		for(Group g : groups){
			if(g.getName().equals(group.getName())){
				throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
			}
		}

		groups.add(group);
	}

	// Удаляет Group из школы. Если такой Group в школе нет, выбрасывает TrainingException с TrainingErrorCode.GROUP_NOT_FOUND
	public void  removeGroup(Group group) throws TrainingException
	{
		if(!groups.contains(group)){
			throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
		}

		groups.remove(group);
	}

	// Удаляет Group с данным названием из школы.
	// Если группа с таким названием не найдена, выбрасывает TrainingException с TrainingErrorCode.GROUP_NOT_FOUND
	public void  removeGroup(String name) throws TrainingException
	{
		for(Group g : groups){
			if(g.getName().equals(name)){
				groups.remove(g);
				return;
			}
		}

		throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
	}

	// Определяет, есть ли в школе группа с таким названием.
	public boolean  containsGroup(Group group)
	{
		return groups.contains(group);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		School school = (School) o;
		return year == school.year &&
				Objects.equals(name, school.name) &&
				Objects.equals(groups, school.groups);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, year, groups);
	}
}
