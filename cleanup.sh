## Linux

## macOS

## Windows

# C:\Windows\WinSxS\
# C:\Windows\servicing\Sessions\
# See: https://learn.microsoft.com/en-us/previous-versions/windows/it-pro/windows-8.1-and-8/dn251565(v=win.10)?redirectedfrom=MSDN
#Dism.exe /Online /Cleanup-Image /AnalyzeComponentStore
#Dism.exe /online /Cleanup-Image /StartComponentCleanup

# C:\Windows\Installer\
# See: https://superuser.com/questions/23479/is-it-safe-to-delete-from-c-windows-installer
# "Installer"->properties->advanced->check "Compress contents to save disk space."->"OK"->"OK" again.

# C:\Windows\System32\DriverStore\FileRepository\
# See: https://www.thewindowsclub.com/how-to-clean-driverstore-folder-in-windows
#pnputil /enum-drivers
#pnputil /delete-driver <driver_name> /uninstall

# C:\Windows\System32\winevt\Logs\
# tasklist /svc /fi "imagename eq svchost.exe"

# Adobe Cleaner Tool
# https://helpx.adobe.com/creative-cloud/apps/troubleshoot/diagnostics-repair-tools/run-creative-cloud-cleaner-tool.html

# C:\Users\Lenovo\AppData\Local\Microsoft\Edge\User Data\Default\Cache\
# Edge Settings → Privacy, search, and services → Clear browsing data → Choose "Cached images and files" and clear.

# C:\Users\Lenovo\AppData\Local\Microsoft\Terminal Server Client\Cache\
# Remove-Item "$env:LOCALAPPDATA\Microsoft\Terminal Server Client\Cache\*" -Force

# Remove unused leftovers in
# C:\Users\Lenovo\AppData\Local\JetBrains\

# https://support.zoom.com/hc/en/article?id=zm_kb&sysparm_article=KB0058833

# edge://settings/privacy/clearBrowsingData/clearOnClose


