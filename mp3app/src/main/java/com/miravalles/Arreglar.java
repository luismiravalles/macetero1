package com.miravalles;

import java.nio.file.Path;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class Arreglar extends VisitCommand {

    void visit(AudioFile audio) throws Exception {
        Tag tag=audio.getTag();
        if(tag==null) {
            return;
        }        

        String artista=tag.getFirst(FieldKey.ARTIST);

        String artistaArreglado=RegExUtils.replaceFirst(artista, "^[()0-9 ]+", "");

        boolean cambiado=false;

        if(StringUtils.isBlank(artistaArreglado)) {
            artistaArreglado=
            Path.of(audio.getFile().toString()).getParent().getParent().getFileName().toString();  
        }

        if(!artista.equals(artistaArreglado)) {
            println("--> " +audio.getFile() +  " Artista:" + artista + " <-> " + artistaArreglado);
            cambiado=true;
            tag.setField(FieldKey.ARTIST, artistaArreglado);            
        }
        
        if(cambiado) {
           audio.commit();
        }

    };

}
