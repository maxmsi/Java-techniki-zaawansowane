package database.models;

public class Installation {
    private String addres;
    private String routherNumber;
    private String serviceType;

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getRoutherNumber() {
        return routherNumber;
    }

    public void setRoutherNumber(String routherNumber) {
        this.routherNumber = routherNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Installation(String addres, String routherNumber, String serviceType) {
        this.addres = addres;
        this.routherNumber = routherNumber;
        this.serviceType = serviceType;
    }
}
