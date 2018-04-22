#!/usr/bin/env bash
echo "java -Dserver.port=8091 -jar mercury-0.0.1-SNAPSHOT.jar >> info.log &"
rm -rf info.log
nohup java -Dserver.port=8091 -jar mercury-0.0.1-SNAPSHOT.jar >> info.log &