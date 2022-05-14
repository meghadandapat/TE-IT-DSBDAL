## Terminal 1

    [cloudera@quickstart ~]$ hbase shell

2022-05-09 23:25:23,141 INFO [main] Configuration.deprecation: hadoop.native.lib is deprecated. Instead, use io.native.lib.available
HBase Shell; enter 'help<RETURN>' for list of supported commands.
Type "exit<RETURN>" to leave the HBase Shell
Version 1.0.0-cdh5.4.2, rUnknown, Tue May 19 17:07:29 PDT 2015

    hbase(main):006:0> create 'flight','finfo', 'fsch'

0 row(s) in 0.4580 seconds

    => Hbase::Table - flight
    hbase(main):007:0> list

TABLE  
flight  
1 row(s) in 0.0230 seconds

    => ["flight"]
    hbase(main):008:0> put 'flight',1,'finfo:source','Pune'
    0 row(s) in 0.1510 seconds

    hbase(main):009:0> put 'flight',1,'finfo:dest','Mumbai'
    0 row(s) in 0.0100 seconds

    hbase(main):010:0> put 'flight',1,'finfo:year', 2009
    0 row(s) in 0.0140 seconds

      hbase(main):011:0> scan 'flight'

ROW COLUMN+CELL  
 1 column=finfo:dest, timestamp=1652165050366, value=Mumbai  
 1 column=finfo:source, timestamp=1652165036536, value=Pune  
 1 column=finfo:year, timestamp=1652165072193, value=2009  
1 row(s) in 0.0270 seconds

    hbase(main):012:0> put 'flight',1,'fsch:at','10:30AM'
    0 row(s) in 0.0180 seconds

    hbase(main):013:0> put 'flight',1,'fsch:dt','09:30AM'
    0 row(s) in 0.0090 seconds

    hbase(main):014:0> put 'flight',1,'fsch:delay_in_mins','6'
    0 row(s) in 0.0150 seconds

      hbase(main):015:0> scan 'flight'

ROW COLUMN+CELL  
 1 column=finfo:dest, timestamp=1652165050366, value=Mumbai  
 1 column=finfo:source, timestamp=1652165036536, value=Pune  
 1 column=finfo:year, timestamp=1652165072193, value=2009  
 1 column=fsch:at, timestamp=1652165121735, value=10:30AM  
 1 column=fsch:delay_in_mins, timestamp=1652165275070, value=6  
 1 column=fsch:dt, timestamp=1652165139657, value=09:30AM  
1 row(s) in 0.0290 seconds

        hbase(main):016:0> alter 'flight',NAME=>'revenue'

Updating all regions with the new schema...
0/1 regions updated.
1/1 regions updated.
Done.
0 row(s) in 2.2040 seconds

    hbase(main):017:0> put 'flight',2,'finfo:source','Delhi'
    0 row(s) in 0.0160 seconds

    hbase(main):018:0> put 'flight',2,'finfo:dest','Kolkata'
    0 row(s) in 0.0070 seconds

    hbase(main):019:0> put 'flight',2,'finfo:year', 2010
    0 row(s) in 0.0070 seconds

    hbase(main):020:0> put 'flight',2,'fsch:at','08:30AM'
    0 row(s) in 0.0120 seconds

    hbase(main):021:0> put 'flight',2,'fsch:dt','06:30AM'
    0 row(s) in 0.0090 seconds

    hbase(main):022:0> put 'flight',2,'fsch:delay_in_mins','10'
    0 row(s) in 0.0070 seconds

    hbase(main):023:0> scan 'flight'

ROW COLUMN+CELL  
 1 column=finfo:dest, timestamp=1652165050366, value=Mumbai  
 1 column=finfo:source, timestamp=1652165036536, value=Pune  
 1 column=finfo:year, timestamp=1652165072193, value=2009  
 1 column=fsch:at, timestamp=1652165121735, value=10:30AM  
 1 column=fsch:delay_in_mins, timestamp=1652165275070, value=6  
 1 column=fsch:dt, timestamp=1652165139657, value=09:30AM  
 2 column=finfo:dest, timestamp=1652165629994, value=Kolkata  
 2 column=finfo:source, timestamp=1652165615541, value=Delhi  
 2 column=finfo:year, timestamp=1652165646779, value=2010  
 2 column=fsch:at, timestamp=1652165688045, value=08:30AM  
 2 column=fsch:delay_in_mins, timestamp=1652165727388, value=10  
 2 column=fsch:dt, timestamp=1652165709288, value=06:30AM  
2 row(s) in 0.0560 seconds

    hbase(main):024:0> put 'flight',1,'revenue:rs',45000
    0 row(s) in 0.0130 seconds

    hbase(main):025:0> put 'flight',2,'revenue:rs',65000
    0 row(s) in 0.0120 seconds

        hbase(main):026:0> scan 'flight'

ROW COLUMN+CELL  
 1 column=finfo:dest, timestamp=1652165050366, value=Mumbai  
 1 column=finfo:source, timestamp=1652165036536, value=Pune  
 1 column=finfo:year, timestamp=1652165072193, value=2009  
 1 column=fsch:at, timestamp=1652165121735, value=10:30AM  
 1 column=fsch:delay_in_mins, timestamp=1652165275070, value=6  
 1 column=fsch:dt, timestamp=1652165139657, value=09:30AM  
 1 column=revenue:rs, timestamp=1652165880465, value=45000  
 2 column=finfo:dest, timestamp=1652165629994, value=Kolkata  
 2 column=finfo:source, timestamp=1652165615541, value=Delhi  
 2 column=finfo:year, timestamp=1652165646779, value=2010  
 2 column=fsch:at, timestamp=1652165688045, value=08:30AM  
 2 column=fsch:delay_in_mins, timestamp=1652165727388, value=10  
 2 column=fsch:dt, timestamp=1652165709288, value=06:30AM  
 2 column=revenue:rs, timestamp=1652165888837, value=65000

## Terminal 1

    [cloudera@quickstart ~]$ hive

Logging initialized using configuration in file:/etc/hive/conf.dist/hive-log4j.properties

    hive> CREATE external TABLE hbase_flight_new(fno int, fsource string, fdest string, fyear int, fs_at string, fsh_dt string, delay int)
    > STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
    > WITH SERDEPROPERTIES("hbase.columns.mapping"=":key,finfo:source,finfo:dest,finfo:year,fsch:at,fsch:dt,fsch:delay_in_mins")
    > TBLPROPERTIES("hbase.table.name"="flight");

OK
Time taken: 3.123 seconds
