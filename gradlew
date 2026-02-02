#!/bin/sh

GRADLE_VERSION="8.6"
GRADLE_HOME="${GRADLE_USER_HOME:-$HOME/.gradle}"
GRADLE_DIST="$GRADLE_HOME/wrapper/dists"
GRADLE_DIR="$GRADLE_DIST/gradle-${GRADLE_VERSION}"

if [ ! -d "$GRADLE_DIR" ]; then
    echo "Downloading Gradle 8.6..."
    mkdir -p "$GRADLE_DIST"
    cd "$GRADLE_DIST" || exit 1
    curl -sSL "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" -o gradle-${GRADLE_VERSION}-bin.zip
    unzip -q gradle-${GRADLE_VERSION}-bin.zip
    rm gradle-${GRADLE_VERSION}-bin.zip
    cd - || exit 1
fi

exec "$GRADLE_DIR/bin/gradle" "$@"
