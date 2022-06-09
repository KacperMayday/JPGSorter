## Opis projektu

Projekt stworzony w zespole dwuosobowym w ramach zaliczenia przedmiotu "Język Java". Celem projektu jest stworzenie
programu do porządkowania plików zdjęciowych JPG.

#### Wymagania funkcjonalne

1. Program obsługuje parametry: liczba wątków w puli, ścieżka do
   katalogu źródłowego, ścieżka do katalogu docelowego
2. Program analizuje podany w parametrze katalog, wyszukując plików zdjęciowych
3. Dla każdego znalezionego pliku wyszukuje metadanych o utworzeniu pliku
4. Program kopiuje każdy plik do katalogu docelowego umieszczając plik w podkatalogu o nazwie takiej jak data utworzenia
   tego pliku
5. Pliki wewnątrz katalogu mają być zapisywane pod nazwami reprezentującymi kolejne liczby całkowite
6. Poszczególne pliki zdjęć mają być kopiowane w oddzielnych wątkach

## Podstawowe założenia projektu

1. Projekt maven’owy z obługą minimalnej liczby zależności zewnętrznych
2. Paczka wynikowa: jar.
3. Zgodność źródeł oraz klas wynikowych z JAVA 11.
4. Obsługa parametrów w wydzielonej klasie: liczba wątków w puli, ścieżka do
   katalogu źródłowego, ścieżka do katalogu docelowego
5. Kod obłożony testami jednostkowymi dostarczonymi w projekcie
6. Kod ma zawierać komentarze do klas, metod i zmiennych składowych
7. Do projektu należy dołączyć wygenerowany javadoc

## Wynik projektu

1. Kod źródłowy programu w `src/java/pl/wit/projekt/`
2. Dokumentacja javadoc + paczka jar w `target/`
3. Testy jednostkowe w `test/java/pl/wit/projekt/` lub poprzez `mvn test`
4. Przykładowe wywołanie projektu (przed wywołaniem należy się upewnić, że katalog docelowy jest pusty):
    * `java -cp target/JPGSorter-1.0-SNAPSHOT-jar-with-dependencies.jar pl.wit.projekt.App src/test/resources/source src/test/resources/target 10
      `

## Podział pracy

#### 1. Jakub:

* Obsługa i walidacja parametrów wejściowych
* Implementacja klasy mapującej pliki zdjęciowe w katalogu źródłowym
* Testy jednostkowe + dokumentacja do tworzonych klas i metod

#### 2. Kacper:

* Implementacja klasy kopiującej pliki zdjęciowe do katalogu docelowego
* Implementacja klasy wątku realizująca kopiowanie pojedynczego pliku
* Testy jednostkowe + dokumentacja do tworzonych klas i metod
