#!/usr/bin/env bash
set -e

JAVA_RELEASE=jdk-17.0.8
JAVA_URL="https://download.java.net/java/GA/jdk17/9/GPL/openjdk-17_linux-x64_bin.tar.gz"
mkdir -p "$NETLIFY_CACHE_DIR/$JAVA_RELEASE"
curl -L "$JAVA_URL" | tar -xz -C "$NETLIFY_CACHE_DIR"
export JAVA_HOME="$NETLIFY_CACHE_DIR/$JAVA_RELEASE"
export PATH="$JAVA_HOME/bin:$PATH"
java -version
