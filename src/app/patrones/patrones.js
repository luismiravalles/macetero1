// Configuración básica
const svgWidth = 900;
const svgHeight = 600;
const rectWidth = 90;
const rectHeight = 60;

const paleta = [ 
    0xc24819,   // Teja
    0x758220,   // Verde
    0xd3cb9e,   // Marron claro
    0xb2935b,   // Marron oscuro

    0xc24819,   // Teja
    0x758220,   // Verde
    0xb2935b,   // Marron oscuro   

    0xfed75a,   // Amarillo 
    ]

// Crear SVG
const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
svg.setAttribute("width", svgWidth);
svg.setAttribute("height", svgHeight);
svg.setAttribute("viewBox", `0 0 ${svgWidth} ${svgHeight}`);
svg.style.border = "1px solid black";

// Generar rectángulos en un patrón
for (let y = svgHeight; y >= 0; y -= rectHeight) {
    for (let x = svgWidth; x >= 0; x -= rectWidth) {
        const rect = document.createElementNS("http://www.w3.org/2000/svg", "rect");
        rect.setAttribute("x", x);
        rect.setAttribute("y", y);

        const  width = rectWidth *  Math.floor(1+ Math.random() * 2);
        const  height = rectHeight * Math.floor(1+ Math.random() * 2);

        rect.setAttribute("width", width);
        rect.setAttribute("height", height);
        rect.setAttribute("stroke", "#ffffff");
        rect.setAttribute("stroke.width", "6");
        const color= paleta[Math.floor(Math.random() * paleta.length)];
        rect.setAttribute("fill", "#" + color.toString(16));
        svg.appendChild(rect);
    }
}

// Agregar SVG al contenedor
document.getElementById("svg-container").appendChild(svg);