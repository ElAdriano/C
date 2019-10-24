#ifndef POINTLIST_H_INCLUDED
#define POINTLIST_H_INCLUDED

#include "grid.h"

typedef struct PointList{
    int x, y;
    struct PointList * next;
}*Plist_l;

Plist_l initPlist( int x, int y );
Plist_l addToPlist( Plist_l list, int x, int y );
void destroyPlist( Plist_l list );

#endif // POINTLIST_H_INCLUDED
