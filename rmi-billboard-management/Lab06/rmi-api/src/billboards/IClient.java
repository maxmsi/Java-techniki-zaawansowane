package billboards;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Duration;

/**
 * Interfejs, ktĂłry powinien zaimplementowaÄ klient (nazwijmy tÄ alikacjÄ
 * Klient) komunikujÄcy siÄ z menadĹźerem tablic. Klient powinien mieÄ interfejs
 * pozwalajÄcy: i) definiowaÄ zamĂłwienia wyĹwietlania ogĹoszenia (tekst
 * ogĹoszenia, czas wyĹwietlania) ii) skĹadaÄ zamĂłwienia wyĹwietlania ogĹoszenia
 * MenadĹźerowi, iii) wycofywaÄ zĹoĹźone zamĂłwienia.
 *
 * Przy okazji skĹadania zamĂłwienia wyĹwietlania ogĹoszenia Klient przekazuje
 * MenadĹźerowi namiastkÄ IClient. MenagdĹźer uĹźyje tej namiastki, by zwrotnie
 * przekazaÄ klientowi numer zamĂłwienia (jeĹli oczywiĹcie zamĂłwienie zostanie
 * przyjÄte). Ma to dziaĹaÄ podobnie jak ValueSetInterface z przykĹadu RMITest.
 *
 * Numery zamĂłwieĹ i treĹci ogĹoszeĹ przyjÄtych przez MenadĹźera powinny byÄ
 * widoczne na interfejsie Klienta. Klient powinien sam zadbaÄ o usuwanie
 * wpisĂłw, ktĂłrych okres wyĹwietlania zakoĹczyĹ siÄ (brak synchronizacji w tym
 * wzglÄdzie z menadĹźerem)
 *
 * Uwaga: Klient powinien byÄ sparametryzowany numerem portu i hostem rejestru
 * rmi, w ktĂłrym zarejestrowano namiastkÄ MenadĹźera, oraz nazwa, pod ktĂłrÄ
 * zarejestrowano tÄ namiastkÄ.
 *
 * @author tkubik
 *
 */
public interface IClient extends Remote { // host, port, nazwa
    /**
     * Metoda sĹuĹźÄca do przekazania numeru przyjÄtego zamĂłwienia (wywoĹywana przez
     * MenadĹźera na namiastce klienta przekazanej w zamĂłwieniu)
     *
     * @param orderId
     * @throws RemoteException
     */
    public void setOrderId(int orderId) throws RemoteException;
    public void echo(String s) throws RemoteException;
    public Order getOrder(int orderId) throws RemoteException;
    public void setOrderId(Order order,int neworderId) throws RemoteException;
    public Order createOrder(String advertText, Duration duration, IClient client) throws RemoteException;

}

