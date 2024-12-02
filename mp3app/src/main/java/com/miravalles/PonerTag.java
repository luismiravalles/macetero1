package com.miravalles;

import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

/**
 * Pone Tag a los que no lo tengan...
 * 
 */
public class PonerTag extends FindSinTag {

    /**
     * Llega aquí todo lo que encuentre, tenga o no tenga fichero, o tenga titulo o no...
     */
    @Override
    protected void procesar(AudioFile mp3, Path relativePath, final String album, final String artist, final String title, String error) throws Exception {

        Path path=relativePath.toAbsolutePath();

        if(mp3==null) {
            return;
        }
        
        String nuevoTitulo=tituloFromPath(path);
            
        String nuevoAlbum=
                path.getParent().getFileName().toString();
        String nuevoArtista=
                path.getParent().getParent().getFileName().toString();    
                
                
        // No parece que me funcione bien lo de getTagOrCreateAndSe... porque me los está
        // modificando todos...                        

        Tag tag=mp3.getTag();
        if(tag==null) {
            tag=mp3.getTagOrCreateAndSetDefault();
        }

        boolean changed=false;

        if(StringUtils.isBlank(tag.getFirst(FieldKey.TITLE))) {
            tag.setField(FieldKey.TITLE, nuevoTitulo);
            changed=true;
        }

        if(StringUtils.isBlank(tag.getFirst(FieldKey.ARTIST))) {
            tag.setField(FieldKey.ARTIST, nuevoArtista);
            changed=true;
        }

        if(StringUtils.isBlank(tag.getFirst(FieldKey.ALBUM))) {
            tag.setField(FieldKey.ALBUM, nuevoAlbum);
            changed=true;
        }

        if(changed) {
            mp3.commit();
            println(
                subpath(path) + "\t" + artist + "\t" + album + "\t" + title + "\t" + error +
                    "\t" + nuevoArtista + "\t" + nuevoAlbum + "\t" + nuevoTitulo);
        }
        
    }
}
