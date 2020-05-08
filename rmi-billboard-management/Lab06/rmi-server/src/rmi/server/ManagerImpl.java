package rmi.server;

import billboards.IBillboard;
import billboards.IManager;
import billboards.Order;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

public class ManagerImpl extends UnicastRemoteObject implements IManager {
    public boolean BillboardStatus = false;
    final AtomicInteger orderId = new AtomicInteger( 0 ) ;


    public ManagerImpl() throws RemoteException, MalformedURLException, NotBoundException {
    }


    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        this.BillboardStatus=true;
        return 0;
    }

    public void echo(String s) throws RemoteException{
        System.out.println(s);
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        this.BillboardStatus=false;
        return false;
    }


    @Override
    public int placeOrder(Order order) throws RemoteException, MalformedURLException, NotBoundException {

       if(true){
            // zamowienie przyjete.
            order.client.setOrderId(orderId.incrementAndGet());
            this.BillboardStatus=true;
            return orderId.get();
        }
  else{
        return 0;
    }}

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        return false;
    }

    @Override
    public boolean checkBillboardStatus() throws RemoteException {
        return BillboardStatus;
    }

}
