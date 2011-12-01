package irgroupproject.tbn;
import java.io.Serializable;


/**
 *  All types of relationships between terms.
 *  <BR><BR> 
 *  Any is added to facilitate operations on the entire {@link InvertedIndexList}.
 *  @author Kurtis Thompson
 *  */
public enum Relationship implements Serializable {
		BROAD, NARROW, RELATED, ANY
}
