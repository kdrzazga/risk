/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *
 * @author golfier
 */
public class ImageAssets {
    
    
  public static void addTerritory(int pixel, int i, int j, List<Territory> listTerritoire) {
	    
	    Color couleur = new Color (pixel);
	    Pixel pixl = new Pixel (i, j, couleur);
	    if(!couleur.equals(Color.WHITE ) && !couleur.equals(Color.BLACK )){
	    	if(!TerritoryAssets.containsTerritory(listTerritoire, couleur)){
		    	Territory tmp = new Territory(couleur);
		    	tmp.addPixel(pixl);
		    	listTerritoire.add(tmp);
		    }
		    else{
		    	TerritoryAssets.addTerritoryPixel(listTerritoire, pixl);
		    }
	    }
	    
	  }
  
  public static List<Territory> imageProcess(BufferedImage image) {
    int w = image.getWidth();
    int h = image.getHeight();
    System.out.println("Width, Height: " + w + ", " + h);
    
    List<Territory> maListeDeTerritoire = new ArrayList<Territory>();
    
    for (int i = 0; i < w; i++) {
        for (int j = 0; j < h; j++) {
            
        	int pixel = image.getRGB(i,j);
            addTerritory(pixel, i, j, maListeDeTerritoire);
            
        }
    }
    
    /*
    ColorUtils utils = new ColorUtils();
	for (Territory terr : maListeDeTerritoire){
		System.out.println(utils.getColorNameFromColor(terr.color));
    }*/
    
    System.out.println("Il y a " + maListeDeTerritoire.size() + " couleurs différentes.");
    
    return maListeDeTerritoire;
  }
  
  
  public static BufferedImage colorTerritoire(BufferedImage image, Territory territoire, Color couleur){
      for (Pixel pix : territoire.pixelList) {
    	  image.setRGB(pix.x, pix.y, couleur.getRGB());
      }
	  return image;
  }
  
  
  public static Image colorTerritoireInit (Image imageParam, List<Player> players, List<Territory> territories){
      
        BufferedImage buffImage = SwingFXUtils.fromFXImage(imageParam, null);
        
        int nb_players = players.size()+1;
        int nb_territories = territories.size()+1;
        int territories_modulo = nb_territories % nb_players;
        int territory_per_player = nb_territories / nb_players;

        
        for (int i=0; i<territories.size(); i++) { // pour chaque territoire
            for (int j=0; j<players.size(); j++) {
                for (int k=0; k<territory_per_player; k++) {
                    Collections.shuffle(territories);
                    players.get(j).getTerritories().add(territories.get(i));
                    for (Pixel pix : territories.get(i).pixelList) {
                        buffImage.setRGB(pix.x, pix.y, players.get(j).getColor().getRGB());
                    }
                }             
            }
            while (territories_modulo > 0) {
                Collections.shuffle(territories);
                players.get(territories_modulo).getTerritories().add(territories.get(i));
                for (Pixel pix : territories.get(i).pixelList) {
                    buffImage.setRGB(pix.x, pix.y, players.get(territories_modulo).getColor().getRGB());
                }
                territories_modulo--;
            }
            
        }
     
        Image image_final =  SwingFXUtils.toFXImage(buffImage, null);
        
        return image_final;
  }
  
}
