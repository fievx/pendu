package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import com.vue.Observable;
import com.vue.Observateur;

public class MotJoue implements Observable {
	//Construction de l'arraylist d'Observateurs
	private ArrayList <Observateur> listobs  = new ArrayList<Observateur> ();
	
	//Variables de la classe
	private String mot = new String ();
	private int taille;
	private char  listLettres [];
	private char lettreJouee;
	private Dictionnaire dico;
	private ArrayList <String> list = new ArrayList <String>();
	private DifficultyEnum difficulty;
	private boolean motAcceptable = false;
	
	//On utilise un int pour compter le nombre de lettres trouv�es
	private		int countTrouvees = 0;
	
	// Emplacements est un arraylist d'integers dans laquelle chaque integer donne l'emplacement des lettres trouv�es dans le mot
	private char motAffichage [];
	
	/*
	 * Constructeur du MotJou�
	 */
	@SuppressWarnings("unchecked")
	public MotJoue (DifficultyEnum pDifficulty){
		difficulty=pDifficulty;
		this.mot = this.choixMot();
		this.taille=mot.length();
		//On cr�e deux tableaux de char sur la base du mot en cours
		this.motAffichage= mot.toCharArray();
		this.listLettres= mot.toCharArray();
		
		// et le motAffichage, on remplace toutes les lettres par des "_"
		for (int i = 0; i<motAffichage.length; i++){
			motAffichage[i]='-';
		}
				
	}
	
	//La methode choixMot renvoie un mot au hasard parmi le dictionnaire pour le jeu
	public String choixMot (){
		String str = new String ();
		//On met le ficher text du dictionnaire dans un buffer
		// Toujours d�clarer les objets en dehors du bloc catch
		try {
			dico = new Dictionnaire ("Images/dictionnaire.txt");
			switch (difficulty){
			case easy :
				list = dico.getEasy();
				break;
			case normal :
				list= dico.getNormal();
				break;
			case hard :
				list= dico.getHard();
				break;
			}
		
			//On choisi un nombre au hasard en faisant une boucle jusqu'� avoir un mot sans caract�res speciaux
			while (motAcceptable == false){
			int nbre = (int)(Math.random()*list.size());
			str = list.get(nbre);
			if (isSpecialCharacter(str)==false)
				motAcceptable = true;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		char tab []= str.toCharArray();
		str = new String();
		int i=0;
		for (char c: tab){
			str+=convert(c);
		}
		System.out.println(str);
		return str;
	}
	
	//La m�thode isLetterIn renvoie le bool�an pour dire si la lettre jou�e est pr�sente dans le mot
	public boolean isLetterIn (char pLettreJouee){
		boolean bool = false;
		for (char a : listLettres){
			if (pLettreJouee == a)
				bool = true;
		}
		return bool;
	}
	
	public String getMot(){
		return mot;
	}
	
	//La methode isSpecialCharacter renvoie un booleen true si le mot jou� contient soit un espace soit un tiret
	public boolean isSpecialCharacter (String pMot){
		boolean bool = false;
		char tab [] = pMot.toCharArray();
		for (char a : tab){
			if (a == ' '|| a == '-')
				bool = true;
		}
		
		return bool;
	}
	
	
	/**
	 * La m�thode convert char est utilis�e au moment du choix du mot. Elle permet de checker chaque lettre et de supprimer les accents
	 * du mot, ainsi que le "�".
	 * @param c
	 * @return
	 */
	public static char convert(char c) {		
		if (c >= 97 && c <= 122) {
			return c;
		} else if (c >= 65 && c <= 90) {
			return c;
		} else if (c >= 43 && c <= 57) {
			return c;
		} else if (c == 32 || c == 39 || c == 40 || c == 41 || c == 58 || c == 63) {
			return c;
		} else {
			switch (c) {
			case '�' : case '�' : case '�' :
				return 'A';			
			case '�' :
				return 'C';
			case '�' : case '�' : case '�' : case '�' :
				return 'E';
			case '�' : case '�' : case '�' :
				return 'a';
			case '�' :
				return 'c';
			case '�' : case '�' : case '�' : case '�' :
				return 'e';
			case '�' : case '�' :
				return 'i';
			case '�' : case '�' : case '�' : case '�' : 
				return 'o';
			case '�' : case '�' : case '�' : 
				return 'u';
			default : 
				return '?';
			}
		}
	}
	
	// La m�thode checkletter renvoie un boolean true si la lettre jou�e est pr�sente dans le mot,
	// et remplace dans le tableau motAffichage, le '-' par la lettre jou�e
	public boolean checkLetter (char pLettreJouee){
		int a = 0;
		boolean bool = false;
		for (char ch : listLettres){
			if (ch==pLettreJouee){
				motAffichage[a]=ch;
				bool= true;
				countTrouvees++ ;
			}
			a++;
			}
		if (countTrouvees == taille){
		this.endGame();
		}
		return bool;
	}
	
	public boolean checkMot (String pMotJoue){
		boolean bool = false;
		if( pMotJoue.equals(mot))
			bool= true;
		return bool;
	}
	
	// La m�thode size renvoie la taille du mot jou�
	public int size (){
		return this.taille;
	}

	// La m�thode afficherMot renvoie le mot � afficher dans la feneter du jeu, avec des "_" � la places des lettres qui n'ont pas �t� trouv�es
	public String afficherMot (){
		//On met le tableau motAffichage en String et on le met tout en majuscule
		String str = new String();
		for (char a : motAffichage){
			str= str+a;
		}
		return str;
	}

	//La m�thode endGame dicte le comportement lorsque toutes les lettres ont �t� trouv�es
	public void endGame(){
		updateObservateur(MenuEnum.newGame);
		updateObservateur(MenuEnum.resetKeyboard);
	}

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
