#!/bin/bash
cat base_conocimiento.pl $1 > temp.pl
cat test_$1 | swipl -s temp.pl
rm temp.pl
