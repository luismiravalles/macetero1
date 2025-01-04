


const paleta = [ 
    0xc24819,   // Teja
    0x758220,   // Verde
    0xd3cb9e,   // Marron claro
    0xb2935b,   // Marron oscuro
    0x777777,   // Gris
    0x4d5614,   // Verde oscuro
    0x732b0e,   // Rojo oscuro
    0xfed75a,   // Amarillo 
    ];

var svgWidth;
var svgHeight;
var rectWidth;
var rectHeight;
var filas;
var columnas;
var lastColor=0;
var matrizColores;
var colores=paleta.length;
var anchoJunta;
var colorJunta;

function inicializar() {
    svgWidth=window.innerWidth > 800 ? 800: window.innerWidth;
    filas = +document.getElementById("filas").value;
    columnas = +document.getElementById("columnas").value;
    colores = +document.getElementById("colores").value;
    anchoJunta = +document.getElementById("anchoJunta").value;
    colorJunta = document.getElementById("colorJunta").value;
    svgHeight = svgWidth * filas / columnas;
    rectWidth = svgWidth / columnas;
    rectHeight = rectWidth;

    console.log("svgWidth " + svgWidth);
    console.log("svgHeight " + svgHeight);

    matrizColores = new Array(filas);
    for (let i = 0; i < filas; i++) {
        matrizColores[i] = new Array(columnas).fill(-1); // Inicializa cada fila con ceros
    }
}

function colorSinChoques(fila, columna) {
    var evitar=new Array(colores);
    evitar.fill(false);
    if(fila>0) {
        color=matrizColores[fila-1][columna];
        if(color>=0) {
            evitar[color]=true;
        }
    }
    if(fila<filas-1) {
        color=matrizColores[fila+1][columna];
        if(color>=0) {
            evitar[color]=true;
        }
    }    
    if(columna>0) {
        color=matrizColores[fila][columna-1];
        if(color>=0) {
            evitar[color]=true;
        }
    }   
    if(columna<columnas-1) {
        color=matrizColores[fila][columna+1];
        if(color>=0) {
            evitar[color]=true;
        }
    }      

    color=Math.floor(Math.random() * colores); 
    while(color<colores && evitar[color]==true ) {
        color++;
    }  
    if(color>=colores) {
        color=0;
        while(color<colores && evitar[color]==true ) {
            color++;
        }  
    }
    return color;
}



repintar();    


function repintar() {
    console.log("Repintar");
    inicializar();
    const algoritmo = document.getElementById("algoritmo").value;
    window[algoritmo]();
}

function rectLadrillo(x, y, width, height) {
    const rect = document.createElementNS("http://www.w3.org/2000/svg", "rect");            
    rect.setAttribute("x", x);
    rect.setAttribute("y", y);
    rect.setAttribute("width", width);
    rect.setAttribute("height", height);
    rect.setAttribute("stroke", colorJunta);
    rect.setAttribute("stroke-width", "" + anchoJunta + "px");
    rect.setAttribute("fill", "#" + nextColor().toString(16));

    return rect;
}

/*
    Como un ladrillo pero usa la matriz de colores y va por filas y columnas exactas.
*/
function parche(columna, fila, cs, fs) {
    const rect = document.createElementNS("http://www.w3.org/2000/svg", "rect");            
    rect.setAttribute("x", columna * rectWidth);
    rect.setAttribute("y", fila * rectHeight);
    rect.setAttribute("width", cs * rectWidth);
    rect.setAttribute("height", fs * rectHeight);
    rect.setAttribute("stroke", colorJunta);
    rect.setAttribute("stroke-width", "" + anchoJunta + "px");

    indiceColor=colorSinChoques(fila , columna);


    for(let y=fila; y<fila+fs; y++) {
        for(let x=columna; x<columna+cs; x++ ) {
            if(x>=0 && x<columnas && y>=0 && y<filas) {
                matrizColores[y][x] = indiceColor;
            }
        }
    }

    rect.setAttribute("fill", "#" + paleta[indiceColor].toString(16));

    return rect;
}





function nextColor() {
    indiceColor=Math.floor(Math.random() * colores); 
    // indiceColor=lastColor+1;
    if(indiceColor==lastColor) {
        indiceColor++;
    }
    if(indiceColor >= colores) { indiceColor=0; } 
    lastColor=indiceColor;
    color=paleta[indiceColor];
    return color;
}

function crearSvg() {
    // Crear SVG
    const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute("id", "patron");
    svg.setAttribute("width", svgWidth);
    svg.setAttribute("height", svgHeight);
    svg.setAttribute("viewBox", `0 0 ${svgWidth} ${svgHeight}`);
    svg.style.border = "1px solid black";
    return svg;
}

