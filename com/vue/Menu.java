package com.vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import Model.MenuEnum;


public class Menu extends JMenuBar implements Observable{
	//Construction de l'arraylist d'Observateurs
	private ArrayList <Observateur> listobs  = new ArrayList<Observateur> ();
	
	//Des Objet JMenus de base
	private JMenu jmFichier = new JMenu ("Fichier");
	private JMenu jmEdition = new JMenu ("Edition");
	private JMenu jmAide = new JMenu ("Aide");
	
	//Des Objets JMenuItem comme s'il en pleuvait
	private JMenuItem jmiOpen = new JMenuItem ("Nouvelle partie");
	private JMenuItem jmiClose = new JMenuItem ("Fermer");
	private JMenu jmDifficulty = new JMenu ("Difficulté");
	private JMenuItem jmiRanking = new JMenuItem ("Afficher top 10");
	private JMenuItem jmiAPropos = new JMenuItem ("A propos");
		
	//Constructeur du Menu
	public Menu (){
		//On ajout tous les éléments au menu
		this.add(jmFichier);
		jmFichier.setMnemonic('F');
		this.add(jmEdition);
		jmEdition.setMnemonic('E');
		this.add(jmAide);
		jmAide.setMnemonic('A');
		
		jmFichier.add(jmiOpen);
		jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		jmFichier.add(jmiRanking);
		jmiRanking.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		jmFichier.add(jmiClose);
		jmiClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
		jmEdition.add(jmDifficulty);
		jmEdition.addSeparator();
		
		
		//Création des sous menu de Difficulté
		JRadioButtonMenuItem rbItemFacile = new JRadioButtonMenuItem ("Facile");
		JRadioButtonMenuItem rbItemNormale = new JRadioButtonMenuItem ("Normale");
		JRadioButtonMenuItem rbItemDifficile = new JRadioButtonMenuItem ("Difficile");
		jmDifficulty.add(rbItemFacile);
		jmDifficulty.add(rbItemNormale);
		jmDifficulty.add(rbItemDifficile);
			//Regroupement des radio Buttons dans un groupe
			ButtonGroup bg = new ButtonGroup();
			bg.add(rbItemFacile);
			bg.add(rbItemNormale);
			bg.add(rbItemDifficile);
		
		
		
		
		//Placement du bouton A propos
		jmAide.add(jmiAPropos);
				
		/*
		 * Mise en place des écouteurs
		 */
		//Sur les menus
		jmiOpen.addActionListener(new MenuListener(MenuEnum.startGame));
		jmiClose.addActionListener(new MenuListener(MenuEnum.close));
		jmiRanking.addActionListener(new MenuListener (MenuEnum.ranking));
		
		//Sur les difficultés
		rbItemFacile.addActionListener(new MenuListener(MenuEnum.easy));
		rbItemNormale.addActionListener(new MenuListener(MenuEnum.normal));
		rbItemDifficile.addActionListener(new MenuListener(MenuEnum.hard));		
		
		
		//Sur le menu Aide
		jmiAPropos.addActionListener(new ActionListener(){
			JOptionPane jop = new JOptionPane();
			ImageIcon i = new ImageIcon("images/ardoise magique.jpg");
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
			jop.showMessageDialog(null, "Pendu V1.0 \n"
					+ "Développé par Jérémy DENAIS \n"
					+ "Et Maintenant retourne jouer ! ","A Propos" ,JOptionPane.INFORMATION_MESSAGE , i);
			}
			
		});
	}
	
		public void addObservateur(Observateur obs) {
			this.listobs.add(obs);
		}

		@Override
		public void delObservateur() {
			// TODO Auto-generated method stub
			
		}

		//CLasse d'action générale des boutons du Menu
		public class MenuListener implements ActionListener {
			protected MenuEnum a ;
			public MenuListener (MenuEnum i){
				a=i;
			}
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for(Observateur obs : listobs)
					obs.update(a);
			}
		}
		

		@Override
		public void updateObservateur(MenuEnum pMenu) {
			// TODO Auto-generated method stub
			
		}
		
}
