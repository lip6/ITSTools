#!/usr/bin/python3
import spot, sys

# Usage script.py file.hoa

aut = spot.automaton(sys.argv[1])
neg = spot.complement(aut).postprocess('TGBA')
short_inv = spot.product(spot.closure(aut), neg).is_empty()
len_inv = spot.product(spot.sl(aut), neg).is_empty()

print('#is_stutter,is_lengthening_ins,is_shortening_ins')
print((short_inv and len_inv)+0, len_inv+0, short_inv+0)
