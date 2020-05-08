package billboards;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Duration;
import java.util.List;

/**
 * Interfejs, ktĂłry powinna implementowaÄ aplikacja peĹniÄca rolÄ tablicy ogĹoszeniowej (nazwijmy jÄ Tablica).
 * Aplikacja ta powinna wyĹwietlaÄ cyklicznie teksty ogĹoszeĹ dostarczane metodÄ addAdvertisement.
 * Za wyĹwietlanie tych ogĹoszeĹ powinien odpowiadaÄ osobny wÄtek.
 * WÄtek powinien mieÄ dostÄp do bufora na ogĹoszenia, ktĂłrego pojemnoĹÄ i liczbÄ wolnych miejsc
 * zwraca metoda getCapacity.
 * Za dodawanie ogĹoszenia do bufora odpowiada metoda addAdvertisment.
 * Z chwilÄ pierwszego wyĹwietlenia ogĹoszenia na tablicy powinien zaczÄÄ zliczaÄ siÄ czas jego wyĹwietlania.
 * Usuwanie ogĹoszenia moĹźe nastÄpiÄ z dwĂłch powodĂłw: i) ogĹoszenie moĹźe zostaÄ usuniÄte na skutek
 * wywoĹania metody removeAdvertisement (przez MenadĹźera); ii) ogĹoszenie moĹźe zostaÄ usuniÄte, gdy skoĹczy siÄ przyznany
 * mu czas wyĹwietlania na tablicy (przez wÄtek odpowiedzialny w Tablicy za cykliczne wyĹwietlanie testĂłw).
 * Usuwanie ogĹoszeĹ z bufora i ich dodawanie do bufora wiÄĹźe siÄ z odpowiednim zarzÄdzaniem
 * podlegĹÄ strukturÄ danych
 * W "buforze" mogÄ siÄ robiÄ "dziury", bo ogĹoszenia mogÄ mieÄ przyznany rĂłĹźny czas wyĹwietlania.
 * NaleĹźy wiÄc wybraÄ odpowiedniÄ strukturÄ danych, ktĂłra pozwoli zapomnieÄ o nieregularnym wystÄpowaniu tych "dziur").
 * Metoda start powinna daÄ sygnaĹ aplikacji, Ĺźe naleĹźy rozpoczÄÄ cykliczne wyĹwietlanie ogĹoszeĹ.
 * Metoda stop zatrzymuje wyĹwietlanie ogĹoszeĹ.
 * Metody start i stop moĹźna odpalaÄ naprzemiennie, przy czym nie powinno to resetowaÄ zliczonych czasĂłw wyĹwietlania
 * poszczegĂłlnych ogĹoszeĹ.
 * Uwaga: Tablica powininna byÄ sparametryzowany numerem portu i hostem rejestru
 * rmi, w ktĂłrym zarejestrowano namiastkÄ MenadĹźera, oraz nazwa, pod ktĂłrÄ
 * zarejestrowano tÄ namiastkÄ.
 * Jest to potrzebne, by Tablica mogĹa dowiÄzaÄ siÄ do MenadĹźera.
 * Tablica robi to wywoĹujÄc metodÄ bindBillboard (przekazujÄc jej swojÄ namiastkÄ typu IBillboard).
 * Otrzymuje przy tym identyfikator, ktĂłry moĹźe uĹźyÄ, by siÄ mogĹa wypisaÄ z MenadĹźera
 * (co moĹźe staÄ siÄ podczas zamykania tablicy).
 *
 * @author tkubik
 *
 */

