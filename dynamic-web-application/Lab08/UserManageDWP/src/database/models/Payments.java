package database.models;

import java.sql.Date;

public class Payments {
    private Date dateOfExecutionPayment;
    private Double paymentAmount;

    public Date getDateOfExecutionPayment() {
        return dateOfExecutionPayment;
    }

    public void setDateOfExecutionPayment(Date dateOfExecutionPayment) {
        this.dateOfExecutionPayment = dateOfExecutionPayment;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Payments(Date dateOfExecutionPayment, Double paymentAmount) {
        this.dateOfExecutionPayment = dateOfExecutionPayment;
        this.paymentAmount = paymentAmount;
    }
}
