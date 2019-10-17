package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.server.Database;
import net.thumbtack.school.hiring.server.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl
{
    private Database db = Database.getInstance();

    public List<User> getRegisteredUsers()
    {
        return db.getRegisteredUsers();
    }

}
