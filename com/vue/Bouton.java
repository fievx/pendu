package com.vue;



import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException; 
import javax.imageio.ImageIO;
import javax.swing.JButton;
  
public class Bouton extends JButton implements MouseListener{
   private Image img;
   private String name = new String();
   
   public Bouton(String pName){
   super(pName);
   this.name = pName;
    
   try {
      img = ImageIO.read(new File("fondBouton.png"));
   } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
       
   this.addMouseListener(this);
   }
     
   public void paintComponent(Graphics g){
      Graphics2D g2d = (Graphics2D)g;
      GradientPaint gp = new GradientPaint(0,0,Color.blue,0,20,Color.cyan,true);
      g2d.setPaint(gp);
      g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
      g2d.setColor(Color.black);
    
      //Objet qui permet de connaître les propriétés d'une police, dont la taille !
      FontMetrics fm = g2d.getFontMetrics();
      //Hauteur de la police d'écriture
      int height = fm.getHeight();
      //Largeur totale de la chaîne passée en paramètre
      int width = fm.stringWidth(this.name);
    
      //On calcule donc la position du texte, et le tour est joué !
      g2d.drawString(this.name, 
          this.getWidth() / 2 - (width / 2), 
          (this.getHeight() / 2) + (height / 4));     
   }
  
   public void mouseClicked(MouseEvent event) {
     //Pas utile d'utiliser cette méthode ici       
   }
  
   public void mouseEntered(MouseEvent event) {    
     //Nous changeons le fond en jaune pour notre image lors du survol
     //avec le fichier fondBoutonHover.png
     try {
         img = ImageIO.read(new File("fondBoutonHover.png"));   
     } catch (IOException e) {
         e.printStackTrace();
     }    
   }
  
   public void mouseExited(MouseEvent event) {
     //Nous mettons en vert pour notre image lorsqu'on quitte le bouton
     //avec le fichier fondBouton.png
     try {
         img = ImageIO.read(new File("fondBouton.png"));
     } catch (IOException e) {
         e.printStackTrace();
     }    
   }
  
   public void mousePressed(MouseEvent event) {
      //Nous mettons en orangé pour notre image lors du clic gauche
      //avec le fichier fondBoutonClic.png
      try {
        img = ImageIO.read(new File("fondBoutonClic.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }    
   }
  
   public void mouseReleased(MouseEvent event) {
      //Nous changeons le fond en orangé pour notre image 
      //lorsqu'on relâche le clic 
      //avec le fichier fondBoutonHover.png   
      //Si on est à l'extérieur, on dessine le fond par défaut
      if(event.getY() > 0){
        try {
          img = ImageIO.read(new File("fondBoutonHover.png"));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      //Sinon on met le fond jaune, la souris est encore dessus…
     else{
       try {
         img = ImageIO.read(new File("fondBouton.png")); 
        } catch (IOException e) {
          e.printStackTrace();
        }
     }   
   }   
}
