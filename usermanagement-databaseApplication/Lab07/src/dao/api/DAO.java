package dao.api;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t, String[] params) throws ParseException;

    void delete(T t);
}
