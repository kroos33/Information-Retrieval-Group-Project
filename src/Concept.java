import java.io.Serializable;




/* Just a wapper for a string.  Making it an object in case I need to add additional properties later */
public class Concept implements Serializable {
	
	private String word;
	
	public Concept()
	{
		word = new String();
	}
	
	public Concept(String s)
	{
		word = s;
	}
	
	public String getConcept()
	{
		return word;
	}
	
	public void setConcept(String s)
	{
		word = s;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if(this.getConcept().equals(((Concept)obj).getConcept()))
		{
			return true;
		}
		return false;
	}
	
	
	
	
	

}
