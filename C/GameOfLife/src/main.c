#include <stdio.h>
#include <stdlib.h>

#include "simulate.h"

int main(int argc, char** argv)
{
    int x;
    if(argc > 1) x = atoi(argv[1]);
    else
    {
    	  printf("Nie podano szerokosci planszy. Prosze o podanie wymaganej wartosci:\n");
    	  scanf("%d", &x);
    }

    int y;
    if(argc > 2) y = atoi(argv[2]);
    else
    {
    	  printf("Nie podano szerokosci planszy. Prosze o podanie wymaganej wartosci:\n");
    	  scanf("%d", &y);
    }

    FILE * in = argc > 3 ? fopen( argv[3], "r" ) : stdin;

    int it;
    if(argc > 4) it = atoi(argv[4]);
    else
    {
	      printf("Nie podano ilosci wykonanych iteracji. Prosze o podanie wymaganej wartosci:\n");
	      scanf("%d", &it);
    }


    if( x <= 0 || y <= 0 )
    {
      	printf("Podano bledna wartosc dla wymiarow planszy.\n");
  	    return 1;
    }

    if( in == NULL )
    {
        fprintf( stderr, "Nie udalo sie otworzyc pliku %s\n", argv[3] );
        return 1;
    }

    if( simulate( in, x, y, it ) == -1 )
    {
        fclose( in );
        fprintf( stderr, "Nie udalo sie przeprowadzic symulacji z powodu braku wczytanych punktow.\n");
        return 0;
    }

    fclose( in );

    return 0;
}
