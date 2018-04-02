/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamblingapp;

/**
 *
 * @author Alex
 */
public class Setting {
    public String name, ruleMessage;
    public int opponentQty;
    public long entryFee, reward;
    
    public Setting(String name, String ruleMessage, long entryFee, int opponentQty, long reward){
        this.name = name;
        this.ruleMessage = ruleMessage;
        this.entryFee = entryFee;
        this.opponentQty = opponentQty;
        this.reward = reward;
    }   
    
}
