#
# Shell para buscar los archivos que tengan un bitrate de 320kbps.
#
# Ejemplo de uso: Ver cuanto ocupan los archivos de 320 bits
#
#      bash buscar-320.sh | du -h --files0-from -
#
#
dir="/media/discos/English"

find "$dir" -name "*.mp3" -print | while  read -r file; do
    ./ffmpeg -i "$file" 2>&1 | grep "bitrate: 3" >/dev/null
    if [ "$?" = 0 ]; then
        echo -n -e "$file\0"
    fi
done

