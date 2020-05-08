package billboards;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfejs, ktĂłry powinna implementowaÄ aplikacja peĹniÄca rolÄ menadĹźera
 * tablic (nazwijmy jÄ MenadĹźer). MenadĹźer powinien wyĹwietlaÄ wszystkie
 * dowiÄzane do niego tablice oraz ich bieĹźÄcy stan. Tablice dowiÄzujÄ siÄ do i
 * odwiÄzujÄ od menadĹźera wywoĹujÄc jego metody bindBillboard oraz
 * unbindBillboard. Z menadĹźerem moĹźe poĹÄczyÄ siÄ Klient przekazujÄc mu
 * zamĂłwienie wyĹwietlania danego ogĹoszenia przez zadany czas. Robi to
 * wywoĹujÄc metodÄ placeOrder. MenadĹźer, jeĹli przyjmie zamĂłwienie, zwraca
 * Klientowi numer zamĂłwienia wykorzystujÄc przy tym przekazanÄ w zamĂłwieniu
 * namiastke. Klient rozpoznaje, czy przyjÄto zamĂłwienie, sprawdzajÄc wynik
 * zwracany z metody placeOrder.
 * ZamĂłwienia natychmiast po przyjÄciu trafiajÄ na dowiÄzane Tablice mogÄce w danej chwili przyjÄÄ ogĹoszenie do wyĹwietlania.
 * JeĹli w danej chwili nie ma Ĺźadnej wolnej Tablicy zamĂłwienie nie powinno byÄ przyjÄte do realizacji.
 * Aby przekonaÄ siÄ o stanie tablic MenadĹźer wywoĹuje metody ich namiastek getCapacity.
 * Wystarczy, Ĺźe istnieje jedna wolna tablica by przyjÄÄ zamĂłwienie.
 * Na ile tablic trafi dane zamĂłwienie decyduje dostÄpnoĹÄ wolnych miejsc w chwili zamĂłwienia.
 *
 * Uwaga: MenadĹźer powinien utworzyÄ lub poĹÄczyÄ siÄ z rejestrem rmi o
 * wskazanym numerze portu. ZakĹadamy, Ĺźe rejestr rmi dziaĹa na tym samym
 * komputerze, co MenadĹźer (moĹźe byÄ czÄĹciÄ aplikacji MenadĹźera).
 * MenadĹźer rejestruje w rejestrze rmi posiadanÄ
 * namiastke IManager pod zadanÄ nazwÄ (nazwa ta nie moĹźe byÄ na twardo
 * zakodowanym ciÄgiem znakĂłw). Nazwa namiastki menadĹźera, host i port na ktĂłrym
 * dziaĹa rejest rmi powinny byÄ dostarczone Klientowi (jako parmetry) oraz Tablicom.
 *
 * @author tkubik
 *
 */
public interface IManager extends Remote { // port, nazwa, GUI

    public void echo(String s) throws RemoteException;

    /**
     * Metoda dowiÄzywania namiastki Tablicy do MenadĹźera (wywoĹywana przez TablicÄ)
     *
     * @param billboard - dowiÄzywana namiastka
     * @return - zwraca numer przyznany namiastce w MenadĹźerze
     * @throws RemoteException
     */
    public int bindBillboard(IBillboard billboard) throws RemoteException;

    /**
     * Metoda odwiÄzujÄca namiastkÄ Tablicy z MenadĹźera (wywoĹywana przez TablicÄ)
     * @param billboardId - numer odwiÄzywanej namiastki
     * @return
     * @throws RemoteException
     */
    public boolean unbindBillboard(int billboardId) throws RemoteException;

    /**
     * Metoda sĹuĹźÄca do skĹadania zamĂłwienia wyĹwietlania ogĹoszenia (wywoĹywana przez Klienta)
     * @param order - szczegĂłĹy zamĂłwienia (wraz z tekstem ogĹoszenia, czasem jego wyĹwietlania i namiastkÄ klienta)
     * @return - zwraca true jeĹli przyjÄto zamĂłwienie oraz false w przeciwnym wypadku
     * @throws RemoteException
     */
    public int placeOrder(Order order) throws RemoteException, MalformedURLException, NotBoundException;

    /**
     * Metoda sĹuĹźÄca do wycofywania zamĂłwienia (wywoĹywana przez Klienta)
     * @param orderId - numer wycofywanego zamĂłwienia
     * @return - zwraca true jeĹli wycofano zamĂłwienie oraz false w przeciwnym wypadku
     * @throws RemoteException
     */
    public boolean withdrawOrder(int orderId) throws RemoteException;
    //Metoda sprawdzajaca stan przypisania tablicy do menadzera.
    public boolean checkBillboardStatus() throws RemoteException;
}

