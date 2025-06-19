#!/usr/bin/env bash
set -e

CACHE_DIR="$NETLIFY_BUILD_BASE/cache"
JAVA_URL="https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_linux-x64_bin.tar.gz"
JAVA_RELEASE="jdk-17.0.2"

current=$(java -version 2>&1 | head -n1 | cut -d'"' -f2 | cut -d'.' -f1)
required="11"

if [ ! "$(printf '%s\n' "$required" "$current" | sort -V | head -n1)" = "$required" ]; then
  echo "Java $required+ required — upgrading"

  if [ ! -d "$CACHE_DIR/$JAVA_RELEASE" ]; then
    wget -qO openjdk.tar.gz "$JAVA_URL"
    mkdir -p "$CACHE_DIR"
    tar xf openjdk.tar.gz -C "$CACHE_DIR"
  fi

  export PATH="$CACHE_DIR/$JAVA_RELEASE/bin:$PATH"
  java -version
else
  echo "Java $current is enough"
fi
