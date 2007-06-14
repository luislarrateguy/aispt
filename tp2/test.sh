#!/bin/bash
cat $1 base_conocimiento.pl > temp.pl
cat test_$1 | swipl -s temp.pl
rm temp.pl
