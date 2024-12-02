package com.miravalles;

import java.nio.file.Path;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.tag.Tag;

public abstract class VisitCommand extends AbstractCommand {

    @Override
    public void run() throws Exception {
        walk(this::visit);
        
    }

    private boolean visit(Path path) throws Exception {
        AudioFile audio;
        try {
            audio=AudioFileIO.read(path.toFile());
        } catch(InvalidAudioFrameException e) {            
            return true;
        }
        visit(audio);
        return true;
    }

    abstract void visit(AudioFile audio) throws Exception;

}
