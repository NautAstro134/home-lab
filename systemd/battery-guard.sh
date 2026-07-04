#!/bin/bash
STATUS=$(cat /sys/class/power_supply/BAT1/status)
CAPACITY=$(cat /sys/class/power_supply/BAT1/capacity)
if [[ "$STATUS" == "Discharging" && "$CAPACITY" -le 5 ]]; then
    /sbin/poweroff
fi
