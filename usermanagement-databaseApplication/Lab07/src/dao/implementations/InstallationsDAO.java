package dao.implementations;

import dao.api.DAO;
import database.models.Bills;
import database.models.Installation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InstallationsDAO implements DAO<Installation> {

    private List<Installation> installationList = new ArrayList<>();

    @Override
    public Optional<Installation> get(long id) {
        return Optional.ofNullable(installationList.get((int) id));
    }

    @Override
    public List<Installation> getAll() {
        return installationList;
    }

    @Override
    public void save(Installation installation) {
        installationList.add(installation);
    }

    @Override
    public void update(Installation installation, String[] params) {
        installation.setAddres(Objects.requireNonNull(
                params[0], "Addres cannot be null"));
        installation.setRoutherNumber(Objects.requireNonNull(
                params[1], "Routher number cannot be null"));
        installation.setServiceType(Objects.requireNonNull(
                params[2], "Service type account number name cannot be null"));

        installationList.add(installation);
    }

    @Override
    public void delete(Installation installation) {
        installationList.remove(installation);
    }
}
