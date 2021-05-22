package ExerciseGestion;

import java.util.HashMap;

public class Text {
		private String text;
		private String textCache="";
		private char occultation='#';
		private HashMap<String, Integer> wordMap;
		private static int endouble=0;
		private boolean casse=true;
		//TODO remplacement partiel des mots
		private boolean partiel =false;
		//TODO autoriser à afficher la solution mais changer couleur dans le controller
		private boolean allowSol = false;
		//TODO autoriser à regarder ses stats
		private boolean allowStat =false;
		
		
		public Text(String text) {
			this.text=text;
			wordMap= new HashMap<String, Integer>();
			createOccultedText();
		}
		
		public void createOccultedText() {
			String newWord="";
			for(int i=0;i<this.text.length();i++) {
				if(text.charAt(i)==' ') {
					textCache+=' ';
					if(casse) {
						if(!wordMap.containsKey(newWord.trim()))
							wordMap.put(newWord.trim(),i-newWord.trim().length());
							else {
								wordMap.put(newWord.trim()+endouble, i-newWord.trim().length());
								endouble++;
							}
					}
					else {
						if(!wordMap.containsKey(newWord.trim()))
							wordMap.put(newWord.trim().toLowerCase(),i-newWord.trim().length());
							else {
								wordMap.put(newWord.trim().toLowerCase()+endouble, i-newWord.trim().length());
								endouble++;
							}
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
			if(!casse)response.toLowerCase();
				for(String key : wordMap.keySet()) {
					if(key.equals(response)||key.equals(response+'.')||key.equals(response+'!')||key.equals(response+'?')||key.equals(response+'\'')||key.equals(response+',')) {
						replace(key,response);
						}
					
					for(int i=0;i<endouble;i++) {
						if(key.equals(response+i)) replace(key,response);
					}
			}
			
		}
		
		private void replace(String key,String response) {
			char[] chars=textCache.toCharArray();
			textCache="";
			int z=0;
			for(int i = wordMap.get(key);i<key.length()+wordMap.get(key).intValue();i++) {
				if(z==response.length()) break;
				chars[i]=response.charAt(z);
				if(z<=response.length() && response.equals(key))z++;
				if(z<=response.length() && !response.equals(key))z++;
			}
			for(int j=0;j<text.length();j++) {
				/*System.out.println(chars[j]);*/
				textCache+=chars[j];
			}
		}
}
