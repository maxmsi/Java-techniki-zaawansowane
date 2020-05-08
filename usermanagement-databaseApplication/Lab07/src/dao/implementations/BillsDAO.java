package dao.implementations;

import dao.api.DAO;
import database.models.Bills;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BillsDAO implements DAO<Bills> {

    private List<Bills> billsList = new ArrayList<>();
    @Override
    public Optional<Bills> get(long id) {
        return Optional.ofNullable(billsList.get((int) id));
    }

    @Override
    public List<Bills> getAll() {
        return null;
    }

    @Override
    public void save(Bills bills) {
        billsList.add(bills);
    }

    @Override
    public void update(Bills bills, String[] params) {
        bills.setDate(Objects.requireNonNull(
            params[0], "Date cannot be null"));
        bills.setPaymentAmount(Objects.requireNonNull(
                params[1], "Payment amount cannot be null"));

        billsList.add(bills);
    }
    @Override
    public void delete(Bills bills) {
        billsList.remove(bills);
    }
}
