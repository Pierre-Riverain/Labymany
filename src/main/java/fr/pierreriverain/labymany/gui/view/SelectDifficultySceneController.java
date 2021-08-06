package fr.pierreriverain.labymany.gui.view;

import java.io.IOException;

import fr.pierreriverain.labymany.Labymany;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Cette classe permet de gérer l'interface graphique qui permet de sélectionner la difficulté du jeu avec des animations d'apparition et de disparition sur les composants graphique de cette scène.
 * @author Pierre Riverain
 *
 */
public class SelectDifficultySceneController {

	/**
	 * Cet objet représente le bouton "Débutant" de cette scène.
	 */
	@FXML
	private Button selectBeginnerDifficultyButton;
	
	/**
	 * Cet objet représente le bouton "Facile" de cette scène.
	 */
	@FXML
	private Button selectEasyDifficultyButton;
	
	/**
	 * Cet objet représente le bouton "Normal" de cette scène.
	 */
	@FXML
	private Button selectNormalDifficultyButton;
	
	/**
	 * Cet objet représente le bouton "Difficile" de cette scène.
	 */
	@FXML
	private Button selectDifficultDifficultyButton;
	
	/**
	 * Cet objet représente le bouton "Expert" de cette scène.
	 */
	@FXML
	private Button selectExpertDifficultyButton;
	
	/**
	 * Cet objet représente la vue qui permet d'afficher le logo d'homonymany.
	 */
	@FXML
	private ImageView logoView;
	
	/**
	 * Cet objet représente la classe de démarrage.
	 */
	private Labymany start;

	public SelectDifficultySceneController() {
		super();
	}
	
	/**
	 * Ce muttateur défini le logo à afficher sur la scène.
	 * @param logo Logo à afficher
	 */
	public void setLogo(Image logo) {
		logoView.setImage(logo);
	}


	/**
	 * Cet accesseur permet d'obtenir le logo qui a été défini pour être affiché sur la scène.
	 * @return Le logo défini.
	 */
	public Image getLogo() {
		return logoView.getImage();
	}
	
	/**
	 * Cet accesseur permet d'obtenir la classe de démarrage qui à été défini.
	 * @return La classe de démarrage défini.
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
	 * Cette méthode permet d'initialiser l'opacité des boutons à 0. Ceci permet d'exécuter les animations d'apparition dans des bonnes conditions.
	 */
	@FXML
	private void initialize() {
		selectBeginnerDifficultyButton.setOpacity(0.00d);
		selectEasyDifficultyButton.setOpacity(0.00d);
		selectNormalDifficultyButton.setOpacity(0.00d);
		selectDifficultDifficultyButton.setOpacity(0.00d);
		selectExpertDifficultyButton.setOpacity(0.00d);
	}
	
