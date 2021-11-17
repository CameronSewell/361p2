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
	public Set<? extends State> getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<? extends State> getFinalStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State getStartState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Character> getABC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DFA getDFA() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<NFAState> getToState(NFAState from, char onSymb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<NFAState> eClosure(NFAState s) {
		// TODO Auto-generated method stub
		return null;
	}

}
