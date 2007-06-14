#!/bin/bash
cat $1 base_conocimiento.pl > temp.pl
swipl -s temp.pl
rm temp.pl
