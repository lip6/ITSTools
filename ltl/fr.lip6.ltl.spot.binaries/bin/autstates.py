#!/usr/bin/python3
import spot
from sys import argv
aut = spot.automaton(argv[1])

sistates = spot.stutter_invariant_states(aut)
if spot.is_stutter_invariant_forward_closed(aut, sistates) != 0 :
    sistates = spot.make_stutter_invariant_forward_closed_inplace(aut, sistates)

first = True
for i in sistates :
    if first : 
        first = False
    else :
        print(" ", end='')
    print(int(i == True), end='')
print()
print(aut.to_str('hoa','t'))
