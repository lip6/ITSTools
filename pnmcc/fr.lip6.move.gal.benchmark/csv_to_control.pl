#! /bin/perl


my @index = ("0","1","10","11","12","13","14","15","2","3","4","5","6","7","8","9");

while (my $line = <STDIN>) {
 # print $line;
  my @fields = split /;/, $line;
  my $prefix = @fields[0]."-".@fields[4]."-";
  my @verdicts = split / /, @fields[5];

  if ($#verdicts < 15) {
    next;
  }
  my @confidence = split  / /,@fields[6];
  my $min = 16;
  foreach my $pot (@confidence) {
    if ($pot < $min) {
      $min = $pot;
    }
  }
  if ($min < 3) {
    print "Skipping line $prefix due to low confidence of $min \n";
    next;
  }
  my $abbrev = @fields[4];
  $abbrev =~ s/[a-z]//g;
  my $outff = @fields[0]."-".$abbrev.".out";

  print "doing $prefix, in file $outff has ".($#verdicts-1)." entries \n";  
  open OUT, "> $outff";
  print OUT "./runatest.sh ".@fields[0]." ".@fields[4] ."\n";
  for (my $i=1 ; $i <= $#verdicts ; $i++) {
    my $res = @verdicts[$i];
    $res =~ s/BOOL\://g;
    print OUT "FORMULA ".$prefix.@index[$i-1]." ".$res." TECHNIQUES ORACLE2015\n";
  }
  close OUT;
}
