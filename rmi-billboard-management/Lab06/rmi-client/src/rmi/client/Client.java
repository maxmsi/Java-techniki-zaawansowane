package rmi.client;

import billboards.IClient;
import billboards.IManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry client_registry = LocateRegistry.getRegistry("localhost",5555);
        IClient client_stub = (IClient) client_registry.lookup("IClient");
        Registry manager_registry = LocateRegistry.getRegistry("localhost", 5556);
        IManager manager_stub = (IManager) manager_registry.lookup("IManager");
        System.out.println(manager_stub.checkBillboardStatus());

    }
}
