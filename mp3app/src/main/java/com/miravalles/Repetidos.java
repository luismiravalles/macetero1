package com.miravalles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jaudiotagger.audio.AudioFile;

public class Repetidos extends VisitCommand {

    Map<String , List<AudioFile>> audios=new HashMap<>() ;


    @Override
    void visit(AudioFile audio) throws Exception {
        
        String nombre=audio.getFile().getName();
        List<AudioFile> lista=audios.get(nombre);
        if(lista==null) {
            lista=new ArrayList<>();
            audios.put(nombre, lista);
        }
        lista.add(audio);

    }

    public void end() {
        // MOstrar los repetidos:
        audios.forEach( (nombre, lista) -> {
            if(lista.size()>1) {
                System.out.println("==============================");
                System.out.println("" + nombre +  " : " + lista.size());
                lista.stream().map(AudioFile::getFile).forEach(System.out::println);
            }
        });

        long  numRepetidos=audios.values().stream().filter(lista -> lista.size() > 1).count();
        
        System.out.println("==========================================================");
        System.out.println(" Total repetidos: " + numRepetidos + " de " + audios.size());
    }


    
}
