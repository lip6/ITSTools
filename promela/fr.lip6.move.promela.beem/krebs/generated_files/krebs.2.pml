byte glukosa=3;
byte ATP=4;
byte ADP=4;
byte NADp=12;
byte NADH=2;
byte puryvat=0;
byte acetyl_co_a=0;
byte O2=15;
byte CO2=0;
byte Hp=10;
byte H20=10;
byte energy=0;

chan electrons =[0] of {int};

active proctype glykoza() { 

q: if
::  d_step {glukosa>=1 && ATP>=2 && ADP>=2 && NADp>=2;glukosa = glukosa-1;ADP = ADP-2;ATP = ATP+2;NADp = NADp-2;NADH = NADH+2;puryvat = puryvat+2;Hp = Hp+2;H20 = H20+2;}  goto q; 

fi;
}

active proctype puryvat_oxyd() { 

q: if
::  d_step {puryvat>=1 && NADp>=1;puryvat = puryvat-1;NADp = NADp-1;acetyl_co_a = acetyl_co_a+1;NADH = NADH+1;CO2 = CO2+1;}  goto q; 

fi;
}

active proctype krebs_0() { 

oxalacetrat: if
::  d_step {acetyl_co_a>=1 && H20>=1;acetyl_co_a = acetyl_co_a-1;H20 = H20-1;}  goto citrat; 

fi;
citrat: if
::  goto isocitrat; 

fi;
isocitrat: if
::  d_step {NADp>=1;NADp = NADp-1;NADH = NADH+1;Hp = Hp+1;CO2 = CO2+1;}  goto oxoglutarat2; 

fi;
oxoglutarat2: if
::  d_step {NADp>=1;NADH = NADH+1;CO2 = CO2+1;}  goto fumarat; 

fi;
fumarat: if
::  d_step {H20>=1;H20 = H20-1;}  goto malat; 

fi;
malat: if
::  d_step {NADp>=1;NADp = NADp-1;NADH = NADH+1;}  goto oxalacetrat; 

fi;
sukcynyl_coa: 
sukcynyl: 
 false; }

active proctype krebs_1() { 

oxalacetrat: if
::  d_step {acetyl_co_a>=1 && H20>=1;acetyl_co_a = acetyl_co_a-1;H20 = H20-1;}  goto citrat; 

fi;
citrat: if
::  goto isocitrat; 

fi;
isocitrat: if
::  d_step {NADp>=1;NADp = NADp-1;NADH = NADH+1;Hp = Hp+1;CO2 = CO2+1;}  goto oxoglutarat2; 

fi;
oxoglutarat2: if
::  d_step {NADp>=1;NADH = NADH+1;CO2 = CO2+1;}  goto fumarat; 

fi;
fumarat: if
::  d_step {H20>=1;H20 = H20-1;}  goto malat; 

fi;
malat: if
::  d_step {NADp>=1;NADp = NADp-1;NADH = NADH+1;}  goto oxalacetrat; 

fi;
sukcynyl_coa: 
sukcynyl: 
 false; }

active proctype glykolyza() { 

q: if
::  atomic {NADH>=1 && O2>=1 && Hp>=2;electrons!0;NADH = NADH-1;NADp = NADp+1;O2 = O2-1;Hp = Hp-2;H20 = H20+1;}  goto q; 

fi;
}

active proctype ETS() { 

q: if
::  atomic {ADP>=2;electrons?0;ADP = ADP-2;ATP = ATP+2;}  goto q; 

fi;
}

active proctype rest_of_cell() { 

q: if
::  d_step {ATP>=1;ATP = ATP-1;ADP = ADP+1;energy = energy+1;}  goto q; 

fi;
}

