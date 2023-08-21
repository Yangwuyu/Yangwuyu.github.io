#!/bin/sh
cd ..
cd ..

if [ -f "TestEntryPoint.pid" ]; then
    cat TestEntryPoint.pid | xargs -i kill -9 {}
fi

echo TestEntryPoint is Stop.
