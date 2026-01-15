## Copyright (c) 2013-2023 NoSocial.Net

### Use this file as a template and copy to your backup dir as `init.sh`

echo 'Initializing Backup Profiler v3.0'

df -h / | tee df.log
df -ih / | tee -a df.log

# Create config startup files for macOS

cat >profile-start.txt <<"EOF"
/
EOF

cat >profile-skip.txt <<"EOF"
/dev/
/proc/
/run/
/snap/
/sys/
/var/snap/
/mnt/
/media/
EOF

cat >profile-unimportant.txt <<"EOF"
/tmp/
EOF
