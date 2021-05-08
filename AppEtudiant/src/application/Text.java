package application;

public class Text {
		public String text;
		public String textCache="";
		public char occultation='#';
		
		public Text(String text) {
			this.text=text;
			for(int i=0;i<this.text.length();i++) {
				if(text.charAt(i)==' ')textCache+=' ';
				else textCache+=occultation;
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
}
