package com.miravalles;

import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;

public class VerGenero extends VisitCommand {

    @Override
    void visit(AudioFile audio) {
        Tag tag=audio.getTag();

        String generos="";
        for(TagField field:tag.getFields(FieldKey.GENRE)) {
            generos=generos + field.toString() + ",";

        }
        
        if(StringUtils.isNotBlank(generos)) {
            System.out.println("" + audio.getFile() + " : " + generos);        
        }
    }

}
