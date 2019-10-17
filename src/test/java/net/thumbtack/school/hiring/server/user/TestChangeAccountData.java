package net.thumbtack.school.hiring.server.user;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.request.user.ChangeFirstNameDtoRequest;
import net.thumbtack.school.hiring.request.user.ChangeLastNameDtoRequest;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.response.user.ChangeFirstNameDtoResponse;

import static org.junit.jupiter.api.Assertions.*;

import net.thumbtack.school.hiring.response.user.ChangeLastNameDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;


import java.util.UUID;

public class TestChangeAccountData
{
    Server s = new Server();
    Gson gson = new Gson();
    TestDao testDao = new TestDao();
    UUID userToken = null;

    // Register employer
    public void registerEmployer() throws ServerException
    {
        RegisterEmployerDtoRequest regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
                "Jonathan", "Blow", "jb", "qwerty123");

        RegisterEmployerDtoResponse result = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);

        userToken = result.getToken();
    }

    // Test changing First name
    @Test
    public void testChangeFirstName() throws ServerException
    {
        if(userToken == null){
            registerEmployer();
        }

        // Valid input
        ChangeFirstNameDtoRequest request = new ChangeFirstNameDtoRequest(userToken, "Jon");
        ChangeFirstNameDtoResponse response = gson.fromJson(s.changeFirstName(gson.toJson(request)), ChangeFirstNameDtoResponse.class);

        assertEquals("Jon", response.getNewFirstName());

        // Invalid input
        request = new ChangeFirstNameDtoRequest(userToken, "");
        response = gson.fromJson(s.changeFirstName(gson.toJson(request)), ChangeFirstNameDtoResponse.class);

        assertEquals(response.getError(), ServerErrorCode.WRONG_FIRSTNAME.getErrorString());

        request = new ChangeFirstNameDtoRequest(null, "Jon");
        response = gson.fromJson(s.changeFirstName(gson.toJson(request)), ChangeFirstNameDtoResponse.class);

        assertEquals(response.getError(), ServerErrorCode.WRONG_TOKEN.getErrorString());

    }
    // Test changing Last name
    @Test
    public void testChangeLastName() throws ServerException
    {
        if(userToken == null){
            registerEmployer();
        }

        // Valid input
        ChangeLastNameDtoRequest request = new  ChangeLastNameDtoRequest(userToken, "Strike");
        ChangeLastNameDtoResponse response = gson.fromJson(s.changeLastName(gson.toJson(request)),  ChangeLastNameDtoResponse.class);

        assertEquals("Strike", response.getNewLastName());

        // Invalid input
        request = new ChangeLastNameDtoRequest(userToken, "");
        response = gson.fromJson(s.changeLastName(gson.toJson(request)), ChangeLastNameDtoResponse.class);

        assertEquals(response.getError(), ServerErrorCode.WRONG_LASTNAME.getErrorString());

        request = new ChangeLastNameDtoRequest(null, "Strike");
        response = gson.fromJson(s.changeLastName(gson.toJson(request)), ChangeLastNameDtoResponse.class);

        assertEquals(response.getError(), ServerErrorCode.WRONG_TOKEN.getErrorString());

        testDao.resetDatabase();
    }

}
