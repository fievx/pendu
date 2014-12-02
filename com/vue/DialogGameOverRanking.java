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
import javax.swing.JTextField;

import Model.MenuEnum;

/*
 * La boite de dialogue DialogGameWon s'affiche lorsque le joueur a trouv� le mot.
 * La boite de dialogue affiche le mot trouv�, le nombre de couts jou�s, le nombre de points marqu�s et le nombre de points 
 * au total sur la partie en cours
 */
public class DialogGameOverRanking extends JDialog implements Observable{

	
	//Un JLabel pour l'affichage du texte dans la boite de dialog
	private JLabel labelTexte = new JLabel();
	
	//Un JPanel comme conteneur principal
	private JPanel container = new JPanel();
	
	// Un Jlabel nickanme, JtextField pour saisir le nickname et un bouton pour valider
	private JLabel nicknameLabel = new JLabel ("Pseudo : ");
	private JTextField nicknameText = new JTextField ();
	private JButton validateButton = new JButton ("Valider");
	private String nickname = new String("");
	private JPanel jpSouth = new JPanel();
	
	public DialogGameOverRanking (int pScoreTotal, String pMotTrouve){
		super ();
		
		// On d�finit les propri�t�s de la boite de dialogue
		//On sp�cifie une taille
		this.setSize(350, 400);
		//La position
		this.setLocationRelativeTo(null);
		//La bo�te ne devra pas �tre redimensionnable
		this.setResizable(true);
		
		this.setTitle("Game Over");
		this.setModal(true);
		
		//On d�finit le JPanel comme conteneur principal et on ajoute le JLabel
		this.getContentPane().add(container);
		container.setLayout(new BorderLayout());
		
		/*
		 * Pour permettre les retours � la ligne dans le JLable, nous utilisons des balises html lues par java. On ouvre et ferme le 
		 * texte avec les balises <html> et </html> et on utilise la balise <br> pour signifier un retour � la ligne.
		 * classique html =)
		 */
		String texte = "<html>Dommage ! <br> Tu n'as pas trouv� le mot "+pMotTrouve+".<br> La partie s'arr�te ici."
				+ "<br>Ton score s'�l�ve � "+pScoreTotal+" points.<br> <br>"
				+ "Mais bonne nouvelle, ton score te permet d'int�grer le top 10 !</html>";
		
		labelTexte.setText(texte);
		labelTexte.setAlignmentX(LEFT_ALIGNMENT);
		
		container.add(labelTexte, BorderLayout.CENTER);
		
		//On ajout les �l�ments de saisie du pseudo du joueur
		container.add(jpSouth, BorderLayout.SOUTH);
		jpSouth.add(nicknameLabel, BorderLayout.SOUTH);
		nicknameText.setPreferredSize(new Dimension(100,20));
		jpSouth.add(nicknameText, BorderLayout.SOUTH);
		
		//On fixe un �couteur sur le bouton et on place le bouton en bas de la fenetre
		validateButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				nickname=nicknameText.getText();
				setVisible(false);
			}
			
		});
		jpSouth.add(validateButton, BorderLayout.SOUTH);
		
		
		//Enfin on l'affiche
		this.setVisible(true);
	}
	
	public String getNickname (){
		return nickname;
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