function ladrillo() {
    console.log("Ladrillo");
    const svg=crearSvg();

    lastColor = Math.floor(Math.random() * colores); 
    // Generar rectángulos en un patrón
    for(let fila=0; fila<filas; fila++)  {        
        for(let col=fila; col<columnas - fila; ) {
            y = fila * rectHeight;
            x = col * rectWidth;
            anchoLadrillo = 2;
            ancho = rectWidth * anchoLadrillo;
            const rect= rectLadrillo(x, y, ancho, rectHeight);
            svg.appendChild(rect);
            col+=anchoLadrillo;    
        }
    }
    for(let col=0; col<columnas; col++) {
        for(let fila=col; fila<filas; fila+=2) {
            const rect=rectLadrillo(col*rectWidth, (fila+1)*rectWidth, rectWidth, rectHeight * 2);
            rect.setAttribute("fill", "#" + nextColor().toString(16));
            svg.appendChild(rect);
        }
        for(let fila=col; fila<filas; fila+=2) {
            const rect=rectLadrillo((columnas-col-1)*rectWidth, (fila+1)*rectWidth, rectWidth, rectHeight * 2);
            rect.setAttribute("fill", "#" + nextColor().toString(16));
            svg.appendChild(rect);
        }  
    }

    // Agregar SVG al contenedor
    document.getElementById("patron").remove();

    document.getElementById("svg-container").appendChild(svg);    
}

function par(x) {
    return x%2==0;
}

function bloques() {
    console.log("Ladrillo");
    const svg=crearSvg();

    lastColor = Math.floor(Math.random() * colores); 
    // Generar rectángulos en un patrón
    for(let fila=0; fila<filas; fila+=3)  {   
        y = fila * rectHeight;     
        for(let col=0; col<columnas; col+=4) {           
            x = col * rectWidth;
            svg.appendChild(parche(col,fila,2,1));
            svg.appendChild(parche(col+2, fila,                1, 2));
            svg.appendChild(parche(col+3, fila,                1, 2));
            
            svg.appendChild(parche(col ,                    fila+1,  1, 2));
            svg.appendChild(parche((col+1),   (fila+1),         1, 2));            
            svg.appendChild(parche((col+2),   (fila+2),         2, 1));            
        }
    }


    // Agregar SVG al contenedor
    document.getElementById("patron").remove();

    document.getElementById("svg-container").appendChild(svg);    
}

function ocupado(fila, columna) {
    return matrizColores[fila][columna] >= 0;
}

function bloquesAzar() {
    console.log("Ladrillo");
    const svg=crearSvg();
    const anchoMaximo= +document.getElementById("anchoMaximo").value;
    const anchoMinimo= +document.getElementById("anchoMinimo").value;

    lastColor = Math.floor(Math.random() * colores); 
    // Generar rectángulos en un patrón
    for(let fila=0; fila<filas; fila++)  {   
        for(let col=0; col<columnas; col++) {           
            if(ocupado(fila, col)) {
                continue;
            }

            var ancho=anchoMinimo + Math.floor(Math.random()*(anchoMaximo-anchoMinimo+1));

            // Al azar elegimos si el bloque es vertical u horizontal.
            var horizontal=false;
            if(Math.floor(Math.random()*2)==0) {
                horizontal=true;
                for(let i=0; i<ancho && i<columnas; i++) {
                    if(ocupado(fila, col+i)) {
                        horizontal=false;
                    }
                }

            }
            if(horizontal) {
                svg.appendChild(parche(col, fila, ancho, 1));
            } else {
                svg.appendChild(parche(col, fila, 1, ancho));
            }       
        }
    }


    // Agregar SVG al contenedor
    document.getElementById("patron").remove();

    document.getElementById("svg-container").appendChild(svg);    
}



function random() {
    console.log("random");
    // Configuración básica

    const anchoMinimo= +document.getElementById("anchoMinimo").value;
    const altoMinimo=  +document.getElementById("altoMinimo").value;
    const anchoMaximo= +document.getElementById("anchoMaximo").value;
    const altoMaximo=  +document.getElementById("altoMaximo").value;
    const mitades= +document.getElementById("mitades").value;

 
    // Crear SVG
    const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute("id", "patron");
    svg.setAttribute("width", svgWidth);
    svg.setAttribute("height", svgHeight);
    svg.setAttribute("viewBox", `0 0 ${svgWidth} ${svgHeight}`);
    svg.style.border = "1px solid black";
    
    for (let fila = filas-1; fila >= 0; fila--) {
        for (let col = columnas-1; col >= 0; col--) {
            const  width = rectWidth *  Math.floor(anchoMinimo+ Math.random() * (anchoMaximo-anchoMinimo)) + 
                                            rectWidth * Math.floor(Math.random() * mitades) / 2;
            const  height = rectHeight * Math.floor(altoMinimo+ Math.random() * (altoMaximo - altoMinimo)) +
                                             rectHeight * Math.floor(Math.random() * mitades) / 2;

            const rect = rectLadrillo(col*rectWidth, fila*rectHeight,width, height);
            svg.appendChild(rect);
        }
    }

    // Agregar SVG al contenedor
    document.getElementById("patron").remove();

    document.getElementById("svg-container").appendChild(svg);
}