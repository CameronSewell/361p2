package fa.nfa;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

/**
*Class to create a NFA object and convert to a dfa
*Design inspiration pulled from fa.dfa,DFA.java by elenasherman
*
*@author Cameron Sewell, Dallas Larsen
*/
public class NFA implements NFAInterface 
{
	
	private Set<NFAState> states;
	private NFAState start;
	private NFAState fstate;
	private Set<Character> ordAbc;

        /**
	*Constructor for a NFA object
	*/
	public NFA()
	{
		states = new LinkedHashSet<NFAState>();
		ordAbc = new LinkedHashSet<Character>();
	}

        /**
	*Sets the start state
	*@param name
	*/
	@Override
	public void addStartState(String name) 
	{
		NFAState s = checkIfExists(name);
		if(s == null)
		{
			s = new NFAState(name);
			addState(s);
		} 
		else 
		{
			System.out.println("WARNING: A state with name " + name + " already exists in the NFA");
		}
		start = s;

	}

        /**
	*adds states to the set of states
	*@param name
	*/
	@Override
	public void addState(String name) 
	{
		NFAState s = checkIfExists(name);
		if( s == null)
		{
			s = new NFAState(name);
			addState(s);
		} 
		else 
		{
			System.out.println("WARNING: A state with name " + name + " already exists in the NFA");
		}

	}

        /**
	*adds final states
	*@param name
	*/
	@Override
	public void addFinalState(String name)
	{
		NFAState s = checkIfExists(name);
		if( s == null)
		{
			s = new NFAState(name, true);
			addState(s);
		} 
		else 
		{
			System.out.println("WARNING: A state with name " + name + " already exists in the NFA");
		}

	}
	
	/**
	*adds states
	*@param s a NFAState
	*/
	private void addState(NFAState s)
	{
		states.add(s);
	}

        /**
	*adds transitions between states
	*@param fromState
	*@param onsymb
	*@param toState
	*/
	@Override
	public void addTransition(String fromState, char onSymb, String toState) 
	{
		NFAState from = checkIfExists(fromState);
		NFAState to = checkIfExists(toState);
		if(from == null)
		{
			System.err.println("ERROR: No NFA state exists with name " + fromState);
			System.exit(2);
		}
		else if (to == null)
		{
			System.err.println("ERROR: No NFA state exists with name " + toState);
			System.exit(2);
		}
		from.addTransition(onSymb, to);
		
		if(!ordAbc.contains(onSymb) && onSymb != 'e')
		{
			ordAbc.add(onSymb);
		}

	}
	
	/**
	*Check is state already exists
	*@param name
	*/
	private NFAState checkIfExists(String name)
	{
		NFAState retval = null;
		for(NFAState s : states)
		{
			if(s.getName().equals(name))
			{
				retval = s;
				break;
			}
		}
		return retval;
	}

        /**
	*return the states
	*@return states
	*/
	@Override
	public Set<? extends State> getStates()
	{
		return states;
	}

        /**
	*return the final states
	*@return retval
	*/
	@Override
	public Set<? extends State> getFinalStates() 
	{
		Set<NFAState> retval = new LinkedHashSet<NFAState>();
		for(NFAState s : states)
		{
			if(s.isFinal())
			{
				retval.add(s);
			}
		}
		return retval;
	}

        /**
	*returns the start state
	*@return start
	*/
	@Override
	public State getStartState() 
	{
		return start;
	}

        /**
	*returns the alphabet
	*@return ordAbc
	*/
	@Override
	public Set<Character> getABC() 
	{
		return ordAbc;
	}

        /**
	*returns a dfa made from the nfa
	@return newNFA
	*/
	@Override
	public DFA getDFA() 
	{
		DFA newNFA = new DFA();
		boolean finalS = false;
		boolean stateInDFA = false;
		
		Queue<Set<NFAState>> nStates = new LinkedList<Set<NFAState>>();
	    nStates.add(eClosure(start));
	    
	    while(!nStates.isEmpty())
	    {
	    	Set<NFAState> current = nStates.poll();
	    	
	    	for(NFAState s : current)
	    	{
	    		if(s.isFinal())
	    		{
	    			finalS = true;
	    		}
	    	}
	    	if(newNFA.getStartState() == null && finalS == false)
	    	{
	    		newNFA.addStartState((current.toString()));
	    	}
	    	else if(newNFA.getStartState() == null && finalS == true)
	    	{
	    		newNFA.addStartState((current.toString()));
	    		newNFA.addFinalState((current.toString()));
	    	}
	    	for(Character onSymb : getABC())
	    	{
	    		LinkedHashSet<NFAState> toSymb = new LinkedHashSet<NFAState>();
	    		for (NFAState s : current) 
	    		{
					if (s.getTo(onSymb) != null) 
					{
						for (NFAState ns : s.getTo(onSymb))
						{
							toSymb.addAll(eClosure(ns));
						}
					}
				}
				for (State states : newNFA.getStates()) 
				{
					if (states.getName().equals(toSymb.toString()))
					{
						stateInDFA = true;
					}
				}
				if (toSymb.toString() == "[]") 
				{
					if (stateInDFA == false) 
					{
						newNFA.addState("[]");
						nStates.add(toSymb);
					}
					newNFA.addTransition(current.toString(), onSymb, "[]");
				} 
				else if (stateInDFA == false)
				{
					finalS = false; //
					for (NFAState testFinal : toSymb) 
					{
						if (testFinal.isFinal()) 
						{
							finalS = true;
						}
					}
					if (finalS) 
					{
						nStates.add(toSymb);
						newNFA.addFinalState(toSymb.toString());
					}
					else
					{
						nStates.add(toSymb);
						newNFA.addState(toSymb.toString());
					}
				}
				newNFA.addTransition(current.toString(), onSymb, toSymb.toString());
				stateInDFA = false;
			}
	    }
		return newNFA;
	}
	
	
        /**
	*returns the state based on symbol and start state
	*@param from
	*@param onSymb
	*@return from.getTo(onSymb)
	*/
	@Override
	public Set<NFAState> getToState(NFAState from, char onSymb) 
	{
		return from.getTo(onSymb);
	}

        /**
	*returns the eClosure
	*@param s
	*@return DFS(s, emptyStates)
	*/
	public Set<NFAState> eClosure(NFAState s)
	{
		LinkedHashSet<NFAState> emptyStates = new LinkedHashSet<>();
		return DFS(s, emptyStates);
	}
	
	/**
	*returns a set of states based on a dfs search
	*@param s
	*@param list
	*@return list
	*/
	private HashSet<NFAState> DFS(NFAState s, HashSet<NFAState> list)
	{
		list.add(s);
		Set<NFAState> eStates = s.getTo('e');
		if (eStates != null) 
		{
			for (NFAState e : eStates)
			{
				if (list.contains(e) == false)
				{
					DFS(e, list);
				}
			}
		}
		return list;
	}

}
