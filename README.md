# TheScopeReport
The Scope Report is a java program that uses Jamf Pro API calls to collect and orgainize specific information about how things are scoped in a Jamf Pro instance. It can be run on any computer that can access Jamf Pro from their web browser and any user with read permission. It makes the .csv file inside the folder it was run in so it is easy to import wherever desired.

Currently it runs a full scope report, leaving out blank fields, for the following Jamf Pro categories: 
- Policies
- Mobile Applicaitons
- Mobile Configuration Profiles
- Mac Applications
- Mac Configuration Profiles
- Ebooks

**NEW: Show what Apps, Ebooks, and Profiles are scoped to which Mobile Device Groups!

**NEW: Show what Apps, Ebooks, and Profiles are scoped to which Computer Device Groups!

**NEW: Progress bar! 

Note: Any Device group search takes a bit of time and a straight scope report is a lot quicker. On my test cloud instance the Group Searches take about a 1 minute each. In larger environments it could take a while. It is a steady stream of API calls that can vary in size for data returned, however the largest I have seen is 1 MB. The ability to search only specific device group names will break up calls for larger environments if needed. 

----------------------------------------------------------------------------------------------------

Requirements: JDK and JRE (Written in version 1.8)

Windows/MacOS: Unzip then go to /dist/MobileDeviceTool.jar and double click.

Linux: Unzip then go to /dist/MobileDeviceTool.jar....Has not been tested in linux, however if you have the most recent versions of the JRE and JDK then it should run fine. It does require a GUI. 

-----------------------------------------------------------------------------------------------------

If the demand is there, it could easily be converted to a script. So let me know!

Future Plans: The User Groups scope search function, the ability to specify which group names to search.

Any suggestions or ideas for functionality are welcomed!

Since the output is CSV we can open the file produced in a text editor or in Excel (reccomended). It does looks much better when imported into Excel so if you do not want to manually adjust the columns size then use the method shown on this site: https://www.hesa.ac.uk/support/user-guides/import-csv

________________________________________________________________________________________________________
