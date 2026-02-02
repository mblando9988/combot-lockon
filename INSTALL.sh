#!/bin/bash

# Combat Lock-On Mod Installation Script
# This script automatically installs the mod to your Modrinth Minecraft profile

set -e

echo "🎮 Combat Lock-On Mod - Automatic Installation"
echo "================================================"
echo ""

# Paths
MOD_FOLDER="/Users/michael/Library/Application Support/ModrinthApp/profiles/Minecraft/mods"
MOD_JAR="build/libs/combat-lockon-1.0.0.jar"
MOD_SOURCES="build/libs/combat-lockon-1.0.0-sources.jar"

# Check if mod JAR exists
if [ ! -f "$MOD_JAR" ]; then
    echo "❌ Error: Mod JAR not found at $MOD_JAR"
    echo ""
    echo "Did you run: ./gradlew build"
    echo ""
    exit 1
fi

# Create mods folder if it doesn't exist
mkdir -p "$MOD_FOLDER"

# Copy the mod
echo "📦 Installing mod to Modrinth..."
cp "$MOD_JAR" "$MOD_FOLDER/"
echo "   ✅ Installed: $MOD_JAR"

# Copy sources if available
if [ -f "$MOD_SOURCES" ]; then
    cp "$MOD_SOURCES" "$MOD_FOLDER/"
    echo "   ✅ Installed sources: $MOD_SOURCES"
fi

echo ""
echo "✅ Installation Complete!"
echo ""
echo "📋 Next Steps:"
echo "   1. Make sure Fabric API is installed (download from modrinth.com)"
echo "   2. Launch Minecraft 1.21.1 with Fabric in Modrinth"
echo "   3. Go to Settings → Mods → check that combat-lockon is loaded"
echo "   4. Test: Hit a mob with a sword"
echo "   5. Your camera should lock onto the mob!"
echo ""
echo "📁 Installed to: $MOD_FOLDER"
ls -lah "$MOD_FOLDER"
echo ""
echo "💡 Tip: You can customize the mod by editing:"
echo "   src/main/java/com/lockon/LockOnManager.java"
echo "   Then run: ./gradlew build && ./INSTALL.sh"
echo ""
