# DSBDAL
New Project Project
Add External Jars -> File Sysystems -> usr -> lib -> hadoop (add all)
Add External Jars -> File Sysystems -> usr -> lib -> hadoop->client (add all)
Finish
Right click on src->new->class->FileName(same as Project Name) -> Finish
Right click on project -> JAVA -> JAR File->Browse->Cloudera ->FileName.jar -> ok -> Finish

Terminal  :
cat > /home/cloudera/Processfile1.txt
//Enter inputs
ctr+z to exit
1. hdfs dfs -mkdir /inputfolder
2. hdfs dfs -put /home/cloudera/Processfile1.txt /inputfolder1
hdfs dfs -cat /inputfolder1/Processfile1.txt
3. hadoop jar /home/cloudera/WordCount.jar WordCount /inputfolder1/Processfile1.txt /out1
4. hadoop fs -ls /out1
5. hadoop fs -cat /out1/part-r-00000