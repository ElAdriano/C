#ifndef NEIGHBOURS_H_INCLUDED
#define NEIGHBOURS_H_INCLUDED

#include "grid.h"
#include "pointlist.h"
#include "simulate.h"

int countNeighbours( int x, int y, Grid_t grid );
Simulator_t analyzePointList( Simulator_t sim );


#endif // NEIGHBOURS_H_INCLUDED
