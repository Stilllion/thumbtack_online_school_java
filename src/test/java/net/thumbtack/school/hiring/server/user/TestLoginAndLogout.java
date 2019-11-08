package net.thumbtack.school.hiring.server.user;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.request.user.ChangeAccountDataDtoRequest;
import net.thumbtack.school.hiring.request.user.LoginDtoRequest;
import net.thumbtack.school.hiring.request.user.LogoutDtoRequest;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.response.user.ChangeAccountDataDtoResponse;
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

    // Register employer
    public UUID registerEmployer() throws ServerException
    {
        RegisterEmployerDtoRequest regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
                "Jonathan", "Blow", "jb", "qwerty123");

        RegisterEmployerDtoResponse result = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);

        return result.getToken();
    }

    @Test
    public void testLogin() throws ServerException
    {
        UUID userToken = registerEmployer();

        LoginDtoRequest request = new LoginDtoRequest("jb", "qwerty123");
        LoginDtoResponse response = gson.fromJson(s.loginRequest(gson.toJson(request)), LoginDtoResponse.class);

        UUID newToken = response.getToken();

        // Assert that new token is valid
        ChangeAccountDataDtoRequest changeNameRequest = new ChangeAccountDataDtoRequest(newToken, "NaySayer");
        ChangeAccountDataDtoResponse changeNameResponse = gson.fromJson(s.changeFirstName(gson.toJson(changeNameRequest)), ChangeAccountDataDtoResponse.class);

        assertNotEquals(null, response.getToken());
        assertEquals("NaySayer", changeNameResponse.getData());
    }

    @Test
    public void testLogout() throws ServerException
    {
        UUID userToken = registerEmployer();

        LogoutDtoRequest request = new LogoutDtoRequest(userToken);
        LogoutDtoResponse response = gson.fromJson(s.logoutRequest(gson.toJson(request)), LogoutDtoResponse.class);

        // Assert that the old token is invalid
        ChangeAccountDataDtoRequest changeNameRequest = new ChangeAccountDataDtoRequest(userToken, "NaySayer");
        ChangeAccountDataDtoResponse changeNameResponse = gson.fromJson(s.changeFirstName(gson.toJson(changeNameRequest)),  ChangeAccountDataDtoResponse.class);

        assertEquals(ServerErrorCode.TOKEN_NOT_FOUND.getErrorString(), changeNameResponse.getError());

        testDao.resetDatabase();
    }
}
