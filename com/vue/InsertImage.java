package com.vue;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class InsertImage extends JPanel{

	private String cheminImage = new String();
	
	public InsertImage (String chemin){
		cheminImage = chemin;
		//this.setVisible(true);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//On choisit une couleur de fond pour le rectangle
		g.setColor(Color.white);
		//On le dessine de sorte qu'il occupe toute la surface
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//Importation de l'image de pendu
		try {
			Image img = ImageIO.read(new File(this.cheminImage));
			g.drawImage(img, 0, 0, this);
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
}
