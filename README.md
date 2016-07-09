# hello_spark
The classic word count example based on http://www.robertomarchetto.com Spark tutorial

## Environment setup
I use [cdh5-pseudo-distributed](https://hub.docker.com/r/chalimartines/cdh5-pseudo-distributed/) docker image, it includes all essential components such as HDFS, YARN, Oozie, Hue, Spark on YARN, etc. Visit the image's [docker hub page](https://hub.docker.com/r/chalimartines/cdh5-pseudo-distributed/) for instructions to set it up.

## Deploy and run the example
   * Set up a Docker volume `/tmp/docker` that maps to `~/tmp/docker` directory in local dev box. 
   * Build the jar, copy the jar, sample input file `src/main/resources/loremipsum.txt`, the script `src/main/scripts/hellospark` into the shared directory.
   * On HDFS, create the directory `/tmp/spark-out`, copy `loremipsum.txt` there (see put command in [file system shell manual](https://hadoop.apache.org/docs/r1.0.4/file_system_shell.html#put)). Make sure `/tmp/spark-out` is writable.
   * Run the script to submit the Spark job and wait for it to finish. Then you should see `/tmp/spark-out/wordcount-out/_SUCCESS` and `/tmp/spark-out/wordcount-out/part-00000`

