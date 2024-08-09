//Jakub Kozub - 3
import java.util.Scanner;
//Idea programu polega na rozszerzeniu algorytmu Kadane na tablice dwuwymiarowe.
//Odbywa sie to poprzez sprawdzanie w petlach mozliwych kombinacji gornego wiersza i dolnego wiersza potencjalnej maksymalnej podtablicy.
//Konkretnie, zewnetrzna petla iteruje po wszystkich mozliwych numerach wierszy, a wewnetrzna po wszystkich mozliwych numerach dolnego wiersza
    //DLA DANEGO NUMERU WIERSZA GORNEGO, tj. zaczynajac wlasnie od numeru wiersza gornego, a nie od 0.
//Dla kazdej z tych kombinacji aktualizowana jest zawartosc tablicy pomocniczej, ktorej elementy sa sumami aktualnie branych pod uwage 
    //elementow kolejnych kolumn tablicy glownej.
//Nastepnie na tablicy pomocniczej wykonujemy algorytm Kadane, w celu wyznaczenia jej maksymalnej podtablicy, co prowadzi do wyznaczenia 
    //maksymalnej podtablicy fragmentu tablicy glownej, ograniczonego obecnymi numerami gornego i dolnego wiersza.
//Wyznaczana dla kazdej kombinacji maksymalna podtablica jest porownywana z aktualna maksymalna podtablica i jesli okaze sie od niej lepsza, 
    //to zastepuje dotychczasowa.
class Source {
    public static Scanner input = new Scanner(System.in);
    public int[][] a; //deklaracja glownej tablicy przechowujacej zadane na wejsciu wartosci elementow tablicy
    public int[] colSums; //deklaracja pomocniczej tablicy przechowujacej sumy aktualnie branych pod uwage elementow kolejnych kolumn
    public static int datasets; //deklaracja zmiennej przechowujacej liczbe zestawow
    
    public Source() { //konstruktor
        a = new int[100][100]; //utworzenie glownej tablicy przechowujacej zadane na wejsciu wartosci elementow tablicy
        colSums = new int[100]; //utworzenie pomocniczej tablicy przechowujacej sumy aktualnie branych pod uwage elementow kolejnych kolumn
    }

