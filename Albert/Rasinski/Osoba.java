package Albert.Rasinski;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Osoba {
    private int wiek;
    private String nazwisko;
    private Wyksztalcenie wyksztalcenie;
    private int waga;

    private static HashMap<Integer, Osoba> mapaOsob = new HashMap<Integer, Osoba>();
    private static HashMap<Integer, Osoba> mapaFiltrowanaOsob = new HashMap<Integer, Osoba>();

    public Osoba(int pesel, int wiek, String nazwisko, Wyksztalcenie wyksztalcenie, int waga) {
        this.wiek = wiek;
        this.nazwisko = nazwisko;
        this.wyksztalcenie = wyksztalcenie;
        this.waga = waga;

        mapaOsob.put(pesel, this);
        mapaFiltrowanaOsob.put(pesel, this);
    }

    public static void dodajZKlawiatury(){
        int pesel;
        int wiek;
        String wyksztalcenie;
        int waga;
        String nazwisko;
        while (true){
            try{
                Scanner scanner = new Scanner(System.in);

                System.out.println("podaj pesel");
                pesel = scanner.nextInt();

                System.out.println("podaj wiek");
                wiek = scanner.nextInt();

                System.out.println("podaj nazwisko");
                nazwisko = scanner.next();

                System.out.println("podaj wykształcenie");
                wyksztalcenie = scanner.next();
                if (wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.GIMNAZJALNE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.PODSTAWOWE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.SREDNIE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.SREDNIE_BRANZOWE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.WYZSZE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.ZASADNICZE_BRANZOWE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.ZASADNICZE_ZAWODOWE.name())){
                    wyksztalcenie = wyksztalcenie.toUpperCase();
                }else{
                    throw new Exception("bledne wyksztalcenie");
                }

                System.out.println("podaj wage");
                waga = scanner.nextInt();

                break;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        new Osoba(pesel,wiek,nazwisko,Wyksztalcenie.valueOf(wyksztalcenie),waga);
    }

    public static boolean dodajZGUI(String pesel, String wiek, String nazwisko, String wyksztalcenie, String waga){
        int pesel1;
        int wiek1;
        String wyksztalcenie1;
        int waga1;
        String nazwisko1;
        try{
            pesel1 = Integer.parseInt(pesel);
            wiek1 = Integer.parseInt(wiek);
            if (wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.GIMNAZJALNE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.PODSTAWOWE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.SREDNIE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.SREDNIE_BRANZOWE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.WYZSZE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.ZASADNICZE_BRANZOWE.name()) || wyksztalcenie.equalsIgnoreCase(Wyksztalcenie.ZASADNICZE_ZAWODOWE.name())){
                wyksztalcenie1 = wyksztalcenie.toUpperCase();
            }else{
                throw new Exception("bledne wyksztalcenie");
            }
            waga1 = Integer.parseInt(waga);
            nazwisko1 = nazwisko;
            new Osoba(pesel1,wiek1,nazwisko1,Wyksztalcenie.valueOf(wyksztalcenie1),waga1);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private static void drukuj(HashMap<Integer, Osoba> mapa){
        for  (Map.Entry<Integer, Osoba> iter : mapa.entrySet()){
            System.out.println(iter.getKey() + ": \t\twiek:" + iter.getValue().wiek + "\t\tnazwisko: " + iter.getValue().nazwisko + "\t\twyksztalcenie: " + iter.getValue().wyksztalcenie + "\t\twaga: " + iter.getValue().waga);
        }
    }

    public static void drukujMape(){
        drukuj(mapaOsob);
    }

    public static void drukujFiltrowanaMape(){
        drukuj(mapaFiltrowanaOsob);
    }

    public static void filtrujMape(Atrybut atrybut, SposobFiltrowania sposobFiltrowania, Object wartoscDoPorownaia){
        Map<Integer, Osoba> result = mapaFiltrowanaOsob.entrySet()
                    .stream()
                    .filter(map-> {
                        try {
                            Field field = map.getValue().getClass().getDeclaredField(atrybut.toString().toLowerCase());
                            int i;
                            if (field.get(map.getValue()) instanceof String){
                                i = ((String) field.get(map.getValue())).compareTo((String) wartoscDoPorownaia);
                            }else{
                                i = field.getInt(map.getValue()) - (int)wartoscDoPorownaia;
                            }
                            switch (sposobFiltrowania){
                                case WIEKSZE:
                                    if (i > 0){
                                        return true;
                                    }else{
                                        return false;
                                    }
                                case WIEKSZE_ROWNE:
                                    if (i >= 0){
                                        return true;
                                    }else{
                                        return false;
                                    }
                                case ROWNE:
                                    if (i == 0){
                                        return true;
                                    }else{
                                        return false;
                                    }
                                case MNIEJSZE:
                                    if (i < 0){
                                        return true;
                                    }else{
                                        return false;
                                    }
                                case MNIEJSZE_ROWNE:
                                    if (i <= 0){
                                        return true;
                                    }else{
                                        return false;
                                    }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    })
                    .collect(Collectors.toMap(map->map.getKey(),map->map.getValue()));

        mapaFiltrowanaOsob.clear();
        mapaFiltrowanaOsob.putAll(result);
    }

    public static void filtrujMapeBezStream(Atrybut atrybut, SposobFiltrowania sposobFiltrowania, Object wartoscDoPorownaia){
        HashMap<Integer, Osoba> resultHashMap = new HashMap<Integer, Osoba>();

        for  (Map.Entry<Integer, Osoba> iter : mapaFiltrowanaOsob.entrySet()){
            try {
                Field field = iter.getValue().getClass().getDeclaredField(atrybut.toString().toLowerCase());
                int i;
                if (field.get(iter.getValue()) instanceof String){
                    i = ((String) field.get(iter.getValue())).compareTo((String) wartoscDoPorownaia);
                }else{
                    i = field.getInt(iter.getValue()) - (int)wartoscDoPorownaia;
                }
                boolean sprawdzPoprawnosc;
                switch (sposobFiltrowania){
                    case WIEKSZE:
                        if (i > 0){
                            sprawdzPoprawnosc = true;
                        }else{
                            sprawdzPoprawnosc = false;
                        }
                        break;
                    case WIEKSZE_ROWNE:
                        if (i >= 0){
                            sprawdzPoprawnosc = true;
                        }else{
                            sprawdzPoprawnosc = false;
                        }
                        break;
                    case ROWNE:
                        if (i == 0){
                            sprawdzPoprawnosc = true;
                        }else{
                            sprawdzPoprawnosc = false;
                        }
                        break;
                    case MNIEJSZE:
                        if (i < 0){
                            sprawdzPoprawnosc = true;
                        }else{
                            sprawdzPoprawnosc = false;
                        }
                        break;
                    case MNIEJSZE_ROWNE:
                        if (i <= 0){
                            sprawdzPoprawnosc = true;
                        }else{
                            sprawdzPoprawnosc = false;
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sposobFiltrowania);
                }
                if (sprawdzPoprawnosc){
                    resultHashMap.put(iter.getKey(), iter.getValue());
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        mapaFiltrowanaOsob.clear();
        mapaFiltrowanaOsob.putAll(resultHashMap);
    }

    private static void daneOOsobieZPeselem(int pesel, HashMap mapa){
        if (mapa.containsKey(pesel)){
            Osoba osoba = (Osoba) mapa.get(pesel);
            System.out.println("pesel" + pesel + "\t\twiek: " + osoba.wiek + "\t\twyksztalcenie: " + osoba.wyksztalcenie + "\t\tnazwisko:" + osoba.nazwisko + "\t\twaga:" + osoba.waga);
        }
    }

    public static void getPeselBasic(int pesel){
        daneOOsobieZPeselem(pesel, mapaOsob);
    }

    public static void getPeselFiltered(int pesel){
        daneOOsobieZPeselem(pesel, mapaFiltrowanaOsob);
    }

    public static Object[] getPeselGUI(int pesel){
        Osoba osoba;
        Object[] arr = new Object[5];
        if (mapaOsob.containsKey(pesel)){
            osoba = (Osoba) mapaOsob.get(pesel);
            arr[0] = pesel;
            arr[1] = osoba.wiek;
            arr[2] = osoba.wyksztalcenie;
            arr[3] = osoba.nazwisko;
            arr[4] = osoba.waga;
        }else{
            arr = null;
        }
        return arr;
    }

    public static void resetFiltr(){
        mapaFiltrowanaOsob.clear();
        mapaFiltrowanaOsob.putAll(mapaOsob);
    }
}