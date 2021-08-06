package fr.pierreriverain.labymany.gui.view;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import fr.pierreriverain.keyboard.KeyboardScreen;
import fr.pierreriverain.labymany.Labymany;
import fr.pierreriverain.labymany.Settings;
import fr.pierreriverain.maze.MazeCursor;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 * Cette classe permet de gérer l'interface graphique qui permet de modifier les paramètres du jeu avec des animations d'apparitions et de disparitions sur les composants graphique de cette scène.
 * @author Pierre Riverain
 *
 */
public class SettingsSceneController {
	
	/**
	 * Cet objet représente la classe de démarrage. Il permet d'accéder et de modifier les paramètres du jeu.
	 */
	private Labymany start;
	
	/**
	 * Cet objet représente le panneaux des onglets de paramètres. Il est utilisé uniquement pour les animations d'apparition et de disparition.
	 */
	@FXML
	private TabPane settingsTabPane;
	
	/**
	 * Cet objet représente le spinner qui permet de modifier le délai de génération entre chaques images.
	 */
	@FXML
	private Spinner<Integer> delayMazeGenerationSpinner;
	
	/**
	 * Cet objet représente la zone de texte qui permet d'afficher la touche qui est défini pour déplacer le pion du joueur vers le haut.
	 */
	@FXML
	private TextField keyPlayUpTextField;
	
	/**
	 * Cet objet représente la zone de texte qui permet d'afficher la touche qui est défini pour déplacer le pion du joueur vers la droite.
	 */
	@FXML
	private TextField keyPlayRightTextField;
	
	/**
	 * Cet objet représente la zone de texte qui permet d'afficher la touche qui est défini pour déplacer le pion du joueur vers le bas.
	 */
	@FXML
	private TextField keyPlayDownTextField;
	
	/**
	 * Cet objet représente la zone de texte qui permet d'afficher la touche qui est défini pour déplacer le pion du joueur vers la gauche.
	 */
	@FXML
	private TextField keyPlayLeftTextField;
	
	/**
	 * Cet objet représente l'imageView qui affiche l'image du pion du joueur.
	 */
	@FXML
	private ImageView playerCursorImageView;
	
	/**
	 * Cet objet représente l'imageView qui affiche l'image de l'arrivée.
	 */
	@FXML
	private ImageView playerWinCursorImageView;
	
	/**
	 * Cet objet représente l'imageView qui affiche l'image du générateur.
	 */
	@FXML
	private ImageView generatorCursorImageView;
	
	/**
	 * Cet objet représente le bouton qui permet de sauvegarder les modifications des paramètres.
	 */
	@FXML
	private Button saveModificationSettingsButton;
	
	/**
	 * Cet objet représente le bouton qui permet d'annuler les modifications des paramètres.
	 */
	@FXML
	private Button cancelModificationSettingsButton;

	/**
	 * Cet objet représente le bouton qui permet de définir la touche pour déplacer le pion du joueur vers le haut.
	 */
	@FXML
	private Button setKeyPlayUpControlButton;
	
	/**
	 * Cet objet représente le bouton qui permet de définir la touche pour déplacer le pion du joueur vers la droite.
	 */
	@FXML
	private Button setKeyPlayRightControlButton;
	
	/**
	 * Cet objet représente le bouton qui permet de définir la touche pour déplacer le pion du joueur vers le bas.
	 */
	@FXML
	private Button setKeyPlayDownControlButton;
	
	/**
	 * Cet objet représente le bouton qui permet de définir la touche pour déplacer le pion du joueur vers la gauche.
	 */
	@FXML
	private Button setKeyPlayLeftControlButton;
	
	/**
	 * Cet objet représente le bouton qui permet de définir la nouvelle image du pion du joueur.
	 */
	@FXML
	private Button setPlayerCursorImageButton;
	
	/**
	 * Cet objet représente le bouton qui permet de définir la nouvelle image de l'arrivée.
	 */
	@FXML
	private Button setPlayerWinCursorImageButton;
	
	/**
	 * Cet objet représente le bouton qui permet de définir la nouvelle image du curseur de génération. Elle est visible uniquement lors de la génération du labyrinthe et si le délai de l'animation de génération entre chaque image est supérieur à 0.
	 */
	@FXML
	private Button setGeneratorCursorImageButton;
	
