## Copyright (c) 2013-2023 NoSocial.Net

### Use this file as a template and copy to your backup dir, e.g., to `../2024-08-01.hostname/`

echo 'Running Backup Profiler v3.0'


# 1. Setting up version control

git init

cat >.gitignore <<"EOF"
profile.log
profile.log.gz
EOF


# 2. Prepare profile (run as root if needed)

#java -Dfile.encoding=UTF-8 -jar ../backup-profiler-bin/backupProfiler.jar prepare


# 3. Change permissions to backup user (optional)
#chown -R backup:backup ./important ./unimportant 
#setfacl -m u:backup:r /home/user
#getfacl /home/user


# 4. Inspect profile (run as backup user if needed)

#sudo su - backup
#set +o history

backup_profile="./important/size/"
#java -Dfile.encoding=UTF-8 -jar ../backup-profiler-bin/backupProfiler.jar inspect --profile "$backup_profile" | tee -a "${backup_profile}"/inspect.log

#cat important/size/inspect.log unimportant/size/inspect.log df.log

# 5. Visualize in Gephi

# Hint: after opening the graph, toggle 'Show Node Labels', then run 'Label Adjust' and 'Expansion' layouts 
#gephi dump.gexf
#rm dump.gexf


# 6. Update profile

#cp ./important/size/profile-ack.txt ./important/count/profile-ack.txt
#cp ./unimportant/size/profile-ack.txt ./unimportant/count/profile-ack.txt

#vim -o ./important/size/inspect.log ./important/size/profile-ack.txt ./profile-unimportant.txt
#vim -o ./important/size/profile-ack.txt ./init.sh

#vim -o ./.../.../inspect.log ./.../.../profile-ack.txt
#vim -o ./.../.../profile-ack.txt ./init.sh


# 7. Compress / unconmpress profile (when done with inspecting / need to re-inspect)

#gzip ./*/*/profile.log
#gunzip ./*/*/profile.log.gz


# 8. Check for version changes

#git add .
#git status
