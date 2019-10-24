all:
	cc src/main.c src/filemanagement.* src/grid.* src/neighbours.* src/pointlist.* src/simulate.* src/pngcreator.* -o bin/game -lpng -Wall -pedantic
test:
	./bin/game 10 10 tests/inputFiles/points 12
memorytest:
	valgrind ./bin/game 10 10 tests/inputFiles/points 12
