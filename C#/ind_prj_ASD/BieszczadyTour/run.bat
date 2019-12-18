IF NOT EXIST "bin" mkdir "bin"
IF NOT EXIST "bin\tour.exe" csc.exe -out:bin\tour.exe *.cs .\src\*.cs .\models\*.cs .\exceptions\*.cs
IF "%4"=="DEBUG" csc.exe -out:bin\tour.exe *.cs .\src\*.cs .\models\*.cs .\exceptions\*.cs

bin\tour.exe %1 %2 %3
