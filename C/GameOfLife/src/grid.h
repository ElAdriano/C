#ifndef GRID_PACK_H
#define GRID_PACK_H

typedef struct grid{
    char** v;
    unsigned int x, y;
}*Grid_t;

Grid_t initGrid( int x, int y );
void destroyGrid( Grid_t g );
void clearGrid( Grid_t g );
void printGrid( Grid_t g );

#endif
