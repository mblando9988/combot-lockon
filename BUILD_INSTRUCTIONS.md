# How to Build the Minecraft Mod

The mod source code is 100% complete. You just need to build it using Java 21+.

## Option 1: Use an Online Build Service (Easiest - 5 minutes)

### GitHub Codespaces (Recommended)
1. **Go to your GitHub**: https://github.com/new/codespaces
2. **Upload this folder** or create a new repo
3. **Open Codespaces** (Click "Create codespace on main")
4. **In the terminal**, run:
   ```bash
   cd combat-lockon
   ./gradlew build
   ```
5. **Find the JAR**: Look in `build/libs/combat-lockon-1.0.0.jar`
6. **Download it** to your computer
7. **Copy to Modrinth mods folder**:
   ```bash
   cp combat-lockon-1.0.0.jar ~/Library/Application\ Support/ModrinthApp/profiles/Minecraft/mods/
   ```

### Replit (Alternative Online Service)
1. Go to https://replit.com
2. Create new Replit
3. Import from GitHub or upload the folder
4. Install Java 21: `sudo apt update && sudo apt install openjdk-21-jdk`
5. Run: `./gradlew build`
6. Download the JAR from `build/libs/`

## Option 2: Install Java 21 Locally (10 minutes)

### macOS (Easiest)
```bash
# Install Homebrew (if not already installed)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Java 21
brew install openjdk@21

# Set it as default
sudo ln -sfn /usr/local/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk

# Verify
java -version
```

### Or Download from Oracle
1. Go to https://www.oracle.com/java/technologies/downloads/#java21
2. Download macOS x64 DMG
3. Install
4. Run: `./gradlew build`

## Option 3: Let Me Build It for You

If you want, I can set up a GitHub Actions workflow to build it automatically. Just:
1. Create a GitHub repo
2. Push this folder
3. GitHub will build it automatically and create releases

## Once You Have the JAR

### Install to Modrinth
```bash
cp build/libs/combat-lockon-1.0.0.jar ~/Library/Application\ Support/ModrinthApp/profiles/Minecraft/mods/
```

### Make Sure You Have:
- ✅ Fabric Loader (should be set up in Modrinth)
- ✅ Fabric API (download from modrinth.com)
- ✅ combat-lockon-1.0.0.jar (the mod we built)

### Launch and Test
1. Open Modrinth Launcher
2. Select Minecraft 1.21.1 with Fabric
3. Create or load a world
4. Find a mob and hit it with a sword
5. Camera should lock onto the mob!

## Troubleshooting

**"Command not found: java"**
- You need to install Java 21+
- Use Option 2 above

**"Cannot find gradlew"**
- Make sure you're in the `combat-lockon` folder
- Run: `cd /Users/michael/combat-lockon && ./gradlew build`

**"Build fails with compilation error"**
- Make sure you're using Java 21+
- Run: `java -version` (should show 21 or higher)

**"JAR file is too small"**
- Build might not have completed
- Check that `./gradlew build` finished without errors

## What the Mod Does

Once installed:
1. Hit any mob/player with a weapon
2. Your camera automatically locks onto them
3. Camera tracks their movement
4. Lock releases when they die or move >30 blocks away

## Questions?

All the mod code is here:
- Main logic: `src/main/java/com/lockon/LockOnManager.java`
- Event handler: `src/main/java/com/lockon/mixin/PlayerEntityMixin.java`
- Configuration: `src/main/resources/fabric.mod.json`

Good luck! 🎮
