package com.vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/*
 * L'objet PanneauPendu gère les différentes images de pendu qui s'affichent pendant le jeu
 */
public class PanneauPendu extends JPanel{

	/*
	 * Le PanneauPendu récupère et affiche une des images de pendu.
	 * On peut ensuite changer l'image avec la méthode setImage
	 */
	private int numeroImage;
	
	public PanneauPendu (int numero){
		super();
		this.numeroImage = numero;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Importation de l'image de pendu
				try {
					Image img = ImageIO.read(new File("Images/pendu"+numeroImage+".png"));
					g.drawImage(img, 0,0,  this.getWidth(), this.getHeight(), this);
					
					} catch (IOException e) {
					e.printStackTrace();
					}
		this.setVisible(true);
	}
	
	
	//La méthode setNextImage permet de changer l'image de fond de pendu
	public void setNextImage (){
		this.numeroImage++;
		this.repaint();
	}

	public void setImage(int numero){
		this.numeroImage=numero;
		this.repaint();
	}
}
