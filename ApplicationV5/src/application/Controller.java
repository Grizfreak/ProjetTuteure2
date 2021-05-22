package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Controller {
	@FXML
	public static Stage stage;
	@FXML
	private TextArea texte;
	@FXML
	private TextArea exo;
	@FXML
	private TextField video;
	@FXML
	private String chemin2;
	@FXML
	private CheckBox eval;
	@FXML
	private CheckBox casseText;
	@FXML
	private CheckBox afficheRaport;
	@FXML
	private CheckBox remplacementPartiel;
	@FXML
	private CheckBox solution;
	@FXML
	private CheckBox time;
	@FXML
	private TextField nom;
	@FXML
	private TextField carocc;
	@FXML
	private TextField min2;
	@FXML
	private TextField sec2;
	@FXML
	private Text deb;

	private String texteocculte;
	private static String texto;
	private int nbOctetVideo;

	private static Scene ancien;

	private static String chemin1;
	private String nomExo;
	private String caractère;

	private int min;
	private int sec;

	private String aide;
	private static String aide2;
	private boolean evalu;
	private static boolean valu;
	private boolean casseTexte;
	private static boolean cassetext;
	private boolean afficheRaports;
	private boolean remplacementPartiels;
	private boolean solutions;

	public void enreg() throws FileNotFoundException {
		String enreg = exo.getText();
		FileChooser enr = new FileChooser();
		File enr2 = enr.showSaveDialog(stage);
		PrintWriter f2 = new PrintWriter(enr2);
		f2.print(enreg);
		f2.close();
	}

	public void creer() throws IOException {

		creerScene("/application/Creerexo.fxml");

	}

	private void creerScene(String chemin) throws IOException {
		FXMLLoader f = new FXMLLoader(getClass().getResource(chemin));
		Parent root = f.load();
		Scene a = new Scene(root);
		Stage stg = new Stage();
		ancien = stage.getScene();
		stg.setScene(a);
		stg.show();
		stage = stg;

	}

	private void changeScene(Parent root) {
		Main.actualRoot = root;
		Scene next = new Scene(root);
		ancien = stage.getScene();
		stage.setScene(next);

	}

	public void changeScene(Parent root, float width, float height) {
		Stage thisStage = (Stage) Main.actualRoot.getScene().getWindow();
		Main.actualRoot = root;
		Scene next = new Scene(root, width, height);
		ancien = stage.getScene();
		thisStage.setScene(next);
	}

	public void ouvrir() throws IOException {
		FileChooser ouv = new FileChooser();
		File fich = ouv.showOpenDialog(stage);
		BufferedReader vous = new BufferedReader(new FileReader(fich));
		String rev;
		String cont = "";
		while ((rev = vous.readLine()) != null) {
			cont += rev;
		}

		exo.setText(cont);

		vous.close();
	}

	public void ouvrirVideo() throws IOException {
		FileChooser ouv = new FileChooser();
		ouv.getExtensionFilters().add(new ExtensionFilter("mp3", "*.mp3"));
		ouv.getExtensionFilters().add(new ExtensionFilter("mp4", "*.mp4"));
		File fich = ouv.showOpenDialog(null);
		video.setText(fich.getAbsolutePath());
		chemin2 = fich.getAbsolutePath();
		chemin1 = chemin2;
	}

	public void save() throws IOException {	
		if (chemin1 != null) {
			try {
				FileInputStream fus = new FileInputStream(chemin1);
				FileOutputStream fas = new FileOutputStream(nom.getText()+".ang");
				int octet = fus.read();
				int nb=0;
				while(octet != -1 ){
					octet = fus.read();
					nb++;
				}
				PrintWriter sortie2 = new PrintWriter(new FileWriter(nom.getText()+".ang", true));
				fus.close();
				
				FileInputStream fis = new FileInputStream(chemin1);
				octet = fis.read();
				while (octet != -1 ){
					fas.write(octet);
					octet = fis.read();
				}
				sortie2.println("\nnb : "+nb+";\n");
				sortie2.close();
				fis.close();
				fas.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		PrintWriter sortie1 = new PrintWriter(new FileWriter(nom.getText()+".ang", true));
		sortie1.print("\n\n");
		sortie1.print("\\");
		sortie1.print(texto);
		sortie1.print("\n\n");
		sortie1.print("\\");
		caractère = carocc.getText();
		sortie1.print(caractère);
		sortie1.print("\n\n");
		if (eval.isSelected()) {
			sortie1.print("\\");
			sortie1.print("1");
			sortie1.print("\n\n");
			sortie1.print("\\");
			sortie1.print("\n\n");
			sortie1.print("\\");
			sortie1.print("\n\n");
			sortie1.print("\\");

		} else {
			
			sortie1.print("\\");
			if(afficheRaport.isSelected()) {
				sortie1.print(1);
			}else {
				sortie1.print(0);
			}
			sortie1.print("\n\n");
			sortie1.print("\\");
			if(remplacementPartiel.isSelected()) {
				sortie1.print(1);
			}
			else {
				sortie1.print(0);
			}
			sortie1.print("\n\n");
			sortie1.print("\\");
			if(solution.isSelected()) {
				sortie1.print(1);
			}else {
				sortie1.print(0);
			}
		}
		sortie1.print("\n\n");
		sortie1.print("\\");
		if (time.isSelected()) {
			sortie1.print(min2.getText());
			sortie1.print(" : ");
			sortie1.print(sec2.getText());
			
		}else {
			sortie1.print("0");
		}
		sortie1.print("\n\n");
		sortie1.print("\\");
		if (casseText.isSelected()) {
			sortie1.print("1");
		}
		else {
			sortie1.print("0");
		}
		sortie1.print("\n\n");
		sortie1.print("\\");
		sortie1.print(texte.getText());
		aide=texte.getText();
		sortie1.close();
		changeScene(FXMLLoader.load(getClass().getResource("/application/Prof.fxml")));
	}

	public void deuxiemePage() throws IOException {
		texteocculte = texte.getText();
		texto = texteocculte;
		System.out.println(texteocculte);
		changeScene(FXMLLoader.load(getClass().getResource("/application/Creerexo2.fxml")));
	}

	public void checkEvaluation() {
		evalu = eval.isSelected();
		if (!evalu) {
			solution.setDisable(false);
			remplacementPartiel.setDisable(false);
			afficheRaport.setDisable(false);
		} else {
			solution.setDisable(true);
			remplacementPartiel.setDisable(true);
			afficheRaport.setDisable(true);
		}
	}

	public void checktime() {
		if (!time.isSelected()) {
			min2.setVisible(false);
			sec2.setVisible(false);
			deb.setVisible(false);
		} else {
			min2.setVisible(true);
			sec2.setVisible(true);
			deb.setVisible(true);
		}
	}

	public void clavier() {
		if (!min2.getText().matches("[0-9]+") || min2.getText().length() > 2) {
			min2.deletePreviousChar();
		}
		if (!sec2.getText().matches("[0-9]+") || sec2.getText().length() > 2) {
			sec2.deletePreviousChar();
		}
	}

	public void arriere() {
		stage.setScene(ancien);
		video.setText(chemin1);
		texte.setText(texto);
	}

}