public interface IBillboard extends Remote {
    /**
     * Metoda dodajÄca tekst ogĹoszenia do tablicy ogĹoszeniowej (wywoĹywana przez
     * MenadĹźera po przyjÄciu zamĂłwienia od Klienta)
     *
     * @param advertText   - tekst ogĹoszenia, jakie ma pojawiÄ siÄ na tablicy
     *                      ogĹoszeniowej
     * @param displayPeriod - czas wyĹwietlania ogĹoszenia liczony od pierwszego
     *                      jego ukazania siÄ na tablicy ogĹoszeniowej
     * @param orderId       - numer ogĹoszenia pod je zarejestrowano w menadĹźerze
     *                      tablic ogĹoszeniowych
     * @return - zwraca true, jeĹli udaĹo siÄ dodaÄ ogĹoszenie lub false w
     *         przeciwnym wypadku (gdy tablica jest peĹna)
     * @throws RemoteException
     */
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException;

    /**
     * Metoda usuwajÄca ogĹoszenie z tablicy (wywoĹywana przez MenadĹźera po
     * wycofaniu zamĂłwienia przez Klienta)
     *
     * @param orderId - numer ogĹoszenia pod jakim je zarejestrowano w menadĹźerze
     *                tablic ogĹoszeniowych
     * @return - zwraca true, jeĹli operacja siÄ powiodĹa lub false w przeciwnym
     *         wypadku (gdy nie ma ogĹoszenia o podanym numerze)
     * @throws RemoteException
     */
    public boolean removeAdvertisement(int orderId) throws RemoteException;

    /**
     * Metoda pobierajÄca informacjÄ o zajÄtoĹci tablicy (wywoĹywana przez
     * MenadĹźera)
     *
     * @return - zwraca tablicÄ dwĂłch liczb caĹkowitych - pierwsza to pojemnoĹÄ
     *         bufora tablicy, druga to liczba wolnych w nim miejsc
     * @throws RemoteException
     */
    public int[] getCapacity() throws RemoteException;

    /**
     * Metoda pozwalajÄca ustawiÄ czas prezentacji danego tekstu ogĹoszenia na
     * tablicy w jednym cyklu (wywoĹywana przez MenadĹźera). ProszÄ nie myliÄ tego z
     * czasem, przez jaki ma byÄ wyĹwietlany sam tekst ogĹoszenia. Prezentacja
     * danego hasĹa musi byÄ powtĂłrzona cyklicznie tyle razy, aby sumaryczny czas
     * prezentacji byĹ rĂłwny lub wiÄkszy zamĂłwionemu czasowi wyĹwietlania tekstu
     * ogĹoszenia.
     *
     * @param displayInterval - definiuje czas, po ktĂłrym nastÄpuje zmiana hasĹa
     *                        wyĹwietlanego na tablicy. OdwrotnoĹÄ tego parametru
     *                        moĹźna interpetowaÄ jako czÄstotliwoĹÄ zmian pola
     *                        reklamowego na Tablicy.
     * @throws RemoteException
     */
    public void setDisplayInterval(Duration displayInterval) throws RemoteException;

    /**
     * Metoda startujÄca cykliczne wyĹwietlanie ogĹoszeĹ (wywoĹywana przez
     * MenadĹźera)
     *
     * @return - zwraca true, jeĹli zostanie poprawnie wykonana lub false w
     *         przeciwnym wypadku
     * @throws RemoteException
     */
    public boolean start() throws RemoteException;

    /**
     * Metoda stopujÄca cykliczne wyĹwietlanie ogĹoszeĹ (wywoĹywana przez MenadĹźera)
     *
     * @return - zwraca true, jeĹli zostanie poprawnie wykonana lub false w
     *         przeciwnym wypadku
     * @throws RemoteException
     */
    public boolean stop() throws RemoteException;

    //Metoda zwracaja liste reklam dla billboardu
    public List<AdvertToShow> getAdvertList() throws RemoteException;
    public void  updateAdvertList(AdvertToShow advertList) throws  RemoteException;

    //metoda zwracajaca true jesli status tablicy jest ustawiony na prace.
    public boolean getWorkStatus() throws RemoteException ;

    public Duration getDisplayInterval() throws RemoteException;
    public Duration getAdvertDuration(AdvertToShow e) throws RemoteException;
    public int getAdvertById(int OrderId)throws RemoteException;
}