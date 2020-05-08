package dao.implementations;

import dao.api.DAO;
import database.models.Installation;
import database.models.Payments;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PaymentsDAO implements DAO<Payments> {

    private List<Payments> paymentList = new ArrayList<>();

    public PaymentsDAO(){
        paymentList.add(new Payments(null,123.23));
    }
    @Override
    public Optional<Payments> get(long id) {
        return Optional.ofNullable(paymentList.get((int)id));
    }

    @Override
    public List<Payments> getAll() {
        return paymentList;
    }

    @Override
    public void save(Payments payments) {
        paymentList.add(payments);
    }

    @Override
    public void update(Payments payments, String[] params) throws ParseException {
        String sDate1=params[0];
        Date date1= (Date) new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

        payments.setDateOfExecutionPayment(Objects.requireNonNull(
                date1, "Date cannot be null"));
        payments.setPaymentAmount(Objects.requireNonNull(
                Double.parseDouble(params[1]), "Payment amount cannot be null"));


        paymentList.add(payments);
    }

    @Override
    public void delete(Payments payments) {
        paymentList.remove(payments);
    }
}
