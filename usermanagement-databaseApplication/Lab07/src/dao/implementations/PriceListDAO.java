package dao.implementations;

import dao.api.DAO;
import database.models.PriceList;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PriceListDAO implements DAO<PriceList> {
    public List<PriceList> priceArrayList = new ArrayList<>();


    @Override
    public Optional<PriceList> get(long id) {
        return Optional.ofNullable(priceArrayList.get((int)id));
    }

    @Override
    public List<PriceList> getAll() {
        return priceArrayList;
    }

    @Override
    public void save(PriceList priceList) {
        priceArrayList.add(priceList);
    }

    @Override
    public void update(PriceList priceList, String[] params) throws ParseException {
        priceList.setServiceType(Objects.requireNonNull(
                params[0], "Service type cannot be null"));
        priceList.setPrice(Objects.requireNonNull(
                Double.parseDouble(params[1]), "Price amount cannot be null"));
    }

    @Override
    public void delete(PriceList priceList) {
        priceArrayList.remove(priceList);
    }
}
