package fr.pierreriverain.labymany.gui.view;



import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Cette classe permet de gérer l'interface graphique de démarrage avec des animations d'apparitions et de disparition sur les composants graphique de cette scène. 
 * @author Pierre Riverain
 *
 */
public class StartSceneController {
	
	/**
	 * Cet objet représente la vue qui permet d'afficher le logo de Labymany.
	 */
	@FXML
	private ImageView logoView;
	
	/**
	 * Cet objet représente le label qui permet d'afficher le texte de bienvenue.
	 */
	@FXML
	private Label textLabel;
	
	public StartSceneController() {
		super();
	}
	
	/**
	 * Cette méthode permet d'initialiser l'opacité des composants graphique à 0, ce qui permet de lancer les animations d'apparition dans les bonnes conditions.
	 */
	@FXML
	private void initialize() {
		logoView.setOpacity(0.00d);
		
		textLabel.setOpacity(0.00d);
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>logoView</code>. Cette méthode est équivalent à <code>showLogo(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation en millisecondes.
	 * @param duration Durée de l'animation en millisecondes.
	 */
	public void showLogo(double delay, double duration) {
		showLogo(delay, duration, null);
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>logoView</code>.
	 * @param delay Délai avant le déclenchement de l'animation en millisecondes.
	 * @param duration Durée de l'animation en millisecondes.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showLogo(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showLogoAnimation = new Timeline(30.00d, new KeyFrame(Duration.ZERO, new KeyValue(logoView.opacityProperty(), 0.00d)), new KeyFrame(new Duration(1000), new KeyValue(logoView.opacityProperty(), 1.00d)));
		showLogoAnimation.setDelay(new Duration(delay));
		showLogoAnimation.setRate(1.00d);
		if (eventOnFinished != null) {
			showLogoAnimation.setOnFinished(eventOnFinished);
		}
		showLogoAnimation.play();
	}
	
	/**
	 * Cette méthode déclenche l'animation de disparition de <code>logoView</code>. Cette méthode est équivalent à <code>hideLogo(double,double,null)</code>
	 * @param delay Délai avant le déclenchemenbt de l'animation en millisecondes.
	 * @param duration Durée de l'animation en millisecondes.
	 */
	public void hideLogo(double delay, double duration) {
		showLogo(delay, duration, null);
	}
	
	/**
	 * Cette méthode déclenche l'animation de disparition de <code>logoView</code>.
	 * @param delay Délai avant le déclenchement de l'animation en millisecondes.
	 * @param duration Durée de l'animation en millisecondes.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideLogo(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideLogoAnimation = new Timeline(30.00d, new KeyFrame(Duration.ZERO, new KeyValue(logoView.opacityProperty(), 1.00d)), new KeyFrame(new Duration(1000), new KeyValue(logoView.opacityProperty(), 0.00d)));
		hideLogoAnimation.setDelay(new Duration(delay));
		hideLogoAnimation.setRate(1.00d);
		if (eventOnFinished != null) {
			hideLogoAnimation.setOnFinished(eventOnFinished);
		}
		hideLogoAnimation.play();
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>textLabel</code>. Cette méthode est équivalent à <code>showText(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation en millisecondes.
	 * @param duration Durée de l'animation en millisecondes.
	 */
	public void showText(double delay, double duration) {
		showText(delay, duration, null);
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>textLabel</code>.
	 * @param delay Délai avant le déclenchement de l'animation en millisecondes.
	 * @param duration Durée de l'animation en millisecondes.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showText(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showTextAnimation = new Timeline(30.00d, new KeyFrame(Duration.ZERO, new KeyValue(textLabel.opacityProperty(), 0.00d)), new KeyFrame(new Duration(1000), new KeyValue(textLabel.opacityProperty(), 1.00d)));
		showTextAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showTextAnimation.setOnFinished(eventOnFinished);
		}
		showTextAnimation.play();
	}
	
	/**
	 * Cette méthode déclenche l'animation de disparition de <code>textLabel</code>. Cette méthode est équivalent à <code>hideText(double,double,null)</code>
	 * @param delay Délai avant le déclenchemenbt de l'animation en millisecondes.
	 * @param duration Durée de l'animation en millisecondes.
	 */
	public void hideText(double delay, double duration) {
		hideText(delay, duration, null);
	}
	
	/**
	 * Cette méthode déclenche l'animation de disparition de <code>textLabel</code>.
	 * @param delay Délai avant le déclenchement de l'animation en millisecondes.
	 * @param duration Durée de l'animation en millisecondes.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideText(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideTextAnimation = new Timeline(30.00d, new KeyFrame(Duration.ZERO, new KeyValue(textLabel.opacityProperty(), 1.00d)), new KeyFrame(new Duration(1000), new KeyValue(textLabel.opacityProperty(), 0.00d)));
		hideTextAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideTextAnimation.setOnFinished(eventOnFinished);
		}
		hideTextAnimation.play();
	}

	/**
	 * Ce muttateur permet de définir un logo à la scène.
	 * @param logo Le logo à définir.
	 */
	public final void setLogo(Image logo) {
		logoView.setImage(logo);
	}

	/**
	 * Cet accesseur permet d'obtenir le logo qui à été défini avec <code>setLogo(Image)</code>.
	 * @return Le logo défini.
	 */
	public final Image getLogo() {
		return logoView.getImage();
	}

	/**
	 * Ce muttateur permet de définir le texte de bienvenue.
	 * @param value Le texte de bienvenue.
	 */
	public final void setText(String value) {
		textLabel.setText(value);
	}

	/**
	 * Cet accesseur permet d'obtenir le message de bienvenue.
	 * @return Le message de bienvenue.
	 */
	public final String getText() {
		return textLabel.getText();
	}
}
