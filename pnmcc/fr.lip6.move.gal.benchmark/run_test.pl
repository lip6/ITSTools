#! /usr/bin/perl


my $title = $ARGV[0];
chomp $title;
$title =~ s/\//\./g;
$title =~ s/\.out//g;

print "##teamcity[testSuiteStarted name='$title']\n";

print "Running test : $title \n";
open IN, "< $ARGV[0]";


my $call = <IN>;
chomp $call;
$call= $call." ".$ARGV[1];

my $header;
my %verdicts = ();

while (my $line = <IN>) {
  if ($line =~ /STATE\_SPACE/ || $line =~ /FORMULA/ ) {
    my @words = split /\s+/,$line;
    # Note that the final reported Statistic is what will be taken
    $verdicts{@words[1]} = @words[2];
  }
}

close IN;

my $nbtests = keys(%verdicts);

print "Test : $title ; ".$nbtests." values to test \n";

print "Control values :\n";
foreach my $key (sort keys %verdicts) {
  print "$key=$verdicts{$key}\n";
}

if ($nbtests == 0) {
  print "\n##teamcity[testStarted name='oracle-integrity']\n";
  print "\n##teamcity[testFailed name='oracle-integrity' message='Oracle file empty or otherwise incorrect' details='Was reading : $title' expected='greater than 0' actual='$nbtests'] \n";
  print "\n##teamcity[testFinished name='oracle-integrity']\n";
  exit 1;
}

# Now run the tool
my $tmpfile = "$ARGV[0].tmp";

# print "syscalling : $call \n";
my %formouts = ();

print "##teamcity[testStarted name='runits']\n";

open IN, "($call) |" or die "An exception was raised when attempting to run "+$call+"\n";
my $first=1;
while (my $line = <IN>) {
  print $line;
  if ($line =~ /STATE\_SPACE/ || $line =~ /FORMULA/ )  {
    if ($first) {
      $first = 0;
      print "##teamcity[testFinished name='runits']\n";
    }
    my @words = split /\s+/,$line;
    $formouts{@words[1]} = @words[2];
    
    my $out = @words[2];
    my $exp =  $verdicts{@words[1]};
    my $tname = @words[1]; #$title.".".@words[1];
    print "##teamcity[testStarted name='$tname']\n";
    if (! defined $exp) {
      print "\n Formula @words[1] : no verdict in oracle !! expected/real : $exp /  $out\n";
      print "\n##teamcity[testFailed name='$tname' message='oracle incomplete : formula ( @words[1] )' details='' expected='$exp' actual='$out'] \n";
    } elsif ( $out !~ /$exp/ ) {
      print "\n##teamcity[testFailed name='$tname' message='regression detected : formula ( @words[1] )' details='' expected='$exp' actual='$out'] \n";
    } else {
      print "\n Formula @words[1] test succesful expected/real : $exp /  $out\n";
    }
    print "##teamcity[testFinished name='$tname']\n";
  }
}

close IN;

print "Actual read output values :\n";
foreach my $key (sort keys %formouts) {
  print "$key=$formouts{$key}\n";
}

$o = keys (%formouts);
$e = keys (%verdicts);
if ( $o != $e ) {
  print "\n##teamcity[testStarted name='all']\n";
  print "\n##teamcity[testFailed name='all' message='regression detected : less results than expected ( $o / $e )' details='' expected='$e' actual='$o'] \n";
  print "\n##teamcity[testFinished name='all']\n";
} elsif ($o > 0) {
  print "All $o tests successful in suite : $title\n";
}


print "##teamcity[testSuiteFinished name='$title']\n";