	/**
	 * Cette méthode est appelé lorque l'utilisateur appuis sur le bouton <code>selectBeginnerDifficultyButton</code>.
	 * Elle lance la partie avec la difficulté débutante.
	 */
	@FXML
	private void onSelectBeginnerDifficultyButtonClicked() {
		launchGame(4, 4);
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>selectEasyDifficultyButton</code>.
	 * Elle lance la partie avec la difficulté facile.
	 */
	@FXML
	private void onSelectEasyDifficultyButtonClicked() {
		launchGame(8, 8);
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>selectNormalDifficultyButton</code>.
	 * Elle lance la partie avec la difficulté normale.
	 */
	@FXML
	private void onSelectNormalDifficultyButtonClicked() {
		launchGame(16, 16);
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>selectDifficultDifficultyButton</code>.
	 * Elle lance la partie avec la difficulté difficile.
	 */
	@FXML
	private void onSelectDifficultDifficultyButtonClicked() {
		launchGame(32, 32);
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>selectExpertDifficultyButton</code>.
	 * Elle lance la partie avec la difficulté experte.
	 */
	@FXML
	private void onSelectExpertDifficultyButtonClicked() {
		launchGame(64, 64);
	}
	
	/**
	 * Cette méthode permet de lancer la partie.
	 * @param columns Le nombre de colonnes que doit avoir le labyrinthe
	 * @param rows Le nombre de Lignes que doit avoir le labyrinthe.
	 */
	private void launchGame(int columns, int rows) {
		EventHandler<ActionEvent> actionOnAnimationFinished = (actionEvent) -> {
			try {

				start.showPlayScene();
				start.getPlaySceneController().setMazeSize(columns, rows);
				start.animatePlayScene(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						
						start.getPlaySceneController().generateMaze();
					}
				});
				
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant le chargement de la partie. Désolé pour le désagrément encouru : ");
			}
		};
		
		hideSelectBeginnerDifficultyButton(0, 1000);
		hideSelectEasyDifficultyButton(200, 1000);
		hideSelectNormalDifficultyButton(400, 1000);
		hideSelectDifficultDifficultyButton(600, 1000);
		hideSelectExpertDifficultyButton(800, 1000);
		hideLogoView(1000, 1000, actionOnAnimationFinished);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>logoView</code>. Cette méthode est équivalent à <code>showPlayButton(double,double,null)</code>.
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
		Timeline showLogoViewAnimation = new Timeline(30, new KeyFrame(Duration.ZERO, new KeyValue(logoView.opacityProperty(), 0.00d)), new KeyFrame(new Duration(duration), new KeyValue(logoView.opacityProperty(), 1.00d)));
		showLogoViewAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showLogoViewAnimation.setOnFinished(eventOnFinished);
		}
		showLogoViewAnimation.play();
	}
	
	/**
	 * Cette méthode déclenche l'animation de disparition de <code>logoView</code>. Cette méthode est équivalent à <code>showPlayButton(double,double,null)</code>.
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
		Timeline hideLogoViewAnimation = new Timeline(30, new KeyFrame(Duration.ZERO, new KeyValue(logoView.opacityProperty(), 1.00d)), new KeyFrame(new Duration(duration), new KeyValue(logoView.opacityProperty(), 0.00d)));
		hideLogoViewAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideLogoViewAnimation.setOnFinished(eventOnFinished);
		}
		hideLogoViewAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>selectBeginnerDifficultyButton</code>. Cette méthode est équivalent à <code>showSelectBeginnerDifficultyButton(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showSelectBeginnerDifficultyButton(double delay, double duration) {
		showSelectBeginnerDifficultyButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>selectBeginnerDifficultyButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showSelectBeginnerDifficultyButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showSelectBeginnerDifficultyButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(selectBeginnerDifficultyButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(selectBeginnerDifficultyButton.opacityProperty(), 1.00d)));
		showSelectBeginnerDifficultyButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showSelectBeginnerDifficultyButtonAnimation.setOnFinished(eventOnFinished);
		}
		showSelectBeginnerDifficultyButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>selectBeginnerDifficultyButton</code>. Cette méthode est équivalent à <code>showSelectBeginnerDifficultyButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideSelectBeginnerDifficultyButton(double delay, double duration) {
		hideSelectBeginnerDifficultyButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>selectBeginnerDifficultyButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideSelectBeginnerDifficultyButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideSelectBeginnerDifficultyButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(selectBeginnerDifficultyButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(selectBeginnerDifficultyButton.opacityProperty(), 0.00d)));
		hideSelectBeginnerDifficultyButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideSelectBeginnerDifficultyButtonAnimation.setOnFinished(eventOnFinished);
		}
		hideSelectBeginnerDifficultyButtonAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>selectEasyDifficultyButton</code>. Cette méthode est équivalent à <code>showSelectEasyDifficultyButton(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showSelectEasyDifficultyButton(double delay, double duration) {
		showSelectEasyDifficultyButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>selectEasyDifficultyButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showSelectEasyDifficultyButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showSelectEasyDifficultyButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(selectEasyDifficultyButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(selectEasyDifficultyButton.opacityProperty(), 1.00d)));
		showSelectEasyDifficultyButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showSelectEasyDifficultyButtonAnimation.setOnFinished(eventOnFinished);
		}
		showSelectEasyDifficultyButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>selectEasyDifficultyButton</code>. Cette méthode est équivalent à <code>showSelectEasyDifficultyButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideSelectEasyDifficultyButton(double delay, double duration) {
		hideSelectEasyDifficultyButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>selectEasyDifficultyButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideSelectEasyDifficultyButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideSelectEasyDifficultyButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(selectEasyDifficultyButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(selectEasyDifficultyButton.opacityProperty(), 0.00d)));
		hideSelectEasyDifficultyButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideSelectEasyDifficultyButtonAnimation.setOnFinished(eventOnFinished);
		}
		hideSelectEasyDifficultyButtonAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>selectNormalDifficultyButton</code>. Cette méthode est équivalent à <code>showSelectNormalDifficultyButton(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showSelectNormalDifficultyButton(double delay, double duration) {
		showSelectNormalDifficultyButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>selectNormalDifficultyButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showSelectNormalDifficultyButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showSelectNormalDifficultyButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(selectNormalDifficultyButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(selectNormalDifficultyButton.opacityProperty(), 1.00d)));
		showSelectNormalDifficultyButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showSelectNormalDifficultyButtonAnimation.setOnFinished(eventOnFinished);
		}
		showSelectNormalDifficultyButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>selectNormalDifficultyButton</code>. Cette méthode est équivalent à <code>showSelectNormalDifficultyButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideSelectNormalDifficultyButton(double delay, double duration) {
		hideSelectNormalDifficultyButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>selectNormalDifficultyButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideSelectNormalDifficultyButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideSelectNormalDifficultyButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(selectNormalDifficultyButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(selectNormalDifficultyButton.opacityProperty(), 0.00d)));
		hideSelectNormalDifficultyButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideSelectNormalDifficultyButtonAnimation.setOnFinished(eventOnFinished);
		}
		hideSelectNormalDifficultyButtonAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>selectDifficultDifficultyButton</code>. Cette méthode est équivalent à <code>showSelectDifficultDifficultyButton(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showSelectDifficultDifficultyButton(double delay, double duration) {
		showSelectDifficultDifficultyButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>selectDifficultDifficultyButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showSelectDifficultDifficultyButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showSelectDifficultDifficultyButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(selectDifficultDifficultyButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(selectDifficultDifficultyButton.opacityProperty(), 1.00d)));
		showSelectDifficultDifficultyButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showSelectDifficultDifficultyButtonAnimation.setOnFinished(eventOnFinished);
		}
		showSelectDifficultDifficultyButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>selectDifficultDifficultyButton</code>. Cette méthode est équivalent à <code>showSelectDifficultDifficultyButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideSelectDifficultDifficultyButton(double delay, double duration) {
		hideSelectDifficultDifficultyButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>selectDifficultDifficultyButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideSelectDifficultDifficultyButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideSelectDifficultDifficultyButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(selectDifficultDifficultyButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(selectDifficultDifficultyButton.opacityProperty(), 0.00d)));
		hideSelectDifficultDifficultyButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideSelectDifficultDifficultyButtonAnimation.setOnFinished(eventOnFinished);
		}
		hideSelectDifficultDifficultyButtonAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>selectExpertDifficultyButton</code>. Cette méthode est équivalent à <code>showSelectExpertDifficultyButton(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showSelectExpertDifficultyButton(double delay, double duration) {
		showSelectExpertDifficultyButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>selectExpertDifficultyButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showSelectExpertDifficultyButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showSelectExpertDifficultyButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(selectExpertDifficultyButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(selectExpertDifficultyButton.opacityProperty(), 1.00d)));
		showSelectExpertDifficultyButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showSelectExpertDifficultyButtonAnimation.setOnFinished(eventOnFinished);
		}
		showSelectExpertDifficultyButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>selectExpertDifficultyButton</code>. Cette méthode est équivalent à <code>showSelectExpertDifficultyButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideSelectExpertDifficultyButton(double delay, double duration) {
		hideSelectExpertDifficultyButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>selectExpertDifficultyButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideSelectExpertDifficultyButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideSelectExpertDifficultyButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(selectExpertDifficultyButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(selectExpertDifficultyButton.opacityProperty(), 0.00d)));
		hideSelectExpertDifficultyButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideSelectExpertDifficultyButtonAnimation.setOnFinished(eventOnFinished);
		}
		hideSelectExpertDifficultyButtonAnimation.play();
	}
}
