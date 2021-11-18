# 361p2
# Project p2

* Author: Cameron Sewell and Dallas Larsen
* Class: CS361 Section 1, Cameron, and 2, Dallas
* Semester: fall 2021

## Overview

Takes in a test file that gives our program the parameters for an NFA and converts it to DFA

## Compiling and Using
javac fa/nfa/NFADriver.java
java fa.nfa.NFADriver ./tests/p2tc0.txt

## Discussion

  * What problems did you have? What went well?
      A big issue we had was we had boolean in a loop that was never getting reset, which resulted in all but one test case failing do the toState alwasy being null.
  * What process did you go through to create the program?
      went throught the given DFA code and ported it over the the NFA classes. Then went through and made changes for NFA functionality.
  * What did you have to research and learn on your own?
     continued use of hashsets
  * What kinds of errors did you get? How did you fix them?
      our toState was always null resulting in an exit state of 2. until we found our loop issue

## Testing

Used the given test cases


## Sources used
https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html

----------
This README template is using Markdown. To preview your README output, you can copy your file contents to a Markdown editor/previewer such as [https://stackedit.io/editor](https://stackedit.io/editor).
