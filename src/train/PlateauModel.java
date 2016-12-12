/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package train;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author remir
 */
public class PlateauModel {
    int[][] board;
    final int tailleX = 10;
    final int tailleY = 20;
    
    ArrayList<Observateur> observation;
    ArrayList<LigneTrain> lignes;
    
    public PlateauModel() {
        board = new int[tailleX][tailleY];
        observation = new ArrayList<Observateur>();
        lignes = new ArrayList<LigneTrain>();
    }
    
    void register(Observateur o) {
        observation.add(o);
    }
    
    void unregister(Observateur o) {
        observation.remove(o);
    }
    
    void avertirAllObservateurs() {
        for(Observateur o : observation){
            o.avertir();
        }
    }
    
    void avertirNewGameAllObservateurs() {
        for(Observateur o : observation){
            o.avertirNewGame();
        }
    }
    
    void nouvellePartie(){
        for(int i=0;i<tailleX;i++){
            for(int j=0;j<tailleY;j++){
                board[i][j] = 0;
                //couronne de montagne autour du plateau 
                if(i==0 || j == 0 || i == tailleX-1|| j == tailleY-1){
                    action(new CaseControler(this, i, j, 8));
                } 
            }
        }
        action(new CaseControler(this, 3, 5, 1));
        action(new CaseControler(this, 5, 10, 1));
        action(new CaseControler(this, 2, 12, 1));
        action(new CaseControler(this, 1, 0, 8));
        action(new CaseControler(this, 1, 1, 8));
        action(new CaseControler(this, 1, 2, 8));
        action(new CaseControler(this, 6, 3, 8));
        action(new CaseControler(this, 6, 4, 8));
        action(new CaseControler(this, 3, 8, 8));
        action(new CaseControler(this, 3, 9, 8));
        
        avertirNewGameAllObservateurs();
    }
    
    public void action(CaseControler c) {
        board[c.getX()][c.getY()] = c.getType();
        updatePlateau();
        avertirAllObservateurs();
        ligneTrain();
    }
    
    public void updatePlateau() {
        for(int i=0;i<tailleX;i++){
            for(int j=0;j<tailleY;j++){
                //si rail : l'entourer de case grise (les 2 cases qui ne sont pas des rails adjacents)
                if(board[i][j] == 2) {
                    int[] adjacents = new int[4];
                    int adjacentCompt = 0;  
                    if((board[i-1][j] == 2) || (board[i-1][j] == 1)) {
                        adjacents[0] = 1;
                        adjacentCompt++;
                    }
                    if((board[i+1][j] == 2) || (board[i+1][j] == 1)) {
                        adjacents[1] = 1;
                        adjacentCompt++;
                    }
                    if((board[i][j-1] == 2) || (board[i][j-1] == 1)) {
                        adjacents[2] = 1;
                        adjacentCompt++;
                    }
                    if((board[i][j+1] == 2) || (board[i][j+1] == 1)) {
                        adjacents[3] = 1;
                        adjacentCompt++;
                    }
                    if(adjacentCompt == 2) {
                        for(int k=0;k<adjacents.length;k++) {
                            if(adjacents[0] == 0) {
                                if(board[i-1][j] == 0){
                                    board[i-1][j] = 9;
                                }
                            }
                            if(adjacents[1] == 0) {
                                if(board[i+1][j] == 0){
                                    board[i+1][j] = 9;
                                }
                            }
                            if(adjacents[2] == 0) {
                                if(board[i][j-1] == 0){
                                    board[i][j-1] = 9;
                                }
                            }
                            if(adjacents[3] == 0) {
                                if(board[i][j+1] == 0){
                                    board[i][j+1] = 9;
                                }
                            }
                        }
                    }
                }
                // retirer case grise si plus de rail autour
                if(board[i][j] == 9) {
                    if((board[i-1][j] != 2) && (board[i+1][j] != 2) && (board[i][j-1] != 2) && (board[i][j+1] != 2)) {
                        board[i][j] = 0;
                    }
                }
            }
        }
    }
    
    public void ligneTrain() {
        for(int i=0;i<tailleX;i++){
            for(int j=0;j<tailleY;j++){
                //si case est une ville
                if(board[i][j] == 1) {
                    int x = i;
                    int y = j;
                    if (board[x-1][y] == 2) {
                        LigneTrain ligne1 = new LigneTrain();
                        int[] dclt = {x-1, y};
                        ligne1.ajouterALigne(dclt);
                        while(RailAdjacent(dclt) == 2) {
                            int[] caseSuivante = ligneTrainCaseSuivante(dclt);
                            ligne1.ajouterALigne(caseSuivante);
                            dclt = caseSuivante;
                        }
                        lignes.add(ligne1);
                    }
                }
            }
        }
    }
    
    public int RailAdjacent(int[] pair) {
        int x = pair[0];
        int y = pair[1];
        if(board[x-1][y] == 2) {
            return board[x-1][y];
        } else if(board[x+1][y] == 2) {
            return board[x+1][y];
        } else if(board[x][y-1] == 2) {
            return board[x][y-1];
        }else if(board[x][y+1] == 2) {
            return board[x][y+1];
        } else {
            return 0;
        }
    }
    
    public int[] ligneTrainCaseSuivante(int[] pair) {
        int x = pair[0];
        int y = pair[1];
        if((board[x-1][y] == 2) && (x-1 != 2) && (y != 2)) {
            int[] pair = {x, y};
            ligne1.ajouterALigne(pair);
            x--;
            casePrecedente = board[x-1][y];
        }
        if((board[x+1][y] == 2) && (board[x+1][y] != casePrecedente)) {
            int[] pair = {x, y};
            ligne1.ajouterALigne(pair);
            x++;
            casePrecedente = board[x+1][y];
        }
        if((board[x][y-1] == 2) && (board[x][y-1] != casePrecedente)) {
            int[] pair = {x, y};
            ligne1.ajouterALigne(pair);
            y--;
            casePrecedente = board[x][y-1];
        }
        if((board[x][y+1] == 2) && (board[x][y+1] != casePrecedente)) {
            int[] pair = {x, y};
            ligne1.ajouterALigne(pair);
            y++;
            casePrecedente = board[x][y+1];
        }
    }
    
    @Override
    public String toString() {
        /*LigneTrain ligne1 = new LigneTrain();
        int[] pair1 = {8, 6};
        int[] pair2 = {1, 7};
        ligne1.ajouterALigne(pair1);
        ligne1.ajouterALigne(pair2);
        ligne1.ligneFinie();
        lignes.add(ligne1);*/
        
        String temp = "";
        for(int i=0;i<tailleX;i++){
            for(int j=0;j<tailleY;j++){
                temp += "" + board[i][j];
            }
            temp += "\n";
        }
        for(LigneTrain l : lignes){
            temp += l.toString();
        }
        return temp;
    }
    
}
