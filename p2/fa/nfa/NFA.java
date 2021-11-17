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
			System.out.println("WARNING: A state with name " + name + " already exists in the DFA");
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
			System.out.println("WARNING: A state with name " + name + " already exists in the DFA");
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
			System.out.println("WARNING: A state with name " + name + " already exists in the DFA");
		}

	}

	@Override
	public void addTransition(String fromState, char onSymb, String toState) {
		// TODO Auto-generated method stub

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
