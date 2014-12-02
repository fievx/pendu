package com.vue;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.DifficultyEnum;
import Model.MenuEnum;

public class Fenetre extends JFrame {


	private PanneauLancement pan = new PanneauLancement ();
	
	
	// Nous utilisons troi JPanels pour positionner nos boutons
	private JPanel container = new JPanel ();
	private JPanel jpsouth = new JPanel ();
	private JPanel jpNorth = new JPanel ();
	private PanneauJeu gamePanel = new PanneauJeu(DifficultyEnum.normal);
	private DifficultyEnum difficulty= DifficultyEnum.normal;

	
	//Nous ajoutons un menu de la classe Perso Menu qui hérite de JMenuBar
	private Menu menu = new Menu();
	
	/* Constructeur de notre fenetre
	 * 
	 */
	public Fenetre (String pName){
		this.setTitle(pName);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setJMenuBar(menu);

		
		// Parametrage du container principal
		this.getContentPane().add(pan);
		
		pan.addObservateur(new MenuObservateur());
		
		
		//Ajout de l'écouteur sur le Menu et sur la ToolBar
		menu.addObservateur(new MenuObservateur());
		
		
		
		// On rend la fenetre visible à la fin du constructeur
		this.setVisible(true);
	}
	
	//Méthode de cloture de la fene
	public void close(){
		this.setVisible(false);
	}
	
	////////////////   Méthodes d'affichages des différentes pages du jeu///////////
	// On affiche la page d'accueil / menu principal
	public void displayMain (){
		this.setContentPane(pan);
		this.repaint();
	}
	
	// On lance le jeu
	public void startGame(){
		gamePanel = new PanneauJeu(difficulty);
		gamePanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		gamePanel.addObservateur(new MenuObservateur());
		
		this.setContentPane(gamePanel);
		// Au moment de changer le panel avec setContentPane, TOUJOURS appeler validate sur le top container (la fenetre) !!! et 
		// ensuite repaint.
		this.validate();
		this.repaint();
		gamePanel.repaint();
	}
	
	// on affiche le classement de joueurs
	public void displayRanking(){
		PanneauRanking ranDisp = new PanneauRanking();
		ranDisp.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		ranDisp.addObservateur(new MenuObservateur());
		this.setContentPane(ranDisp);
		// Au moment de changer le panel avec setContentPane, TOUJOURS appeler validate sur le top container (la fenetre) !!! et 
		// ensuite repaint.
		this.validate();
		this.repaint();
		ranDisp.repaint();
	}
	
	/*
	 * Classe d'action du Menu qui implémente l'interface Observateur
	 * Nous utilisons un switch pour gérer les différentes cas de figures du menu via la 
	 * méthode update de l'interface Observateur
	 */
	/////////////  A UPDATER EN FONCTION DU NOUVEAU MENU ///////////////
	public class MenuObservateur implements Observateur {
		@Override
		public void update(MenuEnum pObservateur) {
			switch (pObservateur){
			// Main = 1
			case main :
				displayMain();
				break;
			// Start Game )=2
			case startGame :
				startGame();
				break;
			//Fermer = 4
			case close:
				close();
				break;
			// Ranking = 5
			case ranking :
				displayRanking();
				break;
			// Facile = 6	
			case easy:
				gamePanel.setDifficulty(DifficultyEnum.easy);
				difficulty= DifficultyEnum.easy;
				break;
			// Normal = 7
			case normal:
				gamePanel.setDifficulty(DifficultyEnum.normal);
				difficulty= DifficultyEnum.normal;
				break;
			// difficile = 8
			case hard :
				gamePanel.setDifficulty(DifficultyEnum.hard);
				difficulty= DifficultyEnum.hard;
				break;
			default :
			break;
			}				
		}
	}

	
}
