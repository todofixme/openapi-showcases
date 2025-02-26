#!/bin/sh

npx @redocly/cli build-docs ./src/main/spec/api-spec.yaml -o target/docs/api-spec.html
