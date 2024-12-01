package com.miravalles;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Predicate;

/**
 * Clase base para heredar los diferentes comandos de ella.
 */
public abstract class AbstractCommand {

    abstract public void run() throws Exception;



    protected void walk(Predicado<Path> p) throws Exception {
        Path directorio=Paths.get(System.getProperty("dir"));
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
