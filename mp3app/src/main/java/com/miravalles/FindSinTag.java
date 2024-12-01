package com.miravalles;

import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

public class FindSinTag extends AbstractCommand {



    private void report(Path path, String album, String artist, String title, String error) {
        if(StringUtils.isBlank(album) || StringUtils.isBlank(artist) || StringUtils.isBlank(title)) {            
            System.out.println(
                subpath(path) + ";" + album + ";" + artist + ";" + title + ";" + error);
        }
    }

    private boolean visit(Path path) throws Exception {
        AudioFile mp3;
        try {
            mp3=AudioFileIO.read(path.toFile());
        } catch(InvalidAudioFrameException e) {
            report(path, "", "", "", e.getMessage());
            return true;
        }
        Tag tag=mp3.getTag();
        if(tag==null) {
            report(path, "", "", "", "No tiene TAG");
            return true;
        }
        String titulo=tag.getFirst(FieldKey.TITLE);
        String album=tag.getFirst(FieldKey.ALBUM);
        String artist=tag.getFirst(FieldKey.ARTIST);

        report(path, album, artist, titulo, "");


        return true;
    }

    @Override
    public void run() throws Exception {
       walk(this::visit);
    }
}
