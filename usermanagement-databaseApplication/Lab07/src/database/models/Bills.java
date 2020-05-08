package database.models;



public class Bills {
    private String Date;
    private String paymentAmount;

    public Bills(String String, String paymentAmount) {
        this.Date = String;
        this.paymentAmount = paymentAmount;
    }

    public String getString() {
        return Date;
    }

    public void setDate(String String) {
        this.Date = String;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
