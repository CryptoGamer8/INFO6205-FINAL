#!/bin/bash

cat './config/config.json' | python3 Model/script.py | python3 Visualization/app/app.py
# echo 'hello'