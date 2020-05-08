package rmi.server;

import billboards.IClient;
import billboards.Order;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;

public class ClientImpl extends UnicastRemoteObject implements IClient {

    public int orderId=0;
    Order order;

    public ClientImpl() throws RemoteException, MalformedURLException, NotBoundException {
    }


    @Override
    public void setOrderId(Order order,int neworderId) throws  RemoteException{
        order.orderId=neworderId;
    }

    @Override
    public void setOrderId(int i) throws RemoteException {

    }

    @Override
    public void echo(String s) throws RemoteException {
            System.out.println(s);
    }

    @Override
    public Order getOrder(int i) throws RemoteException {

        return null;
    }

    @Override
    public Order createOrder(String advertText, Duration duration, IClient client) throws RemoteException {
        Order order=new Order(advertText,duration,client);
        return order;
    }


}
