package com.miravalles;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {


        Logger logger = Logger.getLogger("org.jaudiotagger.tag.id3");
        logger.setLevel(Level.SEVERE);  // Establece el nivel a SEVERE (ERROR)

       try {
            run();
       } catch(Exception e) {
            e.printStackTrace();
       }
    }

    private static void run() throws Exception {
        String cmd=System.getProperty("cmd");
        if(cmd==null) {
            System.out.println("Debe especificar un comando con -Dcmd='Xxxxx'");
            return;
        }

        AbstractCommand comando=(AbstractCommand)Class
                                    .forName("com.miravalles." + cmd)
                                    .getDeclaredConstructor()
                                    .newInstance();
        if(comando==null) {

            System.out.println("NO se encuentra clase/Comando com.miravalles." + cmd);
            return;
        } 

        String out=System.getProperty("out");
        if(out!=null) {
            comando.setWriter(new PrintStream(System.getProperty("out"), Charset.forName("ISO-8859-1")));
        }


        comando.run();
    }
}