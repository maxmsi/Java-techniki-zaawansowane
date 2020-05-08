package dao.implementations;

import database.models.User;
import dao.api.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDAO implements DAO<User> {
    public static List<User> users = new ArrayList<>();


    public UserDAO() {
        users.add(new User("John", "Blake","236HDG4"));
        users.add(new User("Susan", "Kluczynsky","327UJG47"));
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get((int) id));
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(User user, String[] params) {
        user.setName(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        user.setSecondName(Objects.requireNonNull(
                params[1], "Second name cannot be null"));
        user.setSecondName(Objects.requireNonNull(
                params[2], "User account number name cannot be null"));

        users.add(user);
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }

}
