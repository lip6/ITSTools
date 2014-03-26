#!/bin/bash


if [ $# -le 3 ]; then
    echo "syntax: graph.sh output.ps [-v] meth1 meth2 column files..." >&2
    exit 2;
fi

output=$1
shift

echo "Gathering data..."

#set -x
./graphdata.pl $opt "$@" > "$output.data"

count=`wc -l "$output.data" | cut -f 1 -d ' ' `
if [ $count -eq 0 ]
then
    echo "Graph was empty !"
#    echo "$@"
    exit 0
fi

[ "x$1" = "x-v" ] && shift

models=`cut -f 1 -d ' ' "$output.data" | sort -u | tr '\n' ' '`

for i in $models; do
    echo $i;
done;

# eps mode
# set terminal postscript eps enhanced color
# set terminal png large enhanced
cat > "$output.gnuplot"  <<EOF
set terminal postscript eps enhanced color
set xlabel "$1" offset 0.0, 4.0
set ylabel "$2" offset 12.0, 0.0
set xtics nomirror
set ytics nomirror
set logscale x
set logscale y
set view equal xy
set size square

#unset key
set key left
set output '$output'

set clip two


# Use some jitter to distinguish points that would otherwise be equal
# (the jitter is multiplicative because the scale is logarithmic)
spread=10 # maximum percentage added or substracted to the real value
# jitter(x) = x*(100+2*spread*(rand(0)-0.5))/100


plot \\
EOF


#echo "Plots: $models"
x=1
for i in $models; do
  case $i in
  FSEL) name=EL;;
  FSOWCTY) name=OWCTY;;
  BCZ99) name=BCZ;;
  *) name=$i;;
  esac
  sed -n "s/^$i \(.*\)$/\\1/p" < "$output.data" > "$output.$name.data"
#  echo "'$output.$name.data' using (jitter(\$1)):(jitter(\$2)) with points pointtype $x  title \"$name\", \\" >> "$output.gnuplot"
  echo "'$output.$name.data' with points pointtype $x  title \"$name\", \\" >> "$output.gnuplot"
  x=`expr $x + 1`
done

#echo "  0.1*x notitle , \\" >> $output.gnuplot
#echo "  10*x notitle  , \\" >> $output.gnuplot
echo "  x notitle" >> "$output.gnuplot"

echo "Rendering graph..."

gnuplot "$output.gnuplot"

epstool --copy --bbox $output $output.tmp >/dev/null
mv $output.tmp $output
