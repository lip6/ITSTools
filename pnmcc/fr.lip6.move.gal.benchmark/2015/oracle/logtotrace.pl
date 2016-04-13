#! /bin/perl


while (my $line = <STDIN>) {
    # grep "STATE_SPACE TRANSITIONS" | sed 's/.*oracle.//g' | sed  's/  / /g' | sed 's/\] /.out\n/g' 
    if ( $line =~ /STATE_SPACE TRANSITIONS/) {
	$line =~ s/.*oracle.//g;
	$line =~ s/\s+/ /g;
	$line =~ s/(.*)\] //g ;
	my $model = $1;
 #  	i=todo ; if ! grep -q TRANSITIONS $i; then echo $i ; fi
	my $cmd = 'i='.$model.'.out ; if ! grep -q TRANSITIONS $i; then echo "'.$line.'" >> $i ; fi'."\n";
	print $cmd;
    }
}
