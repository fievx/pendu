package com.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * La boite de dialogue DialogGameWon s'affiche lorsque le joueur a trouv� le mot.
 * La boite de dialogue affiche le mot trouv�, le nombre de couts jou�s, le nombre de points marqu�s et le nombre de points 
 * au total sur la partie en cours
 */
public class DialogGameWon extends JDialog{
	private int coupsJoues, scorePartie, scoreTotal;
	private String motTrouve = new String();
	
	//Un JLabel pour l'affichage du texte dans la boite de dialog
	private JLabel labelTexte = new JLabel();
	
	//Un JPanel comme conteneur principal
	private JPanel container = new JPanel();
	
	// Un bouton pour continuer la partie;
	private JButton newGameButton = new JButton ("Nouveau Mot");
	
	public DialogGameWon (int pCoupsJoues, int pScorePartie, int pScoreTotal, String pMotTrouve){
		super ();
		
		// On d�finit les propri�t�s de la boite de dialogue
		//On sp�cifie une taille
		this.setSize(300, 200);
		//La position
		this.setLocationRelativeTo(null);
		//La bo�te ne devra pas �tre redimensionnable
		this.setResizable(false);
		
		this.setTitle("Partie gagn�e !");
		this.setModal(true);
		
		//On d�finit le JPanel comme conteneur principal et on ajoute le JLabel
		this.getContentPane().add(container);
		container.setLayout(new BorderLayout());
		
		/*
		 * Pour permettre les retours � la ligne dans le JLable, nous utilisons des balises html lues par java. On ouvre et ferme le 
		 * texte avec les balises <html> et </html> et on utilise la balise <br> pour signifier un retour � la ligne.
		 * classique html =)
		 */
		String texte;
		if (pCoupsJoues==0){
			texte = "<html>Bravo ! <br> Tu as trouv� le mot '"+pMotTrouve+"' sans une seule erreur.<br> Tu marques "+pScorePartie+" points."
					+ "<br>Ton score actuel s'�l�ve donc � "+pScoreTotal+" points.</html>";
		}
		else  texte = "<html>Bravo ! <br> Tu as trouv� le mot '"+pMotTrouve+"' avec "+pCoupsJoues+" erreurs.<br> Tu marques "+pScorePartie+" points."
				+ "<br>Ton score actuel s'�l�ve donc � "+pScoreTotal+" points.</html>";
		
		labelTexte.setText(texte);
		labelTexte.setAlignmentX(LEFT_ALIGNMENT);
		
		container.add(labelTexte, BorderLayout.CENTER);
		
		//On fixe un �couteur sur le bouton et on place le bouton en bas de la fenetre
		newGameButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
			
		});
		container.add(newGameButton, BorderLayout.SOUTH);
		newGameButton.setPreferredSize(new Dimension(30,20));
		
		
		//Enfin on l'affiche
		this.setVisible(true);
	}

}
