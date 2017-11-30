# TheScopeReport
This is a java program that uses Jamf Pro API calls to collect information about device groups. It can be run on any computer that can access Jamf Pro from their web browser. It makes the text file inside the folder it was run in. 

Currently it runs a full scope report, leaving out blank fields, for the following Jamf Pro categories: 
Policies,
Mobile Applicaitons,
Mobile Configuration Profiles,
Mac Applications,
Mac Configuration Profiles,
Ebooks

Requirements: JDK and JRE (Written in version 1.8)

Windows/MacOS: Unzip then go to /dist/MobileDeviceTool.jar and double click.

Linux: Has not been tested in linux, however if you have the most recent versions of the JRE and JDK then it should run fine. It does require a GUI. If the demand is there, it could easily be converted to a script.


Future Plans for v1.2: To be able to run a report that shows it organized by groups. Also, formatting the .csv output better

Any suggestions or ideas for functionality are welcomed!

Anybody who can help break out the program into functions that would be helpful. I started one way and now it is hard to go back, however it is a pain to make changes. Functions would make this easier.
