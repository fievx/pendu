package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.vue.Observable;
import com.vue.Observateur;

public class Ranking implements Observable {
	/**
	 * La classe Ranking sert à gérer le classement des joueurs lorsqu'ils finissent une partie.
	 * Elle parcours un fichier text dans lequel le classemetn est enregistré
	 * 
	 */
	
	//Nous utilisons un tableau de scores que nous remplissons au moment ou nous lisons le fichier txt
	private static int tabScores [] = {0,0,0,0,0,0,0,0,0,0} ;
	private static String tabNames [] = new String [10];
	
	/**
	 * Le constructeur lit le fichier texte et rempli les tableaux de noms et scores
	 */
	public Ranking (){
		BufferedReader buf;
		try {
			buf = new BufferedReader(new FileReader("Images/ranking.txt"));
			String line = new String();
			for(int i = 0; i<10; i++){
				line = buf.readLine();
				tabNames[i]=readName(line);
				tabScores[i]=readScore(line);
				
			}
			buf.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}	
	}
	
	// readScore renvoie le score du joueur à partir de la ligne "name/score" du fichier text
	private static int readScore (String str){
		boolean reachedSeparator = false;
		String score = new String();
		char tabChar[] = str.toCharArray();
		for (char c : tabChar){
			if (reachedSeparator){
				score+=c;
			}
			if (c == '/')
				reachedSeparator = true;
		}
		
		// On utilise la méthode Integer.parse(String) pour convertir la chaine de chiffres lue en integer
		int i = Integer.parseInt(score);
		return i;
	}
	
	// readName renvoie le nom du joueur à partir de la ligne "name/score" du fichier text
	private static String readName (String str){
			boolean reachedSeparator = false;
			String name = new String();
			char tabChar[] = str.toCharArray();
			for (char c : tabChar){
				if (c == '/')
					reachedSeparator = true;
				if (reachedSeparator == false)
					name+=c;
			}
			return name;
		}
	
	// isRanking renvoie un boolean true si le score du joueur mérite d'être dans le top 10
	public boolean isRanking(int score){
		boolean bool = false;
		for (int i : tabScores){
			if (score > i)
				bool = true;
		}
		return bool;
	}
	
	// positionScore détermine la position du score dans le top 10
	public int positionScore (int i){
		int place = 0;
		while (i < tabScores[place]){
			place++;
		}
		return place;
	}
	
	// insertScore insère le score dans le tableau de scores et le nom dans le tableau des noms
	public void insertScore (String pName, int pScore){
		// On détermine la place du score du joueur
		int place = 0;
		while (pScore < tabScores[place]){
			place++;
		}
		// On remplace tous les scores par le score du dessus jusqu'à l'emplacement du score qui vient d'être joué
		// Autrement dit, on décale d'un cran vers le bas, tous les scores inférieurs au score qui vient d'être joué
		for (int a = 9; a>place ; a--){
			tabScores[a]= tabScores[a-1];
			tabNames [a]=tabNames[a-1];
		}
		// On insère le score et le nom du joueur.
		tabScores[place]= pScore;
		tabNames[place]= pName;
		
		// on appelle la méthode generateRanking qui réecrit le fichier texte avec les nouveaux scores
		generateRanking();
	}
	
	// generateRanking qui réecrit le fichier texte avec les nouveaux scores
	static void generateRanking(){
		BufferedWriter buf;
		try{
			buf = new BufferedWriter(new FileWriter("Images/ranking.txt"));
			String text = new String();
			for(int i= 0; i<tabNames.length; i++){
				text+=tabNames[i]+"/"+tabScores[i]+"\r";
				text+="\n";
			}
			buf.write(text);
			buf.close();
		}catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}	
	}
	
	//namesToString renvoie un string des noms
	public String namesToString(){
		/*
		 * Attention à l'utilisation des balises html dans un String. Les balises <html> et </html> doivent être
		 * placées uniquement au début et à la fin du String. Donc ne pas les répéter dans une boucle !
		 */
		String str = "<html>";
		for (String name : tabNames){
			str += name+"<br><br>";
		}
		str+="</html>";
		return str;		
	}
	
	//scoresToString renvoie un string des scores
	public String scoresToString (){
		String str = "<html>";
		for (int a : tabScores){
			String j ="";
			str += j.valueOf(a)+" Points<br><br>";
		}
		str+="</html>";
		return str;
	}
	
/////////////// Methodes du pattern Observable ///////////////
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
