#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "grid.h"

Grid_t initGrid( int x, int y )
{
    Grid_t g = malloc( sizeof *g  );

    g->v = malloc( sizeof( char* ) * x );
    for( int i = 0; i < x; i++ )
        g->v[i] = calloc( y * sizeof( char ), sizeof( char ) );

    g->x = x;
    g->y = y;

    return g;
}

//----------------------------------------------------------------------------------------

void clearGrid( Grid_t g )
{
    for(int i = 0; i < g->x; i++)
        memset( g->v[i], 0, sizeof( char ) * g->y );
}

//----------------------------------------------------------------------------------------

void destroyGrid( Grid_t g )
{
    for( int i = 0; i < g->x; i++ )
    {
        free( g->v[i] );
    }

    free( g->v );
    free( g );
}

//----------------------------------------------------------------------------------------

void printGrid( Grid_t g )
{
    printf("\n");

    for( int i = 0; i < g->y; i++ ){
        printf( "%d -- \t", i );

        for(int j = 0; j < g->x; j++){
            printf("%d ",g->v[j][i]); //necessary debug
        }
        printf("\n");
    }
}
