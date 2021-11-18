package fa.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import fa.State;

/**
*Class to create a NFAState object
*Design inspiration pulled from fa.dfa,DFAState.java by elenasherman
*
*@author Cameron Sewell, Dallas Larsen
*/
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
    *Constructor for final states
    *@param name
    */
    public NFAState(String name, boolean isFinal)
    {
        this.name = name;
        this.isFinal = isFinal;
        transitions = new HashMap<Character, HashSet<NFAState>>();
    }
    
    /**
    * Gets the state type
    * @return true for final states
    */
    public boolean isFinal()
    {
        return this.isFinal;
    }
    
    /**
    * Adds a transition to the map 
    * @param onSymb alphabet symbol
    * @param toState State transitioned to
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
    * Gets the transition set based on the alphabet symbol
    * @param symb - the alphabet symbol
    * @return the transition set
    */
    public Set<NFAState> getTo(char symb)
    {
        Set<NFAState> returnTransition = transitions.get(symb);
	if(returnTransition == null)
        {
	    return new HashSet<NFAState>();
	}
	else
	{
	     return transitions.get(symb);
	}
}
