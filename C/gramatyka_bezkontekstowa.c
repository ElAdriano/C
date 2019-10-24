#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <ctype.h>
#include <string.h>

typedef struct sl{
    char* lett;
}string;

typedef struct regula{
    char * popp;
    char * nastp;
}regula;

typedef struct gramatyka{
    regula* tregul;
    int n; //ile regul
}gramatyka;

void dodaj_regule(gramatyka * r, char * pop, char * nast){
    regula nast_regula;
    nast_regula.popp = pop;
    nast_regula.nastp = nast;
    r -> tregul[r->n++] = nast_regula;
}

gramatyka* init_gramatyka( gramatyka * s ){
    s = (gramatyka*)malloc(sizeof(gramatyka));
    s->n = 0;
    s->tregul = (regula*)malloc(sizeof(regula) * 1000*100);
    return s;
}

//powyzej definicja gramatyki

#define schar 'S' //definicja znaku poczatkowego

void wypisz_wyrazenie( string * s );

string * zmiana(string * s, char * popp, char * nast){
    char * tmp = malloc( sizeof(char) * 10000);
    if( (strstr(s->lett,popp)) != NULL ){
        char * n = (char*)malloc(sizeof(char) * 1000);
        tmp = strstr(s->lett, popp); //szukam wystapienia S
        strcpy( n, tmp + strlen(popp) );
        strcpy( tmp, nast );
        strcpy( tmp + strlen(nast) , n );
        return s;
    }
    else
        return s;
}

int main(){
    //tworze poczatkowy string
    string* wyrazenie = (string*)malloc( sizeof(string) );
    wyrazenie -> lett = (char*)malloc(sizeof(char)*1000);
    wyrazenie -> lett[0] = 'S'; //symbol poczatkowy

    //tworze gramatyke
    gramatyka * g;
    g = init_gramatyka( g );
    dodaj_regule(g, "S" , "S+S");
    dodaj_regule(g, "S" , "(S)");
    dodaj_regule(g, "S" , "8");
    dodaj_regule(g, "S" , "S-S");
    dodaj_regule(g, "S" , "S/S");
    dodaj_regule(g, "S" , "S*S");
    dodaj_regule(g, "S" , "aSb");

    wypisz_wyrazenie(wyrazenie);

    srand(time(NULL)); //inicjuje maszyne losujaca elementy

    for(int i = 0; i < 10; i++){
      int r = (int)rand()%(g->n);
      zmiana(wyrazenie, g->tregul[r].popp , g->tregul[r].nastp);
    }

    wypisz_wyrazenie(wyrazenie);
    return 0;
}

void wypisz_wyrazenie( string * s ){
    int i = 0;
    while(s->lett[i] != '\0'){
        printf("%c",s->lett[i++]);
    }
    printf("\n");
}
