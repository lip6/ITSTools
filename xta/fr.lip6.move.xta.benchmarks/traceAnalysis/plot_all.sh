#! /bin/zsh


meth[1]=one
meth[2]=pop
meth[3]=Uppaal


# set -x
for (( i = 1; i <= 3; i++ ))
do
  for (( j=$(( i+1 )); j <= 3; j++ ))
    do
    ./graph.sh "${meth[$i]}-${meth[$j]}-time.eps" "${meth[$i]}" "${meth[$j]}" 1 fusedTrace.out
    ./graph.sh "${meth[$i]}-${meth[$j]}-mem.eps" "${meth[$i]}" "${meth[$j]}" 2 fusedTrace.out
    if [ $i -lt "3" -a $j -lt "3" ]
	then
	./graph.sh "${meth[$i]}-${meth[$j]}-prodS.eps" "${meth[$i]}" "${meth[$j]}" 3 fusedTrace.out;
    fi
    done
  done

