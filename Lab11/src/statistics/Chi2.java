package statistics;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Klasa z metodami natywnymi wykorzystywana do wykonywania testu statystycznego chi2
 * @author tkubik
 *
 */
public class Chi2 {
    static public Double[] observed;
    static public Double[] expected;
    static public Double[] calculated;

    static {
        System.loadLibrary("native");
    }

    public static void main(String[] args) {
        int userChoice = 0;

        /*********************************************************/
        while(userChoice!=4) {
            userChoice = menu();
            switch(userChoice) {
                case 1:
                    int n;
                    Scanner s = new Scanner(System.in);
                    System.out.print("Enter no. of elements you want in array:");

                    n = s.nextInt();
                    observed= new Double[n];

                    System.out.println("Enter all the observed elements:");
                    for(int i = 0; i < n; i++)
                    {   try {
                        observed[i] = s.nextDouble();
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Podałeś liczbę w złym formacie, dla double podaj 'x,x' ");
                    }
                    }
                    expected= new Double[n];
                    System.out.println("Enter all the expected elements:");
                    for(int i = 0; i < n; i++)
                    {   try {
                        expected[i] = s.nextDouble();
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Podałeś liczbę w złym formacie, dla double podaj 'x,x' ");
                    }
                    }
                    calculated= new Double[n+1];
                    calculated= calculate0(observed,expected);
                    System.out.println("----=====RESULT=====-----\n χ2j=(Oj - Ej)2/Ej \t");
                    for (int i = 0; i < calculated.length; i++) {
                        if(i==calculated.length-1){
                            System.out.println("Σχ2= "+calculated[i]);
                            break;
                        }
                        System.out.println("At index["+i+"]= "+calculated[i]);
                    }
                    break;
                case 2:
                    int n2;
                    Scanner s2 = new Scanner(System.in);
                    System.out.print("Enter no. of elements you want in array:");

                    n2 = s2.nextInt();
                    observed= new Double[n2];

                    System.out.println("Enter all the observed elements:");
                    for(int i = 0; i < n2; i++)
                        {   try {
                                observed[i] = s2.nextDouble();
                                }
                            catch (InputMismatchException e) {
                                System.out.println("Podałeś liczbę w złym formacie, dla double podaj 'x,x' ");
                            }
                        }
                    expected= new Double[n2];
                    System.out.println("Enter all the expected elements:");
                    for(int i = 0; i < n2; i++)
                        {   try {
                            expected[i] = s2.nextDouble();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Podałeś liczbę w złym formacie, dla double podaj 'x,x' ");
                        }
                        }
                    calculate(observed);
                    break;
                case 3:
                    calculated = new Chi2().calculate();
                    System.out.println("----=====RESULT=====-----\n χ2j=(Oj - Ej)2/Ej \t");
                    for (int i = 0; i < calculated.length; i++) {
                        if(i==calculated.length-1){
                            System.out.println("Σχ2= "+calculated[i]);
                            break;
                        }
                        System.out.println("At index["+i+"]= "+calculated[i]);
                    }
                break;

                case 4:
                // code block
                break;
                default:
                    // code block
            }
        }
    }

    // Declare a native method sayHello() that receives no arguments and returns void
    private native void  sayHello();

    /**
     * Metoda natywna pozwalajÄca przeprowadziÄ test statystyczny.
     * Aplikacja Javy z niej korzystajÄca powinna:
     * - dostarczyÄ formularz na wprowadzenie obserwowanych oraz oczekiwanych danych (z opcjÄ wczytania danych z pliku)
     * - obliczyÄ wartoĹÄ chi2 za pomocÄ opisywanej metody natywnej (przekazujÄc jej atrybuty)
     * - wyĹwietliÄ wyniki
     *
     * @param observed - tablica z danymi wejĹciowymi obserwowanymi
     * @param expected - tablica z danymi wejĹciowymi oczekiwanymi
     * @return - tablica z danymi wynikowymi
     */
    public static native Double[] calculate0(Double[] observed, Double[] expected);

    /**
     * Metoda natywna pozwalajÄca przeprowadziÄ test statystyczny.
     * Aplikacja Javy z niej korzystajÄca powinna:
     * - dostarczyÄ formularz na wprowadzenie obserwowanych oraz oczekiwanych danych (z opcjÄ wczytania danych z pliku),
     *      dane oczekiwane majÄ byÄ wstawione do Ĺrodka instancji wykorzystywanej klasy z opisywanÄ metodÄ natywnÄ,
     *      dane obserwowane majÄ byÄ przekazane do metody natywnej,
     * - obliczyÄ wartoĹÄ chi2 za pomocÄ metody natywnej,
     * 		Po stronie kodu natywnego trzeba bÄdzie siÄgnÄÄ do pola expected instancji klasy,
     *      wziÄÄ przekazany atrybut observed i przeprowadziÄ test,
     *      wpisaÄ wyniki do pola calculated instancji klasy,
     * - wyĹwietliÄ wyniki
     *
     * @param observed - tablica z danymi wejĹciowymi obserwowanymi
     */
    public static native void calculate(Double[] observed);

    /**
     * Metoda natywna pozwalajÄca przeprowadziÄ test statystyczny.
     * Aplikacja Javy z niej korzystajÄca powinna:
     *  - uruchomiÄ obliczenia i wyĹwietliÄ wyniki
     * Po stronie kodu natywnego trzeba bÄdzie
     * 	- wprowadziÄ dane wejĹciowe obserwowane i oczekiwane (za pomocÄ formularza lub wczytujÄc dane z pliku,
     *    przy czym implementacja "okienek formularzy" po stonie kodu natywnego moĹźe byÄ zrobiona natywnie
     *    lub z wykorzystaniem klas Java)
     *  - przekazaÄ wynik testu do kodu Java
     *
     * @return - tablica z danymi wynikowymi
     */
    public static native Double[] calculate();

    public static int menu() {

        int selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/

        System.out.println("Choose from these choices");
        System.out.println("-------------------------\n");
        System.out.println("1 - Run Double[] calculate0(Double[] observed, Double[] expected) method.");
        System.out.println("2 - Run void calculate(Double[] observed) method.");
        System.out.println("3 - Run Double[] calculate() method. ");
        System.out.println("4 - Quit");

        selection = input.nextInt();
        return selection;
    }
}

