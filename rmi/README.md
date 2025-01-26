# Aplikacja zarządzania biblioteką (RMI)

## Projekt wykonany w ramach przedmiotu **Programowanie współbieżne i rozproszone**

**Autor**: Kateryna Kolioglo

---

## Opis aplikacji

Aplikacja "Zarządzanie biblioteką" oparta jest na mechanizmie RMI (Remote Method Invocation) i umożliwia użytkownikom zdalne zarządzanie zasobami biblioteki. System pozwala na dodawanie, wypożyczanie i zwracanie książek oraz śledzenie aktualnego stanu zasobów biblioteki. Projekt wspiera współpracę wielu użytkowników w czasie rzeczywistym.

---

## Funkcjonalności

### Serwer RMI:
1. **Obsługa różnych operacji na książkach:**
   - Dodawanie książek.
   - Wypożyczanie książek.
   - Zwracanie książek.
   - Pobieranie listy dostępnych książek.
   - Pobieranie listy wszystkich książek.

2. **Synchronizacja stanu danych:**
   - Zapewnienie spójności danych nawet przy jednoczesnym wykonywaniu operacji przez wielu klientów.

3. **Rejestracja klientów:**
   - Każdy klient posiada unikalny identyfikator oraz możliwość subskrybowania powiadomień.

### Klient RMI:
1. **Interaktywny interfejs:**
   - Użytkownicy mogą wykonywać operacje na książkach (dodawanie, wypożyczanie, zwracanie).

2. **Wyświetlanie danych:**
   - Pobieranie aktualnej listy dostępnych książek.
   - Pobieranie pełnej listy wszystkich książek.

3. **Powiadomienia:**
   - Klienci otrzymują powiadomienia w czasie rzeczywistym o zmianach wprowadzonych przez innych użytkowników (np. dodanie książki, wypożyczenie).

---

## Instrukcja uruchomienia

### Wymagania wstępne:
1. **Java Development Kit (JDK)** w wersji 9 lub wyższej.
2. Odpowiednio skonfigurowane środowisko do uruchamiania aplikacji Java (np. IntelliJ IDEA, Eclipse lub terminal).

### Kroki:
1. **Kompilacja plików:**
   Skorzystaj z następującej komendy, aby skompilować wszystkie pliki:
   ```bash
   javac *.java
   ```

2. **Uruchomienie serwera:**
   W terminalu uruchom serwer poleceniem:
   ```bash
   java LibraryServer
   ```

3. **Uruchomienie klienta:**
   W osobnym terminalu uruchom klienta poleceniem:
   ```bash
   java LibraryClient
   ```

4. **Interakcja z aplikacją:**
   - Postępuj zgodnie z komunikatami wyświetlanymi w terminalu klienta.

---

## Przykłady interakcji

1. **Dodanie książki:**
   - Komenda w kliencie: `1`
   - Podaj tytuł i autora książki.
   - Wynik: "Book added successfully."

2. **Wypożyczenie książki:**
   - Komenda w kliencie: `2`
   - Podaj tytuł książki.
   - Wynik: "You borrowed: [tytuł książki]".

3. **Otrzymywanie powiadomień:**
   - Inny klient dodaje książkę.
   - Wynik na Twoim kliencie: `[Notification]: Book added: [tytuł książki]`.

---

## Mechanizmy obsługi błędów

1. **Utrata połączenia z serwerem:**
   - Komunikat: "Client error: Connection refused".
   - Rozwiązanie: Sprawdź, czy serwer jest uruchomiony.

2. **Nieprawidłowe dane wejściowe:**
   - Komunikat: "Invalid command. Please try again.".
   - Rozwiązanie: Wprowadź poprawną komendę zgodnie z menu.

3. **Próba wypożyczenia niedostępnej książki:**
   - Komunikat: "Book not available: [tytuł książki]".
   - Rozwiązanie: Wybierz inną książkę.

---

## Podsumowanie

Aplikacja "Zarządzanie biblioteką" pokazuje praktyczne zastosowanie mechanizmów RMI w rozproszonym systemie zarządzania danymi. Dzięki obsłudze wielu klientów oraz implementacji powiadomień w czasie rzeczywistym projekt demonstruje efektywne podejście do synchronizacji i współpracy w systemach rozproszonych.