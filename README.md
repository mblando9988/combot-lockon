# Combat Lock-On Mod

A Minecraft Fabric mod that automatically locks the player's camera onto entities when damage is dealt to them.

## Features

- **Auto Lock-On**: Camera automatically locks onto the target entity when you deal damage
- **Smooth Tracking**: Smooth camera rotation that follows the target
- **Range Detection**: Lock-on deactivates if the target moves beyond 30 blocks away
- **Auto-Deactivation**: Lock-on ends when the target dies
- **Client-Side Only**: No server modifications needed

## Installation

### Requirements
- Java Development Kit 21+ (for development/building)
- Minecraft 1.21.1 with Fabric Loader
- Fabric API

### Build Instructions

1. **Install Java 21+** if not already installed:
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) or [OpenJDK](https://jdk.java.net/21/)
   - Set `JAVA_HOME` environment variable

2. **Build the mod**:
   ```bash
   cd /Users/michael/combat-lockon
   ./gradlew build
   ```

3. **Find the built JAR**:
   ```bash
   ls build/libs/combat-lockon-1.0.0.jar
   ```

4. **Install to Minecraft**:
   - Place the JAR in your `.minecraft/mods` folder
   - Make sure Fabric Loader is installed
   - Make sure Fabric API mod is installed

5. **Launch Minecraft** with Fabric and the mod should load

## Usage

### In-Game
1. Equip a weapon and engage with mobs or other players
2. Deal damage to any living entity
3. Your camera will automatically lock onto the target
4. The camera will smoothly follow the target's movements
5. Lock-on releases when:
   - The target dies
   - The target moves more than 30 blocks away
   - You hit a different entity (switches target)

## Configuration

The mod currently uses hardcoded values. To customize:

Edit `src/main/java/com/lockon/LockOnManager.java`:
- `MAX_RANGE`: Change 30.0f to adjust lock-on range in blocks
- `ROTATION_SPEED`: Change 0.15f to adjust camera rotation smoothness (0.0-1.0)

## Project Structure

```
combat-lockon/
├── src/main/
│   ├── java/com/lockon/
│   │   ├── CombatLockOn.java           # Main mod class
│   │   ├── CombatLockOnClient.java     # Client initialization
│   │   ├── LockOnManager.java          # Lock-on logic and camera rotation
│   │   └── mixin/
│   │       └── PlayerEntityMixin.java  # Damage event interceptor
│   └── resources/
│       ├── fabric.mod.json              # Mod metadata
│       └── combat-lockon.mixins.json   # Mixin configuration
├── gradle/wrapper/                      # Gradle wrapper
├── build.gradle                         # Build configuration
├── gradle.properties                    # Gradle settings
└── settings.gradle
```

## How It Works

### Attack Interception
The mod uses a **Mixin** to intercept the `PlayerEntity.attack()` method. When a player attacks an entity, the mixin passes the target to `LockOnManager`.

### Camera Rotation
The `LockOnManager` calculates the angle from the player's eye position to the target's eye position, then smoothly interpolates the camera yaw and pitch every client tick.

### Validation
Each frame, the manager checks if:
- The target still exists (not removed from world)
- The target is still alive
- The target is within 30 blocks
- If any check fails, the lock-on is released

## Technical Details

- **Type**: Client-side Fabric mod
- **Minecraft Version**: 1.21.1
- **Loader**: Fabric Loader 0.15.11+
- **Dependencies**: Fabric API
- **License**: MIT

## Potential Issues

### Conflict with Camera Mods
This mod directly modifies player yaw and pitch, so it may conflict with:
- Third-person camera mods
- Camera rotation mods
- Other view-modifying mods

### Performance
The mod updates every client tick. If you have low FPS, the rotation may appear jittery. Reduce `ROTATION_SPEED` for smoother motion.

### Rotation Limits
Camera pitch is clamped to Minecraft's limits (-90° to 90°) to prevent invalid rotations.

## Development

### Building from Source
```bash
./gradlew build
```

### Running Tests
Currently no tests are implemented. Tests can be added to `src/test/`.

### Decompiling Minecraft
To view decompiled Minecraft source:
```bash
./gradlew genSources
```

## Future Enhancements

- Keybind to toggle lock-on on/off
- Configuration file for range and rotation speed
- Visual indicator showing lock-on status
- Lock-on icon at screen center
- Lock-on sound effect
- Support for multiple targeting modes (closest, last hit, etc.)
- Integration with damage numbers mods

## Troubleshooting

### Mod doesn't load
- Check that Fabric API is installed
- Check Minecraft console for error messages
- Verify Java version is 21+

### Camera jitter or rotation issues
- Reduce `ROTATION_SPEED` value
- Check for conflicting camera mods
- Try disabling other view-related mods

### Lock-on doesn't activate
- Make sure you're hitting living entities (not blocks)
- Check that the entity isn't in Creative Mode
- Verify mixin injection succeeded (check console for mixin logs)

## License

MIT License - Feel free to use, modify, and distribute

## Contributing

Contributions are welcome! Feel free to submit issues or pull requests.

## Author

LockOn Developer
