#! /usr/bin/perl

my $tnum = 1;


while (my $line = <STDIN>) {
  my $datafile = "test_$tnum.data";
  chomp $line;
  my $title = $line;
  $line = <STDIN> or die "badly formatted test file; we expect a sequence of : title \\n test \\n \n";
  chomp $line;
  open OUT, "> $datafile";
  print OUT  "$title\n";
  print OUT  "$line\n\n";
  close OUT;
  my $call = "($line) >> $datafile";
    print "Creating test data number $tnum : $call \n" ; 
  system $call ;
  $tnum++;
}

system 'echo "Data collected on (machine/date):" > data.info';
system "(uname -a ; date) >> data.info";
system 'echo "Configure run with :" >> data.info';
# system "head -8 ../config.log >> data.info";
# system 'echo "Using revision :" >> data.info';
# system 'svn info >> data.info'
