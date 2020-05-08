package database.models;

public class PriceList {
    private String serviceType;
    private Double price;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PriceList(String serviceType, Double price) {
        this.serviceType = serviceType;
        this.price = price;
    }
}
