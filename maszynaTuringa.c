#include <stdio.h>
#include <stdlib.h>

#define MAXSIZE 10000

typedef struct stany_struct{
    char * stany;
    int n;
}zStanow;

typedef struct symbole{
    char * symbole_d;
    int n;
}zSymboli;

typedef struct przejscie{
    char symbol1;
    char symbol2;
    char stan1;
    char stan2;
    int kierunek;
}przejscia;

typedef struct zprzejsc{
    int n;
    przejscia * t_przejsc;
}zPrzejsc;

typedef struct Maszyna{
    zStanow * Q;
    zStanow * F;
    zSymboli * sigma;
    zPrzejsc * Pr;
    char * tasma;
    int glowica;
    int d_tasmy;
}MT;

MT * init_MT( char a );

void dodaj_stan( zStanow * k, char a );
void dodaj_symbol( zSymboli* k, char a );
void dodaj_przejscie( MT * M, char sym_n_t, char zam_n_t, char popstan, char naststan, char k );

void wypisz_tasme( MT * M, int k );

void daj_wejscie( MT * M, char * a );
int koncowy_stan( MT * M );
przejscia szukaj_reguly( MT * M, char nsymb, char stan );
int symbol_ok( MT * M, char a );
int start_maszyny( MT * M );
void zniszcz_maszyne( MT * M );

int main(int argc, char** argv){
    MT * M = init_MT( 's' );
    
    dodaj_stan( M -> Q, 'a' );
    dodaj_stan( M -> Q, 'b' );
    dodaj_stan( M -> F, 'b' );

    dodaj_przejscie( M, 'B', 'B', 's', 's', 'P' );
    dodaj_przejscie( M, '1', '0', 's', 'a', 'P' );
    dodaj_przejscie( M, '0', '1', 's', 'b', 'P' );
    dodaj_przejscie( M, '1', '0', 'a', 'a', 'P' );
    dodaj_przejscie( M, '0', '1', 'a', 'b', 'P' );
    dodaj_przejscie( M, 'B', '1', 'a', 'b', 'P' );
    dodaj_przejscie( M, 'B', 'B', 'b', 'b', 'P' );
    dodaj_przejscie( M, '0', '0', 'b', 'b', 'P' );
    dodaj_przejscie( M, '1', '1', 'b', 'b', 'P' );

    dodaj_symbol( M -> sigma, '0' );
    dodaj_symbol( M -> sigma, '1' );
    dodaj_symbol( M -> sigma, 'B' );

    daj_wejscie( M, "11010111" ); //daje liczbe 11101011 (zapis binarny) - zapis od konca
    wypisz_tasme( M, 0 );

    int wynik = start_maszyny( M );
    if( wynik == 1 ){
        wypisz_tasme( M, 1 );
        printf("slowo podane na wejsciu zostalo zaakceptowane przez daną maszynę Turinga\n");
    }
    else{
        printf("slowo podane na wejsciu nie jest akceptowane przez daną maszynę Turinga\n");
        if( wynik == 0 ){
            printf("znaleziono symbol nie nalezacy do zbioru symboli stosownych\n");
        }
        if( wynik == 2 ){
            printf("wykonano ruch w lewo od lewego ogranicznika tasmy\n");
        }

    }
    zniszcz_maszyne( M );
    return 0;
}

void zniszcz_maszyne(MT * M){
	free( M -> Q -> stany );
    free( M -> Q );
    free( M -> F -> stany );
	free( M -> F );
	free( M -> sigma -> symbole_d );
	free( M -> sigma );
	free( M -> Pr -> t_przejsc );
    free( M -> Pr );
    free( M -> tasma );
    free( M );
}

int start_maszyny(MT * M){
    while( M -> glowica < M -> d_tasmy ){
        if( symbol_ok(M, M -> tasma[ M -> glowica + 1 ]) == 1 ){
          przejscia zamiana = szukaj_reguly(M, M -> tasma[ M -> glowica + 1 ], M -> tasma[ M -> glowica ]);
          if( M -> glowica == 0 && zamiana.kierunek == -1 ){
              return 2;
          }
          M -> tasma[ M -> glowica + 1 ] = zamiana.symbol2;
          M -> tasma[ M -> glowica ] = zamiana.stan2;
          char tmp = M -> tasma[ M -> glowica + 1 ];
          M -> tasma[ M -> glowica + 1 ] = M -> tasma[ M -> glowica ];
          M -> tasma[ M -> glowica ] = tmp;
          M -> glowica = ( (M -> glowica) + zamiana.kierunek );
        }
        else{
          return 0;
        }
    }
    return 1;
}

