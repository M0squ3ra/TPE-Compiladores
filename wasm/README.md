instalar cmake (https://cmake.org/install/)
instalar wabt: 
    clonar https://github.com/WebAssembly/wabt
    cd wabt
    mkdir build
    cd build
    cmake ..
    cmake --build .
    en la carpeta build se les genero el bin, copiar a donde quede mas comodo

compilar .wat: wat2wasm main.wat

ejecutar algun servidor (vscode tiene live server) y servir main.html