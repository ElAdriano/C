#ifndef SIMULATE_H_INCLUDED
#define SIMULATE_H_INCLUDED

#include <stdio.h>

#include "pointlist.h"

typedef struct {
    Plist_l pointlist;
    Grid_t grid;
    Grid_t nextgrid;
}*Simulator_t;

Simulator_t prepareSimulation( FILE* file );
int simulate( FILE * file, int x, int y, int iterations );
Simulator_t calculateFrame( Simulator_t sim );
Simulator_t initSimulator( int x, int y );
void destroySimulator( Simulator_t simulator );

#endif // SIMULATE_H_INCLUDED
