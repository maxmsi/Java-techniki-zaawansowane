package billboards;

import java.io.Serializable;
import java.time.Duration; // available since JDK 1.8

/**
 * Klasa reprezentujÄca zamĂłwienie wyĹwietlania ogĹoszenia o zadanej treĹci
 * przez zadany czas ze wskazaniem na namiastke klienta, przez ktĂłrÄ moĹźna
 * przesĹaÄ informacje o numerze zamĂłwienia w przypadku jego przyjÄcia
 */
public class Order implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String advertText;
    public Duration displayPeriod;
    public IClient client;
    public int orderId;

    public Order(String advertText, Duration displayPeriod, IClient client) {
        this.advertText = advertText;
        this.displayPeriod = displayPeriod;
        this.client = client;
        //by default
        this.orderId=999;
    }

    public void setOrderId(int orderId){
        this.orderId=orderId;
    }
}