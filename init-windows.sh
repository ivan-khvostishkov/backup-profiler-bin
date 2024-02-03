## Copyright (c) 2013-2023 NoSocial.Net

### Use this file as a template and copy to your backup dir as `init.sh`

echo 'Initializing Backup Profiler v3.0'

# Create config startup files for Windows

cat >profile-start.txt <<"EOF"
C:\
EOF

cat >profile-skip.txt <<"EOF"
A:\
B:\
EOF

cat >profile-unimportant.txt <<"EOF"
C:\hiberfil.sys
C:\pagefile.sys
C:\Windows\Temp\
C:\Windows\WinSxS\
C:\Windows\Installer\
EOF
