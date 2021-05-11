package application;
import java.util.Scanner;

public class test {

	public static void main(String[] args) {
		String response;	
		Scanner in = new Scanner(System.in);
		Text bit = new Text("Salut frérot t'es beau frère ! Salut , tu es magnifique , Salut Salut Salut Salut Salut Salut Salut Salut ");
		System.out.println(bit.getText());
		System.out.println(bit.getTextCache());
		while(!bit.getText().equals(bit.getTextCache())) {
			System.out.print( "Entre ton mot\n" );
			while(!in.hasNextLine()) {
				in.next();
			}
			response = in.nextLine();
			bit.searchAndReplace(response);
			System.out.println(bit.getTextCache());
		}
		System.out.println("Bravo tu as terminé");
	}

}
