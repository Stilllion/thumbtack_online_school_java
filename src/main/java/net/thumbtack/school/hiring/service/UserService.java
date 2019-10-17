package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.UserDaoImpl;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.user.*;
import net.thumbtack.school.hiring.response.user.*;
import net.thumbtack.school.hiring.server.User;

import java.util.List;

public class UserService
{
    private UserDaoImpl userDao;
    private Gson gson = new Gson();

    public UserService()
    {
        userDao = new UserDaoImpl();
    }

    public String loginRequest(String jsonRequestString)
    {
        LoginDtoRequest request = gson.fromJson(jsonRequestString, LoginDtoRequest.class);
        LoginDtoResponse response = new LoginDtoResponse();

        List<User> registeredUsers = userDao.getRegisteredUsers();
        User requestSender = null;

        try{
            request.validate();
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        for(User u : registeredUsers){
            if(u.getLogin().equals(request.getLogin())) {
                requestSender = u;
            }
        }

        if(requestSender == null){
            response.setError(ServerErrorCode.USER_NOT_FOUND.getErrorString());
            return gson.toJson(response);
        }

        if(requestSender.getPassword().equals(request.getPassword())){
            requestSender.setToken(response.getToken());
            requestSender.setOnline(true);

            return gson.toJson(response);
        }

        response.setError(ServerErrorCode.PASSWORD_MISMATCH.getErrorString());
        return gson.toJson(response);
    }

    public String logoutRequest(String jsonRequest)
    {
        LogoutDtoRequest request = gson.fromJson(jsonRequest, LogoutDtoRequest.class);
        LogoutDtoResponse response = new LogoutDtoResponse();

        try{
            request.validate();
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        List<User> registeredUsers = userDao.getRegisteredUsers();

        for(User u : registeredUsers){
            if(u.getToken().equals(request.getToken())){
                u.setToken(null);
                u.setOnline(false);

                return gson.toJson(response);
            }
        }

        response.setError(ServerErrorCode.USER_NOT_FOUND.getErrorString());
        return gson.toJson(response);
    }

    public String changeFirstName(String jsonRequest)
    {
        ChangeFirstNameDtoRequest req = gson.fromJson(jsonRequest, ChangeFirstNameDtoRequest.class);
        ChangeFirstNameDtoResponse response = new ChangeFirstNameDtoResponse();

        try{
            req.validate();
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        List<User> registeredUsers = userDao.getRegisteredUsers();

        for(User u : registeredUsers){
            if(req.getToken().equals(u.getToken())){
                u.setFirstName(req.getNewFirstName());

                response.setNewFirstName(u.getFirstName());
                return gson.toJson(response);
            }
        }

        response.setError(ServerErrorCode.TOKEN_NOT_FOUND.getErrorString());
        return gson.toJson(response);
    }

    public String changeLastName(String jsonRequest)
    {
        ChangeLastNameDtoRequest req = gson.fromJson(jsonRequest, ChangeLastNameDtoRequest.class);
        ChangeLastNameDtoResponse response = new ChangeLastNameDtoResponse();

        try{
            req.validate();
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        List<User> registeredUsers = userDao.getRegisteredUsers();

        for(User u : registeredUsers){
            if(u.getToken().equals(req.getToken())){
                u.setLastName(req.getNewLastName());

                response.setNewLastName(u.getLastName());
                return gson.toJson(response);
            }
        }

        response.setError(ServerErrorCode.TOKEN_NOT_FOUND.getErrorString());
        return gson.toJson(response);
    }

    public String changeEmail(String jsonRequest)
    {
        ChangeEmailDtoRequest req = gson.fromJson(jsonRequest, ChangeEmailDtoRequest.class);
        ChangeEmailDtoResponse response = new ChangeEmailDtoResponse();

        try{
            req.validate();
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        List<User> registeredUsers = userDao.getRegisteredUsers();

        for(User u : registeredUsers){
            if(u.getToken().equals(req.getToken())){
                u.setFirstName(req.getNewEmail());

                response.setNewEmail(u.getEmail());
                return gson.toJson(response);
            }
        }

        // TODO Maybe here we should return Server.ErrorCode.TOKEN_NOT_FOUND
        response.setError(ServerErrorCode.USER_NOT_FOUND.getErrorString());
        return gson.toJson(response);
    }

    public String changePassword(String jsonRequest)
    {
        ChangePasswordDtoRequest req = gson.fromJson(jsonRequest, ChangePasswordDtoRequest.class);
        ChangePasswordDtoResponse response = new ChangePasswordDtoResponse();

        try{
            req.validate();
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        List<User> registeredUsers = userDao.getRegisteredUsers();

        for(User u : registeredUsers){
            if(u.getToken().equals(req.getToken())){
                u.setFirstName(req.getNewPassword());

                response.setNewPassword(u.getPassword());
                return gson.toJson(response);
            }
        }

        response.setError(ServerErrorCode.USER_NOT_FOUND.getErrorString());
        return gson.toJson(response);
    }

    // TODO
   /* public String removeAccount(String jsonRequest)
    {
        RemoveAccountDtoRequest request = gson.fromJson(jsonRequest, RemoveAccountDtoRequest.class);
        RemoveAccountDtoResponse response = new RemoveAccountDtoResponse();

        List<User> registeredUsers = userDao.getRegisteredUsers();

        for(User u : registeredUsers){
            if(u.getToken().equals(request.getToken())){
                userDao.
            }
        }

    }*/
}