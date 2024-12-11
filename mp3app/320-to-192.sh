#
# Shell para buscar los archivos que tengan un bitrate de 320kbps.
# y bajarlo a 192
#
dir="/media/discos/2024-12"
find "$dir" -name "*.mp3" -print0 | while IFS= read -r -d '' file; do
    echo "Consultando $file ..."
    ./ffmpeg -i "$file" 2>&1 | grep "bitrate: 3" >/dev/null
    if [ "$?" = 0 ]; then
        echo "-----------------------> Convirtiendo $file ------------------------"
        du -h "$file"
        rm -f /media/discos/procesado.mp3
        docker run --rm -v /media/discos:/media/discos linuxserver/ffmpeg  -loglevel error \
            -i "$file" -c:v copy  -b:a 192k \
            /media/discos/procesado.mp3
        if [ $? -eq 0 ]; then
            echo "Moviendo  a destino..."
            mv "/media/discos/procesado.mp3" "$file"
            du -h "$file"
        fi            
    fi
done