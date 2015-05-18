SRC = ./src/
INC = ./include/
LIB = ./lib/
OBJ = ./obj/

#libsc2d2 = -lSDL -lSDL_image -lGL -lGLU

comp = g++ -g -O3 -c -I $(INC)

objc2d2f = $(OBJ)chien2d2.o $(OBJ)chien2d2_sdl.o $(OBJ)chien2d2_gl.o \
	$(OBJ)chien2d2primitivas.o $(OBJ)chien2d2primitivas_sdl.o \
	$(OBJ)chien2d2primitivas_gl.o
objc2d2 = chien2d2.o chien2d2_sdl.o chien2d2_gl.o \
	chien2d2primitivas.o chien2d2primitivas_sdl.o \
	chien2d2primitivas_gl.o
objca2f = $(OBJ)chienaudio2.o
objca2 = chienaudio2.o
objcm2f = $(OBJ)chien2d2mapa.o
objcm2 = chien2d2mapa.o
objcatorf = $(OBJ)ator.o
objcator = ator.o
objc2d2luaf = $(OBJ)chienlua2.o $(OBJ)chien2d2lua.o $(OBJ)chienaudio2lua.o $(OBJ)atorlua.o
objc2d2lua = chienlua2.o chien2d2lua.o chienaudio2lua.o atorlua.o

minimal: mkd libc2d2 libca2 libcm2 libcator

all: mkd libc2d2 libca2 libcm2 libcator libc2d2lua

libc2d2 : $(objc2d2)
	ar rcs $(LIB)libc2d2.a $(objc2d2f)

libca2: $(objca2)
	ar rcs $(LIB)libca2.a $(objca2f)

libcm2: $(objcm2)
	ar rcs $(LIB)libcm2.a $(objcm2f)

libcator: $(objcator)
	ar rcs $(LIB)libcator.a $(objcatorf)
	
libc2d2lua: $(objc2d2lua)
	ar rcs $(LIB)libc2d2lua.a $(objc2d2luaf)

chien2d2.o : 
	$(comp) $(SRC)chien2d2.cpp -o $(OBJ)chien2d2.o
chien2d2_sdl.o : 
	$(comp) $(SRC)chien2d2_sdl.cpp -o $(OBJ)chien2d2_sdl.o
chien2d2_gl.o : 
	$(comp) $(SRC)chien2d2_gl.cpp -o $(OBJ)chien2d2_gl.o
chien2d2primitivas.o : 
	$(comp) $(SRC)chien2d2primitivas.cpp -o $(OBJ)chien2d2primitivas.o
chien2d2primitivas_sdl.o : 
	$(comp) $(SRC)chien2d2primitivas_sdl.cpp -o $(OBJ)chien2d2primitivas_sdl.o
chien2d2primitivas_gl.o : 
	$(comp) $(SRC)chien2d2primitivas_gl.cpp -o $(OBJ)chien2d2primitivas_gl.o
chienaudio2.o :
	$(comp) $(SRC)chienaudio2.cpp -o $(OBJ)chienaudio2.o
chien2d2mapa.o :
	$(comp) $(SRC)chien2d2mapa.cpp -o $(OBJ)chien2d2mapa.o	
ator.o :
	$(comp) $(SRC)ator.cpp -o $(OBJ)ator.o	
chienlua2.o :	
	$(comp) $(SRC)chienlua2.cpp -o $(OBJ)chienlua2.o	
chien2d2lua.o :
	$(comp) $(SRC)chien2d2lua.cpp -o $(OBJ)chien2d2lua.o	
chienaudio2lua.o :
	$(comp) $(SRC)chienaudio2lua.cpp -o $(OBJ)chienaudio2lua.o	
atorlua.o :
	$(comp) $(SRC)atorlua.cpp -o $(OBJ)atorlua.o		
install :
	cp -r $(INC)/* /usr/include/ ; cp $(LIB)*.a /usr/lib/
uninstall:
	rm -rf /usr/include/c2d2 ; rm /usr/lib/libc2d2.a /usr/lib/libca2.a /usr/lib/libcm2.a /usr/lib/libcator.a /usr/lib/libc2d2lua.a
clean:
	rm -rf $(OBJ) $(LIB)
mkd:
	mkdir $(OBJ) ; mkdir $(LIB)
