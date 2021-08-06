package fr.pierreriverain.labymany.gui.view;

import java.io.IOException;


import fr.pierreriverain.labymany.Labymany;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Cette classe permet de gérer l'interface graphique qui indique au joueur qu'il à gagné, et lui propose soit de faire une nouvelle partie, soit de revenir sur le menu principal. Elle permet d'animer les composants graphique de cette scène.
 * @author Pierre Riverain.
 */
public class WinMenuSceneController {
	
	/**
	 * Cet objet représente l'<code>ImageView</code> qui permet d'afficher le logo de Labymany. Cet objet permet d'animer ce composant graphique.
	 */
	@FXML
	private ImageView logoView;
	
	/**
	 * Cet objet représente le <code>Label</code> qui permet d'afficher le message félicitant le joueur pour avoir résolu le labyrinthe. Cet objet permet d'animer ce composant graphique.
	 */
	@FXML
	private Label congratulationsMessageLabel;
	
	/**
	 * Cet objet représente la barre de bouton. Cette barre contient tous les boutons qui permettent à l'utilisateur de choisir s'il veut faire une nouvelle partie en choisissant une difficulté inférieure, supérieure, la même difficulté ou revenir au menu principal.
	 */
	@FXML
	private ButtonBar choiceButtonBar;
	
	/**
	 * Cet objet représente le <code>Button</code> qui permet de rejouer à une difficulté inférieure (à une taille inférieure) du labyrinthe.
	 * Cet objet permet également d'animer ce composant graphique.
	 */
	@FXML
	private Button replayWithLowerDifficultyButton;
	
	/**
	 * Cet objet représente le <code>Button</code> qui permet de rejouer avec la même difficulté (la même taille) du labyrinthe. 
	 * Cet objet permet également d'animer ce composant graphique.
	 */
	@FXML
	private Button replayWithSameDifficultyButton;

	/**
	 * Cet objet représente le <code>Button</code> qui permet de rejouer à une difficulté supérieure (à une taille supérieure) du labyrinthe. 
	 * Cet objet permet également d'animer ce composant graphique.
	 */
	@FXML
	private Button replayWithHigherDifficultyButton;
	
	/**
	 * Cet objet représente le <code>Button</code> qui permet de retourner au menu principal de Labymany.
	 * Cet objet permet également d'animer ce composant graphique.
	 */
	@FXML
	private Button returnToTheMainMenuButton;
	
	/**
	 * Cet objet représente l'instance de la classe de démarrage de Labymany. Cet objet permet de retourner sur les autres scènes du jeu.
	 */
	private Labymany start;
	
	/**
	 * Cet entier contient le nombre de colonnes actuel du labyrinthe.
	 */
	private int nbrMazeColumns;
	
	/**
	 * cet entier contient le nombre de lignes actuel du labyrinthe.
	 */
	private int nbrMazeRows;
	

	public WinMenuSceneController() {
		super();
	}

	/**
	 * Cet accesseur permet d'obtenir l'instance de la classe de démarrage.
	 * @return L'instance de la classe de démarrage.
	 */
	public Labymany getStart() {
		return start;
	}

	/**
	 * Ce muttateur permet de définir l'instance de la classe de démarrage. Il permet également de désactiver certains boutons lorsqu'une des limites est atteinte.
	 * @param start L'instance de la classe de démarrage.
	 */
	public void setStart(Labymany start) {
		this.start = start;
		
		if (start.getPlaySceneController().getNbrColumns() == 4 && start.getPlaySceneController().getNbrRows() == 4) {
			choiceButtonBar.getButtons().remove(replayWithLowerDifficultyButton);
		}
		
		if (start.getPlaySceneController().getNbrColumns() == 128 && start.getPlaySceneController().getNbrRows() == 64) {
			choiceButtonBar.getButtons().remove(replayWithHigherDifficultyButton);
		}
	}

	/**
	 * Cet accesseur permet d'obtenir le logo du jeu qui à été défini pour cette scène.
	 * 
	 * Cet accesseur peut être utilisé pour vérifier si le bon logo à été défini pour cette scène par exemple.
	 * @return Le logo défini pour cette scène;
	 */
	public Image getLogo() {
		return logoView.getImage();
	}
	
	/**
	 * Ce muttateur permet de définir le logo du jeu pour cette scène.
	 * @param logo Le logo du jeu pour cette scène.
	 */
	public void setLogo(Image logo) {
		logoView.setImage(logo);
	}

	/**
	 * Cette méthode permet d'initialiser les composant graphique de cette scène. Elle permet d'initialiser l'opacité des composants graphique à 0. 
	 */
	@FXML
	private void initialize() {
		logoView.setOpacity(0.00d);
		congratulationsMessageLabel.setOpacity(0.00d);
		choiceButtonBar.setOpacity(0.00d);
	}
	
	/**
	 * Cette méthode est appelée lorsque l'utilisateur appuis sur le bouton <code>replayWithLowerDifficultyButton</code>. Elle relance une partie de labyrinthe avec une difficulté inférieure
	 * (une taille inférieure).
	 */
	@FXML
	private void onReplayWithLowerDifficultyButtonClicked() {
		int columns = start.getPlaySceneController().getNbrColumns(), rows = start.getPlaySceneController().getNbrRows();
		if(columns > rows) {
			replayGame(columns - 1, rows);
		} else {
			replayGame((rows - 1) * 2, rows - 1);
		}
	}
	
	/**
	 * Cette méthode est appelée lorsque l'utilisateur appuis sur le bouton <code>replayWithSameDifficultyButton</code>. Elle relance une partie de labyrinthe avec la même difficulté 
	 * (la même taille).
	 */
	@FXML
	private void onReplayWithSameDifficultyButtonClicked() {
		replayGame(start.getPlaySceneController().getNbrColumns(), start.getPlaySceneController().getNbrRows());
	}
	