	/**
	 * Cette collection permet de contrôler si la touche n'est pas défini pour une action. 
	 */
	private Set<Integer> controlCheckerListSet = new HashSet<Integer>();
	
	/**
	 * Cet entier permet d'enregistrer temporairement l'identifiant de la touche qui servira à déplacer le pion du joueur vers le haut.
	 */
	private int keyPlayUp;

	/**
	 * Cet entier permet d'enregistrer temporairement l'identifiant de la touche qui servira à déplacer le pion du joueur vers la droite.
	 */
	private int keyPlayRight;
	
	/**
	 * Cet entier permet d'enregistrer temporairement l'identifiant de la touche qui servira à déplacer le pion du joueur vers le bas.
	 */
	private int keyPlayDown;

	/**
	 * Cet entier permet d'enregistrer temporairement l'identifiant de la touche qui servira à déplacer le pion du joueur vers la droite.
	 */
	private int keyPlayLeft;


	public SettingsSceneController() {
		super();
	}
	
	/**
	 * Cet accesseur permet d'obtenir l'instance de la classe de démarrage défini.
	 * @return L'instance de la classe de démarrage.
	 */
	public Labymany getStart() {
		return start;
	}

	/**
	 * Ce muttateur permet de définir l'instance de la classe de démarrage 
	 * @param start L'instance de la classe de démmarrage.
	 */
	public void setStart(Labymany start) {
		this.start = start;
		initializeValues();
	}
	
	private void initializeValues() {
		delayMazeGenerationSpinner.getValueFactory().setValue(start.getSettings().getMazeGenerationDelayAnimation());
		
		keyPlayUp = start.getSettings().getKeyPlayUp();
		controlCheckerListSet.add(keyPlayUp);
		keyPlayUpTextField.setText(NativeKeyEvent.getKeyText(keyPlayUp));
		
		keyPlayRight = start.getSettings().getKeyPlayRight();
		controlCheckerListSet.add(keyPlayRight);
		keyPlayRightTextField.setText(NativeKeyEvent.getKeyText(keyPlayRight));
		
		keyPlayDown = start.getSettings().getKeyPlayDown();
		controlCheckerListSet.add(keyPlayDown);
		keyPlayDownTextField.setText(NativeKeyEvent.getKeyText(keyPlayDown));
		
		keyPlayLeft = start.getSettings().getKeyPlayLeft();
		controlCheckerListSet.add(keyPlayLeft);
		keyPlayLeftTextField.setText(NativeKeyEvent.getKeyText(keyPlayLeft));
		
		playerCursorImageView.setImage(SwingFXUtils.toFXImage(start.getSettings().getPlayerCursor().getMazeCursorPicture(), null));
		playerWinCursorImageView.setImage(SwingFXUtils.toFXImage(start.getSettings().getPlayerWinCursor().getMazeCursorPicture(), null));
		generatorCursorImageView.setImage(SwingFXUtils.toFXImage(start.getSettings().getGeneratorCursor().getMazeCursorPicture(), null));
	}

