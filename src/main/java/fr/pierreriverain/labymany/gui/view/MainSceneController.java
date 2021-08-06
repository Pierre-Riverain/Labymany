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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Cette classe permet de gérer l'interface graphique du menu principal avec des animations d'apparition et de disparition sur les composants graphique de cette scène.
 * @author Pierre Riverain
 *
 */
public class MainSceneController {
	
	/**
	 * Cet objet représente la vue qui permet d'afficher le logo d'homonymany.
	 */
	@FXML
	private ImageView logoView;
	
	/**
	 * Cet objet représente le bouton "paramètres" de cette scène.
	 */
	@FXML
	private Button settingsButton;
	
	/**
	 * Cet objet représente le bouton "jouer" de cette scène.
	 */
	@FXML
	private Button playButton;
	
	/**
	 * Cet pbjet représente le bouton "tutoriel" de cette scène.
	 */
	@FXML
	private Button tutorialOfTheGameButton;
	
	/**
	 * Cet objet représente la classe de démarrage.
	 */
	private Labymany start;
	
	public MainSceneController() {
		super();
	}
	
	/**
	 * Cet accesseur permet d'obtenir le logo qui a été défini pour être affiché sur la scène.
	 * @return Le logo défini.
	 */
	public Image getLogo() {
		return logoView.getImage();
	}

	/**
	 * Ce muttateur défini le logo à afficher sur la scène.
	 * @param logo Logo à afficher
	 */
	public void setLogo(Image logo) {
		logoView.setImage(logo);
	}

	/**
	 * Cet accesseur permet d'obtenir la classe de démarrage.
	 * @return La classe de démarrage.
	 */
	public Labymany getStart() {
		return start;
	}

	/**
	 * Ce muttateur permet de définir la classe de démarrage. Ceci permet de changer de scène quand l'utilisateur clique sur l'un des boutons.
	 * @param start La classe de démarrage à définir.
	 */
	public void setStart(Labymany start) {
		this.start = start;
	}

	/**
	 * Cette méthode permet d'initialiser l'opacité des boutons à 0. Ceci permet d'exécuter les animations d'apparition dans de bonnes conditions.
	 */
	@FXML
	private void initialize() {
		settingsButton.setOpacity(0.00d);
		playButton.setOpacity(0.00d);
		tutorialOfTheGameButton.setOpacity(0.00d);
	}
	
	/**
	 * Cette méthode est appelé quand l'utilisateur clique sur <code>playButton</code>. 
	 * Elle permet de passer à l'interface graphique de sélection de la difficulté en animation.
	 */
	@FXML
	public void onPlayButtonClicked() {
		//Cet objet permet d'effectuer le passage à la scène "SelectDifficultyScene" à la fin des animations sur cette scène.
		EventHandler<ActionEvent> actionOnAnimationFinished = (actionEvent) -> {
			try {
				start.showSelectDifficultyScene();
				start.animateShowSelectDifficultyScene();
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant le chargement de l'écran de sélection de la difficulté. Désolé pour le désagrément encouru : ");
			}
			
		};
		hideSettingsButton(0, 1000);
		hidePlayButton(500, 1000);
		hideTutorialOfTheGameButton(1000, 1000, actionOnAnimationFinished);
	}
	
	/**
	 * Cette méthode est appelé quand l'utilisateur clique sur <code>settingsButton</code>.
	 * Elle permet de passer à l'interface graphique des paramètres en animation.
	 */
	@FXML
	public void onSettingsButtonClicked() {
		EventHandler<ActionEvent> actionOnAnimationFinished = (actionEvent) -> {
			try {
				start.showSettingsScene();
				start.animateSettingsScene();
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant le chargement de l'écran des paramètres. Désolé pour le désagrément encouru : ");
			}
		};
		hideSettingsButton(0, 1000);
		hidePlayButton(333, 1000);
		hideTutorialOfTheGameButton(666, 1000);
		hideLogoView(1000, 1000, actionOnAnimationFinished);
	}
	
	/**
	 * cette méthode est appelé quand l'utilisateur clique sur <code>tutorialOfTheGameButton</code>.
	 * Elle permet de passer à l'interface du tutorialOfTheGame en animation.
	 */
	@FXML
	public void onTutorialOfTheGameButtonClicked() {
		EventHandler<ActionEvent> actionOnAnimationFinished = (ActionEvent) -> {
			try {
				start.showTutorialOfTheGameScene();
				start.animateTutorialOfTheGameScene();
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant le chargement de l'écran du tutoriel. Désolé pour le désagrément encouru : ");
			}
		};
		hideSettingsButton(0, 1000);
		hidePlayButton(333, 1000);
		hideTutorialOfTheGameButton(666, 1000);
		hideLogoView(1000, 1000, actionOnAnimationFinished);
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>playButton</code>. Cette méthode est équivalent à <code>showPlayButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void showPlayButton(double delay, double duration) {
		showPlayButton(delay, duration, null);
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>playButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showPlayButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showPlayButtonAnimation = new Timeline(30, new KeyFrame(Duration.ZERO, new KeyValue(playButton.opacityProperty(), 0.00d)), new KeyFrame(new Duration(duration), new KeyValue(playButton.opacityProperty(), 1.00d)));
		showPlayButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showPlayButtonAnimation.setOnFinished(eventOnFinished);
		}
		showPlayButtonAnimation.play();
	}
	
