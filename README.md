# logmon
Monitor logfiles each hour

Program limitation is its monitoring only the folder where it is launched.

Running instructions. Project is mavenized:

run "mvn clean package" to get packaged jar.

copy the jar in the same folder with the files you want to monitor and run:
"java -cp logmonitor-0.0.1-SNAPSHOT.jar com.pcr.test.logmonitor.App"

program is configurable via hosts.properties file where you can specify the hosts you are looking for.

