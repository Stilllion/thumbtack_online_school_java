package net.thumbtack.school.hiring.server.user;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.request.user.ChangeFirstNameDtoRequest;
import net.thumbtack.school.hiring.request.user.LoginDtoRequest;
import net.thumbtack.school.hiring.request.user.LogoutDtoRequest;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.response.user.ChangeFirstNameDtoResponse;
import net.thumbtack.school.hiring.response.user.LoginDtoResponse;
import net.thumbtack.school.hiring.response.user.LogoutDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class TestLoginAndLogout
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

    @Test
    public void testLogin() throws ServerException
    {
        if(userToken == null){
            registerEmployer();
        }

        LoginDtoRequest request = new LoginDtoRequest("jb", "qwerty123");
        LoginDtoResponse response = gson.fromJson(s.loginRequest(gson.toJson(request)), LoginDtoResponse.class);

        UUID newToken = response.getToken();

        // Assert that new token is valid
        ChangeFirstNameDtoRequest changeNameRequest = new ChangeFirstNameDtoRequest(newToken, "NaySayer");
        ChangeFirstNameDtoResponse changeNameResponse = gson.fromJson(s.changeFirstName(gson.toJson(changeNameRequest)),  ChangeFirstNameDtoResponse.class);

        assertNotEquals(null, response.getToken());
        assertEquals("NaySayer", changeNameResponse.getNewFirstName());
    }

    @Test
    public void testLogout() throws ServerException
    {
        if(userToken == null){
            registerEmployer();
        }

        LogoutDtoRequest request = new LogoutDtoRequest(userToken);
        LogoutDtoResponse response = gson.fromJson(s.logoutRequest(gson.toJson(request)), LogoutDtoResponse.class);

        // Assert that the old token is invalid
        ChangeFirstNameDtoRequest changeNameRequest = new ChangeFirstNameDtoRequest(userToken, "NaySayer");
        ChangeFirstNameDtoResponse changeNameResponse = gson.fromJson(s.changeFirstName(gson.toJson(changeNameRequest)),  ChangeFirstNameDtoResponse.class);

        assertEquals(ServerErrorCode.TOKEN_NOT_FOUND.getErrorString(), changeNameResponse.getError());

        testDao.resetDatabase();
    }
}
