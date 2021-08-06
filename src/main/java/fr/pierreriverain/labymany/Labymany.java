package fr.pierreriverain.labymany;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDateTime;
import org.jnativehook.NativeHookException;

import fr.pierreriverain.keyboard.KeyboardScreen;
import fr.pierreriverain.keyboard.PlayerControls;
import fr.pierreriverain.labymany.gui.view.MainSceneController;
import fr.pierreriverain.labymany.gui.view.PlaySceneController;
import fr.pierreriverain.labymany.gui.view.SelectDifficultySceneController;
import fr.pierreriverain.labymany.gui.view.SettingsSceneController;
import fr.pierreriverain.labymany.gui.view.StartSceneController;
import fr.pierreriverain.labymany.gui.view.TutorialOfTheGameSceneController;
import fr.pierreriverain.labymany.gui.view.WinMenuSceneController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Cette classe est la classe de démarrage de l'application. Elle permet de gérer la fenêtre principale et les scènes qu'elle affiche.
 * @author Pierre Riverain
 *
 */
public class Labymany extends Application {

	/**
	 * Cette constante contient le nom de l'application.
	 */
	public static final String NAME_APP = "Labymany";
	
	/**
	 * Cette constante contient la version de l'application.
	 */
	public static final String VERSION_APP = "1.0.0";
	
	/**
	 * Cet objet représente la fenêtre de l'application.
	 */
	private Stage primaryStage;
	
	/**
	 * Cet objet permet de gérer l'interface du démarrage. 
	 */
	private StartSceneController startSceneController;
	
	/**
	 * Cet objet permet de gérer l'interface du menu principal.
	 */
	private MainSceneController mainSceneController;
	
	/**
	 * Cet objet permet de gérer l'interface permettant au joueur de choisir la difficulté du jeu.
	 */
	private SelectDifficultySceneController selectDifficultySceneController;
	
	/**
	 * Cet objet permet de gérer l'interface du jeu.
	 */
	private PlaySceneController playSceneController;
	
	/**
	 * Cet objet permet de gérer l'interface permettant de modifier les paramètres.
	 */
	private SettingsSceneController settingsSceneController;
	
	/**
	 * Cet objet permet de gérer l'interface qui indique au joueur qu'il à résolu le labyrinthe et qui lui propose de rejouer une partie ou de revenir sur le menu principal.
	 */
	private WinMenuSceneController winMenuSceneController;
	
	/**
	 * Cet objet permet de gérer l'interface qui affiche le tutoriel du jeu.
	 */
	private TutorialOfTheGameSceneController tutorialOfTheGameSceneController;
	
	/**
	 * Cet objet contient le logo de l'application.
	 */
	private Image logo;
	
	/**
	 * Cet objet contient les paramètres de l'application.
	 */
	private volatile Settings settings = new Settings();
	
	/**
	 * Ce double permet de contenir la largeur de la scène. Il permet de garder la même largeur quand il y a un changement de scène.
	 */
	private double sceneWidth = 960;
	
	/**
	 * Ce double permet de contenir la hauteur de la scène. Il permet de garder la même hauteur quand il y a un changement de scène.
	 */
	private double sceneHeight = 540;
	
	public Labymany() {
		super();
	}
	
	/**
	 * Cet accesseur permet d'obtenir l'instace de la fenêtre du jeu.
	 * @return {@link PlayerControls}
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/**
	 * Cet accesseur permet d'obtenir le logo de Labymany. 
	 * @return Le logo du jeu.
	 */
	public Image getLogo() {
		return logo;
	}

	/**
	 * Cet accesseur permet d'obtenir les paramètres actuel de l'application.
	 * @return Les paramètres actuel de l'application.
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * Ce muttateur permet de définir des nouveaux paramètres pour l'application?
	 * @param settings Les nouveaux paramètres de l'application.
	 */
	public void setSettings(Settings settings) {
		this.settings = settings;
	}


	public static void LaunchInstance(String... args) {
		launch(args);
	}

