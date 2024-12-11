package com.miravalles;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;

public class BitRate extends VisitCommand {

       @Override
    void visit(AudioFile audio) {

        if(StringUtils.isNumeric(audio.getAudioHeader().getBitRate()) &&
            Integer.valueOf(
            audio.getAudioHeader().getBitRate()) > 300)  {
            System.out.println(audio.getFile().toString() + " : " + audio.getAudioHeader().getBitRate()); 
            convert(audio);           
        }
    }


    void convert(AudioFile audio) {
        File tmp=new File("/media/discos/tmp.mp3");
        if(tmp.exists() && !tmp.delete()) {
            throw new IllegalStateException("No se ha podido borrar " + tmp);
        }
        String []args = new String []{
            "bash", "ffmpeg", "-loglevel" ,  "error" ,
            "-i" , audio.getFile().toString() , "-c:v" , "copy",   "-b:a" ,  "192k",   tmp.toString() };
        try {
            Process proceso=Runtime.getRuntime().exec(args);

            // Leer y mostrar la salida estándar
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode=proceso.waitFor();

            FileUtils.copyFile(tmp, audio.getFile(), StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException | InterruptedException e) {
            throw new IllegalStateException("Error convirtiendo " + e);
        }
        




    }

}
