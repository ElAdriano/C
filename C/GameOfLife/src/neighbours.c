#include <stdlib.h>

#include "neighbours.h"
#include "grid.h"

int countNeighbours( int x, int y, Grid_t grid )
{
    int neighbours = 0;

    for( int i = x - 1 + grid->x; i < x + 2 + grid->x; i++ )
        for( int j = y - 1 + grid->y; j < y + 2 + grid->y; j++ )
            if( ( i % grid->x != x || j % grid->y != y ) && ( grid->v[i % grid->x][j % grid->y] == 1 ) )
                neighbours++;

    return neighbours;
}
