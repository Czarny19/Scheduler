Założenia dla zadań projektowych
Zadania projektowe wykonywane są w trzech grupach. Zadania projektowe należy realizować zgodnie z wybraną metodyką. Opracowane rozwiązania muszą spełniać poniższe założenia.

1. Aplikacja napisana w Javie wykorzystując Android SDK i Android Studio.
1. Asynchroniczność wykonywanych operacji w tle (AsyncTask)
1. Zastosować wzorce projektowe programowania zdarzeniowego.
1. Parametryzacja aplikacji np. poprzez przez pliki properties.
1. Opracowanie przykładowych danych testowych, dostarczanych poprzez jedno z kilku źródeł danych (ma być: xml, firebase)
1. Budowa komponentów z wykorzystaniem Gradle i środowiska kontroli wersji, np. SVN / Github / Bitbucket

 Ponadto należy opracować sprawozdanie zawierające:
	* opis wykorzystanych technologii
* 	opis komponentów wykorzystanych w projekcie
* 	diagram klas/encji oraz interfejsów reprezentujący struktury wykorzystywane w projektowanym komponencie
* 	rozmieszczenie wszelkich plików konfiguracyjnych wraz z opisem właściwości konfiguracyjnych wykorzystywanych w aplikacji
* 	wybrane widoki z aplikacji


###Aplikacja dostarczająca generyczny komponent do planowania (ang. Scheduler) - 6 osób:###
*  	implementacja własnego komponentu kalendarza;
*  	implementacja własnego komponentu czasu w postaci zegara, dane przechowywane w obiekcie np. Date#
*  	implementacjaschedulera, czyli kalendarz w którym dla odkreślonychzasobów przypisuje się obiekty np. zasoby (osoby) i zadania
*  	możliwość definiowania notek z wykorzystaniem własnej implementacji notatnika
*  	mechanizm notyfikacji powiadamiający o zmianach
*  	mechanizm zdarzeniowego powiadamiania komponentu o zmianie danych w źródle danych
*  	scheduler powiadamiania o zadaniach/zaplanowanych obiektach, np. definiujemy zadanie startu o 10:20, scheduler o godzinie 10:20 powiadamia zdarzeniowo o akcji, mechanizm notyfikacji powiadamia użytkownika
*  	przenoszenie zadańpomiędzyrożnymi dniami, tygodniami, miesiącami (drag and drop)
*  	komponent Timeline (oś czasu) dla wizualizacji zadań danego dnia