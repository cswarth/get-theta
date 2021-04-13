# get-theta

Build with
```
    $ ./gradlew shadowJar
```
  
Run with
```
    $ java -jar build/libs/cswtest-1.0-SNAPSHOT-all.jar -c acc_now_delinq /Users/chris/work/druid/whylabs/profiles/metrics.bin
```
  
Produces TSV like:
```
    acc_now_delinq  xAMEDAoAzJPRAQAAAACAP/////////9//////////38AAAAAAAAAAAFA[...]AAAAAAA==
```
  
option '-c' takes name of column to output.  If not supplied, all columns are produced (man, that's a lot of data!)

  
  