	/**
	 * Cette méthode est la méthode de démarrage de l'application. Elle charge la scène <code>StartScene</code> en animation puis
	 * elle charge la scène <code>MainScene</code> en animation.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			KeyboardScreen.registerNativeHook();
		} catch (NativeHookException e1) {
			showError(e1, "L'erreur suivante s'est produite pendant le démarrage de l'écoute des évènements claviers. Désolé pour le désagrément encouru : ");
		}
		if (new File("labymany.settings").exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("labymany.settings"))));
				settings = (Settings) ois.readObject();
				ois.close();
			} catch (IOException e) {
				showError(e, "L'erreur suivante s'est produite pendant la lecture des paramètres, il est donc impossible de les charger. Ceux par défaut seront chargé. Désolé pour le désagrément encouru : ");
			} catch (ClassNotFoundException e) {
				showError(e, "L'erreur suivante s'est produite pendant la lecture des paramètres, il est donc impossible de les charger. Ceux par défaut seront chargé. Désolé pour le désagrément encouru : ");
			}
		}
		
		settings.loadPictures();
		
		try {
			primaryStage.setTitle(NAME_APP + " " + VERSION_APP);
			URL url = Labymany.class.getResource("gui/pictures/logo_labymany.png");
			logo = new Image(url.toString());
			
			this.primaryStage = primaryStage;
			
			showStartScene();
			this.primaryStage.show();
		
			animateStartScene((actionEvent) -> {
				try {
					showMainScene();
					animateMainScene();
				} catch (IOException e) {
					appShutdownFollowingAnInternalerror(e);
				}
			});
		} catch (Exception e) {
			appShutdownFollowingAnInternalerror(e);
		}
	}
	
	/**
	 * Cette méthode est appelé lorsque labymany s'arrête. Elle permet d'arrêter l'écoute des évenements claviers et souris si cette écoute est activé.
	 */
	@Override
	public void stop() throws Exception {
		KeyboardScreen.unregisterNativeHook();
		super.stop();
	}

	/**
	 * Cette méthode permet d'initialiser la taille de la région passé en paramètre
	 * @param region la région où il faut initialiser la taille.
	 */
	private void initialiseRegionSize(Region region) {
		region.setPrefWidth(sceneWidth);
		region.setPrefHeight(sceneHeight);
		region.widthProperty().addListener((observable, oldValue, newValue) -> {
			sceneWidth = newValue.doubleValue();
		});
		region.heightProperty().addListener((observable, oldValue, newValue) -> {
			sceneHeight = newValue.doubleValue();
		});
	}
	
