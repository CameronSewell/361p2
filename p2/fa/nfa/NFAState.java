package re;

import fa.State;
import fa.dfa.DFA;
import fa.nfa.NFA;
import fa.nfa.NFAState;

public class RE implements REInterface 
{
  private String input ;
  private int stateCount = 0;

  public RE(String input)  
  {
    this.input = input ;
  }
  
  //private NFA factor()
  //{
  //  NFA baseNFA = base();
     
  //  return baseNFA;
  //}
  
  public NFA getNFA()
  {
    return reExpression();
  }
  
  private char peek() 
  {
        return input.charAt(0);
  }
  
  private void next(char c)
  {
    if(peek() == c)
    {
      this.input = this.input.substring(1);
    }
    else
    {
      throw new RuntimeException();
    }
   }
  
  private char moveToNext()
  {
    char c = peek();
    next(c);
    return c;
  }
  
  private boolean hasNext()
  {
   return input.length() > 0;
  }
  
  private NFA reExpression()
  {
    NFA e = end();
    if(hasNext() && peek() == '|')
    {
      next('|');
      return decide(end(), reExpression());
    }
    else
    {
      return e;
    }
  }
  
  private NFA end()
  {
      NFA factor = new NFA();

      String name = Integer.toString(stateCount++);

      factor.addStartState(name);
      factor.addFinalState(name);

      while (hasNext() && peek() != ')' && peek() != '|') 
      {
          NFA nextFactor = factor();
          factor = sequence(factor, nextFactor);
      }
      return factor;
  }
  
  private NFA decide(NFA n1, NFA n2)
  {
    NFAState s1 = (NFAState) n1.getStartState();
    NFAState s2 = (NFAState) n2.getStartState();
    
    n1.addNFAStates(n2.getStates());
    n1.addAbc(n2.getABC());
    
    String sState = Integer.toString(stateCount++);
    String fState = Integer.toString(stateCount++);
    n1.addStartState(sState);
    n1.addFinalState(fState);

    n1.addTransition(sState, 'e', s1.getName());
    n1.addTransition(sState, 'e', s2.getName());

    return n1;
  }
  
  private NFA base()
  {
    switch(peek())
    {
      case '(':
        next('(');
        NFA n = reExpression();
        next(')');
        return n;
      default:
        return primitive(moveToNext());
     }
   }
   
   private NFA sequence(NFA n1, NFA n2)
   {
     n1.addAbc(n2.getABC());
     
     for (State s : n1.getFinalStates())
     {
         NFAState tState =  (NFAState) n1.getStartState();
         tState.setNonFinal();
         tState.addTransition('e', (NFAState) n2.getStartState());
     }
        n1.addNFAStates(n2.getStates());
        
        return n1;
    }
   
 private NFA factor() 
 {
    NFA n = base();

     while (hasNext() && peek() == '*') 
     {
        next('*');
        n = repeat(n);
     }
      return n;
  }
   
  private NFA repeat(NFA n) 
  {
        NFAState state = (NFAState) n.getStartState();

        for (State s : n.getFinalStates()) 
        {
            n.addTransition(s.getName(), 'e', state.getName());
        }
        
        String store = Integer.toString(stateCount++);
        n.addStartState(store);
        n.addFinalState(store);
        n.addTransition(store, 'e', state.getName());
        return n;
    }
    
    private NFA primitive(char c) 
    {
      NFA n = new NFA();

      String sState = Integer.toString(stateCount++);
      n.addStartState(sState);

      String fState = Integer.toString(stateCount++);
      n.addFinalState(fState);

      n.addTransition(sState, c, fState);

      return n;
    }
}
