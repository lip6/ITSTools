#! /usr/bin/perl


print "Running test : $ARGV[0] \n";
open IN, "< $ARGV[0]";


my $title = $ARGV[0];
my $call = <IN>;
chomp $call;

my $header;
my @nominals = ();

while (my $line = <IN>) {
	if ($line =~ /STATE\_SPACE/ ) {
		push  @nominals, $line;
	}

    # Note that the final reported Statistic is what will be taken
#    if ($line =~ /Model ,\|S\| /) {
#	$header = $line;
#	$line = <IN> or die "Unexpected end of file after stats readout";
#	chomp $line;
#	@nominal = split (/\,/,$line);
#    }
}

close IN;

print "Test : $title ; ".($#nominals + 1)." values to test) \n"; 

if (! @nominals) {
    print "\n##teamcity[testFailed name='$title' message='Oracle file empty or otherwise incorrect' details='Was reading : $title' expected='greater than 0' actual='$#nominals'] \n";
}


# Now run the tool

my $tmpfile = "$ARGV[0].tmp";


# print "syscalling : $call \n";
print "##teamcity[testStarted name='$title']\n";

my @tested;

my @outputs = ();

open IN, "($call) |" or die "An exception was raised when attempting to run "+$call+"\n";
while (my $line = <IN>) {
	if ($line =~ /STATE\_SPACE/ ) {
		push @outputs, $line;
	}

##  print "$line";
#  push (@outputs,$line);
#  if ($line =~ /Model ,\|S\| /) {
#    $line = <IN> or die "Unexpected end of file after stats readout";
#    push (@outputs,$line);
#    chomp $line;
#    @tested = split (/\,/,$line);

##    last;
#  }
}

close IN;

# print "@outputs\n";



if ( $#nominals != $#outputs ) {
    print "\n##teamcity[testFailed name='$title' message='regression detected : less results than expected ( $#outputs / $#nominals )' details='' expected='$#nominals' actual='$#outputs'] \n";
}


foreach my $i  (0..$#outputs) {
    my $lo = @outputs[$i];
    my $ln = @nominals[$i];

    print "Test : $title ; (output $i) \n"; 
    print "Output  : $lo \n";
    print "Control : $ln \n";

    if ($lo =~ /STATE_SPACE/) {
	my $sso =  (split /\s+/, $lo) [2]; 
	my $ssn =  (split /\s+/, $ln) [2]; 

	if ( $sso != $ssn ) {
	    print "\n##teamcity[testFailed name='$title' message='regression detected on STATE_SPACE' details='' expected='$ssn' actual='$sso'] \n";
	} else {
#	print "##teamcity[buildStatisticValue key='testDuration' value='@tested[2]']\n";
#	print "##teamcity[buildStatisticValue key='testMemory' value='@tested[3]']\n";
	    print "Test successful : $title \n";
	}
    } else {
	print "please write an oracle for this case, it is not handled.";
    }
}

#if ($call =~ /-reachable/) {
#    # so state count may not be accurate if we got interruption. However 0 != positive integer
#    if ( (@nominal[1]==0 && @tested[1] > 0 ) || (@nominal[1]>0 && @tested[1]==0 ) ) {
#	  print "\n##teamcity[testFailed name='$title' message='reachability test regression detected' details='' expected='@nominal[1]' actual='@tested[1]'] \n";
#	  print "Expected :  @nominal[1]  Obtained :  @tested[1] \n";
#    } else {
#	print "##teamcity[buildStatisticValue key='testDuration' value='@tested[2]']\n";
#	print "##teamcity[buildStatisticValue key='testMemory' value='@tested[3]']\n";
#	print "Test successful : $title \n";
#	print "Control Values/Obtained : \n$title\n@nominal\n@tested\n";
#    }
#} else {
#    if ( @nominal[1] != @tested[1] ) {
#	print "\n##teamcity[testFailed name='$title' message='regression detected' details='' expected='@nominal[1]' actual='@tested[1]'] \n";
#	print "Expected :  @nominal[1]  Obtained :  @tested[1] \n";
#    } else {
#	print "##teamcity[buildStatisticValue key='testDuration' value='@tested[2]']\n";
#	print "##teamcity[buildStatisticValue key='testMemory' value='@tested[3]']\n";
#	print "Test successful : $title \n";
#	print "Control Values/Obtained : \n$title\n@nominal\n@tested\n";
#    }
#}

print "##teamcity[testFinished name='$title']\n";

