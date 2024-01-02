# 1. Create config startup files

cat >profile-start.txt <<EOF
/
EOF

cat >profile-skip.txt <<EOF
/dev
/Volumes/
/System/Volumes/
/private/var/vm
/net
/home
/Network/Servers
/vm/swapfile0
EOF

cat >profile-unimportant.txt <<EOF
/System/Applications/
/System/Developer/
/System/DriverKit/
/System/iOSSupport/
/System/Library/
/Applications/
/usr/
/Library/
/private/
/opt/
EOF

# 2. Copy files from previous backup for incremental profile (optional)

#mkdir -p ./important/count/ ./important/size/ ./important/time/ ./unimportant/count/ ./unimportant/size/

#cp -v ../2024-01-02/important/count/profile-ack.txt ./important/count/ 
#cp -v ../2024-01-02/important/size/profile-ack.txt ./important/size/ 
#cp ../2024-01-02/important/time/profile-ack.txt ./important/time/ 
#cp -v ../2024-01-02/unimportant/count/profile-ack.txt ./unimportant/count/ 
#cp -v ../2024-01-02/unimportant/size/profile-ack.txt ./unimportant/size/ 

# 3. Prepare profile

#java -jar ../BackupProfiler/backupProfiler.jar prepare

# 4. Inspect profile

#java -jar ../BackupProfiler/backupProfiler.jar inspect --profile ./important/size | tee ./important/size/inspect.log