    public void maxSubarray() { //metoda wczytujaca dane tablicy oraz wyznaczajaca i wypisujaca parametry jej maksymalnej podtablicy
        int n; //liczba wierszy
        int m; //liczba kolumn
        int currentDataset; //numer obecnie analizowanego zestawu
        int topEnd; //zmienna przechowujaca indeks pierwszej (lewej) z aktualnie analizowanych kolumn
        int bottomEnd; //zmienna przechowujaca indeks ostatniej (prawej) z aktualnie analizowanych kolumn
        int maxLeftEnd = 0; //indeks pierwszej z lewej kolumny maksymalnej podtablicy
        int maxRightEnd = 0; //indeks pierwszej z prawej kolumny maksymalnej podtablicy
        int maxTopEnd = 0; //indeks pierwszego od gory wiersza maksymalnej podtablicy
        int maxBottomEnd = 0; //indeks pierwszego od dolu wiersza maksymalnej podtablicy
        int maxSum = -1; //suma elementow maksymalnej podtablicy o najmniejszej liczbie elementow
        int currentSum = 0; //suma elementow obecnie sprawdzanej podtablicy
        int currentLeftEnd = 0; //indeks poczatku obecnie sprawdzanej podtablicy
        int currentElements; //liczba elementow obecnie sprawdzanej podtablicy
        int maxElements = 0; //liczba elementow maksymalnej podtablicy
        int maxElementsKadane = 0; //liczba elementow podtablicy maksymalnej wyznaczonej przez algorytm Kadane na pomocniczej tablicy
        int maxLeftEndKadane = 0; //indeks poczatku maksymalnej podtablicy tablicy pomocniczej
        int maxRightEndKadane = 0; //indeks konca maksymalnej podtablicy tablicy pomocniczej
        int maxSumKadane = -1; //maksymalna suma elementow wyznaczona w danym wykonaniu algorytmu Kadane
        boolean hasNonnegative = false; //zmienna przechowujaca informacje o wystapieniu w tablicy co najmniej jednego elementu nieujemnego
        currentDataset = input.nextInt(); //wczytanie numeru obecnie analizowanego zestawu
        input.next(); //pominiecie obecnego na wejsciu dwukropka
        n = input.nextInt(); //wczytanie liczby wierszy tablicy
        m = input.nextInt(); // wczytanie liczby kolumn tablicy
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = input.nextInt(); //wczytanie elementow tablicy
                if ((hasNonnegative == false)&&(a[i][j] >= 0)) { //sprawdzenie czy w tablicy wystepuje co najmniej jeden element nieujemny
                    hasNonnegative = true;
                }
            }
        }

        for (topEnd = 0; topEnd < n; topEnd++) { //petla 
            for (int j = 0; j < m; j++) { 
                colSums[j] = 0; // wyzerowanie elementow pomocniczej tablicy przechowujacej sumy kolumn
            }
            for (bottomEnd = topEnd; bottomEnd < n; bottomEnd++) {
                for (int k = 0; k < m; k++) {
                    colSums[k] += a[bottomEnd][k]; //dodanie do poszczegolnych elementow tablicy pomocniczej odpowiednich elementow nowego dla danej iteracji wiersza
                }
                // zmodyfikowany algorytm Kadane, wyznaczajacy maksymalna podtablice tablicy pomocniczej:
                maxLeftEndKadane = 0;
                maxRightEndKadane = 0;
                currentSum = 0;
                maxSumKadane = Integer.MIN_VALUE;
                currentLeftEnd = 0;
                for (int k = 0; k < m; k++) { // algorytm wyznacza maksymalna podtablice konczaca sie na elemencie o indeksie k
                    currentSum += colSums[k]; //powiekszenie obecnej sumy o element tablicy pomocniczej o indeksie k
                    currentElements = (bottomEnd - topEnd + 1) * (k - currentLeftEnd + 1); //obliczenie liczby elementow podtablicy glownej tablicy
                    if (currentSum < 0) { //napotkanie elementu ujemnego, po dodaniu ktorego suma stala sie ujemna
                        currentSum = 0; //wyzerowanie sumy
                        currentLeftEnd = k + 1; //ustawienie poczatku potencjalnej maksymalnej podtablicy na nastepny indeks, gdyz branie pod uwage dotychczasowych elementow byloby niekorzystne, skoro ich suma jest ujemna
                    } else if (currentSum == 0) {
                        if (maxSumKadane < 0) {
                            maxSumKadane = currentSum;
                            maxElementsKadane = currentElements;
                            maxLeftEndKadane = currentLeftEnd;
                            maxRightEndKadane = k;
                        }
                        currentLeftEnd = k + 1; //ustawienie poczatku potencjalnej maksymalnej podtablicy na nastepny indeks
                    } else if ((currentSum > maxSumKadane)||((currentSum == maxSumKadane) && (currentElements < maxElementsKadane))) {
                        //przypadek, w ktorym obecna podtablica tablicy pomocniczej jest lepsza od dotychczasowej maksymalnej
                        maxSumKadane = currentSum;
                        maxElementsKadane = currentElements;
                        maxLeftEndKadane = currentLeftEnd;
                        maxRightEndKadane = k;
                    }
                }
                //po zakonczeniu dzialania algorytmu Kadane nastepuje sprawdzenie, czy wyznaczona przez niego maksymalna podtablica jest "lepsza" od dotychczasowej maksymalnej
                if ((maxSumKadane > maxSum) || ((maxSumKadane == maxSum) && (maxElementsKadane < maxElements))) {
                    //jesli tak, to zmiennym zawierajacym informacje o maksymalnej podtablicy przypisujemy dane nowej maksymalnej podtablicy
                    maxTopEnd = topEnd;
                    maxBottomEnd = bottomEnd;
                    maxLeftEnd = maxLeftEndKadane;
                    maxRightEnd = maxRightEndKadane;
                    maxSum = maxSumKadane;
                    maxElements = maxElementsKadane;
                }

            }
        }
        //wypisanie wyniku:
        if (hasNonnegative == false) { //przypadek tablicy wylacznie ujemnych elementow, czyli maksymalna podtablica jest pusta
            System.out.println(currentDataset + ": n = " + n + " m = " + m + ", s = 0, mst is empty");
        } else { //pozostale przypadki, w ktorych maksymalna tablica nie jest pusta
            System.out.println(currentDataset + ": n = " + n + " m = " + m + ", s = " + maxSum + ", mst = a["+ maxTopEnd + ".." + maxBottomEnd + "][" + maxLeftEnd + ".." + maxRightEnd + "]");
        }   
    }
    
    public static void main(String[] args) {
        datasets = input.nextInt(); //wczytanie liczby zestawow
        Source a1 = new Source(); //utworzenie obiektu klasy Source
        for (int s = 0; s < datasets; s++) { //petla iterujaca po kolejnych zestawach
            a1.maxSubarray(); //wywolanie funkcji
        } //koniec petli iterujacej po kolejnych zestawach
    }
}

/*
test0.in:
9
1 : 1 6
1 2 3 4 5 6
2 : 4 5
 1  2 -1 -4 -20
-8 -3  4  2   1
 3  8  10 1   3
-4 -1   1 7  -6
3 : 1 7
1 2 3 4 -20 0 10
4 : 7 1
1 
2 
3 
4 
-20
0
10
5 : 3 3
0 0 0
1 0 0
1 0 2
6 : 1 9
0 0 0 0 0 -1 0 2 0
7 : 3 3
0 0 0
1 0 0
1 -3 2
8 : 1 1
-1
9 : 1 1
0

test0.out:
1: n = 1 m = 6, s = 21, mst = a[0..0][0..5]
2: n = 4 m = 5, s = 29, mst = a[1..3][1..3]
3: n = 1 m = 7, s = 10, mst = a[0..0][6..6]
4: n = 7 m = 1, s = 10, mst = a[6..6][0..0]
5: n = 3 m = 3, s = 4, mst = a[1..2][0..2]
6: n = 1 m = 9, s = 2, mst = a[0..0][7..7]
7: n = 3 m = 3, s = 2, mst = a[2..2][2..2]
8: n = 1 m = 1, s = 0, mst is empty
9: n = 1 m = 1, s = 0, mst = a[0..0][0..0]

*/