	/**
	 * Cette méthode permet d'initialiser tous les composants graphiques de cette scène. Elle initialise également les composants <code>settingsTabPane</code>, 
	 * <code>cancelModificationSettingsButton</code> et <code>saveModificationSettingsButton</code> à 0 pour l'animation d'apparition.
	 */
	@FXML
	private void initialize() {

		settingsTabPane.setOpacity(0.0d);
		cancelModificationSettingsButton.setOpacity(0.0d);
		saveModificationSettingsButton.setOpacity(0.0d);
		
		delayMazeGenerationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5000));
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>saveModificationSettingsButton</code>. Elle permet de sauvegarder les changements des paramètres.
	 */
	@FXML
	private void onSaveModificationSettingsButtonClicked() {

		Settings settings = new Settings();
		
		try {
			settings.setMazeGenerationDelayAnimation(delayMazeGenerationSpinner.getValue());
			
			settings.setKeyPlayUp(keyPlayUp);
			settings.setKeyPlayRight(keyPlayRight);
			settings.setKeyPlayDown(keyPlayDown);
			settings.setKeyPlayLeft(keyPlayLeft);
			settings.getPictureFolder().mkdirs();
			
			MazeCursor playerCursor = new MazeCursor();
			BufferedImage playerCursorImage = SwingFXUtils.fromFXImage(playerCursorImageView.getImage(), null);
			playerCursor.setMazeCursorPicture(playerCursorImage);
			ImageIO.write(playerCursorImage, "png", settings.getPlayerCursorImageFile());
			settings.setPlayerCursor(playerCursor);
			
			MazeCursor playerWinCursor = new MazeCursor();
			BufferedImage playerWinCursorImage = SwingFXUtils.fromFXImage(playerWinCursorImageView.getImage(), null);
			playerWinCursor.setMazeCursorPicture(playerWinCursorImage);
			ImageIO.write(playerWinCursorImage, "png", settings.getPlayerWinCursorImageFile());
			settings.setPlayerWinCursor(playerWinCursor);
			
			MazeCursor generatorCursor = new MazeCursor();
			BufferedImage generatorCursorImage = SwingFXUtils.fromFXImage(generatorCursorImageView.getImage(), null);
			generatorCursor.setMazeCursorPicture(generatorCursorImage);
			ImageIO.write(generatorCursorImage, "png", settings.getGeneratorCursorImageFile());
			settings.setGeneratorCursor(generatorCursor);
			
			ObjectOutputStream settingsWriter = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("labymany.settings"))));
			settingsWriter.writeObject(settings);
			settingsWriter.close();
		} catch (Exception e) {
			start.showError(e, "L'erreur suivante s'est produite pendant l'enregistrement des modifications des paramètres. Désolé pour le désagrément encouru : ");
		}
		
		start.setSettings(settings);
		
		EventHandler<ActionEvent> actionOnAnimationFinished = (ActionEvent) -> {
			try {
				start.showMainScene();
				start.animateMainScene();
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant le chargement de l'écran du menu principal. Désolé pour le désagrément encouru : ");
			}
			
		};
		hideSettingsTabPane(0, 1000);
		hideCancelModificationSettingsButton(500, 1000);
		hideSaveModificationSettingsButton(1000, 1000, actionOnAnimationFinished);
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>cancelModificationSettingsButton</code>. Elle permet d'annuler les changements des paramètres.
	 */
	@FXML
	private void onCancelModificationSettingsButtonClicked() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(Labymany.NAME_APP + " " + Labymany.VERSION_APP);
		alert.setHeaderText("Etes-vous sûr de vouloir annuler les modifications ?");
		alert.setContentText("Notez que les modifications seront perdus si vous cliquez sur oui.");
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> buttonTypePressed = alert.showAndWait();
		
		if (buttonTypePressed.get().equals(ButtonType.YES)) {
			EventHandler<ActionEvent> actionOnAnimationFinished = (ActionEvent) -> {
				try {
					start.showMainScene();
					start.animateMainScene();
				} catch (IOException e) {
					start.showError(e, "L'erreur suivante s'est produite pendant le chargement de l'écran du menu principal. Désolé pour le désagrément encouru : ");
				}
				
			};
			hideSettingsTabPane(0, 1000);
			hideCancelModificationSettingsButton(500, 1000);
			hideSaveModificationSettingsButton(1000, 1000, actionOnAnimationFinished);
		}
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>setKeyPlayUpControlButton</code>. Elle permet d'enregistrer la nouvelle touche qui déplacera le pion du joueur vers le haut si cette touche n'est pas enregistré pour
	 * une autre action.
	 */
	@FXML
	private void onSetKeyPlayUpControlButtonClicked() {
		NativeKeyListener nativeKeyListener = new NativeKeyListener() {
			
			boolean capturedControl = false;
			
			@Override
			public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
				capturedControl = false;
				KeyboardScreen.removeNativeKeyListener(this);
			}
			
			@Override
			public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
				if (!capturedControl) {
					capturedControl = true;
					int keyCode = nativeEvent.getKeyCode();
					if (controlCheckerListSet.contains(keyCode)) {
						Platform.runLater(() -> {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle(Labymany.NAME_APP + " " + Labymany.VERSION_APP);
							alert.setHeaderText("Attention, vous essayez d'attribuer la touche "+NativeKeyEvent.getKeyText(keyCode)+ " pour déplacer le pion vers le haut alors que la touche est déjà attribuée pour une autre action.");
							alert.setContentText("Pour éviter tout problème, vous ne pouvez attribuer une touche que pour une action uniquement.");
							alert.showAndWait();
						});
					} else {
						controlCheckerListSet.remove(keyPlayUp);
						keyPlayUp = keyCode;
						keyPlayUpTextField.setText(NativeKeyEvent.getKeyText(keyPlayUp));
						controlCheckerListSet.add(keyPlayUp);
					}
				}
			}

			@Override
			public void nativeKeyTyped(NativeKeyEvent nativeEvent) {}
		};
		
		KeyboardScreen.addNativeKeyListener(nativeKeyListener);
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>setKeyPlayRightControlButton</code>. Elle permet d'enregistrer la nouvelle touche qui déplacera le pion du joueur vers la droite si cette touche n'est pas enregistré
	 * pour une autre action.
	 */
	@FXML
	private void onSetKeyPlayRightControlButtonClicked() {

		NativeKeyListener nativeKeyListener = new NativeKeyListener() {
			
			boolean capturedControl = false;
			
			@Override
			public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
				capturedControl = false;
				KeyboardScreen.removeNativeKeyListener(this);
			}
			
			@Override
			public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
				if (!capturedControl) {
					capturedControl = true;
					int keyCode = nativeEvent.getKeyCode();
					if (controlCheckerListSet.contains(keyCode)) {
						Platform.runLater(() -> {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle(Labymany.NAME_APP + " " + Labymany.VERSION_APP);
							alert.setHeaderText("Attention, vous essayez d'attribuer la touche "+NativeKeyEvent.getKeyText(keyCode)+ " pour déplacer le pion vers la droite alors que la touche est déjà attribuée pour une autre action.");
							alert.setContentText("Pour éviter tout problème, vous ne pouvez attribuer une touche que pour une action uniquement.");
							alert.showAndWait();
						});
					} else {
						controlCheckerListSet.remove(keyPlayRight);
						keyPlayRight = keyCode;
						keyPlayRightTextField.setText(NativeKeyEvent.getKeyText(keyPlayRight));
						controlCheckerListSet.add(keyPlayRight);
					}
				}
			}

			@Override
			public void nativeKeyTyped(NativeKeyEvent nativeEvent) {}
		};
		
		KeyboardScreen.addNativeKeyListener(nativeKeyListener);
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>setKeyPlayDownControlButton</code>. Elle permet d'enregistrer la nouvelle touche qui déplacera le pion du joueur vers le bas si cette touche n'est pas enregsitré pour
	 * une autre action.
	 */
	@FXML
	private void onSetKeyPlayDownControlButtonClicked() {
		NativeKeyListener nativeKeyListener = new NativeKeyListener() {
			boolean capturedControl = false;
			
			@Override
			public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
				capturedControl = false;
				KeyboardScreen.removeNativeKeyListener(this);
			}
			
			@Override
			public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
				if (!capturedControl) {
					capturedControl = true;
					int keyCode = nativeEvent.getKeyCode();
					if (controlCheckerListSet.contains(keyCode)) {
						Platform.runLater(() -> {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle(Labymany.NAME_APP + " " + Labymany.VERSION_APP);
							alert.setHeaderText("Attention, vous essayez d'attribuer la touche "+NativeKeyEvent.getKeyText(keyCode)+ " pour déplacer le pion vers le bas alors que la touche est déjà attribuée pour une autre action.");
							alert.setContentText("Pour éviter tout problème, vous ne pouvez attribuer une touche que pour une action uniquement.");
							alert.showAndWait();
						});
					} else {
						controlCheckerListSet.remove(keyPlayDown);
						keyPlayDown = keyCode;
						keyPlayDownTextField.setText(NativeKeyEvent.getKeyText(keyPlayDown));
						controlCheckerListSet.add(keyPlayDown);
					}
				}
			}

			@Override
			public void nativeKeyTyped(NativeKeyEvent nativeEvent) {}
		};
		
		KeyboardScreen.addNativeKeyListener(nativeKeyListener);
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>setKeyPlayLeftControlButton</code>. Elle permet d'enregistrer la nouvelle touche qui déplacera le pion du joueur vers la droite si cette touche n'est pas enregistré
	 * pour une autre action.
	 */
	@FXML
	private void onSetKeyPlayLeftControlButtonClicked() {
		NativeKeyListener nativeKeyListener = new NativeKeyListener() {
			boolean capturedControl = false;
			
			@Override
			public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
				capturedControl = false;
				KeyboardScreen.removeNativeKeyListener(this);
			}
			
			@Override
			public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
				if (!capturedControl) {
					capturedControl = true;
					int keyCode = nativeEvent.getKeyCode();
					if (controlCheckerListSet.contains(keyCode)) {
						Platform.runLater(() -> {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle(Labymany.NAME_APP + " " + Labymany.VERSION_APP);
							alert.setHeaderText("Attention, vous essayez d'attribuer la touche "+NativeKeyEvent.getKeyText(keyCode)+ " pour déplacer le pion vers la gauche alors que la touche est déjà attribuée pour une autre action.");
							alert.setContentText("Pour éviter tout problème, vous ne pouvez attribuer une touche que pour une action uniquement.");
							alert.showAndWait();
						});
					} else {
						controlCheckerListSet.remove(keyPlayRight);
						keyPlayLeft = keyCode;
						keyPlayLeftTextField.setText(NativeKeyEvent.getKeyText(keyPlayLeft));
						controlCheckerListSet.add(keyPlayLeft);
					}
				}
			}

			@Override
			public void nativeKeyTyped(NativeKeyEvent nativeEvent) {}
		};
		
		KeyboardScreen.addNativeKeyListener(nativeKeyListener);
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>setPlayerCursorImageButton</code>. Elle permet de définir une nouvelle image qui représentera le pion du joueur. Il est déconseillé de mettre la même image que celle 
	 * représentant l'arrivée.
	 */
	@FXML
	private void onSetPlayerCursorImageButtonClicked() {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle(Labymany.NAME_APP+ " " + Labymany.VERSION_APP +"- Choisissez une image qui représentera le pion du joueur.");
			fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers images", "*.jpg", "*.bmp", "*.png", "*.gif"));
			
			File newPlayerCursorImageFile = fileChooser.showOpenDialog(start.getPrimaryStage());
			
			Image playerCursorImage = new Image(newPlayerCursorImageFile.toURI().toURL().toString());
			playerCursorImageView.setImage(playerCursorImage);
		} catch (MalformedURLException e) {
			start.showError(e, "L'erreur suivante s'est produite pendant l'enregistrement de la nouvelle image pour le pion du joueur. Désolé pour le désagrément encouru : ");
		}
		
		
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>setPlayerWinCursorImageButton</code>. Elle permet de définir une nouvelle image qui représentera l'arrivée. Il est déconseillé de mettre la même image que celle 
	 * représentant le pion du joueur.
	 */
	@FXML
	private void onSetPlayerWinCursorImageButtonClicked() {

		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle(Labymany.NAME_APP+ " " + Labymany.VERSION_APP +"- Choisissez une image qui représentera l'arrivée.");
			fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers images", "*.jpg", "*.bmp", "*.png", "*.gif"));
			
			File newPlayerWinCursorImageFile = fileChooser.showOpenDialog(start.getPrimaryStage());
			
			Image playerWinCursorImage;
			
			playerWinCursorImage = new Image(newPlayerWinCursorImageFile.toURI().toURL().toString());
			playerWinCursorImageView.setImage(playerWinCursorImage);
		} catch (MalformedURLException e) {
			start.showError(e, "L'erreur suivante s'est produite pendant l'afichage et l'enrregistrement de la nouvelle image pour l'arrivée. Désolé pour le désagrément encouru : ");
		}
		
	}
	
	/**
	 * Cette méthode est appelé lorsque l'utilisateur appuis sur le bouton <code>setGeneratorCursorImageButton</code>. Elle permet de définir une nouvelle image qui représentera le curseur de génération durant l'animation de génération (visible
	 * si le délai entre images de génération est supérieur à 0ms).
	 */
	@FXML
	private void onSetGeneratorCursorImageButtonClicked() {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle(Labymany.NAME_APP+ " " + Labymany.VERSION_APP +"- Choisissez une image qui représentera le curseur de génération (visible si le délai entre chaque images de génération (animation) est supérieur à 0).");
			fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers images", "*.jpg", "*.bmp", "*.png", "*.gif"));
			
			File newGeneratorCursorImageFile = fileChooser.showOpenDialog(start.getPrimaryStage());
			
			Image generatorCursorImage = new Image(newGeneratorCursorImageFile.toURI().toURL().toString());
			generatorCursorImageView.setImage(generatorCursorImage);
		} catch (MalformedURLException e) {
			start.showError(e, "L'erreur suivante s'est produite pendant l'affichage et l'enregistrement de la nouvelle image pour le curseur de génération. Désolé pour le désagrément encouru : ");
		}
		
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>settingsTabPane</code>. Cette méthode est équivalent à <code>showSettingsTabPane(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showSettingsTabPane(double delay, double duration) {
		showSettingsTabPane(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>settingsTabPane</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showSettingsTabPane(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showSettingsTabPaneAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(settingsTabPane.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(settingsTabPane.opacityProperty(), 1.00d)));
		showSettingsTabPaneAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showSettingsTabPaneAnimation.setOnFinished(eventOnFinished);
		}
		showSettingsTabPaneAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>settingsTabPane</code>. Cette méthode est équivalent à <code>showSettingsTabPane(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideSettingsTabPane(double delay, double duration) {
		hideSettingsTabPane(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>settingsTabPane</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideSettingsTabPane(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideSettingsTabPaneAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(settingsTabPane.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(settingsTabPane.opacityProperty(), 0.00d)));
		hideSettingsTabPaneAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideSettingsTabPaneAnimation.setOnFinished(eventOnFinished);
		}
		hideSettingsTabPaneAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>saveModificationSettingsButton</code>. Cette méthode est équivalent à <code>showSaveModificationSettingsButton(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showSaveModificationSettingsButton(double delay, double duration) {
		showSaveModificationSettingsButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>saveModificationSettingsButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showSaveModificationSettingsButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showSaveModificationSettingsButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(saveModificationSettingsButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(saveModificationSettingsButton.opacityProperty(), 1.00d)));
		showSaveModificationSettingsButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showSaveModificationSettingsButtonAnimation.setOnFinished(eventOnFinished);
		}
		showSaveModificationSettingsButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>saveModificationSettingsButton</code>. Cette méthode est équivalent à <code>showSaveModificationSettingsButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideSaveModificationSettingsButton(double delay, double duration) {
		hideSaveModificationSettingsButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>saveModificationSettingsButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideSaveModificationSettingsButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideSaveModificationSettingsButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(saveModificationSettingsButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(saveModificationSettingsButton.opacityProperty(), 0.00d)));
		hideSaveModificationSettingsButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideSaveModificationSettingsButtonAnimation.setOnFinished(eventOnFinished);
		}
		hideSaveModificationSettingsButtonAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>cancelModificationSettingsButton</code>. Cette méthode est équivalent à <code>showCancelModificationSettingsButton(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showCancelModificationSettingsButton(double delay, double duration) {
		showCancelModificationSettingsButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>cancelModificationSettingsButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showCancelModificationSettingsButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showCancelModificationSettingsButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(cancelModificationSettingsButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(cancelModificationSettingsButton.opacityProperty(), 1.00d)));
		showCancelModificationSettingsButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showCancelModificationSettingsButtonAnimation.setOnFinished(eventOnFinished);
		}
		showCancelModificationSettingsButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>cancelModificationSettingsButton</code>. Cette méthode est équivalent à <code>showCancelModificationSettingsButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideCancelModificationSettingsButton(double delay, double duration) {
		hideCancelModificationSettingsButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>cancelModificationSettingsButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideCancelModificationSettingsButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideCancelModificationSettingsButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(cancelModificationSettingsButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(cancelModificationSettingsButton.opacityProperty(), 0.00d)));
		hideCancelModificationSettingsButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideCancelModificationSettingsButtonAnimation.setOnFinished(eventOnFinished);
		}
		hideCancelModificationSettingsButtonAnimation.play();
	}
}
