# TheScopeReport
The Scope Report is a java program that uses Jamf Pro API calls to collect and organize specific information about how pretty much everything is scoped in Jamf Pro. It can be run from any computer that can access Jamf Pro from their web browser and any user with read permission. It makes the .csv file inside the folder it was run in so it is easy to import wherever desired.

The needs it is fulfilling: 

 - The specific reporting of scoping that is not available in a Jamf Pro summary.

 - The feature request for checking what is scoped to a group without manually searching thorough the items or digging up a lengthy MySQL query. 

Currently it runs a full scope report, leaving out blank fields, for the following Jamf Pro categories: 
- Policies
- Mobile Applications
- Mobile Configuration Profiles
- Mac Applications
- Mac Configuration Profiles
- Ebooks

**NEW: Show what Apps, Ebooks, and Profiles are scoped to which Mobile Device Groups! 
      (Does not show limitations or exclusions, this can be added if requested)

**NEW: Show what Apps, Ebooks, and Profiles are scoped to which Computer Device Groups!
      (Does not show limitations or exclusions, this can be added if requested)

**NEW: Handy Dandy Progress bar! 

Note: Any Device group search takes a bit of time and a straight scope report is a lot quicker. On my test cloud instance the Group Searches take about a 1 minute each. In larger environments it could take a while. It is a steady stream of API calls that can vary in size for data returned, however the largest data stream return I have seen is 1 MB. Which should be very manageable in most environments. The ability to search only specific device group names will break up calls for larger environments if needed. Running directly on the same server as Jamf Pro would decrease network traffic and would already all the Java needed. 

----------------------------------------------------------------------------------------------------

Requirements: JDK and JRE (Written in version 1.8)

Windows/MacOS: Unzip then go to /dist/MobileDeviceTool.jar and double click.

Linux: Unzip then go to /dist/MobileDeviceTool.jar....Has not been tested in linux, however if you have the most recent versions of the JRE and JDK then it should run fine. It does require a GUI. 

-----------------------------------------------------------------------------------------------------

If the demand is there, it could easily be converted to a script. So let me know!

Future Plans: The User Groups scope search function, the ability to specify which group names to search, better error handling.

Any suggestions or ideas for functionality are welcomed!

Since the output is CSV we can open the file produced in a text editor or in Excel (recommended). It does looks much better when imported into Excel so if you do not want to manually adjust the columns size then use the method shown on this site: https://www.hesa.ac.uk/support/user-guides/import-csv

________________________________________________________________________________________________________
