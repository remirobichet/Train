/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package train;

import java.util.ArrayList;

/**
 *
 * @author remir
 */
public class LigneTrain {
    
    ArrayList<int[]> ligneTrain;
    boolean ligneFinie;
    
    public LigneTrain() {
        
        ligneTrain = new ArrayList<int[]>();
        ligneFinie = false;
        
    }
    
    public void ajouterALigne(int[] z) {
        ligneTrain.add(z);
    }
    
    public void ligneFinie() {
        ligneFinie = true;
    }
    
    @Override
    public String toString() {
        String temp = "";
        for(int i = 0; i < ligneTrain.size(); i++){
            temp += ligneTrain.get(i)[0] + ", " + ligneTrain.get(i)[1];
            temp += "\n";
        }
        return temp;
    }
    
}