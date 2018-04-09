# Gra RMI - Listen To Captain

Aplikacja składająca się z trzech modułów: serwera oraz dwóch rodzajów klienta. Pierwszy pełni rolę kapitana gry, drugi
to gracze wykonujący jego polecenia. Istnieją 3 typy graczy. Kapitan ma możliwość rozpoczęcia, zakończenia gry, wydawania 
poleceń graczom, odbierania wartości określonych przez nich atryburów i przyznawania/odbierania punktów.
Serwer przyjmuje połączenia graczy, wyświetla ich listę oraz umożliwia wyrzucenie z gry.

### JavaFX Bean będący reprezentacją panelu gracza:

Kod komponentu Java Beans znajduje się na branchu - player_fx_bean_jar.
W celu skompilowania projektu w pierwszej kolejności z poziomu brancha - player_fx_bean_jar należy utworzyć
lokalne repozytorium dla paczki .jar za pomocą komendy mvn::deploy. Po wykonaniu tej czynności zależność dot. komponentu
Java Beans zostanie poprawnie zaciągnięta z lokalnego repozytorium Mavena.

### Autor:

- Kamil Cieślik <br />