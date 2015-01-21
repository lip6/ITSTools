#!/usr/bin/perl -w
use Getopt::Std;
use strict;

if ($#ARGV <= 2)
{
    print STDERR "syntax: graphdata.pl [-v] meth1 meth2 column files...\n";
    print STDERR "\n  -v distinguish verdicts instead of models";
    exit(2);
}

my $opt_v = 0;
if ($ARGV[0] eq '-v')
{
    $opt_v = 1;
    shift @ARGV;
}

my %verdict = ( 1 => "OK", 2 => "unknown" );

my $opt_x = shift @ARGV;
my $opt_y = shift @ARGV;
my $opt_c = (shift @ARGV);


my $max = 0;

my $count = 0;
my $fail = 0;

my $head = <>;
my @head = split(', ', $head);
shift @head;
shift @head;
shift @head;

my %result;

while (<>)
{
#    next if ($_ =~ /^\w*$/) or ($_ eq $head);

    chomp;

    my @res = split(', ',$_);
    my $meth = shift @res;
    my $model = shift @res;
    # kill verdict OK, TO, MO
    shift @res;
    if (defined $res[$opt_c] && $res[$opt_c] !~ /^\s*$/) {
	if ($res[$opt_c] > $max) {
	    $max = $res[$opt_c];
	}
    } else {
      $res[$opt_c] = -1;
    }
    $result{"$model"}{$meth} = [@res];
}

my $awin0=0;
my $bwin0=0;
my $awin1=0;
my $bwin1=0;
my $awin2=0;
my $bwin2=0;
my $nbvalues =0;
my $faila=0;
my $failb=0;
my $failureanotb=0;
my $failurebnota=0;
my $fullfail=0;

foreach my $key (keys %result)
{
    if (defined $result{$key}{$opt_x}
	and defined $result{$key}{$opt_y})
    {
	my $t1 = $result{$key}{$opt_x};
	my $val1 = $t1->[$opt_c];
	if (int($t1->[0]) <0) {
#	  print STDERR "Fail A\n";
	    $val1 = 3* $max;
	    $faila=1;
	}
	my $t2 = $result{$key}{$opt_y};
	my $val2 = $t2->[$opt_c];
	if (int($t2->[0])<0) {
#	  print STDERR "Fail B\n";
	    $val2 = 3* $max;
	    $failb=1;
	}

	my $model;

	if ($opt_v)
	{
	    my $v1 = int $t1->[0];
	    my $v2 = int $t2->[0];
	    $v1 = $v2 if ($v1 < 0);
	    $model = $verdict{$v1};
	}
	else
	{
	    ($model = $key) =~ s/_input// ;
	    $model =~ s/[_\d]//g;
#	    $model =~ s/\d+//;
	}
	print "$model $val1 $val2\n";
#	print STDERR "$model $val1 $val2\n";
#	print STDERR "DEBUG : opt_c=$opt_c\n$t1->[0] $t2->[0]\n$t1->[1] $t2->[1]\n$t1->[2] $t2->[2]";
	$nbvalues++;

	if ($faila && ! $failb) {
	  $failureanotb++;
	} elsif (!$faila && $failb) {
	  $failurebnota++;
	} elsif ($faila && $failb) {
	  $fullfail++;
	} elsif ($val1 > 100 * $val2) {
	  $awin2++;
	  $awin1++;
	  $awin0++;
#	  print STDERR "Over 2 orders of magnitude : $opt_x :$val1 > $opt_y :$val2 FOR  $key\n";
	} elsif ( $val2 > 100 * $val1) {
	  $bwin2++;
	  $bwin1++;
	  $bwin0++;
#	  print STDERR "Over 2 orders of magnitude : $opt_y :$val2 > $opt_x :$val1  FOR  $key\n";
	} elsif  ($val1 > 10 * $val2) {
	  $awin1++;$awin0++;
	} elsif  ($val2 > 10 * $val1) {
	  $bwin1++;$bwin0++;
	} elsif  ($val1 > 2 * $val2) {
	  $awin0++;
	} elsif  ($val2 > 2 * $val1) {
	  $bwin0++;
	}
	$faila=0;
	$failb=0;
    }
}

my $col = $head[$opt_c];

print STDERR "In total for $nbvalues experiments (- $fullfail failures), significant differences on column $col:\n";
print STDERR "$opt_x failed for $failureanotb, $opt_y failed for $failurebnota when the other reached verdict.:\n";
if ($awin2 > 0) {
  print STDERR "$opt_x > 100*$opt_y in $awin2 (*10 in $awin1, *2 in $awin0) cases\n";
} elsif ($awin1 > 0) {
  print STDERR "$opt_x > 10*$opt_y in $awin1 (*2 in $awin0) cases\n";
} elsif ($awin0 > 0) {
  print STDERR "$opt_x > 2*$opt_y in $awin0 cases\n";
}

if ($bwin2 > 0) {
  print STDERR "$opt_y > 100*$opt_x in $bwin2 (*10 in $bwin1, *2 in $bwin0) cases\n";
} elsif ($bwin1 > 0) {
  print STDERR "$opt_y > 10*$opt_x in $bwin1 (*2 in $bwin0) cases\n";
}  elsif ($bwin0 > 0) {
  print STDERR "$opt_y > 2*$opt_x in $bwin0 cases\n";
}


