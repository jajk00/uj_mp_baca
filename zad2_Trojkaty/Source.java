//Jakub Kozub - 3
import java.util.Scanner;
//Idea programu polega na przejsciu w dwoch zagniezdzonych petlach po mozliwych kombinacjach bez powtorzen dwoch pierwszych bokach trojkata w uprzednio posortowanej tablicy,
    //a nastepnie binarnym wyszukaniu najwiekszego indeksu elementu, ktory jako dlugosc odcinka spelnialby przy danych w biezacej iteracji dlugosciach dwoch innych bokow nierownosc trojkata, 
    //czyli moze byc trzecim jego bokiem.
//Jako, ze tablica jest na tym etapie uporzadkowana niemalejaco wiemy, ze wszystkie jej elementy o indeksach mniejszych od wyznaczonego zawieraja elementy mniejsze lub rowne, 
    //czyli rowniez spelniajace warunek i mogace byc trzecim bokiem trojkata dla danych dwoch pierwszych bokow.
//Wiemy zatem, ze dla danych dwoch bokow o indeksach a, b mozemy zbudowac trojkat o bokach pod indeksami a, b, c dla wszystkich indeksow od nastepujacego bezposrednio po b (czyli b+1),
    //az do wyznaczonego w wyzej wspomnianym wyszukiwaniu.
//Znajac zakres mozliwych indeksow c spelniajacych warunki, mozemy wyznaczyc liczbe trojkatow mozliwych do zbudowania przy danych w biezacej iteracji indeksow a i b jako liczbe indeksow
    //nalezacych do tego zakresu oraz iterujac po nich w kolejnej petli wypisac mozliwe trojki indeksow dlugosci odcinkow.
//Uzycie kolejnej zagniezdzonej petli jest mozliwe przy zachowaniu zalozonej w zadaniu zlozonosci obliczeniowej, gdyz liczba jej wykonan nie jest bezposrednio zalezna od n, a na dodatek jeden
    // z jej warunkow zapewnia, ze nie wykona sie ona wiecej niz 10 razy. 
    //Zlozonosc wynosi zatem O((n^2)*log2(n)), gdyz mamy dwie zagniezdzone petle iterujace po wiekszosci elementow tablicy, wewnatrz ktorych wykonujemy wyszukiwanie binarne (O(logn)) w tej tablicy.

class Source {
    public static Scanner input = new Scanner(System.in);
    public static int n; //liczba odcinkow wczytywanego zestawu
    public int[] T; //deklaracja glownej tablicy przechowujacej zadane na wejsciu dlugosci odcinkow
    public static int datasets; //deklaracja zmiennej przechowujacej liczbe zestawow
    
    public Source() { //konstruktor
        T = new int[100]; //utworzenie glownej tablicy przechowujacej zadane dlugosci odcinkow
    }

    public void readInput() { //metoda wczytujaca zawartosc obecnego zestawu i wpisujaca dlugosci odcinkow do tablicy
        n = input.nextInt(); //wczytanie liczby odcinkow
        for (int i = 0; i < n; i++) {
            T[i] = input.nextInt(); //wczytanie elementow tablicy
        }
    }

    public void insertionSort() { //metoda realizujaca sortowanie tablicy przez proste wstawianie
        int tmp;
        int j;
        for (int i = 0; i < n; i++) {
            tmp = T[i];
            j = i - 1;
            while (j >= 0 && tmp < T[j]) {
                T[j+1] = T[j];
                j--;
            }
            T[j+1] = tmp;
        }
    }

    public int searchLastLessEq(int start, int key) { //metoda wyszukujaca binarnie najwiekszy indeks fragmentu tablicy 
        //(ograniczonego od dolu zadanym w argumencie indeksem poczatkowym), pod ktorym znajduje sie element mniejszy lub rowny kluczowi
        int end = n-1;
        int current;
        int ans = -1;
        while (start <= end) {
            current = (start + end)/2; //srodkowy indeks 
            if (T[current] > key) { // jesli klucz jest wiekszy od elementu pod srodkowym indeksem tablicy
                end = current - 1; // to przechodzimy do podtablicy na lewo od srodka
            }
            else {
                ans = current;
                start = current + 1;
            }
        }
        return ans;
    }

    public void printArray() { //metoda wypisujaca na wyjscie kolejne elementy tablicy
        String array = new String(""); //zmienna napisowa, do ktorej dodane zostana kolejne elementy tablicy wraz z odpowiednimi separatorami
        for (int i = 0; i < n; i++) {
            if (i % 25 == 0) { //co 25 wypisanych elementow
                if (i != 0) //za wyjatkiem pierwszego
                    array += System.lineSeparator(); //przejscie do nowej linii
            }
            else {
                array += " ";
            }
            array += T[i];
        }
        System.out.println(array); //wypisanie wyniku na wyjscie
    }

