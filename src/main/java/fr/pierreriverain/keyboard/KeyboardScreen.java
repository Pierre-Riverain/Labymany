package fr.pierreriverain.keyboard;

import java.io.IOException;
import java.io.StringWriter;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Cette classe va permettre d'écouter les évènements clavier et va permettre de savoir si un listener est ajouté ou non et combiens de fois il à été ajouté.
 * @author Pierre Riverain
 *
 * @see com.github.kwhat.jnativehook.GlobalScreen
 */
public class KeyboardScreen extends GlobalScreen {

	public KeyboardScreen() {
		super();
	}

	/**
	 * Cette méthode indique si le listener passé en paramètre est dans la liste des listeners.
	 * @param nativeKeyListener Le listener à vérifier s'il est bien dans la liste des listeners.
	 * @return <code>true</code> si au moins une occurence est dans la liste des listeners. 
	 */
	public static boolean containsNativeKeyListener(NativeKeyListener nativeKeyListener) {
		boolean contains = false;
		
		if (nbrInstancesOfNativeKeyListener(nativeKeyListener) > 0) {
			contains = true;
		}
		
		return contains;
	}
	
	/**
	 * Cette méthode indique combiens de fois le listener passé en paramètre est dans la liste des listeners.
	 * @param nativeKeyListener Le listener à compter le nombre d'occurences dans la liste des listeners.
	 * @return Le nombre d'occurences du listener passé en paramètre.
	 */
	public static int nbrInstancesOfNativeKeyListener(NativeKeyListener nativeKeyListener) {
		int nbrInstances = 0;
		NativeKeyListener[] nativeKeyListeners = eventListeners.getListeners(NativeKeyListener.class);
		
		for (NativeKeyListener nativeKeyListener2 : nativeKeyListeners) {
			if (nativeKeyListener2.equals(nativeKeyListener)) {
				nbrInstances++;
			}
		}
		
		return nbrInstances;
	}
}
