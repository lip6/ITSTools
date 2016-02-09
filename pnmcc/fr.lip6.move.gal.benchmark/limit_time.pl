#!/usr/bin/perl

$time = @ARGV[0] or die "Arg 0 is time limit";
$cmd = join (" ",@ARGV[1..$#ARGV]);

# directly off the manual
# simulate open(FOO, "-|")
sub pipe_from_fork ($) {
  my $parent = shift;
  pipe $parent, my $child or die;
  my $pid = fork();
  die "fork() failed: $!" unless defined $pid;
  if ($pid) {
    close $child;
  }
  else {
    close $parent;
    open(STDOUT, ">&=" . fileno($child)) or die;
  }
  $pid;
}


print $cmd."\n";


if (my $pid = pipe_from_fork('BAR')) {
  $SIG{ALRM} = sub {
    print "TIME LIMIT: Killed by timeout after $time seconds \n";
    system ("head -2 /proc/meminfo");
    kill 9,$pid ;
    wait ;
    print "After kill :\n";
    system ("head -2 /proc/meminfo");
    exit 0 ;
  };

  alarm $time;
  # parent
  while (<BAR>) { print; }
  close BAR;
} else {
  # child
  # print "pipe_from_fork\n";
  # copy to cmd output to stdout
  exec @ARGV[1..$#ARGV];
}
exit(0);


