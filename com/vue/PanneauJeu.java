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
 * La classe panneau jeu g�re l'affichage des �l�ments pendant une partie de pendu. 
 * C'est un JPanel qui contient le mot en haut, l'image du pendu au milieu et un clavier virtuel en bas pour
 * s�lectionner les lettres � jouer.
 */

public class PanneauJeu extends JPanel implements Observable{
	
	// Les variables sont un MotJou�, un tableau de lettres de l'alphabet, un compteur de coups, un TextField pour saisir le mot devin�
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
	
	// Un integer qui cumule les points. Nous ne le mettons pas � z�ro entre chaques parties pour accumuler les scores.
	private int scorePartie = 0;
	private int scoreTotal;
	
	// Un JLabel pour l'affichage du mot en cour
	private JLabel affichageMot = new JLabel();
	
	// Un Jpanel pour le dessin du pendu, un JPanel pour l'affichage du mot � trouver, un JPanel pour tous les boutons de l'alphabet.
	private JPanel motPanel, alphaPanel;
	private PanneauPendu penduPanel = new PanneauPendu(0);
	
	// Un Ranking pour le ranking
	private Ranking ranking = new Ranking();
	
	//Constructeur du PanneauJeu
	public PanneauJeu (DifficultyEnum pDiff){
		//On d�finit quelques proprit�t�s du PanneauJeu
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.add(JPSouth, BorderLayout.SOUTH);
		JPSouth.setLayout(new BorderLayout());
		difficulty= pDiff;
		//On initialise le mot jou�
		mot = new MotJoue(difficulty);
		mot.addObservateur(new PanneauObservateur());
		
		//On d�finit les param�tres de l'image du pendu et on ajoute au centerPanel
		this.add(penduPanel);
		
		// Nous construisons un bouton par lettre de l'alphabet
		alphaPanel = new JPanel();
		alphaPanel.setBackground(Color.white);
		alphaPanel.setPreferredSize(new Dimension(this.getWidth(), 100));
	
		int i = 0;
		for (char lettre : alphaTab){
			//On cast la lettre qui est un char en String, et ensuite on cr�e un bonton par lettre
			String str = new String();
			str= String.valueOf(lettre);
			buttonTab[i]=new JButton (str);	
			i++;
		}

		// On ajoute tous les boutons du tableau � l'alphaPanel grave � une boucle for, et on place le panel plein de boutons sur le JPSouth
		for (JButton bouton : buttonTab){
			alphaPanel.add(bouton);
			bouton.addActionListener(new LettreListener());
		}
		JPSouth.add(alphaPanel, BorderLayout.NORTH);
		
		//  On ajoute le JLabel, le JTextfield et le JButton sous les boutons d'alphabets, pour que le joueur puisse saisir le mot qu'il croit
		// avoir trouv�.
		JPsaisie.add(labelSaisie, BorderLayout.SOUTH);
		motSaisie = new JTextField();
		motSaisie.setPreferredSize(new Dimension(150,20));
		JPsaisie.add(motSaisie, BorderLayout.SOUTH);
		boutonSaisie = new JButton ("V�rifier");
		JPsaisie.add(boutonSaisie, BorderLayout.SOUTH);
		boutonSaisie.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//On ajoute un coup au compteur
				compteur++;
				//On check le mot saisie et s'il est bon, on appelle la m�thode newGame
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
		
		
		//On appelle la fonction de r�glages du JLabel et on ajoute au motPanel
		initAffichageMot(mot.afficherMot());
		motPanel = new JPanel();
		motPanel.setBackground(Color.white);
		motPanel.add(affichageMot);
		
		
		this.add(motPanel, BorderLayout.NORTH);
		//this.add(centerPanel, BorderLayout.CENTER);
			
	}
	
	//La m�thode initAffichageMot d�finit les r�glagles du JLabel qui g�re l'affichage du mot qui est jou�
	public void initAffichageMot (String mot){
		//D�finition d'une police d'�criture
		affichageMot.setText(mot);
		affichageMot.setForeground(Color.BLACK);
		Font police = new Font("Tahoma", Font.BOLD, 24);
		//On l'applique au JLabel
		affichageMot.setFont(police);
		affichageMot.setSize(200, 100);
		affichageMot.setHorizontalAlignment(JLabel.CENTER);
	}
	
	//La m�thode resetKeyboard r�active toutes les lettres du clavier lorsque le joueur � trouv� le mot et passe au mot suivant.
	public void resetKeyboard (){
		for (JButton bouton : buttonTab)
			bouton.setEnabled(true);
	}
	
	/*
	 * La Classe d'action des boutons avec les lettres.
	 * A chaque clic, nous faisons les actions suivantes :
	 *  		mise � jour du JLabel du mot en cous
	 *  		incr�menter le compteur
	 *  		d�sactiver la lettre
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
			// On d�sactive la lettre jou�e
			buttonTab[charToInt(a)].setEnabled(false);
			mot.checkLetter(a);
			//On met � jour l'affichage du mot
			initAffichageMot(mot.afficherMot());

			if (mot.isLetterIn(a)==false){
				penduPanel.setNextImage();
				compteurErreurs++;
				if (compteurErreurs == 7)
					gameOver();
		}
		}
	}

	// La m�thode newGame g�re le comportement lorsque le mot a �t� trouv�
	public void newGame (){
		getScore();
		DialogGameWon dialog = new DialogGameWon(compteurErreurs, scorePartie, scoreTotal, mot.getMot());
		mot = new MotJoue(difficulty);
		initAffichageMot(mot.afficherMot());
		penduPanel.setImage(0);
		compteur = 0;
		compteurErreurs = 0;
		//On vide le JText Field au cas o� le joueur aurait tap� un mot
		motSaisie.setText("");
		resetKeyboard();
	}
	
	// La m�thode gameOver g�re le cas ou le joueur ne trouve pas le mot
	public void gameOver (){
		
		//on checke si le score doit �tre dans le ranking, et si oui, on fait apparaitre une boite de dialogue avec un JTextField
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
	
	// La m�thode getScore renvoie le nombre de points marqu�s en fonction du nombre de coups jou�s avant de trouver le mot
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
	
	
	//La classe PanneauObservateur impl�mente l'interface Observateur et permet d'ajouter des Observateurs sur des �l�ments du Panneau
	public class PanneauObservateur implements Observateur{
	/*
	 * La m�thode updateObservateur permet aux classes observ�es par Panneaujeu d'envoyer des instructions � PanneauJeu
	 * Ces instructions sont g�r�es par un SWITCH dans la m�thode.
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


	// La m�thode charToInt renvoie un entier entre 0 et 25 correspondant � la lettre de l'alphabet
	public int charToInt (char a){
		int i = 0;
		while (alphaTab[i]!=a){
			i++;
		}
		return i;
	}

	// displayRanking affiche le ranking en appelant la m�thode update Observateur de la fenere
	public void displayRanking (){
	updateObservateur(MenuEnum.ranking);	
	}
	
	// setDifficulty permet � la fenetre de changer la difficult�
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

	 /* Classe d'action du Menu qui impl�mente l'interface Observateur
	 * Nous utilisons un switch pour g�rer les diff�rentes cas de figures du menu via la 
	 * m�thode update de l'interface Observateur
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