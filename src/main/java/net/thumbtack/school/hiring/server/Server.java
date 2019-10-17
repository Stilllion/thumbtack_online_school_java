package net.thumbtack.school.hiring.server;

import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;
import net.thumbtack.school.hiring.service.UserService;
import org.apache.commons.cli.*;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/*

TODO List:

0. Add MiddleName to User's fields
1. Change "boolean validate()" to "void validate()" in Requests
2. When returning Vacancies and Employees list check weather they are Active or not
3. Style: Remove "noise words" String NameString (String is redundant)
4. inconsistent code in Employer Service.
everywhere else i construct new Response object right away and then use setError() if needed instead of multiple "new" statements
5. Rename GetEmployeesRequiredLevelDtoRequest/Response class. GetEmployeesRequiredLvlDtoRequest?
   GetEmployeesAllSkillsDtoRequest?


*/

public class Server
{
	EmployerService emploerService;
	EmployeeService employeeService;
	UserService userService;
	static Database db = Database.getInstance();

    public static void main(String args[])
    {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();

        // Required options
        options.addOption(new Option("l", true, "specifies location of a database file"));
        options.addOption(new Option("s", true, "specifies location of a save file"));

        String saveLocation = "";
        String loadLocation = "";

        List<Employer> registeredEmployers;

        try {
            CommandLine commandLine = parser.parse(options, args);
            // Getting required arguments

            loadLocation = (String) commandLine.getParsedOptionValue("l");
            saveLocation = (String) commandLine.getParsedOptionValue("s");

            System.out.println(loadLocation);
            System.out.println(saveLocation);

        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

        SaveRegisteredEmployers(saveLocation);
        //SaveRegisteredEmployees();
    }

    private static void SaveRegisteredEmployers(String filePath)
    {
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath))){
            // Write container size
            // then write container's data
            for(Employer e : db.getRegisteredEmployers()){
                dos.writeInt(e.getCompanyName().length());
                dos.writeBytes(e.getCompanyName());

                dos.writeInt(e.getAdress().length());
                dos.writeBytes(e.getAdress());

                dos.writeInt(e.getFirstName().length());
                dos.writeBytes(e.getFirstName());

                dos.writeInt(e.getLastName().length());
                dos.writeBytes(e.getLastName());

                dos.writeInt(e.getLogin().length());
                dos.writeBytes(e.getPassword());

                dos.writeInt(e.getEmail().length());
                dos.writeBytes(e.getEmail());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server()
    {
        emploerService = new EmployerService();
        employeeService = new EmployeeService();
        userService = new UserService();
    }

    //  Производит всю необходимую инициализацию и запускает сервер.
    // Если savedDataFileName == null, восстановление состояния не производится, сервер стартует “с нуля”.
    // savedDataFileName - имя файла, в котором было сохранено состояние сервера.
    public void startServer(String savedDataFileName)
    {

    }

    //Останавливает сервер и записывает все его содержимое в файл сохранения с именем savedDataFileName.
    // Если savedDataFileName == null, запись содержимого не производится.
    public void stopServer(String saveDataFileName)
    {

    }
		
    public String getRegisteredEmployers()
    {
        return emploerService.getRegisteredEmployers();
    }

    public String getRegisteredEmployees()
	{
		return employeeService.getRegisteredEmployees();
	}

    // EMPLOYER METHODS
	public String registerEmployer(String requestJsonString)
	{
		return emploerService.registerEmployer(requestJsonString);
	}
    // TODO TEST
    public String addVacancy(String requestJsonString)
    {
        return emploerService.addVacancy(requestJsonString);
    }

    // TODO TEST
    public String removeRequirement(String jsonRequest)
    {
        return emploerService.removeRequirement(jsonRequest);
    }

    // TODO TEST
    public String addRequirement(String jsonRequest)
    {
        return emploerService.addRequirement(jsonRequest);
    }

    // TODO TEST
    public String getEmployerVacanciesAll(String jsonRequest)
    {
        return emploerService.getVacanciesAll(jsonRequest);
    }

    // TODO TEST
    public String getEmployerVacanciesActive(String jsonRequest)
    {
        return emploerService.getVacanciesActive(jsonRequest);
    }

    // TODO TEST
    public String getEmployerVacanciesInactive(String jsonRequest)
    {
        return emploerService.getVacanciesInactive(jsonRequest);
    }

    // TODO TEST
    public String setVacancyActive(String jsonRequest)
    {
        return emploerService.setVacancyActive(jsonRequest);
    }

    // TODO Do we actually need 2 separate methods for setting Vacancy Active/Inactive status
    // TODO maybe something like setVacancyStatus(json:{isActive: true/false}); would be better
    public String setVacancyInactive(String jsonRequest)
    {
        return emploerService.setVacancyActive(jsonRequest);
    }

    // TODO TEST
    public String getEmployeesAllSkillsRequiredLevel(String requestJsonString)
	{
	    return emploerService.getEmployeesAllSkillsRequiredLevel(requestJsonString);
	}

   // TODO TEST
	public String getEmployeesMandatory(String requestJsonString)
	{
        return emploerService.getEmployeesAllMandatorySkills(requestJsonString);
	}

   // TODO TEST
	public String getEmployeesAnyLevel(String requestJsonString)
	{
        return emploerService.getEmployeesAnyLevel(requestJsonString);
	}

   // TODO TEST
	public String getEmployeesAnySkill(String requestJsonString)
	{
        return emploerService.getEmployeesAnySkill(requestJsonString);
	}

	// TODO TEST
	public String changeRequirementLevel(String jsonRequest)
    {
        return emploerService.changeRequirementLevel(jsonRequest);
    }

    // TODO TEST
    public String changeRequirementName(String jsonRequest)
    {
        return emploerService.changeRequirementName(jsonRequest);
    }

	// EMPLOYEE METHODS
	public String registerEmployee(String requestJsonString)
    {
        return employeeService.registerEmployee(requestJsonString);
    }

    public String addSkill(String requestJsonString)
    {
        return employeeService.AddSkill(requestJsonString);
    }

    public String getVacanciesRequiredLevel(String requestJsonString)
    {
        return employeeService.getVacanciesRequiredLevel(requestJsonString);
    }

    public String getVacanciesMandatorySkills(String requestJsonString)
    {
        return employeeService.getVacanciesMandatorySkills(requestJsonString);
    }

    public String getVacanciesAnyLevel(String requestJsonString)
    {
        return employeeService.getVacanciesAnyLevel(requestJsonString);
    }

    // TODO Method implementation
    // TODO TEST
    public String getVacanciesAnySkill(String requestJsonString)
    {
        return "";
    }

    // USER METHODS
    public String loginRequest(String jsonRequest)
    {
        return userService.loginRequest(jsonRequest);
    }

    public String logoutRequest(String jsonRequest)
    {
        return userService.logoutRequest(jsonRequest);
    }

    public String changeFirstName(String jsonRequest)
    {
        return userService.changeFirstName(jsonRequest);
    }

    public String changeLastName(String jsonRequest)
    {
        return userService.changeLastName(jsonRequest);
    }

    // TODO TEST
    public String changeEmail(String jsonRequest)
    {
        return userService.changeEmail(jsonRequest);
    }

    // TODO TEST
    public String changePassword(String jsonRequest)
    {
        return userService.changePassword(jsonRequest);
    }

    // TODO Method implementation
    // TODO TEST
	/*public String removeAccount(String jsonRequest)
	{
		return userService.removeAccount(jsonRequest);
	}*/
}
