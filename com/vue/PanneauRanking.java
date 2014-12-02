package com.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Model.MenuEnum;
import Model.Ranking;

public class PanneauRanking extends JPanel implements Observable{

	private Ranking ranking = new Ranking();
	private JLabel labelNoms, labelScores, labelIntro;
	private JButton bouton = new JButton ("Retour à la page d'accueil");
	
	public PanneauRanking (){
		
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		labelIntro = new JLabel("TOP 10 :");
		//labelIntro.setFont(new Font(Arial, 20, 20));
		this.add(labelIntro, BorderLayout.NORTH);
		
		labelNoms = new JLabel(ranking.namesToString());
		this.add(labelNoms, BorderLayout.WEST);
		
		labelScores = new JLabel (ranking.scoresToString());
		labelScores.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(labelScores,BorderLayout.EAST);
		
		this.add(bouton, BorderLayout.SOUTH);
		bouton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				updateObservateur(MenuEnum.main);
			}
			
		});
		
		this.setVisible(true);
	}
	
	
	//////////////// Methodes du pattern Observable//////////
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
