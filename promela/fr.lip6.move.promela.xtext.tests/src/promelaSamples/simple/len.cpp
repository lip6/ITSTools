#include "lengthFifo.h"

class _len : public StrongShom {

public :
_len () {}
GSDD phiOne() const {cout<<"ONE len"<<endl; return GSDD::top;}

GShom phi(int var, const DataSet& val) const{
    return countFifo(0);
}

size_t hash() const {return 758203;}

bool operator ==(const StrongShom &s) const {_len *ps = (_len*) &s; return typeid(s)==typeid(*this);}
};

GShom len() {return GShom(new _len());}

