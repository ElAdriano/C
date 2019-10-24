#include <stdio.h>
#include <png.h>
#include "pngcreator.h"
#include "grid.h"
#include <stdlib.h>

void createPng( char* filename, Grid_t grid )
{
	
    png_bytep * rowPointers = (png_bytep*) malloc( sizeof( png_bytep ) * grid->y );
    for( int i = 0; i < grid->y; i++ )
        rowPointers[i] = (png_byte*) malloc( sizeof( png_byte ) * grid->x );

    for( int i = 0; i < grid->y; i++ )
    {
        png_byte * row = rowPointers[i];
        for( int j = 0; j < grid->x; j++ )
            row[j] = grid->v[j][i] ? 0 : 255;
    }

    FILE * file = fopen( filename, "wb" );

    png_structp pngptr = png_create_write_struct( PNG_LIBPNG_VER_STRING, NULL, NULL, NULL );

    png_infop infoptr = png_create_info_struct( pngptr );
    if( setjmp( png_jmpbuf( pngptr ) ) )
        printf("Error during writing PNG file\n" );

    png_init_io( pngptr, file );
    if( setjmp( png_jmpbuf( pngptr ) ) )
        printf("Error during writing PNG file\n" );

    png_set_IHDR( pngptr, infoptr, grid->x, grid->y,
       8, PNG_COLOR_TYPE_GRAY, PNG_INTERLACE_NONE,
       PNG_COMPRESSION_TYPE_BASE, PNG_FILTER_TYPE_BASE );

    png_write_info( pngptr, infoptr );
    if( setjmp( png_jmpbuf( pngptr ) ) )
        printf("Error during writing PNG file\n" );

    png_write_image(pngptr, rowPointers);
    if( setjmp( png_jmpbuf( pngptr ) ) )
        printf("Error during writing PNG file\n" );

    png_write_end( pngptr, NULL );

    for( int i = 0; i < grid->y; i++ )
        free( rowPointers[i] );
    free( rowPointers );

    fclose( file );
    png_free_data(pngptr, infoptr, PNG_FREE_ALL, -1);
    png_destroy_write_struct(&pngptr, &infoptr);
	
}

