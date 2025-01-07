/*
    Datos extraidos de https://tablademareas.com/mareas/coeficiente-marea
*/

def texto=new File("coeficientes-2025.xml").text
def xml=new XmlSlurper().parseText(texto)

String [][] coef=new String[12][31]

int base=0
xml.table.each { table ->
    int dia=0
    table.tbody.tr.each { tr ->        
        println "dia ${dia}"
        int mes = 0
        tr.td.each { td ->
            if(td.localText()[0]!=null && mes>0 && dia>0) {
                int mesReal=mes+base
                println "Coeficiente para día ${dia} y mes ${mesReal} : " + td.localText()[0]       
                coef[mesReal-1][dia-1]=td.localText()[0]
            }
            mes++
        }
        dia++
    }
    base+=6
}

for(int mes=0; mes<12; mes++) {
    print "coef-2025-" + (mes+1) + "="
    for(int dia=0; dia<31; dia++) {
        if(coef[mes][dia]!=null) {
            if(dia>0) {
                print ";"
            }
            print coef[mes][dia]
        }
    }
    println ""
}
