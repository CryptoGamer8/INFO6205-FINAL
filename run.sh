#!/bin/bash

cat './config/config.json' | python3 Python/script.py | python3 app_python/app/app.py
# echo 'hello'