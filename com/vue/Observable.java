package com.vue;

import Model.MenuEnum;

public interface Observable {

	public void addObservateur (Observateur obs);
	public void delObservateur ();
	public void updateObservateur(MenuEnum a);
	
}
