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
public class Player extends Opposition {
    public String name;
    public long balance;
    private Boolean victory;
    
    public Player(String name){
        super();
        this.name = name;
        balance = 20;
    }
    
    public Boolean enterSetting(Setting chosenSetting){
        if(balance < chosenSetting.entryFee){
            JOptionPane.showMessageDialog(null, "You don't have enough money!");
            return false;
        }
        else{
            balance -= chosenSetting.entryFee;
            return true;
        }
    }
    
    public void wonGame(Boolean thirtyOne){
        if(thirtyOne)
            balance += GameGUI.curSetting.reward * 2;
        else
            balance += GameGUI.curSetting.reward;
    }
}
