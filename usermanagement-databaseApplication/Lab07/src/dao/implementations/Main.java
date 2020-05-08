package dao.implementations;

import dao.api.DAO;
import database.models.User;

import java.util.Optional;

public class Main {

    private static DAO<User> userDAO;

    public static void main(String[] args) {
        userDAO=new UserDAO();
        User user1 = getUser(0);
        System.out.println(user1.getSecondName());
    }

    private static User getUser(long id) {
        Optional<User> user = userDAO.get(id);

        return user.orElseGet(
                () -> new User("non-existing user", "no-email","NULL"));
    }
}