	/**
	 * Cette méthode est appelée lorsque l'utilisateur appuis sur le bouton <code>replayWithHigher</code>. Elle relance une partie de labyrinthe avec une difficulté supérieure
	 * (une taille supérieure).
	 */
	@FXML
	private void onReplayWithHigherDifficultyButtonClicked() {
		int columns = start.getPlaySceneController().getNbrColumns(), rows = start.getPlaySceneController().getNbrRows();
		if(columns < rows * 2) {
			replayGame(columns + 1, rows);
		} else {
			replayGame(rows + 1, rows + 1);
		}
	}
	
	/**
	 * Cette méthode est appelée lorsque l'utilisateur appuis sur le bouton <code>returnToTheMainMenuButton</code>. Elle permet de retourner au menu principal.
	 */
	@FXML
	private void onReturnToTheMainButtonClicked() {
		EventHandler<ActionEvent> actionOnAnimationFinished = (actionEvent) -> {
			try {

				start.showMainScene();
				start.animateMainScene(true);
				
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant le chargement du menu principal. Désolé pour le désagrément encouru : ");
			}
		};
		
		hideChoiceButtonBar(0, 1000);
		hideCongratulationsMessageLabel(500, 1000);
		hideLogoView(1000, 1000, actionOnAnimationFinished);
	}
	
	/**
	 * Cette méthode permet de rejouer une partie au labyrinthe.
	 * @param columns Le nombre de colonnes que doit avoir le labyrinthe
	 * @param rows Le nombre de Lignes que doit avoir le labyrinthe.
	 */
	private void replayGame(int columns, int rows) {
		EventHandler<ActionEvent> actionOnAnimationFinished = (actionEvent) -> {
			Platform.runLater(()-> {
				try {

					start.showPlayScene();
					start.getPlaySceneController().setMazeSize(columns, rows);
					start.animatePlayScene(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							
							Platform.runLater(() -> {
								start.getPlaySceneController().generateMaze();
							});
						}
					});
					
				} catch (IOException e) {
					start.showError(e, "L'erreur suivante s'est produite pendant le chargement de la partie. Désolé pour le désagrément encouru : ");
				}
			});
		};
		
		hideChoiceButtonBar(0, 1000);
		hideCongratulationsMessageLabel(500, 1000);
		hideLogoView(1000, 1000, actionOnAnimationFinished);
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
		Timeline showLogoViewAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(logoView.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(logoView.opacityProperty(), 1.00d)));
		showLogoViewAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showLogoViewAnimation.setOnFinished(eventOnFinished);
		}
		showLogoViewAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>logoView</code>. Cette méthode est équivalent à <code>showLogoView(double,double,null)</code>.
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
		Timeline hideLogoViewAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(logoView.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(logoView.opacityProperty(), 0.00d)));
		hideLogoViewAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideLogoViewAnimation.setOnFinished(eventOnFinished);
		}
		hideLogoViewAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>congratulationsMessageLabel</code>. Cette méthode est équivalent à <code>showCongratulationsMessageLabel(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showCongratulationsMessageLabel(double delay, double duration) {
		showCongratulationsMessageLabel(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>congratulationsMessageLabel</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showCongratulationsMessageLabel(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showCongratulationsMessageLabelAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(congratulationsMessageLabel.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(congratulationsMessageLabel.opacityProperty(), 1.00d)));
		showCongratulationsMessageLabelAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showCongratulationsMessageLabelAnimation.setOnFinished(eventOnFinished);
		}
		showCongratulationsMessageLabelAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>congratulationsMessageLabel</code>. Cette méthode est équivalent à <code>showCongratulationsMessageLabel(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideCongratulationsMessageLabel(double delay, double duration) {
		hideCongratulationsMessageLabel(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>congratulationsMessageLabel</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideCongratulationsMessageLabel(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideCongratulationsMessageLabelAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(congratulationsMessageLabel.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(congratulationsMessageLabel.opacityProperty(), 0.00d)));
		hideCongratulationsMessageLabelAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideCongratulationsMessageLabelAnimation.setOnFinished(eventOnFinished);
		}
		hideCongratulationsMessageLabelAnimation.play();
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>choiceButtonBar</code>. Cette méthode est équivalent à <code>showChoiceButtonBar(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void showChoiceButtonBar(double delay, double duration) {
		showChoiceButtonBar(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>choiceButtonBar</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showChoiceButtonBar(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showChoiceButtonBarAnimation = new Timeline(30,
			new KeyFrame(Duration.ZERO, new KeyValue(choiceButtonBar.opacityProperty(), 0.00d)),
			new KeyFrame(new Duration(duration), new KeyValue(choiceButtonBar.opacityProperty(), 1.00d)));
		showChoiceButtonBarAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showChoiceButtonBarAnimation.setOnFinished(eventOnFinished);
		}
		showChoiceButtonBarAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>choiceButtonBar</code>. Cette méthode est équivalent à <code>showChoiceButtonBar(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideChoiceButtonBar(double delay, double duration) {
		hideChoiceButtonBar(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>choiceButtonBar</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideChoiceButtonBar(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideChoiceButtonBarAnimation = new Timeline(30,
			new KeyFrame(Duration.ZERO, new KeyValue(choiceButtonBar.opacityProperty(), 1.00d)),
			new KeyFrame(new Duration(duration), new KeyValue(choiceButtonBar.opacityProperty(), 0.00d)));
		hideChoiceButtonBarAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideChoiceButtonBarAnimation.setOnFinished(eventOnFinished);
		}
		hideChoiceButtonBarAnimation.play();
	}
}