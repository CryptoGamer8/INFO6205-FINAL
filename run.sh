#!/bin/bash

cat './config/config.json' | python3 Python/main.py | python3 Python/app/app.py
