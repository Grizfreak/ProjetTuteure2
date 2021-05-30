package ExerciseGestion;

import java.util.HashMap;

public class OculText {
	private String text;
	private String textCache="";
	private String aide;
	private char occultation='#';
	private int nbmots=0;
	private int nbmotstrouves=0;
	private HashMap<String, Integer> wordMap;
	private static int endouble=0;
	private boolean casse=true;
	private boolean partiel =true;
	private boolean allowSol = true;
	private boolean allowStat =false;
	//TODO autoriser à afficher la solution mais changer couleur dans le controller
	//TODO autoriser à regarder ses stats


	public OculText(String text) {
		this.text=text;
		wordMap= new HashMap<String, Integer>();
		createOccultedText();
	}

	public OculText(String text,String aide,char ocult,boolean casse,boolean partiel,boolean sol,boolean stat) {
		this.text=text;
		this.aide=aide;
		occultation=ocult;
		this.casse=casse;
		this.partiel=partiel;
		allowSol=sol;
		allowStat=stat;
		wordMap= new HashMap<String, Integer>();
		createOccultedText();
	}

	public void createOccultedText() {
		String newWord="";
		for(int i=0;i<this.text.length();i++) {
			if(text.charAt(i)==' ') {
				textCache+=' ';
				/*if(casse) {
						String modifiedWord = newWord.replaceAll("[^a-zA-Z\'éÉ-]", "");
						if(!wordMap.containsKey(modifiedWord.trim())) {
							wordMap.put(modifiedWord.trim(),i-newWord.trim().length());
							}
							else {
								wordMap.put(modifiedWord.trim()+endouble, i-newWord.trim().length());
								endouble++;
							}
					}*/
				String modifiedWord = newWord.replaceAll("[^a-zA-Z0-9\'éÉ-]", "");
				if(!wordMap.containsKey(modifiedWord.trim())) {
					wordMap.put(modifiedWord.trim(),i-newWord.trim().length());
					nbmots++;
				}	
				else {
					wordMap.put(modifiedWord.trim().toLowerCase()+endouble, i-newWord.trim().length());
					endouble++;
				}

				newWord="";
			}
			else if(text.charAt(i)=='.'||text.charAt(i)=='!'||text.charAt(i)=='?'||text.charAt(i)=='\''||text.charAt(i)==',') textCache+=text.charAt(i);
			else textCache+=occultation;
			newWord+=text.charAt(i);

		}
		for(String keys : wordMap.keySet()) {
			System.out.println("Valeur clé :"+keys+"|valeur mot :"+wordMap.get(keys)+"|valeur fin mot :" +(keys.length()-1+wordMap.get(keys).intValue()));
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		for(int i=0;i<this.text.length();i++) {
			textCache=textCache+text.charAt(i);
		}
	}

	public String getTextCache() {
		return textCache;
	}

	public void searchAndReplace(String response) {
		String keym="";
		for(String key : wordMap.keySet()) {
			nbmotstrouves++;
			if(!partiel) {
				if(!casse) {
					keym=response.toLowerCase();
				}
				if(key.equals(response)||key.toLowerCase().equals(keym)) {
					replace(key,response);
				}

				for(int i=0;i<endouble;i++) {
					if(key.equals(response+i)) replace(key,response);
				}
			}
			else{
				int nb = 0;
				if(!casse) {
					keym=response.toLowerCase();
				}
				for(int i=0;i<key.length();i++) {
					for(int j=0;j<response.length();j++) {
						if(response.charAt(j)==key.charAt(i))nb++;
					}
				}
				if(key.equals(response)) {
					replace(key,response);
				}
				for(int i=0;i<endouble;i++) {
					if(key.equals(response+i)) replace(key,response);
				}
				if(key.contains(response) && response!="" && nb>=3) {
					replace(key,response);
					for(int i=0;i<endouble;i++) {
						if(key.equals(response+i)) replace(key,response);
					}
				}

				nb=0;
			}
		}

	}

	private void replace(String key,String response) {
		char[] chars=textCache.toCharArray();
		response=response.toLowerCase();
		textCache="";
		int z=0;
		for(int i = wordMap.get(key);i<key.length()+wordMap.get(key).intValue();i++) {
			if(!partiel) {
				if(z==response.length()) break;
				chars[i]=key.charAt(z);
				if(z<=response.length() && response.equals(key))z++;
				if(z<=response.length() && !response.equals(key))z++;
			}
			else {
				//TODO bonne soirée je pleure :pensive: MODIFIER LES REGEX
				String keyz="";
				if(key.matches("\\d+")) {
					keyz=key;
				}
				else if(key.matches(".*[0-9][0-9]")) {
					keyz = key.substring(0, key.length()-2);
				}
				else if (key.matches(".*[0-9]")) {
					keyz = key.substring(0,key.length()-1);
				}

				else {
					keyz=key;
				}
				if(z==keyz.length()) break;
				chars[i]=keyz.charAt(z);
				z++;

			}
		}
		for(int j=0;j<text.length();j++) {
			/*System.out.println(chars[j]);*/
			textCache+=chars[j];
		}
	}

	public boolean isAllowSol() {
		return allowSol;
	}

	public boolean isAllowStat() {
		return allowStat;
	}

	public int getNbmots() {
		return nbmots;
	}

	public int getMotTrouves() {
		return nbmotstrouves;
	}
}