	/**
	 * Cette méthode déclenche l'animation de disparition de <code>playButton</code>. Cette méthode est équivalent à <code>showPlayButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hidePlayButton(double delay, double duration) {
		hidePlayButton(delay, duration, null);
	}
	
	/**
	 * Cette méthode déclenche l'animation de disparition de <code>playButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hidePlayButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hidePlayButtonAnimation = new Timeline(30, new KeyFrame(Duration.ZERO, new KeyValue(playButton.opacityProperty(), 1.00d)), new KeyFrame(new Duration(duration), new KeyValue(playButton.opacityProperty(), 0.00d)));
		hidePlayButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hidePlayButtonAnimation.setOnFinished(eventOnFinished);
		}
		hidePlayButtonAnimation.play();
	}
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>settingsButton</code>. Cette méthode est équivalent à <code>showSettingsButton(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showSettingsButton(double delay, double duration) {
		showSettingsButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>settingsButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showSettingsButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showSettingsButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(settingsButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(settingsButton.opacityProperty(), 1.00d)));
		showSettingsButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showSettingsButtonAnimation.setOnFinished(eventOnFinished);
		}
		showSettingsButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>settingsButton</code>. Cette méthode est équivalent à <code>showSettingsButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideSettingsButton(double delay, double duration) {
		hideSettingsButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>settingsButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideSettingsButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideSettingsButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(settingsButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(settingsButton.opacityProperty(), 0.00d)));
		hideSettingsButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideSettingsButtonAnimation.setOnFinished(eventOnFinished);
		}
		hideSettingsButtonAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>tutorialOfTheGameButton</code>. Cette méthode est équivalent à <code>showTutorialOfTheGameButton(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showTutorialOfTheGameButton(double delay, double duration) {
		showTutorialOfTheGameButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>tutorialOfTheGameButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showTutorialOfTheGameButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showTutorialOfTheGameButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(tutorialOfTheGameButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(tutorialOfTheGameButton.opacityProperty(), 1.00d)));
		showTutorialOfTheGameButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showTutorialOfTheGameButtonAnimation.setOnFinished(eventOnFinished);
		}
		showTutorialOfTheGameButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>tutorialOfTheGameButton</code>. Cette méthode est équivalent à <code>showTutorialOfTheGameButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideTutorialOfTheGameButton(double delay, double duration) {
		hideTutorialOfTheGameButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>tutorialOfTheGameButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideTutorialOfTheGameButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideTutorialOfTheGameButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(tutorialOfTheGameButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(tutorialOfTheGameButton.opacityProperty(), 0.00d)));
		hideTutorialOfTheGameButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideTutorialOfTheGameButtonAnimation.setOnFinished(eventOnFinished);
		}
		hideTutorialOfTheGameButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>logoView</code>. Cette méthode est équivalent à <code>showLogoView(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void showLogoView(double delay, double duration) {
		showLogoView(delay, duration, null);
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>logoView</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showLogoView(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showPlayButtonAnimation = new Timeline(30, new KeyFrame(Duration.ZERO, new KeyValue(logoView.opacityProperty(), 0.00d)), new KeyFrame(new Duration(duration), new KeyValue(logoView.opacityProperty(), 1.00d)));
		showPlayButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showPlayButtonAnimation.setOnFinished(eventOnFinished);
		}
		showPlayButtonAnimation.play();
	}
	
	/**
	 * Cette méthode déclenche l'animation de disparition de <code>logoView</code>. Cette méthode est équivalent à <code>hideLogoView(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideLogoView(double delay, double duration) {
		hideLogoView(delay, duration, null);
	}
	
	/**
	 * Cette méthode déclenche l'animation de disparition de <code>logoView</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideLogoView(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hidePlayButtonAnimation = new Timeline(30, new KeyFrame(Duration.ZERO, new KeyValue(logoView.opacityProperty(), 1.00d)), new KeyFrame(new Duration(duration), new KeyValue(logoView.opacityProperty(), 0.00d)));
		hidePlayButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hidePlayButtonAnimation.setOnFinished(eventOnFinished);
		}
		hidePlayButtonAnimation.play();
	}
	
	/**
	 * Cette péthode permet d'initialiser l'opacité <code>logoView</code> à 0. Elle sert pour l'animation d'apparition de <code>LogoView</code> quand labymany revient sur le lenu principal.
	 */
	public void setLogoViewOpacityToZero() {
		logoView.setOpacity(0.00d);
	}
}
