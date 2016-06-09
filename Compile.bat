del bin\*.java
del bin\*.txt
copy src\*.java .\bin
copy src\*.txt .\bin
chdir bin
javac Krislet.java
chdir ..
