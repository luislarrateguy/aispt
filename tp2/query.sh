#!/bin/bash
cat base_conocimiento.pl $1 > temp.pl
swipl -s temp.pl
rm temp.pl
