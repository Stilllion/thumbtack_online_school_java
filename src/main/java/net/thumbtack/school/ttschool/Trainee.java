package net.thumbtack.school.ttschool;

import java.io.Serializable;
import java.util.Objects;

public class Trainee implements Serializable
{
	private static final long serialVersionUID = 42L;

	private String firstName;
	private String lastName;
	private int rating;
	
    public Trainee(String firstName, String lastName, int rating) throws TrainingException
    {
        setFirstName(firstName);
        setLastName(lastName);
        setRating(rating);
    }

    // Возвращает имя учащегося
    public String getFirstName()
	{
		return firstName;
	}

    public void setFirstName(String firstName) throws TrainingException
	{
		if(firstName == null || firstName == "") { throw new TrainingException(TrainingErrorCode.TRAINEE_WRONG_FIRSTNAME);}
		
		this.firstName = firstName;
	}

    public String getLastName()
	{
		return lastName;
	}

    public void setLastName(String lastName) throws TrainingException
	{
		if(lastName == null || lastName == "") { throw new TrainingException(TrainingErrorCode.TRAINEE_WRONG_LASTNAME);}
		
		this.lastName = lastName;
	}

    public int getRating()
	{
		return rating;
	}

    public void setRating(int rating) throws TrainingException
	{
		if(rating < 1 || rating > 5){ throw new TrainingException(TrainingErrorCode.TRAINEE_WRONG_RATING);}
		
		this.rating = rating;
	}

    public String getFullName()
	{
		return new StringBuilder(firstName + " " + lastName).toString();
	}

	@Override
	public String toString() {
		return "Trainee{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", rating=" + rating +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Trainee trainee = (Trainee) o;
		return rating == trainee.rating &&
				Objects.equals(firstName, trainee.firstName) &&
				Objects.equals(lastName, trainee.lastName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, rating);
	}
}
