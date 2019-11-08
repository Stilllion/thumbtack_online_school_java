package net.thumbtack.school.hiring.server;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;
import net.thumbtack.school.hiring.service.UserService;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    EmployerService employerService = new EmployerService();
    EmployeeService employeeService;
    UserService userService;

    static String loadLocation = null;
    static String saveLocation = null;

    static Database db = Database.getInstance();

    public static void main(String args[]) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();

        // Required options
        options.addOption(new Option("l", true, "specifies location of a database file"));
        options.addOption(new Option("s", true, "specifies location of a save file"));

        List<Employer> registeredEmployers;

        try {
            CommandLine commandLine = parser.parse(options, args);
            // Getting required arguments
            loadLocation = (String) commandLine.getParsedOptionValue("l");
            saveLocation = (String) commandLine.getParsedOptionValue("s");

            if(loadLocation != null){
                startServer(loadLocation);
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveToFile(String filepath)
    {
        FileOutputStream fos;

        try (DataOutputStream dos = new DataOutputStream(fos  = new FileOutputStream(filepath))) {
            saveRegisteredEmployers(dos);
            saveVacancies(dos);

            saveRegisteredEmployees(dos);
            saveSkills(dos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void readFromFile(String filepath)
    {
        FileInputStream fis;

        try (DataInputStream dis = new DataInputStream(fis  = new FileInputStream(filepath))) {
            readRegisteredEmployers(dis);
            readVacancies(dis);

            readRegisteredEmployees(dis);
            readSkills(dis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveRegisteredEmployers(DataOutputStream dos) {

        try {
            dos.writeInt(db.getRegisteredEmployers().size());

            for (Employer e : db.getRegisteredEmployers()) {
                dos.writeInt(e.getCompanyName().length());
                dos.writeBytes(e.getCompanyName());

                dos.writeInt(e.getAdress().length());
                dos.writeBytes(e.getAdress());

                dos.writeInt(e.getFirstName().length());
                dos.writeBytes(e.getFirstName());

                dos.writeInt(e.getLastName().length());
                dos.writeBytes(e.getLastName());

                dos.writeInt(e.getLogin().length());
                dos.writeBytes(e.getLogin());

                dos.writeInt(e.getPassword().length());
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

    private static void saveRegisteredEmployees(DataOutputStream dos)
    {
        try {
            dos.writeInt(db.getRegisteredEmployees().size());

            for (Employee e : db.getRegisteredEmployees()) {
                dos.writeInt(e.getFirstName().length());
                dos.writeBytes(e.getFirstName());

                dos.writeInt(e.getLastName().length());
                dos.writeBytes(e.getLastName());

                dos.writeInt(e.getLogin().length());
                dos.writeBytes(e.getLogin());

                dos.writeInt(e.getPassword().length());
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

    private static void saveVacancies(DataOutputStream dos)
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(dos);
            // Employer count
            oos.writeInt(db.getVacanciesAll().keySet().size());

            for (Employer e : db.getVacanciesAll().keySet()) {
                // Write Employer
                oos.writeObject(e);
                // Write his Vacancy
                oos.writeObject(db.getVacancies(e));
            }

            return;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
	
	private static void readRegisteredEmployers(DataInputStream dis) {
        Employer employer = new Employer();

        try {
            // Read number of entries
            int numberOfEntries = dis.readInt();

            for (int i = 0; i < numberOfEntries; ++i) {
                // CompanyName
                byte[] readData = new byte[dis.readInt()];
                dis.read(readData);
                employer.setCompanyName(new String(readData));

                // Address
                readData = new byte[dis.readInt()];
                dis.read(readData);
                employer.setAdress(new String(readData));

                //First name
                readData = new byte[dis.readInt()];
                dis.read(readData);
                employer.setFirstName(new String(readData));

                // Last name
                readData = new byte[dis.readInt()];
                dis.read(readData);
                employer.setLastName(new String(readData));

                // Login
                readData = new byte[dis.readInt()];
                dis.read(readData);
                employer.setLogin(new String(readData));

                // Password
                readData = new byte[dis.readInt()];
                dis.read(readData);
                employer.setPassword(new String(readData));

                // Email
                readData = new byte[dis.readInt()];
                dis.read(readData);
                employer.setEmail(new String(readData));

                db.getRegisteredEmployers().add(employer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readVacancies(DataInputStream dis)
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(dis);
            Employer readEmployer;
            List<Vacancy> readVacancies;

            Map<Employer, List<Vacancy>> tempMap = new HashMap<>();

            int employerCount = ois.readInt();

            for (int i = 0; i < employerCount; ++i) {
                readEmployer = (Employer) ois.readObject();
                readVacancies = (List<Vacancy>) ois.readObject();

                db.getVacanciesAll().put(readEmployer, readVacancies);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

	
	private static Employee readRegisteredEmployees(DataInputStream dis)
    {
        Employee employee = new Employee();
        try {
            int numberOfEntries = dis.readInt();

            for (int i = 0; i < numberOfEntries; ++i) {

                byte[] readData = new byte[dis.readInt()];
                dis.read(readData);
                employee.setFirstName(new String(readData));

                readData = new byte[dis.readInt()];
                dis.read(readData);
                employee.setLastName(new String(readData));

                readData = new byte[dis.readInt()];
                dis.read(readData);
                employee.setLogin(new String(readData));

                readData = new byte[dis.readInt()];
                dis.read(readData);
                employee.setPassword(new String(readData));

                readData = new byte[dis.readInt()];
                dis.read(readData);
                employee.setEmail(new String(readData));

                db.getRegisteredEmployees().add(employee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employee;
    }
	
    private static void saveSkills(DataOutputStream dos)
    {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(dos);
            // Employee count
            oos.writeInt(db.getSkillsAll().keySet().size());

            for (Employee e : db.getSkillsAll().keySet()) {
                oos.writeObject(e);
                oos.writeObject(db.getSkills(e));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    private static void readSkills(DataInputStream dis)
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(dis);
            Employee readEmployee;
            List<Skill> readSkills;

            Map<Employee, List<Skill>> tempMap = new HashMap<>();

            int employerCount = ois.readInt();

            for (int i = 0; i < employerCount; ++i) {
                readEmployee = (Employee) ois.readObject();
                readSkills = (List<Skill>) ois.readObject();

                db.getSkillsAll().put(readEmployee, readSkills);
                tempMap.put(readEmployee, readSkills);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Server() {
        employeeService = new EmployeeService();
        employerService = new EmployerService();
        userService = new UserService();
    }

    //  Производит всю необходимую инициализацию и запускает сервер.
    // Если savedDataFileName == null, восстановление состояния не производится, сервер стартует “с нуля”.
    // savedDataFileName - имя файла, в котором было сохранено состояние сервера.
    public static void startServer(String savedDataFileName)
    {
		if(savedDataFileName != null){
			
			readFromFile(savedDataFileName);
		}
    }

    // Останавливает сервер и записывает все его содержимое в файл сохранения с именем savedDataFileName.
    // Если savedDataFileName == null, запись содержимого не производится.
    public void stopServer(String saveDataFileName)
    {
		if(saveDataFileName != null){
			saveToFile(saveDataFileName);
		}
    }

    public String getRegisteredEmployers() {
        return employerService.getRegisteredEmployers();
    }

    public String getRegisteredEmployees() {
        return employeeService.getRegisteredEmployees();
    }

    // EMPLOYER METHODS
    public String registerEmployer(String jsonRequest) {
        return employerService.registerEmployer(jsonRequest);
    }

    public String addVacancy(String jsonRequest) {
        return employerService.addVacancy(jsonRequest);
    }

    public String removeRequirement(String jsonRequest) {
        return employerService.removeRequirement(jsonRequest);
    }

    public String addRequirement(String jsonRequest) {
        return employerService.addRequirement(jsonRequest);
    }

    public String getEmployerVacanciesAll(String jsonRequest) {
        return employerService.getVacanciesAll(jsonRequest);
    }

    public String getEmployerVacanciesActive(String jsonRequest) {
        return employerService.getVacanciesActive(jsonRequest);
    }

    public String getEmployerVacanciesInactive(String jsonRequest) {
        return employerService.getVacanciesInactive(jsonRequest);
    }

    public String setVacancyStatus(String jsonRequest) {
        return employerService.setVacancyStatus(jsonRequest);
    }

    public String getEmployeesAllSkillsRequiredLevel(String jsonRequest) {
        return employerService.getEmployeesAllSkillsRequiredLevel(jsonRequest);
    }

    public String getEmployeesMandatory(String jsonRequest) {
        return employerService.getEmployeesAllMandatorySkills(jsonRequest);
    }

    public String getEmployeesAnyLevel(String jsonRequest) {
        return employerService.getEmployeesAnyLevel(jsonRequest);
    }

    public String getSkills(String jsonRequest)
    {
        Gson gson = new Gson();
        return gson.toJson(db.getSkillsList());
    }

    public String getEmployeesAnySkill(String jsonRequest) {
        return employerService.getEmployeesAnySkill(jsonRequest);
    }

    public String changeRequirementLevel(String jsonRequest) {
        return employerService.changeRequirementLevel(jsonRequest);
    }

    public String changeRequirementName(String jsonRequest) {
        return employerService.changeRequirementName(jsonRequest);
    }

    public String hire(String jsonRequest) {
        return employerService.hire(jsonRequest);
    }

    // EMPLOYEE METHODS
    public String changeSkillLevel(String jsonRequest) {
        return employeeService.changeSkillLevel(jsonRequest);
    }

    public String setProfileInactive(String jsonRequest) {
        return employeeService.setIsActive(jsonRequest);
    }

    public String removeSkill(String jsonRequest) {
        return employeeService.removeSkill(jsonRequest);
    }

    public String registerEmployee(String jsonRequest) {
        return employeeService.registerEmployee(jsonRequest);
    }

    public String addSkill(String requestJsonString) {
        return employeeService.AddSkill(requestJsonString);
    }

    public String getVacanciesRequiredLevel(String jsonRequest) {
        return employeeService.getVacanciesRequiredLevel(jsonRequest);
    }

    public String getVacanciesMandatorySkills(String jsonRequest) {
        return employeeService.getVacanciesMandatorySkills(jsonRequest);
    }

    public String getVacanciesAnyLevel(String jsonRequest) {
        return employeeService.getVacanciesAnyLevel(jsonRequest);
    }

    public String getVacanciesAnySkill(String jsonRequest) {
        return employeeService.getVacanciesAnySkill(jsonRequest);
    }

    // USER METHODS
    public String loginRequest(String jsonRequest) {
        return userService.loginRequest(jsonRequest);
    }

    public String logoutRequest(String jsonRequest) {
        return userService.logoutRequest(jsonRequest);
    }

    public String changeFirstName(String jsonRequest) {
        return userService.changeFirstName(jsonRequest);
    }

    public String changeLastName(String jsonRequest) {
        return userService.changeLastName(jsonRequest);
    }

    public String changeEmail(String jsonRequest) {
        return userService.changeEmail(jsonRequest);
    }

    public String changePassword(String jsonRequest) {
        return userService.changePassword(jsonRequest);
    }

    public String removeEmployer(String jsonRequest) {
        return employerService.removeAccount(jsonRequest);
    }

    public String removeEmployee(String jsonRequest) {
        return employeeService.removeAccount(jsonRequest);
    }
}