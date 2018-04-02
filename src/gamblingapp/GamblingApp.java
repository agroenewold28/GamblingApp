/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamblingapp;

import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class GamblingApp {
    static Setting atlanta, toronto, amsterdam, barcelona, paris, vegas;
    static Player player;
    static SettingSelectorWindow w;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        player = new Player(JOptionPane.showInputDialog(null, "Please enter a name"));
        createSettings();
        Card.setCardSuitList();
        Card.setCardSuits();
        w = new SettingSelectorWindow();
        w.setVisible(true);
    }
    
    private static void createSettings(){
        atlanta = new Setting("Atlanta", "You have one opponent in Atlanta", 10, 1, 50);
        toronto = new Setting("Toronto", "You have two opponents in Toronto", 30, 2, 100);
        amsterdam = new Setting("Amsterdam", "You have three opponents in Amsterdam", 150, 3, 750);
        barcelona = new Setting("Barcelona", "You have four opponents in Barcelona", 850, 4, 2500);
        paris = new Setting("Paris", "You have four opponents in Paris", 7000, 4, 20000);
        vegas = new Setting("Vegas", "You have five opponents in Toronto", 60000, 5, 1000000);
    }
}