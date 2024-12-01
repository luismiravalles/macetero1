package com.miravalles;


public class Contar extends AbstractCommand {

    int n=0;

    @Override
    public void run() throws Exception {        
       walk( path -> {
            n++;
            return true;
        }
       );
       System.out.println(n);
    }
}
