package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionnaire {

	/*
	 * La classe Dctionnaire tri par ordre de grandeur et alphabétique tous les mots du fichier texte dictionnaire
	 */
	private BufferedReader buf;
	private BufferedWriter bufW;
	private ArrayList<String> list, listTri;
	private int dicoSize = 0;
	
	public Dictionnaire (String pFileInputName) throws IOException{
		
			list = new ArrayList();	
		try {
			String line = new String();
			buf = new BufferedReader(new FileReader(new File(pFileInputName)));
			// On met l
			while((line = buf.readLine())!= null)
				list.add(line);
			// Maintenant qu'on a tous les mots dans la lit, on peut fermer le flux
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void sortFile (String pFileOutputName){
		listTri = new ArrayList<String>();
		try{				
			for (int i = 1; i<27;i++){
				for (int a =0; a<list.size(); a++){
					// dès qu'on voit un mot qui fait la taille i, on l'ajoute à la listTri
					if(list.get(a).length()==i)
						listTri.add(list.get(a));
					}
				}
			
			buf.close();
			bufW = new BufferedWriter(new FileWriter(new File(pFileOutputName)));
			for(int i = 0; i<list.size();i++){
			bufW.write(listTri.get(i));
			bufW.newLine();
			}
			bufW.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 *  Les méthodes get+difficulity renvoie une liste de mots de tailles différentes selon la difficulté choisie
	 *  Easy => 1 à 8 lettres
	 *  Normal => 9 à 15 lettres
	 *  Hard => 16 à 26 lettres
	 */
	
	public ArrayList<String> getEasy(){
		ArrayList <String> listEasy = new ArrayList <String>();
		for (int i = 0; i<list.size(); i++){
			if(list.get(i).length()<9){
				listEasy.add(list.get(i));
			}
		}	
		return listEasy;
	}
	
	public ArrayList<String> getNormal(){
		ArrayList <String> listNormal = new ArrayList <String>();
		for (int i = 0; i<list.size(); i++){
			if(list.get(i).length()>8 && list.get(i).length()<16){
				listNormal.add(list.get(i));
			}
		}	
		return listNormal;
	}
	
	public ArrayList<String> getHard(){
		ArrayList <String> listHard = new ArrayList <String>();
		for (int i = 0; i<list.size(); i++){
			if(list.get(i).length()>15){
				listHard.add(list.get(i));
			}
		}	
		return listHard;
	}
}
