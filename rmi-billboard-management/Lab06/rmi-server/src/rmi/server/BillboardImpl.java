package rmi.server;

import billboards.AdvertToShow;
import billboards.IBillboard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BillboardImpl extends UnicastRemoteObject implements IBillboard {

    public List<AdvertToShow> advertList= new ArrayList<>();

    //false jesli billbaord nie pracuje.
    public boolean workStatus=false;
    public Duration displayInterval;

    public BillboardImpl() throws RemoteException {
    }

    @Override
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
        AdvertToShow tempObject = new AdvertToShow(advertText,displayPeriod,orderId);
        advertList.add(tempObject);
        System.out.println("dodane do advertList");
        return true;
    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        for (AdvertToShow obj : advertList) {
            if (obj.getAdvertId() == orderId) {
                advertList.remove(obj);
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return new int[0];
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {
            this.displayInterval=displayInterval;
    }

    @Override
    public boolean start() throws RemoteException {
        this.workStatus=true;
        return false;
    }

    @Override
    public boolean stop() throws RemoteException {
        return false;
    }

    @Override
    public List<AdvertToShow> getAdvertList() throws RemoteException {
        return advertList;
    }

    @Override
    public void updateAdvertList(AdvertToShow advertToShow) throws RemoteException {
        this.advertList= (List<AdvertToShow>) advertToShow;
    }

    @Override
    public boolean getWorkStatus() throws RemoteException {
        return workStatus;
    }

    @Override
    public Duration getDisplayInterval() throws RemoteException {
        return displayInterval;
    }

    @Override
    public Duration getAdvertDuration(AdvertToShow advertToShow) {
        return null;
    }

    @Override
    public int getAdvertById(int i) throws RemoteException {
        return 0;
    }


}
