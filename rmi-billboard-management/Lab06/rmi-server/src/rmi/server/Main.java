package rmi.server;

import billboards.IBillboard;
import billboards.IClient;
import billboards.IManager;

import javax.print.attribute.standard.MediaName;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        //Utworzenie rejestrow rmi na poszczegolnych portach.
        Registry client_registry = LocateRegistry.createRegistry(5555);
        Registry manager_registry = LocateRegistry.createRegistry(5556);
        Registry billboard_registry = LocateRegistry.createRegistry(5557);

        // Utworzenie instancji namiastek i umieszczenie w zdalnym rejestrze.
        ClientImpl client_engine= new ClientImpl();
        client_registry.rebind("IClient",client_engine);

        ManagerImpl manager_engine = new ManagerImpl();
        manager_registry.rebind("IManager",manager_engine);

        BillboardImpl billboard_engine = new BillboardImpl();
        billboard_registry.rebind("IBillboard",billboard_engine);

        System.out.println("Server is working!");
    }
}
