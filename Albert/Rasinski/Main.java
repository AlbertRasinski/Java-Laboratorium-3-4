package Albert.Rasinski;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
	    new Osoba(999,15,"ra", Wyksztalcenie.GIMNAZJALNE, 60);
        new Osoba(123,29,"fsdxv", Wyksztalcenie.WYZSZE, 80);
        new Osoba(581,75,"aaaa", Wyksztalcenie.PODSTAWOWE, 120);
        //Osoba.dodajZKlawiatury();
        Osoba.drukujMape();
        Osoba.filtrujMapeBezStream(Atrybut.WIEK, SposobFiltrowania.MNIEJSZE_ROWNE, 29);
        System.out.println("");
        Osoba.drukujFiltrowanaMape();
        System.out.println("");
        Osoba.getPeselFiltered(999);

        new SwingGUI();
    }
}
