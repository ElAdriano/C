#include <stdlib.h>
#include <stdio.h>

#include "simulate.h"
#include "pointlist.h"
#include "grid.h"
#include "filemanagement.h"
#include "neighbours.h"

Simulator_t prepareSimulator( FILE* file, int x, int y )
{
    Simulator_t sim = initSimulator( x, y );
    readFile( file, sim );

    if( sim->pointlist == NULL  )
    {
        destroySimulator( sim );
        return NULL;
    }

    return sim;
}

//----------------------------------------------------------------------------------------

int simulate( FILE * file, int x, int y, int iterations )
{
    Simulator_t sim = prepareSimulator( file, x, y );

    if( sim == NULL )
        return -1;

    char filename[9];

    for( int i = 0; i < iterations; i++ )
    {
        sprintf( filename, "%d.png", i );
        createPng( filename, sim->grid );

        sim = calculateFrame( sim );
    }

    sprintf( filename, "%d.png", iterations );
    createPng( filename, sim->grid );

    destroySimulator( sim );

    return 1;
}

//----------------------------------------------------------------------------------------

Simulator_t calculateFrame( Simulator_t sim )
{
    int x, y, neighbours;

    Grid_t grid = sim->grid;
    Grid_t nextgrid = sim->nextgrid;

    Plist_l list = sim->pointlist;
    Plist_l newlist = NULL;

    while( list != NULL )
    {
        x = list->x;
        y = list->y;

        if( ( neighbours = countNeighbours( x, y, grid ) ) == 2 || neighbours == 3 )
        {
            nextgrid->v[x][y] = 1;
            newlist = addToPlist( newlist, x, y );
        }

        for( int i = x - 1 + grid->x; i < x + 2 + grid->x; i++ )
            for( int j = y - 1 + grid->y; j < y + 2 + grid->y; j++ )
                if( ( i % grid->x != x || j % grid->y != y ) && ( grid->v[i % grid->x][j % grid->y] == 0 ) )
                {
                    grid->v[i % grid->x][j % grid->y] = 2;
                    if( countNeighbours( i % grid->x, j % grid->y, grid ) == 3 )
                    {
                        nextgrid->v[i % grid->x][j % grid->y] = 1;
                        newlist = addToPlist( newlist, i % grid->x, j % grid->y );
                    }
                }

        list = list->next;
    }

    destroyPlist( sim->pointlist );
    sim->pointlist = newlist;

    clearGrid( grid );
    sim->grid = nextgrid;
    sim->nextgrid = grid;

    return sim;
}

//----------------------------------------------------------------------------------------

Simulator_t initSimulator( int x, int y )
{
    Simulator_t sim = malloc( sizeof *sim );
    sim->grid = initGrid( x, y );
    sim->nextgrid = initGrid( x, y );
    sim->pointlist = NULL;
    return sim;
}

//----------------------------------------------------------------------------------------

void destroySimulator( Simulator_t sim )
{
    destroyPlist( sim->pointlist );
    destroyGrid( sim->grid );
    destroyGrid( sim->nextgrid );
    free( sim );
}
