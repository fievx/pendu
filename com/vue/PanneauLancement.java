package com.vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import Model.MenuEnum;


	
public class PanneauLancement extends JPanel implements Observable{

	private JPanel container = new JPanel();
	private JButton boutonLancement = new JButton ("Démarrer la partie");
	private InsertImage imagePendu;
	
	
		public PanneauLancement(){
			imagePendu = new InsertImage("Images/penduIntro.png");
			this.setLayout(new BorderLayout());
			this.add(imagePendu, BorderLayout.CENTER);
			this.add(boutonLancement, BorderLayout.SOUTH);
			
			boutonLancement.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					updateObservateur(MenuEnum.startGame);
				}
				
			});
		}

		////////////////// Méthodes relatives au pattern Observable /////////////////
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
