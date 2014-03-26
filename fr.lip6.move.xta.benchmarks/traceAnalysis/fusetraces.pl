#!/usr/bin/perl

use Getopt::Std;
use Cwd;
use File::Basename;
use File::Find;



open IN,"cat trace.out  | grep -v '_state' | grep -v ' cost' | grep -v  'command run as' | grep -v 'SDD cache' |";

while ( my $line = <IN> ) {

  if ($line =~ /its-reach\s+-i\s+\b([\.\w\d]+)\b.+/) {
    my $model = $1;
    my @parts = split(/\./,$model);
    $model = @parts[0];
    my $method = @parts[1];

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
    my $nbstates="";
    if ($vline =~ /Exit \[0\]/) {
      $nbstates = @reslines[2];
      $nbstates =~ s/\s*Total reachable state count :\s*/, /;
      chomp $nbstates;
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

    print "$method, $model, $verdict, $totaltime, $totalmem$nbstates\n";
  } else {
    # I checked that we only lose date and empty lines here
  #  print "ignoring : $line";
  }
}
