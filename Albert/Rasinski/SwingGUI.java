package Albert.Rasinski;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;

public class SwingGUI{
    private JFrame frame;
    private JPanel panel;

    private JTextField peselTF;
    private JTextField wiekTF;
    private JTextField nazwiskoTF;
    private JTextField wyksztalcenieTF;
    private JTextField wagaTF;

    private JLabel peselLabel;
    private JLabel wiekLabel;
    private JLabel nazwiskoLabel;
    private JLabel wyksztalcenieLabel;
    private JLabel wagaLabel;

    private JLabel wiadomoscButton;
    private JButton buttonDodaj;

    private JTextField miejsceNaPesel;
    private JButton znajdzPoPeselButton;

    public SwingGUI(){
        frame = new JFrame("App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);
        frame.setResizable(false);

        panel = new JPanel();
        stworzPanel();
        frame.add(panel);

        frame.setVisible(true);
    }

    private void stworzPanel(){
        panel.setLayout(new GridLayout(7,2));

        peselLabel = new JLabel("PESEL:  ");
        peselLabel.setHorizontalAlignment(JLabel.RIGHT);
        peselLabel.setFont(peselLabel.getFont().deriveFont(18.0f));
        panel.add(peselLabel);
        peselTF = new JTextField();
        panel.add(peselTF);

        wiekLabel = new JLabel("Wiek:  ");
        wiekLabel.setHorizontalAlignment(JLabel.RIGHT);
        wiekLabel.setFont(wiekLabel.getFont().deriveFont(18.0f));
        panel.add(wiekLabel);
        wiekTF = new JTextField();
        panel.add(wiekTF);

        nazwiskoLabel = new JLabel("Nazwisko:  ");
        nazwiskoLabel.setHorizontalAlignment(JLabel.RIGHT);
        nazwiskoLabel.setFont(wiekLabel.getFont().deriveFont(18.0f));
        panel.add(nazwiskoLabel);
        nazwiskoTF = new JTextField();
        panel.add(nazwiskoTF);

        wyksztalcenieLabel = new JLabel("Wykształcenie:  ");
        wyksztalcenieLabel.setHorizontalAlignment(JLabel.RIGHT);
        wyksztalcenieLabel.setFont(wyksztalcenieLabel.getFont().deriveFont(18.0f));
        panel.add(wyksztalcenieLabel);
        wyksztalcenieTF = new JTextField();
        panel.add(wyksztalcenieTF);

        wagaLabel = new JLabel("Waga:  ");
        wagaLabel.setHorizontalAlignment(JLabel.RIGHT);
        wagaLabel.setFont(wagaLabel.getFont().deriveFont(18.0f));
        panel.add(wagaLabel);
        wagaTF = new JTextField();
        panel.add(wagaTF);

        wiadomoscButton = new JLabel("");
        wiadomoscButton.setHorizontalAlignment(JLabel.RIGHT);
        wiadomoscButton.setFont(wagaLabel.getFont().deriveFont(18.0f));
        panel.add(wiadomoscButton);
        buttonDodaj = new JButton("dodaj");
        buttonDodaj.setFont(wagaLabel.getFont().deriveFont(18.0f));
        buttonDodaj.addActionListener(new DodajButtonOnClick(peselTF, wiekTF, nazwiskoTF, wyksztalcenieTF, wagaTF, wiadomoscButton));
        panel.add(buttonDodaj);

        miejsceNaPesel = new JTextField();
        panel.add(miejsceNaPesel);
        znajdzPoPeselButton = new JButton("Znajdz po Peselu");
        znajdzPoPeselButton.setFont(wagaLabel.getFont().deriveFont(18.0f));
        znajdzPoPeselButton.addActionListener(new ZnajdzButtonOnClick(miejsceNaPesel, peselTF, wiekTF, nazwiskoTF, wyksztalcenieTF, wagaTF));
        panel.add(znajdzPoPeselButton);
    }
}

class DodajButtonOnClick implements ActionListener{
    private JTextField peselTF;
    private JTextField wiekTF;
    private JTextField nazwiskoTF;
    private JTextField wyksztalcenieTF;
    private JTextField wagaTF;
    private JLabel msg;

    public DodajButtonOnClick(JTextField peselTF, JTextField wiekTF, JTextField nazwiskoTF, JTextField wyksztalcenieTF, JTextField wagaTF, JLabel wiadomoscButton){
        this.peselTF = peselTF;
        this.wiekTF = wiekTF;
        this.nazwiskoTF = nazwiskoTF;
        this.wyksztalcenieTF = wyksztalcenieTF;
        this.wagaTF = wagaTF;
        msg = wiadomoscButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Osoba.dodajZGUI(peselTF.getText(), wiekTF.getText(), nazwiskoTF.getText(), wyksztalcenieTF.getText(), wagaTF.getText())){
            JOptionPane.showMessageDialog(null,"dodano");
        }else{
            JOptionPane.showMessageDialog(null,"nie dodano","blad",JOptionPane.ERROR_MESSAGE);
            msg.setText("błędne dane   ");
        }
    }
}

class ZnajdzButtonOnClick implements ActionListener{
    private JTextField miejsceNaPesel;
    private JTextField peselTF;
    private JTextField wiekTF;
    private JTextField nazwiskoTF;
    private JTextField wyksztalcenieTF;
    private JTextField wagaTF;

    public ZnajdzButtonOnClick(JTextField miejsceNaPesel, JTextField peselTF, JTextField wiekTF, JTextField nazwiskoTF, JTextField wyksztalcenieTF, JTextField wagaTF){
        this.miejsceNaPesel = miejsceNaPesel;
        this.peselTF = peselTF;
        this.wiekTF = wiekTF;
        this.nazwiskoTF = nazwiskoTF;
        this.wyksztalcenieTF = wyksztalcenieTF;
        this.wagaTF = wagaTF;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object[] arr = null;
        arr = Osoba.getPeselGUI(Integer.parseInt(miejsceNaPesel.getText()));
        if (arr != null){
            JOptionPane.showMessageDialog(null,"Pesel: " + arr[0] + "   wiek: " + arr[1] + "   wyksztalcenie: " + arr[2] + "   nazwisko: " + arr[3] + "   waga: " + arr[4]);
        }else{
            JOptionPane.showMessageDialog(null,"nie znaleziono","blad",JOptionPane.ERROR_MESSAGE);
            System.out.println("null");
        }
    }
}