# Aplikacja czatu (model klient-serwer)

Ten projekt został zrealizowany jako część przedmiotu **Programowanie współbieżne i rozproszone** na potrzeby zadania praktycznego. Aplikacja implementuje czat oparty na architekturze klient-serwer z wykorzystaniem gniazd (sockets). Użytkownicy mogą łączyć się z serwerem, komunikować się w czasie rzeczywistym i korzystać z różnych funkcji, takich jak przeglądanie historii czatu czy zmiana nazwy użytkownika.

**Autor: Kateryna Kolioglo**

---

## Funkcje

### Serwer:

1. **Komunikacja w czasie rzeczywistym:**

   - Obsługuje wielu klientów jednocześnie, wykorzystując architekturę wielowątkową.
   - Przesyła wiadomości do wszystkich podłączonych klientów.

2. **Obsługa poleceń:**

   - `/history`: Wyświetla historię czatu dla żądającego klienta.
   - `/users`: Wyświetla listę aktualnie podłączonych użytkowników.
   - `/help`: Dostarcza listę dostępnych poleceń.

3. **Synchronizacja:**

   - Zapewnia bezpieczny dostęp do współdzielonych zasobów (np. lista klientów, historia czatu).

4. **Powiadomienia o użytkownikach:**

   - Wysyła powiadomienia, gdy użytkownicy dołączają lub opuszczają czat.

### Klient:

1. **Interaktywny interfejs:**

   - Użytkownicy mogą wprowadzać polecenia i wiadomości czatu w trybie interaktywnym.
   - Nazwa użytkownika ustawiana jest na początku sesji.

2. **Obsługa poleceń:**

   - `/history`: Żąda historii czatu od serwera.
   - `/users`: Żąda listy podłączonych użytkowników.
   - `/help`: Wyświetla listę poleceń i ich opis.
   - `exit`: Rozłącza klienta z serwerem.

3. **Asynchroniczne odbieranie wiadomości:**

   - Klient ciągle nasłuchuje wiadomości od serwera, umożliwiając jednocześnie wpisywanie wiadomości przez użytkownika.

4. **Obsługa błędów:**

   - Powiadamia użytkownika w przypadku utraty połączenia lub błędów serwera.

---

## Wymagania techniczne

1. Wielowątkowość do obsługi wielu klientów.
2. Działanie w sieci lokalnej.
3. Bezpieczny współdzielony dostęp do zasobów po stronie serwera.
4. Komunikacja klient-serwer przez gniazda (sockets).

---

## Instrukcje uruchamiania

### Wymagania wstępne

1. **Java Development Kit (JDK):** Upewnij się, że masz zainstalowany JDK w wersji 8 lub wyższej.
2. **IDE lub wiersz poleceń:** Możesz używać IDE, takiego jak IntelliJ IDEA lub Eclipse, albo uruchamiać aplikację bezpośrednio z wiersza poleceń.

### Kroki

#### 1. Uruchom serwer:

1. Przejdź do folderu zawierającego plik `Server.java`.
2. Skompiluj serwer:
   ```
   javac Server.java
   ```
3. Uruchom serwer:
   ```
   java Server
   ```

#### 2. Uruchom klientów:

1. Przejdź do folderu zawierającego plik `Client.java`.
2. Skompiluj klienta:
   ```
   javac Client.java
   ```
3. Uruchom klienta:
   ```
   java Client
   ```
4. Wprowadź swoją nazwę użytkownika, gdy pojawi się monit.

### Dostępne polecenia dla użytkowników

1. **/history:** Wyświetla poprzednie wiadomości.
2. **/users:** Wyświetla listę podłączonych użytkowników.
3. **/help:** Wyświetla listę dostępnych poleceń.
4. **exit:** Opuszcza czat.

---

## Przykład interakcji

### Log serwera:

```
Server started on port 12345
New client connected: 127.0.0.1
New client connected: 127.0.0.1
Anonymous is now known as Rika.
Anonymous is now known as Katia.
Rika: Hello everyone!
Katia: Hi Rika!
```

### Klient 1 (Rika):

```
Enter your username: Rika
Connected to the chat server as Rika.
Welcome to the chat server! Type '/help' for a list of commands.
Rika: Hello everyone!
Katia: Hi Rika!
```

### Klient 2 (Katia):

```
Enter your username: Katia
Connected to the chat server as Katia.
Welcome to the chat server! Type '/help' for a list of commands.
Rika: Hello everyone!
Katia: Hi Rika!
```

---

## Struktura projektu

- **Server.java:** Implementuje logikę po stronie serwera, obsługując połączenia klientów, polecenia i przesyłanie wiadomości.
- **Client.java:** Implementuje logikę po stronie klienta, umożliwiając użytkownikom interakcję z serwerem i innymi klientami.

---

## Obsługa błędów

1. **Problemy z połączeniem:**
   - Klienci są powiadamiani, jeśli serwer jest niedostępny lub połączenie zostało zerwane.
2. **Nieznane polecenia:**
   - Nieprawidłowe polecenia są obsługiwane, a użytkownicy są kierowani do użycia `/help`.

---

## Przyszłe ulepszenia

1. **Trwałość wiadomości:**
   - Przechowywanie historii czatu w pliku lub bazie danych na potrzeby przyszłych sesji.
2. **Wiadomości prywatne:**
   - Umożliwienie użytkownikom wysyłania wiadomości bezpośrednich do określonych klientów.
3. **Szyfrowanie:**
   - Dodanie szyfrowania dla bezpiecznej komunikacji.
4. **Graficzny interfejs użytkownika (GUI):**
   - Zastąpienie interfejsu konsolowego bardziej przyjaznym dla użytkownika GUI.

---

## Autor

Projekt został zrealizowany przez Katerynę Kolioglo jako zadanie praktyczne na przedmiot **Programowanie współbieżne i rozproszone**. Aplikacja demonstruje wielowątkowość, programowanie gniazd (socket programming) oraz synchronizację w Javie.