package com.miravalles;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.lang3.StringUtils;


/**
 * Clase base para heredar los diferentes comandos de ella.
 */
public abstract class AbstractCommand {


    protected PrintStream writer;

    public void setWriter(PrintStream writer) {
        this.writer=writer;
    }    

    abstract public void run() throws Exception;

    protected String getDir() {
        return System.getProperty("dir");
    }

    protected String subpath(Path path) {
        return StringUtils.removeStart(path.toString(), getDir());
    }

    /**
     * Escribe en el writer y en el System.out-
     */
    protected void println(String cadena) {
        if(writer!=null) {
            writer.println(cadena);
        }
        System.out.println(cadena);
    }


    protected void walk(Predicado<Path> p) throws Exception {
        Path directorio=Paths.get(getDir());
        Files.walkFileTree(directorio, new FileVisitor<Path>() {
            public FileVisitResult postVisitDirectory(Path path, java.io.IOException arg1) throws IOException { return FileVisitResult.CONTINUE;};
            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes arg1) throws IOException  { return FileVisitResult.CONTINUE; };
            public FileVisitResult visitFile(Path path, BasicFileAttributes arg1) 
                throws IOException { 
                    try {                        
                        if(path.getFileName().toString().endsWith(".mp3")) {
                            return p.call(path) ? FileVisitResult.CONTINUE: FileVisitResult.TERMINATE;
                        } else {
                            return FileVisitResult.CONTINUE;
                        }
                    } catch(Exception e) {
                        throw new IllegalStateException(e);
                    }
                };
            public FileVisitResult visitFileFailed(Path path, java.io.IOException arg1) throws IOException { return FileVisitResult.CONTINUE;};
        } );
    }

}
