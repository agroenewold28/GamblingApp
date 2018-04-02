/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamblingapp;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class GameController {
    static Card discardPileCard;
    static Card oldCard;
    static Boolean firstGame = true;
    int numOfPlayers;
    static ArrayList<Card> topPile;
    ArrayList<Opposition> players;
    Opposition knockingPlayer;
    static int topPileCount;
    
    public GameController(Setting s){
        numOfPlayers = s.opponentQty + 1;
        topPile = new ArrayList();
        players = new ArrayList();
        topPileCount = 0;
        this.fillDeck();
    }
    
    private void fillDeck(){
        if(!firstGame)
            GamblingApp.player.playerCards = new ArrayList();
        for(int i = 0; i < 4; i++){
            for(int j = 2; j <= 14; j++){
                topPile.add(new Card(j, Card.cardSuits.get(i)));
                topPileCount++;
            }
        }
    }
    
    public void shuffleCards(){
        Collections.shuffle(topPile);
    }
    
    public void dealCards(){
        int i = topPileCount - 1;
        for(int j = 0; j < 3; j++){
            for(Opposition x : players){
                x.playerCards.add(topPile.remove(i));
                i--;
                topPileCount--;
            }
        }
        discardPileCard = topPile.remove(i);
        topPileCount--;
    }
    
    public void addPlayers(){
        players.add(GamblingApp.player);
        for(int i = 0; i < numOfPlayers - 1; i++){
            players.add(new Opposition());
        }
    }
    
    public void nextTurn(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i) == GameGUI.curPlayer){
                if(i == players.size() - 1){
                    GameGUI.curPlayer = players.get(0);
                    this.playerTurn(false);
                    break;
                }
                else{
                    GameGUI.curPlayer = players.get(i + 1);
                    break;
                }
            }
        }
    }
    
    public void playerTurn(Boolean firstTurn){
        if(SettingSelectorWindow.game.knock)
            return;
        else if(firstTurn)
            JOptionPane.showMessageDialog(null, "It's your turn first!");
        else
            JOptionPane.showMessageDialog(null, "It's your turn!");
    }
    
    public void knockPerformed(){
        JOptionPane.showMessageDialog(null, "Everyone gets one turn and then the game is over!");
        knockingPlayer = GameGUI.curPlayer;
    }
    
    public void terminateGame(){
        if(this.computeWinner() == GamblingApp.player){
            if(SettingSelectorWindow.game.getPlayerSum(GamblingApp.player) == 31){
                JOptionPane.showMessageDialog(null, "You won double the reward of $" + GameGUI.curSetting.reward + "!");
                GamblingApp.player.wonGame(true);
            }
            else{
                JOptionPane.showMessageDialog(null, "You won! Your reward is $" + GameGUI.curSetting.reward + "!");
                GamblingApp.player.wonGame(false);
            }
        }
        else{
            int i;
            for(i = 0; i < players.size(); i++){
                if(this.computeWinner() == players.get(i))
                    break;
            }
            JOptionPane.showMessageDialog(null, "Opponent " + i + " wins");
        }
        GamblingApp.w.updatePlayerBalance();
        SettingSelectorWindow.game.setVisible(false);
        GamblingApp.w.setVisible(false);
        GamblingApp.w = new SettingSelectorWindow();
        GamblingApp.w.setVisible(true);
        if(GamblingApp.player.balance == 0){
            JOptionPane.showMessageDialog(null, "You have been given 10 free dollars cause you suck!");
            GamblingApp.player.balance += 10;
            GamblingApp.w.updatePlayerBalance();
        }
    }
    
    private Opposition computeWinner(){
        ArrayList<Opposition> winners = new ArrayList();
        Opposition winner = null;
        int highest = 0;
        int tie = 0;
        for(Opposition x : players){
            int temp = SettingSelectorWindow.game.getPlayerSum(x);
            if(temp > highest){
                if(temp == 31)
                    return x;
                else{
                    highest = temp;
                    winner = x;
                }
            }
            else if(temp == highest)
                tie++;
        }
        if(tie > 0){
            for(Opposition x : players){
                if(SettingSelectorWindow.game.getPlayerSum(x) == highest){
                    winners.add(x);
                }
            }
            double r = Math.random();
            double temp = 1 / winners.size() - 1;
            for(int i = 0; i < winners.size(); i++){
                if(r <= temp)
                    return winners.get(i);
                else
                    temp += temp;
            }
        }
        return winner;
    }
    
    public void reShuffleCards(){
        Card d = null;
        Boolean currentCard = false;
        ArrayList<Card> cardsPresent = new ArrayList();
        for(Opposition x : players){
            for(Card c : x.playerCards){
                cardsPresent.add(c);
            }
        }
        for(int i = 0; i < 4; i++){
            for(int j = 2; j <= 14; j++){
                d = new Card(j, Card.cardSuits.get(i));
                for(Card e : cardsPresent){
                    if(d.tempValue == e.tempValue && d.suit.equals(e.suit))
                        currentCard = true;
                }
                if(d.tempValue == GameController.discardPileCard.tempValue && d.suit.equals(GameController.discardPileCard.suit))
                    currentCard = true;
            }
            if(currentCard)
                break;
            else{
                topPile.add(d);
                topPileCount++;
            }
        }
        this.shuffleCards();
        JOptionPane.showMessageDialog(null, "Top deck has been reshuffled");
    }
}
