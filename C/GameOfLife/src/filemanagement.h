#ifndef IO_H
#define IO_H

#include <stdio.h>
#include <stdlib.h>

#include "pointlist.h"

#include "simulate.h"

void readFile( FILE * in, Simulator_t sim );
void createPng( char* filename, Grid_t grid );

#endif
