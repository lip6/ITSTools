#! /bin/perl


my @index = ("0","1","10","11","12","13","14","15","2","3","4","5","6","7","8","9");

while (my $line = <STDIN>) {
 # print $line;
  my @fields = split /,/, $line;
  my $prefix = @fields[0]."-".@fields[1]."-";
  my @verdicts = split //, @fields[2];

  if ($#verdicts < 16) {
    next;
  }
  my $abbrev = @fields[1];
  $abbrev =~ s/[a-z]//g;
  my $outff = @fields[0]."-".$abbrev.".out";

  print "doing $prefix, in file $outff has ".($#verdicts)." entries \n";  
  open OUT, "> $outff";
  print OUT "./runatest.sh ".@fields[0]." ".@fields[1] ."\n";
  for (my $i=0 ; $i < $#verdicts ; $i++) {
    my $res = @verdicts[$i];   
    $res =~ s/F/FALSE/g;
    $res =~ s/T/TRUE/g;
    print OUT "FORMULA ".$prefix.@index[$i]." ".$res." TECHNIQUES ORACLE2015\n";
  }
  close OUT;
}
