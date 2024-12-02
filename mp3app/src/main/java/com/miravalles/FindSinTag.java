package com.miravalles;

import java.nio.file.Path;


import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;


public class FindSinTag extends AbstractCommand {



    protected void procesar(AudioFile mp3, Path relativePath,  String album, String artist, String title, String error) throws Exception {

        Path path=relativePath.toAbsolutePath();

        if(StringUtils.isBlank(album) || StringUtils.isBlank(artist) || StringUtils.isBlank(title)) {    
            
            String nuevoTitulo=tituloFromPath(path);
           
            String nuevoAlbum=
                path.getParent().getFileName().toString();
            String nuevoArtista=
                path.getParent().getParent().getFileName().toString();               

            println(
                subpath(path) + "\t" + artist + "\t" + album + "\t" + title + "\t" + error +
                    "\t" + nuevoArtista + "\t" + nuevoAlbum + "\t" + nuevoTitulo);
        }
    }

    private boolean visit(Path path) throws Exception {
        AudioFile mp3;
        try {
            mp3=AudioFileIO.read(path.toFile());
        } catch(InvalidAudioFrameException e) {
            procesar(null, path, "", "", "", e.getMessage());
            return true;
        }
        Tag tag=mp3.getTag();
        if(tag==null) {
            procesar(mp3, path, "", "", "", "No tiene TAG");
            return true;
        }
        String titulo=tag.getFirst(FieldKey.TITLE);
        String album=tag.getFirst(FieldKey.ALBUM);
        String artist=tag.getFirst(FieldKey.ARTIST);

        procesar(mp3, path, album, artist, titulo, "");


        return true;
    }

    @Override
    public void run() throws Exception {
       walk(this::visit);
    }
}
