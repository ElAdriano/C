#include <stdlib.h>
#include <stdio.h>

#include "filemanagement.h"
#include "simulate.h"
#include "pointlist.h"
#include "grid.h"

void readFile( FILE * in, Simulator_t sim )
{
    int x, y;
    int i = 0;
    int r;      // used to hold number of read numbers

    while( ( r = fscanf( in, "%d %d\n", &x, &y ) ) != EOF )
    {
        i++;

        if( r == 2 )
        {
            if( x < sim->grid->x && y < sim->grid->y && x >= 0 && y >= 0 )
            {
                sim->pointlist = addToPlist( sim->pointlist, x, y );
                sim->grid->v[x][y] = 1;
            }
            else
                printf( "Punkt w linii nr %d o wspolrzednych ( %d, %d ) przekracza wymiary planszy. Pominieto jego wczytywanie.\n", i, x, y );
        }
        else
            printf( "Punkt w linii nr %d jest niepoprawny.\n", i );
    }
}

//----------------------------------------------------------------------------------------

void saveToFile( char* filename, Plist_l list )
{
    FILE * file = fopen( filename, "w" );

    while( list != NULL )
    {
        fprintf( file, "%d %d\n", list->x, list->y );
        list = list->next;
    }

    fclose( file );
}
