#!/usr/bin/perl -w
use Getopt::Std;

our $opt_f;
getopt('f');  # Filter.

my @sum;
my @avg;
my @max;
my @min;
my $count = 0;
my $fail = 0;

my $head = <>;
my @head = split(', ', $head);
shift @head;
shift @head;
shift @head;

while (<>)
{
    next if ($_ =~ /^\w*$/) or ($_ eq $head);

    next if (defined $opt_f) and ($_ !~ $opt_f);

    chomp;
    my @res = split(', ',$_);
    my $meth = shift @res;
    my $form = shift @res;
    my $what = shift @res;
    my $verdict = shift @res;

    if ($verdict < 0 ) {
      ++$fail;
#      print "Failed : $meth, $form, $what \n" ;
    } else {
      if ($count)
	{
	  my $i;
	  for ($i = 0; $i <= $#sum; ++$i)
	    {
	      $sum[$i] += $res[$i];
	      $max[$i] = $res[$i] if $max[$i] < $res[$i];
	      $min[$i] = $res[$i] if $min[$i] > $res[$i];
	    }
	}
      else
	{
	  @sum = @res;
	  @max = @res;
	  @min = @res;
	}
    }
    ++$count;
}

for ($i = 0; $i <= $#sum; ++$i)
{
    $avg[$i] = $sum[$i] / $count;
    $avg[$i] = int($avg[$i] * 100) / 100;
}


print "Failed : $fail/$count\n";
print "\t@{[join(', ',@head)]}";
print "TOT:\t@{[join(', ',@sum)]}\n";
print "AVG:\t@{[join(', ',@avg)]}\n";
print "MIN:\t@{[join(', ',@min)]}\n";
print "MAX:\t@{[join(', ',@max)]}\n";