	/**
	 * Cette méthode permet d'afficher une boite de dialogue lorsqu'une <code>Exception</code> est levée avec un message d'erreur et l'exception levée.
	 * @param e l'exception levée.
	 * @param message Un message d'erreur à afficher.
	 */
	public static void showError(Exception e, String message) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		TextArea textArea = new TextArea(stringWriter.toString());
		textArea.setEditable(false);
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(NAME_APP + " " + VERSION_APP);
		alert.setHeaderText(message);
		alert.getDialogPane().setContent(textArea);
		alert.showAndWait();
	}
	
	/**
	 * Cette méthode est appelé lorsqu'une exception est levée lors du démarrage. Elle affiche l'erreur dans une boite de dialogue avec un message d'erreur et l'exception à l'aide de la méthode <code>showError(e, message)</code>, 
	 * puis arrête l'application.
	 * @param e L'exception qui à été levée.
	 */
	private void appShutdownFollowingAnInternalerror(Exception e) {
		showError(e, "L'erreur suivante s'est produite pendant le démarrage de Labymany, le jeu doit s'arrêter. Désolé pour le désagrément encouru : ");
		
		primaryStage.hide();
		System.exit(1);
	}
	
	//------------------------- Partie concernant l'interface du démarrage -------------------------
	
	/**
	 * Cet accesseur permet d'obtenir l'instance de la classe qui permet de gérer l'interface du démarrage.
	 * @return <code>startSceneController</code>.
	 */
	public StartSceneController getStartSceneController() {
		return startSceneController;
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition et de disparition de l'interface du démarrage. 
	 * @param eventOnFinished Action à éffectuer à la fin de l'animation.
	 */
	public void animateStartScene(EventHandler<ActionEvent> eventOnFinished) {
		startSceneController.showLogo(0, 1000);
		
		int hour = LocalDateTime.now().getHour();
		
		if (hour < 17) {
			startSceneController.setText("Bonjour");
		} else {
			startSceneController.setText("Bonsoir");
		}
		startSceneController.showText(1000, 1000);
		startSceneController.hideText(3000, 1000, (actionEvent) -> {
			startSceneController.setText("Bienvenue !");
		});
		
		startSceneController.showText(5000, 1000);
		startSceneController.hideText(7000, 1000, eventOnFinished);
	}
	
	/**
	 * Cette méthode charge l'interface du démarrage.
	 * @throws IOException Cette exception est levée si une erreur s'est produite pendant le chargement de l'interface .
	 */
	public void showStartScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(Labymany.class.getResource("gui/view/StartScene.fxml"));
		
		AnchorPane startSceneAnchorPane = (AnchorPane) loader.load();
		initialiseRegionSize(startSceneAnchorPane);
		
		Scene startScene = new Scene(startSceneAnchorPane);
		
		primaryStage.setScene(startScene);
		
		startSceneController = loader.getController();	
		startSceneController.setLogo(logo);
		
	}
	
	//------------------------- Partie concernant l'interface du menu principal -------------------------

	/**
	 * Cet accesseur permet d'obtenir l'instance de la classe qui permet de gérer l'interface du menu principal.
	 * @return <code>mainSceneController</code>.
	 */
	public MainSceneController getMainSceneController() {
		return mainSceneController;
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de l'interface du menu principal. Cette action est équivalente à <code>animateShowScene(false)</code>.
	 */
	public void animateMainScene() {
		animateMainScene(false);
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de l'interface du menu principal. 
	 * @param animateLogo Si la valeur de ce booléen est <code>true</code>, l'animation d'apparition du logo du jeu s'éffectue.
	 */
	public void animateMainScene(boolean animateLogo) {
		if (animateLogo) {
			mainSceneController.setLogoViewOpacityToZero();
			mainSceneController.showSettingsButton(0, 1000);
			mainSceneController.showPlayButton(333, 1000);
			mainSceneController.showTutorialOfTheGameButton(666, 1000);
			mainSceneController.showLogoView(1000, 1000);
		} else {
			mainSceneController.showSettingsButton(0, 1000);
			mainSceneController.showPlayButton(500, 1000);
			mainSceneController.showTutorialOfTheGameButton(1000, 1000);
		}
	}
	
	/**
	 * Cette méthode charge l'interface du menu principal.
	 * @throws IOException Cette exception est levée si une erreur s'est produite pendant le chargement de l'interface .
	 */
	public void showMainScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(Labymany.class.getResource("gui/view/MainScene.fxml"));
		
		AnchorPane mainSceneAnchorPane = (AnchorPane) loader.load();
		initialiseRegionSize(mainSceneAnchorPane);
		
		Scene mainScene = new Scene(mainSceneAnchorPane);
		
		primaryStage.setScene(mainScene);
		
		mainSceneController = loader.getController();
		mainSceneController.setLogo(logo);
		mainSceneController.setStart(this);
	}
	
	//------------------------- Partie concernant l'interface du menu de sélection de la difficulté -------------------------
	
	/**
	 * Cet accesseur permet d'obtenir l'instance de la classe qui permet de gérer l'interface où l'on peut choisir la difficulté du jeu.
	 * @return <code>selectDifficultySceneController</code>.
	 */
	public SelectDifficultySceneController getSelectDifficultySceneController() {
		return selectDifficultySceneController;
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de l'interface du menu de sélection de la difficulté.
	 */
	public void animateShowSelectDifficultyScene() {
		selectDifficultySceneController.showSelectBeginnerDifficultyButton(0, 1000);
		selectDifficultySceneController.showSelectEasyDifficultyButton(250, 1000);
		selectDifficultySceneController.showSelectNormalDifficultyButton(500, 1000);
		selectDifficultySceneController.showSelectDifficultDifficultyButton(750, 1000);
		selectDifficultySceneController.showSelectExpertDifficultyButton(1000, 1000);
	}
	
	/**
	 * Cette méthode charge l'interface du menu de sélection de la difficulté.
	 * @throws IOException Cette exception est levée si une erreur s'est produite pendant le chargement de l'interface .
	 */
	public void showSelectDifficultyScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(Labymany.class.getResource("gui/view/SelectDifficultyScene.fxml"));
		
		AnchorPane selectDifficultySceneAnchorPane = (AnchorPane) loader.load();
		initialiseRegionSize(selectDifficultySceneAnchorPane);

		Scene selectDifficultyScene = new Scene(selectDifficultySceneAnchorPane);
		
		primaryStage.setScene(selectDifficultyScene);
		
		selectDifficultySceneController = loader.getController();
		selectDifficultySceneController.setLogo(logo);
		selectDifficultySceneController.setStart(this);
	}
	
	//------------------------- Partie concernant l'interface du jeu -------------------------
	
	/**
	 * Cet accesseur permet d'obtenir l'instance de la classe qui permet de gérer l'interface du jeu.
	 * @return <code>playSceneController</code>.
	 */
	public PlaySceneController getPlaySceneController() {
		return playSceneController;
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de l'interface du jeu.
	 */
	public void animatePlayScene(EventHandler<ActionEvent> eventOnFinished) {
		playSceneController.showMazeView(0, 1000, eventOnFinished);
	}
	
	/**
	 * Cette méthode charge l'interface du jeu.
	 * @throws IOException Cette exception est levée si une erreur s'est produite pendant le chargement de l'interface.
	 */
	public void showPlayScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(Labymany.class.getResource("gui/view/PlayScene.fxml"));
		
		AnchorPane playSceneAnchorPane = (AnchorPane) loader.load();
		initialiseRegionSize(playSceneAnchorPane);

		Scene playScene = new Scene(playSceneAnchorPane);
		
		primaryStage.setScene(playScene);
		
		playSceneController = loader.getController();
		playSceneController.setStart(this);
	}
	
	//------------------------- Partie concernant l'interface permettant de modifier les paramètres -------------------------
	
	/**
	 * Cet accesseur permet d'obtenir l'instance de la classe qui permet de gérer l'interface des paramètres.
	 * @return <code>settingsSceneController</code>.
	 */
	public SettingsSceneController getSettingsSceneController() {
		return settingsSceneController;
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de l'interface permettant de modifier les paramètres.
	 */
	public void animateSettingsScene() {
		settingsSceneController.showSettingsTabPane(0, 1000);
		settingsSceneController.showCancelModificationSettingsButton(500, 1000);
		settingsSceneController.showSaveModificationSettingsButton(1000, 1000);
	}
	
	/**
	 * Cette méthode charge l'interface permettant de modifier les paramètres.
	 * @throws IOException cette exception est levée si une erreur s'est produite pendant le chargement de l'interface .
	 */
	public void showSettingsScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(Labymany.class.getResource("gui/view/SettingsScene.fxml"));
		
		AnchorPane settingsSceneAnchorPane = (AnchorPane) loader.load();
		initialiseRegionSize(settingsSceneAnchorPane);
		
		Scene settingsScene = new Scene(settingsSceneAnchorPane);
		
		primaryStage.setScene(settingsScene);
		
		settingsSceneController = loader.getController();
		settingsSceneController.setStart(this);
	}
	
	//------------------------- Partie concernant l'interface qui indique au joueur qu'il à réussi à résoudre le labyrinthe et qui lui propose de rejouer une partie ou de revenir sur le menu principal -------------------------
	
	/**
	 * Cet accesseur permet d'obtenir l'instance de la classe qui permet de gérer l'interface qui indique au joueur qu'il à réussi à résoudre le labyrinthe, et qui lui propose de rejouer une partie ou de revenir sur le menu principal.
	 * @return <code>winMenuSceneController</code>.
	 */
	public WinMenuSceneController getWinMenuSceneController() {
		return winMenuSceneController;
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de l'interface qui indique au joueur qu'il à réussi à résoudre le labyrinthe et qui lui propose de rejouer une partie ou de revenir sur le menu principal.
	 */
	public void animateWinMenuScene() {
		winMenuSceneController.showLogoView(0, 1000);
		winMenuSceneController.showCongratulationsMessageLabel(500, 1000);
		winMenuSceneController.showChoiceButtonBar(1000, 1000);
	}
	
	/**
	 * Cette méthode charge l'interface qui indique au joueur qu'il à réussi à résoudre le labyrinthe et qui lui propose de rejouer une partie ou de revenir sur le menu principal.
	 * @throws IOException cette exception si une erreur s'est produite pendant le chargement de l'interface .
	 */
	public void showWinMenuScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(Labymany.class.getResource("gui/view/WinMenuScene.fxml"));
		
		AnchorPane winMenuSceneAnchorPane = (AnchorPane) loader.load();
		initialiseRegionSize(winMenuSceneAnchorPane);
		
		Scene winMenuScene = new Scene(winMenuSceneAnchorPane);
		
		primaryStage.setScene(winMenuScene);
		
		winMenuSceneController = loader.getController();
		winMenuSceneController.setLogo(logo);
		winMenuSceneController.setStart(this);
	
	
	//------------------------- Partie concernant l'interface qui affiche le tutoriel du jeu -------------------------
	
	}

	/**
	 * Cet accesseur permet d'obtenir l'instace de la classe qui gère l'interface qui affiche le tutoriel du jeu.
	 * @return <code>tutorialOfTheGameSceneController</code>
	 */
	public TutorialOfTheGameSceneController getTutorialOfTheGameSceneController() {
		return tutorialOfTheGameSceneController;
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de l'interface qui affiche le tutoriel 
	 */
	public void animateTutorialOfTheGameScene() {
		tutorialOfTheGameSceneController.showTutorialScreenVBox(0, 1000);
		tutorialOfTheGameSceneController.showReturnToTheMainScene(1000, 1000);
	}
	
	public void showTutorialOfTheGameScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(Labymany.class.getResource("gui/view/TutorialOfTheGameScene.fxml"));
		
		AnchorPane tutorialOfTheGameSceneAnchorPane = (AnchorPane) loader.load();
		initialiseRegionSize(tutorialOfTheGameSceneAnchorPane);
		
		Scene tutorialOfTheGameScene = new Scene(tutorialOfTheGameSceneAnchorPane);
		
		primaryStage.setScene(tutorialOfTheGameScene);
		
		tutorialOfTheGameSceneController = loader.getController();
		tutorialOfTheGameSceneController.setStart(this);
	}
}
