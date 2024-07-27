## Copyright (c) 2013-2023 NoSocial.Net

### Use this file as a template and copy to your backup dir, e.g., to `../2024-08-01.hostname.incremental/`

echo 'Initializing Backup Profiler v3.0 - Incremental run'


# 1. Copy files from previous backup for incremental profile

mkdir -p ./important/count/ ./important/size/ ./important/time/ ./unimportant/count/ ./unimportant/size/

previous='../2024-05-01.hostname/'

cp -v "$previous/important/count/profile-ack.txt" ./important/count/ 
cp -v "$previous/important/size/profile-ack.txt" ./important/size/ 
cp -v "$previous/important/time/profile-ack.txt" ./important/time/ 
cp -v "$previous/unimportant/count/profile-ack.txt" ./unimportant/count/ 
cp -v "$previous/unimportant/size/profile-ack.txt" ./unimportant/size/ 


