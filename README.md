# Gra - Listen To Your Captain (RMI)

Aplikacja składająca się z trzech modułów: serwera oraz trzech rodzajów klienta. Pierwszy pełni rolę kapitana gry, drugi
to gracze wykonujący jego polecenia. Istnieją 3 typy graczy. Kapitan ma możliwość rozpoczęcia, zakończenia gry, wydawania 
poleceń graczom, odbierania wartości określonych przez nich atrybutów i przyznawania/odbierania punktów.
Serwer przyjmuje połączenia graczy, wyświetla ich listę oraz umożliwia wyrzucenie z gry.

### JavaFX Bean będący reprezentacją panelu gracza:

Kod komponentu Java Beans znajduje się na branchu - player_fx_bean_jar.
W celu skompilowania projektu w pierwszej kolejności z poziomu brancha - player_fx_bean_jar należy utworzyć
lokalne repozytorium dla paczki .jar za pomocą komendy mvn::deploy. Po wykonaniu tej czynności zależność dot. komponentu
Java Beans zostanie poprawnie zaciągnięta z lokalnego repozytorium Mavena.

### Autor:

- Kamil Cieślik <br />

### Zrzuty ekranu prezentujące działanie aplikacji:
<p align="center">
1. Inicjacja aplikacji.
</p>
<p align="center">
<img height="476" width="812" src="https://image.ibb.co/jA3u2S/1.png" />
</p>
<p align="center">

<p align="center">
2. Ekran główny #1.
</p>
<p align="center">
<img height="750" width="1373" src="https://image.ibb.co/cpTiwn/2.png" />
</p>
<p align="center">

<p align="center">
3. Ekran główny #2.
</p>
<p align="center">
<img height="750" width="1373" src="https://image.ibb.co/bymsp7/3.png" />
</p>
<p align="center">

<p align="center">
4. Ekran główny #3.
</p>
<p align="center">
<img height="750" width="1373" src="https://image.ibb.co/fq13wn/4.png" />
</p>
<p align="center">

<p align="center">
5. Inicjacja aplikacji.
</p>
<p align="center">
<img height="549" width="1373" src="https://image.ibb.co/b71Q97/5.png" />
</p>
<p align="center">

<p align="center">
6. Ekran główny #1.
</p>
<p align="center">
<img height="750" width="1373" src="https://image.ibb.co/fNV7NS/6.png" />
</p>
<p align="center">

<p align="center">
7. Ekran główny #2.
</p>
<p align="center">
<img height="750" width="1373" src="https://image.ibb.co/kYgE2S/7.png" />
</p>
<p align="center">

<p align="center">
8. Ekran główny #3.
</p>
<p align="center">
<img height="750" width="1373" src="https://image.ibb.co/goyu2S/8.png" />
</p>
<p align="center">