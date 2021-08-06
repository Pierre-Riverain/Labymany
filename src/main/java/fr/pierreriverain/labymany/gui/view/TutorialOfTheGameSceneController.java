package fr.pierreriverain.labymany.gui.view;

import java.io.IOException;

import fr.pierreriverain.labymany.Labymany;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Cette classe permet de gérer l'interface graphique qui affiche le tutoriel du jeu.
 * @author Pierre Riverain
 *
 */
public class TutorialOfTheGameSceneController {
	
	/**
	 * Cet objet représente la vue qui contient les <code>Label</code> qui affichent le tutoriel. Cet objet est utilisée pour les animations.
	 */
	@FXML
	private VBox tutorialScreenVBox;

	/**
	 * Cet objet représente le bouton qui permet de retourner sur l'interface graphique du menu principal.
	 */
	@FXML
	private Button returnToTheMainSceneButton;
	
	/**
	 * Cet objet représente l'instance de la classe de démarrage. Cet objet permet de revenir sur l'interface graphique du menu principal.
	 */
	private Labymany start;
	
	public TutorialOfTheGameSceneController() {
		super();
	}
	
	/**
	 * Cet accesseur permet d'obtenir l'instance de la classe de démarrage. Elle permet de retourner à l'interface graphique du menu principal.
	 * @return L'instance de la classe de démarrage.
	 */
	public Labymany getStart() {
		return start;
	}

	/**
	 * ce muttateur permet de définir l'instace de la classe de démarrage; Elle permet de retourner à l'interface graphique du menu principal.
	 * @param start L'instance de la classe de démarrage.
	 */
	public void setStart(Labymany start) {
		this.start = start;
	}

	/**
	 * Cette méthode permet d'initialiser l'opacité de la vue <code>tutorialScreenVBox</code> et du bouton <code>returnToTheMainSceneButton</code> pour l'animation d'apparition.
	 */
	@FXML
	private void initialize() {
		tutorialScreenVBox.setOpacity(0.00d);
		returnToTheMainSceneButton.setOpacity(0.00d);
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur clique sur le bouton <code>onReturnToTheMainDceneButton</code>. Elle permet de retourner sur l'interface graphique du menu principal.
	 */
	@FXML
	private void onReturnToTheMainSceneButtonClicked() {
		EventHandler<ActionEvent> actionOnAnimationFinished = (actionEvent) -> {
			try {
				start.showMainScene();
				start.animateMainScene(true);
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant le chargement du menu principal. Désolé pour le désagrément encouru : ");
			}
		};
		
		hideTutorialScreenVBox(0, 1000);
		hideReturnToTheMainScene(1000, 1000, actionOnAnimationFinished);
	}

	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>tutorialScreenVBox</code>. Cette méthode est équivalent à <code>showTutorialScreenVBox(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showTutorialScreenVBox(double delay, double duration) {
		showTutorialScreenVBox(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>tutorialScreenVBox</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showTutorialScreenVBox(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showTutorialScreenVBoxAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(tutorialScreenVBox.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(tutorialScreenVBox.opacityProperty(), 1.00d)));
		showTutorialScreenVBoxAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showTutorialScreenVBoxAnimation.setOnFinished(eventOnFinished);
		}
		showTutorialScreenVBoxAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>tutorialScreenVBox</code>. Cette méthode est équivalent à <code>showTutorialScreenVBox(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideTutorialScreenVBox(double delay, double duration) {
		hideTutorialScreenVBox(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>tutorialScreenVBox</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideTutorialScreenVBox(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideTutorialScreenVBoxAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(tutorialScreenVBox.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(tutorialScreenVBox.opacityProperty(), 0.00d)));
		hideTutorialScreenVBoxAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideTutorialScreenVBoxAnimation.setOnFinished(eventOnFinished);
		}
		hideTutorialScreenVBoxAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>returnToTheMainSceneButton</code>. Cette méthode est équivalent à <code>showReturnToTheMainScene(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showReturnToTheMainScene(double delay, double duration) {
		showReturnToTheMainScene(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>returnToTheMainSceneButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showReturnToTheMainScene(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showReturnToTheMainSceneAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(returnToTheMainSceneButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(returnToTheMainSceneButton.opacityProperty(), 1.00d)));
		showReturnToTheMainSceneAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showReturnToTheMainSceneAnimation.setOnFinished(eventOnFinished);
		}
		showReturnToTheMainSceneAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>returnToTheMainSceneButton</code>. Cette méthode est équivalent à <code>showReturnToTheMainScene(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideReturnToTheMainScene(double delay, double duration) {
		hideReturnToTheMainScene(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>returnToTheMainSceneButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideReturnToTheMainScene(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideReturnToTheMainSceneAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(returnToTheMainSceneButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(returnToTheMainSceneButton.opacityProperty(), 0.00d)));
		hideReturnToTheMainSceneAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideReturnToTheMainSceneAnimation.setOnFinished(eventOnFinished);
		}
		hideReturnToTheMainSceneAnimation.play();
	}
}
