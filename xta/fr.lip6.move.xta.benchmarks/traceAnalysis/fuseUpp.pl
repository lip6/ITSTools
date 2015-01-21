#!/usr/bin/perl

use Getopt::Std;
use Cwd;
use File::Basename;
use File::Find;



open IN,"cat upptrace.out | grep -v 'Options for' | grep -v 'Generating no trace' | grep -v 'Search order is breadth first' | grep -v 'Using conservative space' | grep -v 'Seed is' | grep -v 'State space representation uses minimal constraint' | grep -v 'Verifying property 1 at line 1' |";

while ( my $line = <IN> ) {

  if ($line =~ /Guessed query file:.+0m([\w]+)\.q.*/) {
    my $model = $1;
    my @reslines = [];
    while ($line = <IN>) {
      push @reslines, $line;
      if ($line =~ /Max VSize/) {
	last;
      }
    }
    # ok, so lines is the result for $model
    my $vline =  @reslines[$#reslines-1];
    my $verdict;
    if ($vline =~ /Exit \[0\]/) {
#      $nbstates = @reslines[2];
#      $nbstates =~ s/\s*Total reachable state count :\s*//;
#      chomp $nbstates;
      $verdict = "OK, 1";
    } elsif ($vline =~ /Killed \[9\]/) {
      $verdict = "TO, -1";
    } elsif ($vline =~ /Exit \[1\]/  || $vline =~ /Killed \[6\]/ || $vline =~ /Killed \[11\]/) {
      $verdict = "MO, -2";
    }


    my $memtime = @reslines[$#reslines];
    my @fields = split(/ /,$memtime);
    my $totaltime = @fields[0] + @fields[2];
    my $totalmem = @fields[$#fields];
    $totalmem =~ s/KB//;
    chomp $totalmem;
#    print $memtime;
#    print "time = $totaltime mem = $totalmem";
    
    print "Uppaal, $model, $verdict, $totaltime, $totalmem\n";
  } else {
    # I checked that we only lose date and empty lines here
#    print "ignoring : $line";
  }
}
