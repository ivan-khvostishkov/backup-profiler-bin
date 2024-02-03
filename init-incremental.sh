## Copyright (c) 2013-2023 NoSocial.Net

### Use this file as a template and copy to your backup dir, e.g., to `../2024-08-01.hostname.incremental/`

echo 'Initializing Backup Profiler v3.0'


# 1. Copy files from previous backup for incremental profile

mkdir -p ./important/count/ ./important/size/ ./important/time/ ./unimportant/count/ ./unimportant/size/

cp -v ../2024-01-02.hostname/important/count/profile-ack.txt ./important/count/ 
cp -v ../2024-01-02.hostname/important/size/profile-ack.txt ./important/size/ 
cp -v ../2024-01-02.hostname/important/time/profile-ack.txt ./important/time/ 
cp -v ../2024-01-02.hostname/unimportant/count/profile-ack.txt ./unimportant/count/ 
cp -v ../2024-01-02.hostname/unimportant/size/profile-ack.txt ./unimportant/size/ 


