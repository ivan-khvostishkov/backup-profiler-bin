# Backup Profiler

A Java application for analyzing and reviewing disk storage to identify what files and directories are important for cleanup and space management.

## What it does

Backup Profiler helps you understand and clean up your storage by:

- **Traversing file systems** and measuring storage consumption of each directory/file
- **Generating storage profiles** that show which paths consume the most space
- **Providing an interactive GUI** to explore storage usage in a flat, size-sorted list
- **Tracking acknowledgment** of reviewed paths to focus cleanup efforts on unreviewed areas

## Main Components

### Prepare Mode
```bash
java -jar backupProfiler.jar prepare
```
- Scans file system based on include/exclude lists
- Generates storage profiles in `important/` and `unimportant/` directories
- Creates size and count profiles for analysis

### Inspect Mode  
```bash
java -jar backupProfiler.jar inspect [--profile <dir>] [--incomplete] [--limit <n>]
```
- Interactive GUI for exploring storage profiles in flat list format
- Navigate by diving into specific path prefixes
- Mark paths as acknowledged/reviewed for cleanup progress tracking
- Search functionality
- Export graph data

#### GUI Controls
- **Enter** - Dive into selected path prefix
- **Backspace** - Go back to parent prefix
- **t** - Toggle acknowledgment of selected path(s)
- **c** - Copy selected path to clipboard
- **s** - Search for paths containing text
- **n** - Jump to next unreviewed path
- **d** - Export graph data to dump.gexf file

## Key Features

- **File System Traversal**: Respects include/exclude lists from text files
- **Storage Analysis**: Measures and profiles disk space consumption
- **Interactive Navigation**: Drill down into large storage consumers
- **Progress Tracking**: Mark reviewed areas to focus cleanup efforts on remaining storage
- **Multiple Views**: Sort by size, count, or storage consumption

## Configuration Files

- `profile-start.txt` - Starting paths for storage analysis
- `profile-skip.txt` - Paths to exclude from analysis  
- `profile-unimportant.txt` - Paths marked as low priority for cleanup
- `profile-ack.txt` - Acknowledged/reviewed paths during cleanup process

## Use Case

A unique storage analysis tool for reviewing and cleaning up disk space. Unlike traditional hierarchical disk cleaners, it presents storage as a flat list of paths sorted by size in decreasing order, with files and folders mixed together.

### Innovative Approach Benefits

- **Size-first navigation**: Root directories appear at top, followed by largest consumers regardless of hierarchy
- **Progress tracking**: Shows percentage of already reviewed storage to focus cleanup efforts
- **Deep diving**: Press Enter on any path to explore that specific prefix in detail
- **Efficient workflow**: Review largest space consumers first for maximum cleanup impact
- **Non-hierarchical view**: Eliminates need to navigate complex folder structures
- **Mixed presentation**: Files and folders sorted together by actual storage impact

This approach is more efficient than traditional tree-view disk cleaners because it prioritizes storage impact over file system structure, allowing you to quickly identify and clean the biggest space consumers.