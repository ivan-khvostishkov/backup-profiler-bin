## Copyright (c) 2013-2023 NoSocial.Net

### Use this file as a template and copy to your backup dir as `init.sh`

echo 'Initializing Backup Profiler v3.0'

# Create config startup files for macOS

cat >profile-start.txt <<"EOF"
/
EOF

cat >profile-skip.txt <<"EOF"
/dev/
/Volumes/
/System/Volumes/
/private/var/vm/
/net/
/home/
/Network/Servers/
/vm/swapfile0
EOF

cat >profile-unimportant.txt <<"EOF"
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
