package fa.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import fa.State;

public class NFAState extends State
{
    private HashMap<Character, HashSet<NFAState>> transitions;//delta
	  private boolean isFinal;//remembers its type
    
    /**
    *Constructor
    *@param name
    */
    public NFAState(String name)
    {
        this.name = name;
        isFinal = false;
        transitions = new HashMap<Character, HashSet<NFAState>>();
    }
    
    /**
    *Constructor
    *@param name
    */
    public NFAState(String name, boolean isFinal)
    {
        this.name = name;
        this.isFinal = isFinal;
        transitions = new HashMap<Character, HashSet<NFAState>>();
    }
    
    /**
	  * Accessor for the state type
	  * @return true if final and false otherwise
	  */
	  public boolean isFinal()
    {
		    return isFinal;
	  }
    
    /**
	  * Add the transition from <code> this </code> object
	  * @param onSymb the alphabet symbol
	  * @param toState to NFA state
	  */
	  public void addTransition(char onSymb, NFAState toState)
    {
        if (transitions.containsKey(onSymb) == true)
        {
		        transitions.get(onSymb).add(toState);
        }
        else
        {
            HashSet<NFAState> NFASet = new HashSet<NFAState>();
            transitions.put(onSymb, toState);
            transitions.get(onSymb).add(toState);
        }
	  }
    
    /**
	  * Retrieves the state that <code>this</code> transitions to
	  * on the given symbol
	  * @param symb - the alphabet symbol
	  * @return the new state 
	  */
	  public Set<NFAState> getTo(char symb)
    {
		    Set<NFAState> returnTransition = transitions.get(symb);
		    if(returnTransition == null)
        {
			      return new HashSet<NFAState>();
			  }
		    return transitions.get(symb);
	  }
}
