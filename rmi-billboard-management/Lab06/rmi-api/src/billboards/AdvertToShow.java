package billboards;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.Duration;
//Pomocncza klasa do utworzenia modelu poszczegolnej reklamy dla aplikacji Billboardu.

public class AdvertToShow implements Serializable {
    String advertText;
    Duration duration;
    int orderId;

   public AdvertToShow(String advertText, Duration duration, int order)  {
        this.advertText = advertText;
        this.duration = duration;
        this.orderId = order;
    }

    public String getAdvertText() {
        return advertText;
    }
    public Duration getAdvertDuration()  {
        return duration;
    }
    public void setAdvertDuration(Duration duration)  {
        this.duration=duration;
    }
    public int getAdvertId(){
       return this.orderId;
    }

}