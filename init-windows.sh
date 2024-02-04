## Copyright (c) 2013-2023 NoSocial.Net

### Use this file as a template and copy to your backup dir as `init.sh`

echo 'Initializing Backup Profiler v3.0'

df -h /c/ | tee df.log

# Create config startup files for Windows

cat >profile-start.txt <<"EOF"
C:\
EOF

cat >profile-skip.txt <<"EOF"
C:\Dokumente und Einstellungen\
C:\ProgramData\Anwendungsdaten\
C:\Programme\
C:\Program Files\Gemeinsame Dateien\
C:\Users\Default\AppData\Local\Anwendungsdaten\
C:\Users\Default\Lokale Einstellungen\Anwendungsdaten\
C:\Users\Default\Anwendungsdaten\
C:\Users\Default\Lokale Einstellungen\
C:\Users\Default\Eigene Dateien\
C:\Users\Default User\AppData\Local\Anwendungsdaten\
C:\Users\Default User\Lokale Einstellungen\Anwendungsdaten\
C:\Users\Default User\Anwendungsdaten\
C:\Users\Default User\Lokale Einstellungen\
C:\Users\Default User\Eigene Dateien\
EOF

cat >profile-unimportant.txt <<"EOF"
C:\$Recycle.Bin\
C:\hiberfil.sys
C:\pagefile.sys
C:\Windows\Temp\
C:\Windows\WinSxS\
C:\Windows\Installer\
EOF

