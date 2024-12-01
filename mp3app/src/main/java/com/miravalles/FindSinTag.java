package com.miravalles;

import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

public class FindSinTag extends AbstractCommand {

    @Override
    public void run() throws Exception {
       walk( path -> {
            AudioFile mp3=AudioFileIO.read(path.toFile());
            Tag tag=mp3.getTag();
            if(tag==null) {
                System.out.println("--> " + path + ": No tiene TAG");
                return true;
            }

            String titulo=tag.getFirst(FieldKey.TITLE);
            if(StringUtils.isBlank(titulo)) {
                System.out.println("-->" + path + ": No tiene título");
                return true;
            }

            // System.out.println("---> El titulo de " + path + " es " + titulo);

            return true;
        }
       );
    }
}