int symbol_ok(MT * M, char a){
    for(int i = 0; i < M -> sigma -> n; i++){
        if(a == M -> sigma -> symbole_d[i]){
            return 1;
        }
    }
    return 0;
}

int koncowy_stan(MT * M){
    for(int i = 0; i < M -> F -> n; i++){
        if(M -> tasma[ M -> glowica ] == M -> F -> stany[i]){
            return 1;
        }
    }
    return 0;
}

przejscia szukaj_reguly(MT * M, char nsymb, char stan){
    for(int i = 0; i < M -> Pr -> n; i++){
        char tmpsymb = M -> Pr -> t_przejsc[i].symbol1;
        char tmpstan = M -> Pr -> t_przejsc[i].stan1;
        if(nsymb == tmpsymb && stan == tmpstan){
            return M -> Pr -> t_przejsc[i];
        }
    }
}

void wypisz_tasme(MT * M, int k){
    int i = 0;
    if( k == 0 ){
        while( i < M -> d_tasmy+1 ){
            printf("%c",M -> tasma[i++]);
        }
    }
    if( k == 1 ){
        while( i < M -> glowica ){
            printf("%c",M -> tasma[i++]);
        }
    }
    printf("\n");
}

void daj_wejscie(MT * M, char * a){
    int i = 0;
    int idx_t = 1;
    while(a[i] != '\0'){
        M -> tasma[idx_t++] = a[i++];
    }
    M -> d_tasmy = idx_t-1;
}

MT * init_MT(char a){
    MT * Maszyna = malloc(sizeof(MT));

    Maszyna -> Q = (zStanow*)malloc(sizeof(zStanow));
    Maszyna -> Q -> stany = malloc(MAXSIZE * sizeof(char));
    Maszyna -> Q -> stany[0] = '\0';
    Maszyna -> Q -> n = 0;

    Maszyna -> F = (zStanow*)malloc(sizeof(zStanow));
    Maszyna -> F -> stany = malloc(MAXSIZE * sizeof(char));
    Maszyna -> F -> stany[0] = '\0';
    Maszyna -> F -> n = 0;

    Maszyna -> sigma = (zSymboli*)malloc(sizeof(zSymboli));
    Maszyna -> sigma -> symbole_d = malloc(MAXSIZE * sizeof(char));
    Maszyna -> sigma -> symbole_d[0] =  '\0';
    Maszyna -> sigma -> n = 0;

    Maszyna -> Pr = (zPrzejsc*)malloc(sizeof(zPrzejsc));
    Maszyna -> Pr -> t_przejsc = malloc(MAXSIZE * sizeof(przejscia));
    Maszyna -> Pr -> n = 0;

    Maszyna -> tasma = (char*)malloc(MAXSIZE * sizeof(char));
    Maszyna -> glowica = 0;
    for(int i = 0; i < MAXSIZE; i++){
        Maszyna -> tasma[i] = 'B';
    }
    Maszyna -> tasma[0] = a;
    return Maszyna;
}

void dodaj_przejscie(MT * M, char sym_n_t, char zam_n_t, char popstan, char naststan, char k){
    if( k != 'L' && k != 'P' ){
        return;
    }
    int idx = M -> Pr -> n;
    M -> Pr -> t_przejsc[idx].symbol1 = sym_n_t;
    M -> Pr -> t_przejsc[idx].symbol2 = zam_n_t;
    M -> Pr -> t_przejsc[idx].stan1 = popstan;
    M -> Pr -> t_przejsc[idx].stan2 = naststan;
    if( k == 'L' ){
        M -> Pr -> t_przejsc[idx].kierunek = -1;
    }
    else  if( k == 'P'){
        M -> Pr -> t_przejsc[idx].kierunek = 1;
    }
    M -> Pr -> n++;
    return;
}

void dodaj_symbol(zSymboli * k, char a){
    k -> symbole_d[ k -> n++ ] = a;
}

void dodaj_stan(zStanow * k, char a){
    k -> stany[ k -> n++ ] = a;
}
