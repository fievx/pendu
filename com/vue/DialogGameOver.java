package com.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.MenuEnum;

/*
 * La boite de dialogue DialogGameWon s'affiche lorsque le joueur a trouvé le mot.
 * La boite de dialogue affiche le mot trouvé, le nombre de couts joués, le nombre de points marqués et le nombre de points 
 * au total sur la partie en cours
 */
public class DialogGameOver extends JDialog implements Observable {

	
	//Un JLabel pour l'affichage du texte dans la boite de dialog
	private JLabel labelTexte = new JLabel();
	
	//Un JPanel comme conteneur principal
	private JPanel container = new JPanel();
	
	// Un bouton pour continuer la partie;
	private JButton mainMenuButton = new JButton ("Afficher top 10");
	
	public DialogGameOver (int pScoreTotal, String pMotTrouve){
		super ();
		
		// On définit les propriétés de la boite de dialogue
		//On spécifie une taille
		this.setSize(300, 200);
		//La position
		this.setLocationRelativeTo(null);
		//La boîte ne devra pas être redimensionnable
		this.setResizable(false);
		
		this.setTitle("Game Over");
		this.setModal(true);
		
		//On définit le JPanel comme conteneur principal et on ajoute le JLabel
		this.getContentPane().add(container);
		container.setLayout(new BorderLayout());
		
		/*
		 * Pour permettre les retours à la ligne dans le JLable, nous utilisons des balises html lues par java. On ouvre et ferme le 
		 * texte avec les balises <html> et </html> et on utilise la balise <br> pour signifier un retour à la ligne.
		 * classique html =)
		 */
		String texte = "<html>Dommage ! <br> Tu n'as pas trouvé le mot "+pMotTrouve+".<br> La partie s'arrête ici."
				+ "<br>Ton score s'élève à "+pScoreTotal+" points.</html>";
		
		labelTexte.setText(texte);
		labelTexte.setAlignmentX(LEFT_ALIGNMENT);
		
		container.add(labelTexte, BorderLayout.CENTER);
		
		//On fixe un écouteur sur le bouton et on place le bouton en bas de la fenetre
		mainMenuButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
			
		});
		container.add(mainMenuButton, BorderLayout.SOUTH);
		mainMenuButton.setPreferredSize(new Dimension(30,20));
		
		
		//Enfin on l'affiche
		this.setVisible(true);
	}

	
//////////////////Methodes relatives au pattern Observateur //////////////////////////
//Construction de l'arraylist d'Observateurs
private ArrayList <Observateur> listobs  = new ArrayList<Observateur> ();
@Override
public void addObservateur(Observateur obs) {
// TODO Auto-generated method stub
this.listobs.add(obs);
}

@Override
public void delObservateur() {
// TODO Auto-generated method stub

}

@Override
public void updateObservateur(MenuEnum a) {
// TODO Auto-generated method stub
for(Observateur obs : listobs)
obs.update(a);
}


}
