package com.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.DifficultyEnum;
import Model.MenuEnum;
import Model.MotJoue;
import Model.Ranking;

/*
 * La classe panneau jeu gère l'affichage des éléments pendant une partie de pendu. 
 * C'est un JPanel qui contient le mot en haut, l'image du pendu au milieu et un clavier virtuel en bas pour
 * sélectionner les lettres à jouer.
 */

public class PanneauJeu extends JPanel implements Observable{
	
	// Les variables sont un MotJoué, un tableau de lettres de l'alphabet, un compteur de coups, un TextField pour saisir le mot deviné
	private MotJoue mot;
	private char alphaTab [] =  {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private JButton buttonTab[] = new JButton[26];
	private int compteur = 0;
	private JTextField	motSaisie;
	private JLabel labelSaisie = new JLabel ("Je crois que le mot est : ");
	private JButton boutonSaisie;
	private JPanel JPSouth = new JPanel();
	private JPanel JPsaisie = new JPanel();
	private DifficultyEnum difficulty;
	
	//Un integer qui compte les erreurs
	int compteurErreurs = 0;
	
	// Un integer qui cumule les points. Nous ne le mettons pas à zéro entre chaques parties pour accumuler les scores.
	private int scorePartie = 0;
	private int scoreTotal;
	
	// Un JLabel pour l'affichage du mot en cour
	private JLabel affichageMot = new JLabel();
	
	// Un Jpanel pour le dessin du pendu, un JPanel pour l'affichage du mot à trouver, un JPanel pour tous les boutons de l'alphabet.
	private JPanel motPanel, alphaPanel;
	private PanneauPendu penduPanel = new PanneauPendu(0);
	
	// Un Ranking pour le ranking
	private Ranking ranking = new Ranking();
	
	//Constructeur du PanneauJeu
	public PanneauJeu (DifficultyEnum pDiff){
		//On définit quelques propritétés du PanneauJeu
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.add(JPSouth, BorderLayout.SOUTH);
		JPSouth.setLayout(new BorderLayout());
		difficulty= pDiff;
		//On initialise le mot joué
		mot = new MotJoue(difficulty);
		mot.addObservateur(new PanneauObservateur());
		
		//On définit les paramètres de l'image du pendu et on ajoute au centerPanel
		this.add(penduPanel);
		
		// Nous construisons un bouton par lettre de l'alphabet
		alphaPanel = new JPanel();
		alphaPanel.setBackground(Color.white);
		alphaPanel.setPreferredSize(new Dimension(this.getWidth(), 100));
	
		int i = 0;
		for (char lettre : alphaTab){
			//On cast la lettre qui est un char en String, et ensuite on crée un bonton par lettre
			String str = new String();
			str= String.valueOf(lettre);
			buttonTab[i]=new JButton (str);	
			i++;
		}

		// On ajoute tous les boutons du tableau à l'alphaPanel grave à une boucle for, et on place le panel plein de boutons sur le JPSouth
		for (JButton bouton : buttonTab){
			alphaPanel.add(bouton);
			bouton.addActionListener(new LettreListener());
		}
		JPSouth.add(alphaPanel, BorderLayout.NORTH);
		
		//  On ajoute le JLabel, le JTextfield et le JButton sous les boutons d'alphabets, pour que le joueur puisse saisir le mot qu'il croit
		// avoir trouvé.
		JPsaisie.add(labelSaisie, BorderLayout.SOUTH);
		motSaisie = new JTextField();
		motSaisie.setPreferredSize(new Dimension(150,20));
		JPsaisie.add(motSaisie, BorderLayout.SOUTH);
		boutonSaisie = new JButton ("Vérifier");
		JPsaisie.add(boutonSaisie, BorderLayout.SOUTH);
		boutonSaisie.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//On ajoute un coup au compteur
				compteur++;
				//On check le mot saisie et s'il est bon, on appelle la méthode newGame
				if(mot.checkMot(motSaisie.getText())==true){
					newGame();
				}
				else {
				penduPanel.setNextImage();	
				compteurErreurs++;
				if (compteurErreurs == 7)
					gameOver();
				}
			}
			
		});
		JPSouth.add(JPsaisie, BorderLayout.SOUTH);
		
		
		//On appelle la fonction de réglages du JLabel et on ajoute au motPanel
		initAffichageMot(mot.afficherMot());
		motPanel = new JPanel();
		motPanel.setBackground(Color.white);
		motPanel.add(affichageMot);
		
		
		this.add(motPanel, BorderLayout.NORTH);
		//this.add(centerPanel, BorderLayout.CENTER);
			
	}
	
	//La méthode initAffichageMot définit les réglagles du JLabel qui gère l'affichage du mot qui est joué
	public void initAffichageMot (String mot){
		//Définition d'une police d'écriture
		affichageMot.setText(mot);
		affichageMot.setForeground(Color.BLACK);
		Font police = new Font("Tahoma", Font.BOLD, 24);
		//On l'applique au JLabel
		affichageMot.setFont(police);
		affichageMot.setSize(200, 100);
		affichageMot.setHorizontalAlignment(JLabel.CENTER);
	}
	
	//La méthode resetKeyboard réactive toutes les lettres du clavier lorsque le joueur à trouvé le mot et passe au mot suivant.
	public void resetKeyboard (){
		for (JButton bouton : buttonTab)
			bouton.setEnabled(true);
	}
	
	/*
	 * La Classe d'action des boutons avec les lettres.
	 * A chaque clic, nous faisons les actions suivantes :
	 *  		mise à jour du JLabel du mot en cous
	 *  		incrémenter le compteur
	 *  		désactiver la lettre
	 *  si la lettre n'est pas dans le mot : 
	 *  		changer l'image du pendu
	 *  		
	 */
	public class LettreListener implements ActionListener {
		private char a;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			//On change l'action command du bouton en char
			a = arg0.getActionCommand().charAt(0);
			// On désactive la lettre jouée
			buttonTab[charToInt(a)].setEnabled(false);
			mot.checkLetter(a);
			//On met à jour l'affichage du mot
			initAffichageMot(mot.afficherMot());

			if (mot.isLetterIn(a)==false){
				penduPanel.setNextImage();
				compteurErreurs++;
				if (compteurErreurs == 7)
					gameOver();
		}
		}
	}

	// La méthode newGame gère le comportement lorsque le mot a été trouvé
	public void newGame (){
		getScore();
		DialogGameWon dialog = new DialogGameWon(compteurErreurs, scorePartie, scoreTotal, mot.getMot());
		mot = new MotJoue(difficulty);
		initAffichageMot(mot.afficherMot());
		penduPanel.setImage(0);
		compteur = 0;
		compteurErreurs = 0;
		//On vide le JText Field au cas où le joueur aurait tapé un mot
		motSaisie.setText("");
		resetKeyboard();
	}
	
	// La méthode gameOver gère le cas ou le joueur ne trouve pas le mot
	public void gameOver (){
		
		//on checke si le score doit être dans le ranking, et si oui, on fait apparaitre une boite de dialogue avec un JTextField
		if (ranking.isRanking(scoreTotal)){
			DialogGameOverRanking dialog = new DialogGameOverRanking (scoreTotal, mot.getMot());
			ranking.insertScore(dialog.getNickname(), scoreTotal);
			updateObservateur(MenuEnum.ranking);
		}	
		else{
		DialogGameOver dialog = new DialogGameOver(scoreTotal, mot.getMot());
		updateObservateur(MenuEnum.ranking);
		}
	}
	
	// La méthode getScore renvoie le nombre de points marqués en fonction du nombre de coups joués avant de trouver le mot
	public void getScore (){
		switch (compteurErreurs) {
			case 0:
				scorePartie=700;
				break;
			case 1 :
				scorePartie =500;
				break;
			case 2 :
				scorePartie=300;
				break;
			case 3 :
				scorePartie=200;
				break;
			case 4 :
				scorePartie=100;
				break;
			case 5 :
				scorePartie= 50;
				break;
			case 6 :
				scorePartie= 10;
				break;
		}
		scoreTotal+=scorePartie;
	}
	
	
	//La classe PanneauObservateur implémente l'interface Observateur et permet d'ajouter des Observateurs sur des éléments du Panneau
	public class PanneauObservateur implements Observateur{
	/*
	 * La méthode updateObservateur permet aux classes observées par Panneaujeu d'envoyer des instructions à PanneauJeu
	 * Ces instructions sont gérées par un SWITCH dans la méthode.
	 * (non-Javadoc)
	 * @see com.vue.Observateur#update(java.lang.String)
	 */
	@Override
	public void update(MenuEnum pObservateur) {
		// TODO Auto-generated method stub
		switch (pObservateur) {
		//New Word = 1
		case newGame :
			newGame();
			break;
		// Rest Keyboard = 2
		case resetKeyboard :
			resetKeyboard();
			break;
		}
	}
	}


	// La méthode charToInt renvoie un entier entre 0 et 25 correspondant à la lettre de l'alphabet
	public int charToInt (char a){
		int i = 0;
		while (alphaTab[i]!=a){
			i++;
		}
		return i;
	}

	// displayRanking affiche le ranking en appelant la méthode update Observateur de la fenere
	public void displayRanking (){
	updateObservateur(MenuEnum.ranking);	
	}
	
	// setDifficulty permet à la fenetre de changer la difficulté
	public void setDifficulty (DifficultyEnum pDiff){
		difficulty = pDiff;
	}
	
	//////////////////   Methodes relatives au pattern Observateur //////////////////////////
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

	 /* Classe d'action du Menu qui implémente l'interface Observateur
	 * Nous utilisons un switch pour gérer les différentes cas de figures du menu via la 
	 * méthode update de l'interface Observateur
	 */
	/////////////  A UPDATER EN FONCTION DU NOUVEAU MENU ///////////////
	public class MenuObservateur implements Observateur {
		@Override
		public void update (MenuEnum pObservateur) {
			switch (pObservateur){
			// ranking = 1
			case ranking :
				displayRanking();
				break;
			default :
			break;
			}				
		}
	}
}