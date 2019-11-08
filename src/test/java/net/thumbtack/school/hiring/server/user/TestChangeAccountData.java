package net.thumbtack.school.hiring.server.user;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.request.user.ChangeAccountDataDtoRequest;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;

import net.thumbtack.school.hiring.response.user.ChangeAccountDataDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

public class TestChangeAccountData
{
    Server s = new Server();
    Gson gson = new Gson();
    TestDao testDao = new TestDao();

    // Register employer
    public UUID registerEmployer()
    {
        RegisterEmployerDtoRequest regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
                "Jonathan", "Blow", "jb", "qwerty123");

        RegisterEmployerDtoResponse result = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);

        return result.getToken();
    }

    @Test
    public void testChangeFirstName()
    {
        UUID userToken = registerEmployer();

        // Valid input
        ChangeAccountDataDtoRequest request = new ChangeAccountDataDtoRequest(userToken, "Jon");
        ChangeAccountDataDtoResponse response = gson.fromJson(s.changeFirstName(gson.toJson(request)), ChangeAccountDataDtoResponse.class);

        assertEquals("Jon", response.getData());

        // Invalid input
        request = new ChangeAccountDataDtoRequest(userToken, "");
        response = gson.fromJson(s.changeFirstName(gson.toJson(request)), ChangeAccountDataDtoResponse.class);

        assertEquals(response.getError(), ServerErrorCode.WRONG_DATA.getErrorString());

        request = new ChangeAccountDataDtoRequest(null, "Jon");
        response = gson.fromJson(s.changeFirstName(gson.toJson(request)), ChangeAccountDataDtoResponse.class);

        assertEquals(response.getError(), ServerErrorCode.WRONG_TOKEN.getErrorString());

        testDao.resetDatabase();
    }

    @Test
    public void testChangeLastName()
    {
        UUID userToken = registerEmployer();

        // Valid input
        ChangeAccountDataDtoRequest request = new  ChangeAccountDataDtoRequest(userToken, "Strike");
        ChangeAccountDataDtoResponse response = gson.fromJson(s.changeLastName(gson.toJson(request)),  ChangeAccountDataDtoResponse.class);

        assertEquals("Strike", response.getData());

        // Invalid input
        request = new ChangeAccountDataDtoRequest(userToken, "");
        response = gson.fromJson(s.changeLastName(gson.toJson(request)), ChangeAccountDataDtoResponse.class);

        assertEquals(response.getError(), ServerErrorCode.WRONG_DATA.getErrorString());

        request = new ChangeAccountDataDtoRequest(null, "Strike");
        response = gson.fromJson(s.changeLastName(gson.toJson(request)), ChangeAccountDataDtoResponse.class);

        assertEquals(response.getError(), ServerErrorCode.WRONG_TOKEN.getErrorString());

        testDao.resetDatabase();
    }

    @Test
    public void testChangeEmail()
    {
        UUID userToken = registerEmployer();

        // Valid input
        ChangeAccountDataDtoRequest request = new  ChangeAccountDataDtoRequest(userToken, "new@email.com");
        ChangeAccountDataDtoResponse response = gson.fromJson(s.changeEmail(gson.toJson(request)),  ChangeAccountDataDtoResponse.class);

        assertEquals("new@email.com", response.getData());

        // Invalid input
        request = new ChangeAccountDataDtoRequest(userToken, "");
        response = gson.fromJson(s.changeEmail(gson.toJson(request)), ChangeAccountDataDtoResponse.class);

        assertEquals(response.getError(), ServerErrorCode.WRONG_DATA.getErrorString());

        request = new ChangeAccountDataDtoRequest(null, "new@email.com");
        response = gson.fromJson(s.changeEmail(gson.toJson(request)), ChangeAccountDataDtoResponse.class);

        assertEquals(response.getError(), ServerErrorCode.WRONG_TOKEN.getErrorString());

        testDao.resetDatabase();
    }

    @Test
    public void testChangePassword()
    {
        UUID userToken = registerEmployer();

        // Valid input
        ChangeAccountDataDtoRequest request = new  ChangeAccountDataDtoRequest(userToken, "newQwerty123");
        ChangeAccountDataDtoResponse response = gson.fromJson(s.changePassword(gson.toJson(request)),  ChangeAccountDataDtoResponse.class);

        assertEquals("newQwerty123", response.getData());

        // Invalid input
        request = new ChangeAccountDataDtoRequest(userToken, "");
        response = gson.fromJson(s.changePassword(gson.toJson(request)), ChangeAccountDataDtoResponse.class);

        assertEquals(ServerErrorCode.WRONG_DATA.getErrorString(), response.getError());

        request = new ChangeAccountDataDtoRequest(null, "newQwerty123");
        response = gson.fromJson(s.changePassword(gson.toJson(request)), ChangeAccountDataDtoResponse.class);

        assertEquals(ServerErrorCode.WRONG_TOKEN.getErrorString(), response.getError());

        testDao.resetDatabase();
    }
}
