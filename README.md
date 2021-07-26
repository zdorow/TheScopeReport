There is a more frequently updated project that can be found here: https://github.com/BIG-RAT/Object-Info

# TheScopeReport![logo](Resources/Scope.png)

The Scope Report is a Java program that uses Jamf Pro API calls to collect and organize specific information about how everything is scoped in your Jamf Pro environment. It can be run from any computer that can access Jamf Pro from their web browser and any user with  at least read permission. It makes the .csv file inside the folder it was run in so it is easy to import wherever desired.

The need it is fulfilling: 

 - The specific reporting of scoping that is not available in a Jamf Pro summary.

 - The feature request for checking what is scoped to a device or computer group without painstakingly searching through the items or digging up a lengthy MySQL query. 

Currently it runs a full scope report, leaving out blank fields, for the following Jamf Pro categories: 
- Policies
- Mobile Applications
- Mobile Configuration Profiles
- Mac Applications
- Mac Configuration Profiles
- Ebooks
- Show what is scoped to Mobile Device Groups (Does not show limitations or exclusions)
- Show what is scoped to Computer Device Groups (Does not show limitations or exclusions)

Features: Handy Dandy Progress bar!

***NEW: No Java needed if you use the Mac App***

***NEW:*** ERROR HANDLING! It will now tell you when you have the URL or the username/password incorrect. If you see the "Please file an issue error" please feel free to file an issue. Including the search you were performing and any data for recreation would be helpful in figuring out a fix. 

***NEW:*** Show what Apps, Ebooks, and Mobile Profiles are scoped to which User Groups
(Does not show LDAP users, limitations or exclusions, this can be added if requested. Does not work for MacOS Profiles and Policies due to a lack of an API endpoint, details in the warning.)
      
***Warning!!*** The API endpoint for MacOS (computer) policies and profiles does not report user groups limitatons and exclusions properly. The only search that works for user's scoped searches is for MacOS profiles and for Mac Apps, it does not work for users scoped to policies and only pulls one user group for profiles. A product issue has been filed and if the endpoint is fixed this program will be updated. As a side note all iOS (mobile) searches work great!

**Note:** Any Device group search takes a bit of time and a straight scope report is a lot quicker. On my test cloud instance, the Group Searches take about a 1 minute each. In larger environments it could take a while. It is a steady stream of (non-concurrent) API calls that can vary in size for data returned, however the largest data stream return I have seen is 1 MB. Which should be very manageable in most environments. The ability to search only specific device group names would break up calls for larger environments if needed. So let me know! 

Running directly on the same server as Jamf Pro would decrease network traffic and would already have all the Java needed. 

----------------------------------------------------------------------------------------------------

Requirements: JDK and JRE (Written in version 1.8) Preliminary testing in Java 9 showed no issues.

***NEW: No Java needed if you use the Mac APP***

***To Launch:***

-Windows: Download Windows version and run the Start_Windows.bat or go to /dist/MobileDeviceTool.jar and double click.

[Windows Download](TheScopeReport_Windows.zip)

-MacOS: Download Mac version. Unzip and double click the MacOS_Launcher or go to /dist/MobileDeviceTool.jar and double click.

[MacOS Download](TheScopeReport_MacOS.zip)

-Dont want to download Java? Use the App!

[MacOS App Download](TheScopeReport_MacOS_APP.zip)

-Linux: Download Linux version. Unzip, then use java -jar to open /dist/MobileDeviceTool.jar 

[Linux Download](TheScopeReport_Linux.zip)

EXAMPLE: java -jar '/home/jamfuser/TheScopeReport/dist/TheScopeReport.jar'....Has not been tested much in linux, however if you have the most recent versions of the JRE and JDK then it should run fine. It does not launch correctly if you double click (without a jar launcher) on it. It needs to be run using java -jar from terminal. It does require a GUI. 

The different versions are for ease of launching outside of using the standalone app. They are all the same if you go to /dist and use java -jar or a Jar Launcher to run the .jar

-----------------------------------------------------------------------------------------------------

If the demand is there this could easily be converted to a gui-less app. So, let me know!

Any suggestions or ideas for functionality are welcomed!

Since the output is .CSV we can open the file produced in a text editor or in Excel (recommended). It does look better in Excel. 
It does not size the columns unless imported into Excel. If you do not want to manually adjust the columns size then one option is to use the method shown on this site: [Import .csv guide](https://www.hesa.ac.uk/support/user-guides/import-csv)

[MIT license](https://github.com/zdorow/TheScopeReport/blob/master/Resources/LICENSE.txt)

________________________________________________________________________________________________________
