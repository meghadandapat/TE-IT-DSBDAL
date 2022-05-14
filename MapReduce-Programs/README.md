1. New Java Project
2. Add External Jars -> File Sysystems -> usr -> lib -> hadoop (add all .jar files)
3. Add External Jars -> File Sysystems -> usr -> lib -> hadoop->client (add all .jar files)
4. Finish
5. Right click on src -> new -> class -> FileName(same as Project Name) -> Finish
6. Right click on project -> Export -> JAVA -> JAR File-> Browse -> Cloudera ->FileName.jar -> ok -> Finish

Terminal :
cat > /home/cloudera/Processfile1.txt (Enter inputs, ctr+z to exit )

1. hdfs dfs -mkdir /inputfolder
2. hdfs dfs -put /home/cloudera/Processfile1.txt /inputfolder1
3. hdfs dfs -cat /inputfolder1/Processfile1.txt
4. hadoop jar /home/cloudera/WordCount.jar WordCount /inputfolder1/Processfile1.txt /out1
5. hadoop fs -ls /out1
6. hadoop fs -cat /out1/part-r-00000
