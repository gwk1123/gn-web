APPNAME=gn-web
PORT=19095
docker build -t $APPNAME .
docker run -itd --name $APPNAME -p $PORT:$PORT $APPNAME
