package ExerciseGestion;
import java.util.Scanner;

public class test {

	public static void main(String[] args) {
		String response;	
		//"19620 Ce @discours @de Kennedy est @consid�r� comme l'un de ses meilleurs., mais aussi comme un moment fort de la guerre froide. Il avait pour but de montrer le soutien des �tats-Unis aux habitants de l'Allemagne de l'Ouest, et notamment aux Berlinois de l'Ouest qui vivaient dans une enclave en Allemagne de l'Est � au milieu de territoires communistes, alors d�limit�s depuis presque deux ans par le mur de Berlin � et craignaient une possible invasion de la part des troupes du bloc sovi�tique. Le discours tranche avec l'attitude peu engag�e et assez ti�de des �tats-Unis au d�but de la crise @berlinoise."
		Scanner in = new Scanner(System.in);
		
		OculText bit = new OculText("Here's to you, Nicola and Bart @ "
				+ "Rest forever here in our hearts @ "
				+ "The last and final moment is yours @ "
				+ "That agony is your triumph ");
		System.out.println(bit.getText());
		while(!bit.getText().equals(bit.getTextCache())) {
			System.out.print( "Entre ton mot\n" );
			while(!in.hasNextLine()) {
				in.next();
			}
			response = in.nextLine().trim();
			bit.searchAndReplace(response);
			System.out.println(bit.getTextCache());
		}
		in.close();
		System.out.println("Bravo tu as termin�");
	}

}
