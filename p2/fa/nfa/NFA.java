package fa.nfa;

import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

public class NFA implements NFAInterface 
{
	
	private Set<NFAState> states;
	private NFAState start;
	private NFAState fstate;
	private Set<Character> ordAbc;

	public NFA(){
		states = new LinkedHashSet<NFAState>();
		ordAbc = new LinkedHashSet<Character>();
	}

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
	
	private void addState(NFAState s){
		states.add(s);
	}

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
		
		if(!ordAbc.contains(onSymb))
		{
			ordAbc.add(onSymb);
		}

	}
	
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

	@Override
	public Set<? extends State> getStates()
	{
		return states;
	}

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

	@Override
	public State getStartState() 
	{
		return start;
	}

	@Override
	public Set<Character> getABC() 
	{
		return ordAbc;
	}

	@Override
	public DFA getDFA() 
	{
		return null;
	}

	@Override
	public Set<NFAState> getToState(NFAState from, char onSymb) 
	{
		return from.getTo(onSymb);
	}

	public Set<NFAState> eClosure(NFAState s)
	{
		LinkedHashSet<NFAState> emptyStates = new LinkedHashSet<>();
		return DFS(s, emptyStates);
	}
	
	
	private LinkedHashSet<NFAState> DFS(NFAState s, LinkedHashSet<NFAState> list)
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
