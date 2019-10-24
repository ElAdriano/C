//fpc 3.0.0

program koza;

uses sysutils, math, Crt;
//**************************************************************************
//definicja typu 'stany'
type stany = record
    St : array[0..3] of integer;
    res : string;
    end;
    
//**************************************************************************
//zmienne globalne
var
    stos : array[0..10000] of stany;
    k,l,tops : integer;

//**************************************************************************
//funkcje i procedury
function sprawdzenie_stanu( S : stany ; b : integer ) : boolean;
begin
    if (S.St[0] <> S.St[1]) and (S.St[1] = S.St[2]) and (S.St[1] = b) then //wilk z koza
    begin
        sprawdzenie_stanu := false;
    end

    else if (S.St[0] <> S.St[2]) and (S.St[2] = S.St[3]) and (S.St[2] = b) then //koza z salata
    begin
        sprawdzenie_stanu := false;
    end

    else //nie ma problemu
    begin
        sprawdzenie_stanu := true;
    end;
end;

function przewiezieni( t : stany ) : boolean;
var
        i, counter : integer;
begin
        for i:=0 to 3 do
        begin
                if(t.St[i] = 1)then
                begin
                        counter := counter + 1;
                end;
        end;
        if(counter = 4)then
        begin
                przewiezieni := true;
        end
        else
        begin
                przewiezieni := false;
        end;
end;

procedure przepisz_stan_na_stos( t : stany ; r : string);
var
        i : integer;
begin
        for i := 0 to 3 do
        begin
                stos[tops].St[i] := t.St[i];
        end;
        stos[tops].res := r;
        tops := tops + 1;
end;

procedure wypisz_stos( fin : integer );
var
        idx, i : integer;
        tmp_s : String;
begin
        for idx := 0 to fin do
        begin
              tmp_s := '[';
              for i := 0 to 3 do
              begin
                  if( i = 0 ) and ( stos[idx].St[i] = 0 )then tmp_s := tmp_s + 'C';
                  if( i = 1 ) and ( stos[idx].St[i] = 0 )then tmp_s := tmp_s + 'W';
                  if( i = 2 ) and ( stos[idx].St[i] = 0 )then tmp_s := tmp_s + 'K';
                  if( i = 3 ) and ( stos[idx].St[i] = 0 )then tmp_s := tmp_s + 'S';
              end;
              tmp_s := tmp_s + ' - ';

              for i := 0 to 3 do
              begin
                  if( i = 0 ) and ( stos[idx].St[i] = 1 )then tmp_s := tmp_s + 'C';
                  if( i = 1 ) and ( stos[idx].St[i] = 1 )then tmp_s := tmp_s + 'W';
                  if( i = 2 ) and ( stos[idx].St[i] = 1 )then tmp_s := tmp_s + 'K';
                  if( i = 3 ) and ( stos[idx].St[i] = 1 )then tmp_s := tmp_s + 'S';
              end;
              tmp_s := tmp_s + ']';
              Writeln( tmp_s, ' | ocena stanu: ', stos[idx].res );
        end;
end;

function konflikt_zwierzat( stan : stany ) : boolean;
begin
        if(stan.St[1] = stan.St[2]) or (stan.St[2] = stan.St[3])then
        begin
                konflikt_zwierzat := true;
        end
        else
        begin
                konflikt_zwierzat := false;
        end;
end;

function bliski_koniec( t : stany ) : boolean;
var
        i, licznik : integer;
        konf : boolean;
begin
        for i := 0 to 3 do
        begin
                if(t.St[i] = 1)then licznik := licznik + 1;
        end;
        konf := konflikt_zwierzat( t );
        if(licznik = 3) and (konf = false)then
        begin
                bliski_koniec := true;
        end
        else
        begin
                bliski_koniec := false;
        end;
end;

procedure przewoz( S : stany ; idpop : integer );
var
    wynik_stanu0, wynik_stanu1, niemamcoprzewiezc, koniec, ostateczny_przewoz : boolean;
    tmp : stany;
    i : integer;
begin
    for i := 0 to 3 do
    begin
        tmp.St[i] := S.St[i]; //przepisuje tablice stanow
    end;

    koniec := przewiezieni( tmp );
    if(koniec = true)then
    begin
                przepisz_stan_na_stos( tmp, 'SUKCES - WSZYSCY PRZEWIEZIENI' );
                EXIT;
    end;

    ostateczny_przewoz := bliski_koniec( tmp );
    if(ostateczny_przewoz = true)then
    begin
                przepisz_stan_na_stos( tmp, 'OK' );
                tmp.St[0] := 1 - tmp.St[0];
                przewoz( tmp, idpop );
                EXIT;
    end

    else
    begin

        wynik_stanu0 := sprawdzenie_stanu(tmp,0); //sprawdzam czy to dozwolony stan - jesli ok wrzucam na stos
        wynik_stanu1 := sprawdzenie_stanu(tmp,1);
        if( wynik_stanu0 =  true ) and ( wynik_stanu1 = true )then //nie ma problemu
        begin
                przepisz_stan_na_stos( tmp, 'OK' );
                Write('');
                niemamcoprzewiezc := true;

                for i:=1 to 3 do
                begin
                        if(i <> idpop) and (tmp.St[0] = tmp.St[i])then
                        begin
                                niemamcoprzewiezc := false;
                                tmp.St[0] := 1 - tmp.St[0];
                                tmp.St[i] := 1 - tmp.St[i];
                                przewoz( tmp, i );
                                tmp.St[0] := 1 - tmp.St[0];
                                tmp.St[i] := 1 - tmp.St[i];
                        end;
                end;

                if(niemamcoprzewiezc = true)then
                begin
                        tmp.St[0] := 1 - tmp.St[0];
                        przewoz( tmp, idpop );
                end;
        end

        else
        begin
                przepisz_stan_na_stos( tmp, 'ZLY' );
                EXIT;
        end;
    end;
end;

//**************************************************************************
//zmienne dla funkcji main
var
    S : stany;

//main *********************************************************************
begin
    //wszyscy na 0 brzegu
    S.St[0] := 0;
    S.St[1] := 0;
    S.St[2] := 0;
    S.St[3] := 0;
    tops := 0;
    przewoz( S, 0 );
    wypisz_stos(tops-1);
end.
