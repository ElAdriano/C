#include <stdlib.h>

#include "pointlist.h"
#include "grid.h"

Plist_l initPlist( int x, int y )
{
    Plist_l list = malloc( sizeof( *list ) );
    list->x = x;
    list->y = y;
    list->next = NULL;
    return list;
}

Plist_l addToPlist( Plist_l list, int x, int y )
{
    if( list == NULL )
    {
        list = initPlist( x, y );
    }
    else
    {
        Plist_l tmp = list;
        while( tmp->next != NULL )
            tmp = tmp->next;
        tmp->next = malloc( sizeof *tmp );
        tmp->next->x = x;
        tmp->next->y = y;
        tmp->next->next = NULL;
    }
    return list;
}

void destroyPlist( Plist_l list )
{
    Plist_l tmp;
    while( list != NULL )
    {
        tmp = list;
        list = list->next;
        free( tmp );
    }
}