    public void triangles() { //metoda sprawdzajaca mozliwosc utworzenia trojkatow z zawartych w posortowanej tablicy dlugosci odcinkow oraz wypisujaca 
        //ich liczbe i liste uporzadkowanych leksykograficznie co najwyzej 10 pierwszych trojkek zawierajace indeksy odcinkow, z ktorych mozna je zbudowac
        int numberOfTriangles = 0; // zmienna przechowujaca liczbe mozliwych do zbudowania trojkatow
        int a, b, c; //indeksy kolejnych dlugosci odcinkow
        int maxLength; // maksymalna dlugosc trzeciego odcinka
        int maxLengthIndex; //najwiekszy indeks tablicy zawierajacy dlugosc mniejsza lub rowna maksymalnej dlugosci trzeciego odcinka
        int displayed = 0; //zmienna zliczajaca juz wypisane trojki
        String output = new String(""); //zmienna napisowa, w ktorej zapisywane beda kolejne trojki indeksow odcinkow
        
        for (a = 0; a < n-2; a++) { //petla iterujaca po mozliwych indeksach dlugosci pierwszego odcinka
            for (b = a + 1; b < n-1; b++) { //petla iterujaca po mozliwych indeksach dlugosci drugiego odcinka
                maxLength = T[a] + T[b] - 1; //maksymalna calkowita dlugosc trzeciego odcinka trojkata, dla danych dlugosci dwoch pierwszych
                maxLengthIndex = searchLastLessEq(b+1, maxLength); //wyszukiwanie najwiekszego indeksu zawierajacego dlugosc mniejsza lub rowna powyzszej
                if (maxLengthIndex != -1) { //sprawdzenie czy w tablicy znaleziono element spelniajacy w/w warunek
                    numberOfTriangles += (maxLengthIndex - (b + 1) + 1);
                    c = b + 1; //ustawienie indeksu trzeciego odcinka na nastepny po indeksie drugiego odcinka
                    while ((c <= maxLengthIndex) && (displayed < 10)) { //petla iterujaca po mozliwych indeksach trzeciego boku
                        output += "(" + a + "," + b + "," + c + ") "; //dopisanie do zmiennej trojki indeksow
                        c++;
                        displayed++;
                    }
                }
            }
        }
        //wypisanie wyniku:
        if (numberOfTriangles == 0) { //przypadek, w ktorym nie da sie zbudowac trojkata dla danych dlugosci odcinkow
            System.out.println("Triangles cannot be built");
        } else { //przypadek, w ktorym da sie zbudowac co najmniej 1 trojkat z odcinkow o danych dlugosciach
            System.out.println(output+System.lineSeparator()+"Number of triangles: "+numberOfTriangles);
        }  
        
    }
    
    public static void main(String[] args) {
        datasets = input.nextInt(); //wczytanie liczby zestawow
        Source a1 = new Source(); //utworzenie obiektu klasy Source
        for (int s = 1; s <= datasets; s++) { //petla iterujaca po kolejnych zestawach
            a1.readInput(); //wczytanie elementow tablicy
            a1.insertionSort(); //uporzadkowanie niemalejace elementow tablicy
            System.out.println(s+": n= "+n); //wypisanie na wyjscie numeru obecnego zestawu oraz liczby odcinkow w tym zestawie
            a1.printArray(); //wypisanie na wyjscie elementow tablicy
            a1.triangles(); //wywolanie metody sprawdzajacej mozliwosc utworzenia trojkatow, wypisujaca ich liczbe i liste trojkek indeksow odcinkow
        } //koniec petli iterujacej po kolejnych zestawach
    }
}

/*
test.in:
12
6
1 2 3 4 4 7
5
4 3 6 7 3
3
1 2 3
5
2 2 2 2 2
6
4 5 4 5 2 1
3
1 1 1
4
4 2 5 2
4
3 5 6 2
5
2 4 6 3 1
4
7 1 1 1
6
9 9 9 7 1 2
4
2 1 3 11

test.out:
1: n= 6
(0,3,4) (1,2,3) (1,2,4) (1,3,4) (2,3,4) (3,4,5)
Number of triangles: 6
2: n= 5
(0,1,2) (0,2,3) (0,3,4) (1,2,3) (1,3,4) (2,3,4)
Number of triangles: 6
3: n= 3
Triangles cannot be built
4: n= 5
(0,1,2) (0,1,3) (0,1,4) (0,2,3) (0,2,4) (0,3,4) (1,2,3) (1,2,4) (1,3,4) (2,3,4)
Number of triangles: 10
5: n= 6
(0,2,3) (0,4,5) (1,2,3) (1,2,4) (1,2,5) (1,3,4) (1,3,5) (1,4,5) (2,3,4) (2,3,5)
Number of triangles: 12
6: n= 3
(0,1,2)
Number of triangles: 1
7: n= 4
(0,2,3) (1,2,3)
Number of triangles: 2
8: n= 4
2 3 5 6
(0,2,3) (1,2,3)
Number of triangles: 2
9: n= 5
(1,2,3) (2,3,4)
Number of triangles: 2
10: n= 4
(0,1,2)
Number of triangles: 1
11: n= 6
(0,3,4) (0,3,5) (0,4,5) (1,3,4) (1,3,5) (1,4,5) (2,3,4) (2,3,5) (2,4,5) (3,4,5)
Number of triangles: 10
12: n= 4
1 2 3 11
Triangles cannot be built

*/