#!/bin/bash

rm -fr ./buildSrc/
echo "delete buildSrc dir..."

if cp -fr plugin-publish ./buildSrc/; then
  echo "copy success!"
  exit 0
else
  echo "copy fail!"
  exit 1
fi
