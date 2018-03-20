package thescopereport;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.*;
/**
 *
 * @author Zach Dorow
 */
public class TheScopeReport extends Application {
        public static void main(String[] args)
    {
        launch(args);
    }

//Radio button naming   
private RadioButton ComputerGroupsButton = new RadioButton("Computer Groups");
private RadioButton MobileGroupsButton = new RadioButton("Mobile Groups");
private RadioButton UserGroupsButton = new RadioButton("User Groups");
private RadioButton MobileProfilesCategoryButton = new RadioButton("Mobile Profiles");
private RadioButton MacProfilesCategoryButton = new RadioButton("Mac Profiles");
private RadioButton MobileAppsCategoryButton = new RadioButton("Mobile Applications");
private RadioButton MacAppsCategoryButton = new RadioButton("Mac Applications");
private RadioButton PoliciesCategoryButton = new RadioButton("Policies");
private RadioButton EbooksCategoryButton = new RadioButton("Ebooks");

private ApiCalls api;
private ApiCallsException apiEx;
private String Report;

    @Override
    public void start(Stage primaryStage) throws ApiCallsException {
        
//Radio button configurations
       ToggleGroup DeviceGroup = new ToggleGroup();
 //      ToggleGroup CategoryGroup = new ToggleGroup();
       DeviceGroup.getToggles().addAll(getComputerGroupsButton(), getMobileGroupsButton(), getUserGroupsButton(), getMobileProfilesCategoryButton(), getMobileAppsCategoryButton(), getMacProfilesCategoryButton(), getMacAppsCategoryButton(), getPoliciesCategoryButton(), getEbooksCategoryButton());  
//       CategoryGroup.getToggles().addAll(ProfilesCategoryButton, AppsCategoryButton, PoliciesCategoryButton, EbooksCategoryButton);
       
//Selections for testing
        getComputerGroupsButton().setSelected(false);
        getMobileGroupsButton().setSelected(false);
        getUserGroupsButton().setSelected(false);
        getMobileProfilesCategoryButton().setSelected(true);
        getMacProfilesCategoryButton().setSelected(false);
        getMobileAppsCategoryButton().setSelected(false);
        getMacAppsCategoryButton().setSelected(false);
        getPoliciesCategoryButton().setSelected(false);
        getEbooksCategoryButton().setSelected(false);
//Labels for text fields
        Font font = new Font("Dialog", 18);
        Font font1 = new Font("Dialog Bold", 14);
                
        Label NamesLabel = new Label("Organize scope report by:");
        NamesLabel.setFont(font);
        NamesLabel.setLineSpacing(20);
        GridPane.setHalignment(NamesLabel, HPos.CENTER);
        
        Label JamfProLabel = new Label("Jamf Pro URL:");
        JamfProLabel.setFont(font);
        JamfProLabel.setLineSpacing(20);
        GridPane.setHalignment(JamfProLabel, HPos.CENTER);
        
        Label JamfProUsernameLabel = new Label("Jamf Pro Username:");
        JamfProUsernameLabel.setFont(font);
        JamfProUsernameLabel.setLineSpacing(20);
        GridPane.setHalignment(JamfProUsernameLabel, HPos.CENTER);
        
        Label JamfProPasswordLabel = new Label("Jamf Pro Password:");
        JamfProPasswordLabel.setFont(font);
        JamfProPasswordLabel.setLineSpacing(20);
        GridPane.setHalignment(JamfProPasswordLabel, HPos.CENTER);
        
        Label OutputMessage = new Label("Please include https://");
        OutputMessage.setFont(font1);
        OutputMessage.setTextFill(Color.rgb(50, 60, 125));
                
        //Text Fields for processing input       
        TextField JamfProURLInput = new TextField();
        JamfProURLInput.setPromptText("");
        JamfProURLInput.setFont(font);
        GridPane.setHalignment(JamfProURLInput, HPos.CENTER);
        
        TextField JamfProUsernameInput = new TextField();
        JamfProUsernameInput.setPromptText("Jamf Pro Username ");
        JamfProUsernameInput.setFont(font);
        GridPane.setHalignment(JamfProUsernameInput, HPos.CENTER);
        
        PasswordField JamfProPasswordInput = new PasswordField();
        JamfProPasswordInput.setPromptText("Jamf Pro Password ");
        JamfProPasswordInput.setFont(font);
        GridPane.setHalignment(JamfProPasswordInput, HPos.CENTER);        
        
/*      TextField DeviceNamesInput = new TextField();
        DeviceNamesInput.setPromptText("Add device names followed by a comma (optional)");
        DeviceNamesInput.setFont(font);
        GridPane.setHalignment(DeviceNamesInput, HPos.CENTER);
*/        
        Button btn = new Button();
        btn.setText("Export Scope Report to .csv file");
        btn.setAlignment(Pos.BOTTOM_CENTER);    
      
//Vbox for input group
        VBox Input = new VBox(JamfProLabel, JamfProURLInput, JamfProUsernameLabel,
        JamfProUsernameInput, JamfProPasswordLabel, JamfProPasswordInput, NamesLabel);
        Input.setAlignment(Pos.TOP_CENTER);
        Input.setPadding(new Insets(10, 10, 10, 10));
        Input.setSpacing(5);
        
//Radial Buttons        ComputerGroupsButton, MobileGroupsButton, to be added soon
        HBox DeviceGroupOptions = new HBox(getUserGroupsButton(), getMobileGroupsButton(), getComputerGroupsButton(), getPoliciesCategoryButton());
        DeviceGroupOptions.setAlignment(Pos.CENTER);
        DeviceGroupOptions.setPadding(new Insets(10, 10, 10, 10));
        DeviceGroupOptions.setSpacing(18);
        getUserGroupsButton().setDisable(false);
        getMobileGroupsButton().setDisable(false);
        getComputerGroupsButton().setDisable(false);
        
        HBox CategoryOptions = new HBox(getEbooksCategoryButton(), getMobileProfilesCategoryButton(), getMobileAppsCategoryButton(), getMacProfilesCategoryButton(), getMacAppsCategoryButton());
        CategoryOptions.setAlignment(Pos.CENTER);
        CategoryOptions.setPadding(new Insets(10, 10, 10, 45));
        CategoryOptions.setSpacing(10);
        
//Bottom Button        
        HBox Button = new HBox(btn);
        Button.setAlignment(Pos.CENTER);
        Button.setPadding(new Insets(10, 10, 10, 10));
        Button.setSpacing(10);
            
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(Input, DeviceGroupOptions, CategoryOptions, 
        Button, OutputMessage);
                
        Scene scene = new Scene(root, 600, 425);
        
        primaryStage.getIcons().add(new Image("file:JamfLogo.png"));
        primaryStage.setTitle("The Device Groups Tool");
        primaryStage.setScene(scene);
        primaryStage.show();



    btn.setOnAction((ActionEvent event) -> {
                    
        DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        ProgressForm pForm = new ProgressForm();
        Task<Void> mainTask = new Task<Void>(){
               
               @Override
               public Void call() throws InterruptedException, JSONException {
                   try {
                       String url = JamfProURLInput.getText();
                       String username = JamfProUsernameInput.getText();
                       String password = JamfProPasswordInput.getText();
                       //String devices = "mobiledevicegroups";
                                              
                        setApi(new ApiCalls(url, username, password, ApiCalls.FORMAT.JSON, ApiCalls.FORMAT.JSON));
                       
                       if (getComputerGroupsButton().isSelected()){
                           try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "ComputerGroupsScopeReport.csv"))) {
                               setReport("ComputerGroups.csv");
                               JSONObject jsonPolicy = new JSONObject(getApi().get("computergroups"));
                               JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("computer_groups");
                               writer.write("Total Computer Groups are:" + jsonPolicyArray.length());
                               writer.newLine();
                               for(int i=0; i<jsonPolicyArray.length(); i++){
                                   JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                                   
                                   int id = dataObj.getInt("id");
                                   String name = dataObj.getString("name");
                                   String[] ComputerGroupsArray = name.split("name");
                                   for (String ComputerGroupsArray1 : ComputerGroupsArray) {
                                       writer.write("Group *" + ComputerGroupsArray1 + "*is in Scope for: ");
                                       writer.newLine();
                                       //Getting all the applcations
                                       JSONObject jsonMobileApps = new JSONObject(getApi().get("macapplications"));
                                       JSONArray jsonAppArray = jsonMobileApps.getJSONArray("mac_applications");
                                       
                                       writer.write("Applications:,");
                                       
                                       //Getting the scope of all the applications
                                       for(int count=0; count<jsonAppArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonAppArray.get(count);
                                           int DappId = dataObj2.getInt("id");
                                           String AppName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("macapplications/id/" + DappId + "/subset/scope"));
                                           
                                           //Checking the mobile device group scope
                                           JSONObject jsonGroupAPP = jsonAPPScope.getJSONObject("mac_application");
                                           JSONObject jsonAppScope = jsonGroupAPP.getJSONObject("scope");
                                           JSONArray MobileGroupScopeARRAY = jsonAppScope.getJSONArray("computer_groups");
                                           
                                           String MobileGroupScope = MobileGroupScopeARRAY.toString();
                                           // Comparing ID of the mobile device group
                                           
                                           if (!"[]".equals(MobileGroupScope)){
                                               
                                               for(int c=0; c < MobileGroupScopeARRAY.length(); c++){
                                                   JSONObject MobileGroupScopeObject = (JSONObject) MobileGroupScopeARRAY.get(c);
                                                   int ScopeDgroupId = MobileGroupScopeObject.getInt("id");
                                                   String MobileGroupSLIM = MobileGroupScope.replace("[", "").replace("]", "");
                                                   String[] MobileGroupARRAY = MobileGroupSLIM.split("name:");
                                                   for (String MobileGroupARRAY1 : MobileGroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           writer.write(AppName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                       //Getting all the Ebooks
                                       JSONObject jsonMobileEbooks = new JSONObject(getApi().get("ebooks"));
                                       JSONArray jsonEbooksArray = jsonMobileEbooks.getJSONArray("ebooks");
                                       writer.newLine();
                                       writer.write("Ebooks:,");
                                       
                                       //Getting the scope of all the ebooks
                                       for(int count=0; count<jsonEbooksArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonEbooksArray.get(count);
                                           int DebookId = dataObj2.getInt("id");
                                           String EbookName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("ebooks/id/" + DebookId + "/subset/scope"));
                                           
                                           //Checking the mobile device group scope
                                           JSONObject jsonGroup = jsonAPPScope.getJSONObject("ebook");
                                           JSONObject jsonScope = jsonGroup.getJSONObject("scope");
                                           JSONArray MobileGroupScopeARRAY = jsonScope.getJSONArray("computer_groups");
                                           
                                           String MobileGroupScope = MobileGroupScopeARRAY.toString();
                                           // Comparing ID of the mobile device group
                                           
                                           if (!"[]".equals(MobileGroupScope)){
                                               
                                               for(int c=0; c < MobileGroupScopeARRAY.length(); c++){
                                                   JSONObject MobileGroupScopeObject = (JSONObject) MobileGroupScopeARRAY.get(c);
                                                   int ScopeDgroupId = MobileGroupScopeObject.getInt("id");
                                                   String MobileGroupSLIM = MobileGroupScope.replace("[", "").replace("]", "");
                                                   String[] MobileGroupARRAY = MobileGroupSLIM.split("name:");
                                                   for (String MobileGroupARRAY1 : MobileGroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           writer.write(EbookName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                       //Getting all the Profiles
                                       JSONObject jsonMobileProfiles = new JSONObject(getApi().get("osxconfigurationprofiles"));
                                       JSONArray jsonProfilesArray = jsonMobileProfiles.getJSONArray("os_x_configuration_profiles");
                                       writer.newLine();
                                       writer.write("Profiles:,");
                                       
                                       //Getting the scope of all the Profiles
                                       for(int count=0; count<jsonProfilesArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonProfilesArray.get(count);
                                           int DpolicyId = dataObj2.getInt("id");
                                           String ProfileName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("osxconfigurationprofiles/id/" + DpolicyId + "/subset/scope"));
                                           
                                           //Checking the mobile device group scope
                                           JSONObject jsonGroup = jsonAPPScope.getJSONObject("os_x_configuration_profile");
                                           JSONObject jsonScope = jsonGroup.getJSONObject("scope");
                                           JSONArray MobileGroupScopeARRAY = jsonScope.getJSONArray("computer_groups");
                                           
                                           String MobileGroupScope = MobileGroupScopeARRAY.toString();
                                           // Comparing ID of the mobile device group
                                           
                                           if (!"[]".equals(MobileGroupScope)){
                                               
                                               for(int c=0; c < MobileGroupScopeARRAY.length(); c++){
                                                   JSONObject MobileGroupScopeObject = (JSONObject) MobileGroupScopeARRAY.get(c);
                                                   int ScopeDgroupId = MobileGroupScopeObject.getInt("id");
                                                   String MobileGroupSLIM = MobileGroupScope.replace("[", "").replace("]", "");
                                                   String[] MobileGroupARRAY = MobileGroupSLIM.split("name:");
                                                   for (String MobileGroupARRAY1 : MobileGroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           
                                                           writer.write(ProfileName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                       //Getting all the Policies
                                       JSONObject jsonCompPolicies = new JSONObject(getApi().get("policies"));
                                       JSONArray jsonCompPoliciesArray = jsonCompPolicies.getJSONArray("policies");
                                       writer.newLine();
                                       writer.write("Policies:,");
                                       
                                       //Getting the scope of all the Policies
                                       for(int count=0; count<jsonCompPoliciesArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonCompPoliciesArray.get(count);
                                           int DpolicyId = dataObj2.getInt("id");
                                           String PolicyName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("policies/id/" + DpolicyId + "/subset/scope"));
                                           
                                           //Checking the mobile device group scope
                                           JSONObject jsonGroup = jsonAPPScope.getJSONObject("policy");
                                           JSONObject jsonScope = jsonGroup.getJSONObject("scope");
                                           JSONArray GroupScopeARRAY = jsonScope.getJSONArray("computer_groups");
                                           
                                           String GroupScope = GroupScopeARRAY.toString();
                                           // Comparing ID of the mobile device group
                                           
                                           if (!"[]".equals(GroupScope)){
                                               
                                               for(int c=0; c < GroupScopeARRAY.length(); c++){
                                                   JSONObject GroupScopeObject = (JSONObject) GroupScopeARRAY.get(c);
                                                   int ScopeDgroupId = GroupScopeObject.getInt("id");
                                                   String GroupSLIM = GroupScope.replace("[", "").replace("]", "");
                                                   String[] GroupARRAY = GroupSLIM.split("name:");
                                                   for (String GroupARRAY1 : GroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           
                                                           writer.write(PolicyName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                   }
                                   writer.newLine();
                                   updateProgress(i, jsonPolicyArray.length());
                               }
                           }
                       }
                       else if (getMobileGroupsButton().isSelected()){
                           try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "MobileGroupsScopeReport.csv"))) {
                               setReport("MobileGroups.csv");
                               JSONObject jsonPolicy = new JSONObject(getApi().get("mobiledevicegroups"));
                               JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("mobile_device_groups");
                               writer.write("Total Mobile Groups are:" + jsonPolicyArray.length());
                               writer.newLine();
                               for(int i=0; i<jsonPolicyArray.length(); i++){
                                   JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                                   
                                   int id = dataObj.getInt("id");
                                   String name = dataObj.getString("name");
                                   String[] MobileGroupsArray = name.split("name");
                                   for (String MobileGroupsArray1 : MobileGroupsArray) {
                                       writer.write("Group *" + MobileGroupsArray1 + "*is in Scope for: ");
                                       writer.newLine();
                                       //Getting all the applcations
                                       JSONObject jsonMobileApps = new JSONObject(getApi().get("mobiledeviceapplications"));
                                       JSONArray jsonAppArray = jsonMobileApps.getJSONArray("mobile_device_applications");
                                       
                                       writer.write("Mobile Applications:,");
                                       
                                       //Getting the scope of all the applications
                                       for(int count=0; count<jsonAppArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonAppArray.get(count);
                                           int DappId = dataObj2.getInt("id");
                                           String AppName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("mobiledeviceapplications/id/" + DappId + "/subset/scope"));
                                           
                                           //Checking the mobile device group scope
                                           JSONObject jsonGroupAPP = jsonAPPScope.getJSONObject("mobile_device_application");
                                           JSONObject jsonAppScope = jsonGroupAPP.getJSONObject("scope");
                                           JSONArray MobileGroupScopeARRAY = jsonAppScope.getJSONArray("mobile_device_groups");
                                           
                                           String MobileGroupScope = MobileGroupScopeARRAY.toString();
                                           // Comparing ID of the mobile device group
                                           
                                           if (!"[]".equals(MobileGroupScope)){
                                               
                                               for(int c=0; c < MobileGroupScopeARRAY.length(); c++){
                                                   JSONObject MobileGroupScopeObject = (JSONObject) MobileGroupScopeARRAY.get(c);
                                                   int ScopeDgroupId = MobileGroupScopeObject.getInt("id");
                                                   String MobileGroupSLIM = MobileGroupScope.replace("[", "").replace("]", "");
                                                   String[] MobileGroupARRAY = MobileGroupSLIM.split("name:");
                                                   for (String MobileGroupARRAY1 : MobileGroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           writer.write(AppName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                       //Getting all the Ebooks
                                       JSONObject jsonMobileEbooks = new JSONObject(getApi().get("ebooks"));
                                       JSONArray jsonEbooksArray = jsonMobileEbooks.getJSONArray("ebooks");
                                       writer.newLine();
                                       writer.write("Ebooks:,");
                                       
                                       //Getting the scope of all the ebooks
                                       for(int count=0; count<jsonEbooksArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonEbooksArray.get(count);
                                           int DebookId = dataObj2.getInt("id");
                                           String EbookName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("ebooks/id/" + DebookId + "/subset/scope"));
                                           
                                           //Checking the mobile device group scope
                                           JSONObject jsonGroup = jsonAPPScope.getJSONObject("ebook");
                                           JSONObject jsonScope = jsonGroup.getJSONObject("scope");
                                           JSONArray MobileGroupScopeARRAY = jsonScope.getJSONArray("mobile_device_groups");
                                           
                                           String MobileGroupScope = MobileGroupScopeARRAY.toString();
                                           // Comparing ID of the mobile device group
                                           
                                           if (!"[]".equals(MobileGroupScope)){
                                               
                                               for(int c=0; c < MobileGroupScopeARRAY.length(); c++){
                                                   JSONObject MobileGroupScopeObject = (JSONObject) MobileGroupScopeARRAY.get(c);
                                                   int ScopeDgroupId = MobileGroupScopeObject.getInt("id");
                                                   String MobileGroupSLIM = MobileGroupScope.replace("[", "").replace("]", "");
                                                   String[] MobileGroupARRAY = MobileGroupSLIM.split("name:");
                                                   for (String MobileGroupARRAY1 : MobileGroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           writer.write(EbookName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                       //Getting all the Profiles
                                       JSONObject jsonMobileProfiles = new JSONObject(getApi().get("mobiledeviceconfigurationprofiles"));
                                       JSONArray jsonProfilesArray = jsonMobileProfiles.getJSONArray("configuration_profiles");
                                       writer.newLine();
                                       writer.write("Profiles:,");
                                       
                                       //Getting the scope of all the Profiles
                                       for(int count=0; count<jsonProfilesArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonProfilesArray.get(count);
                                           int DpolicyId = dataObj2.getInt("id");
                                           String PolicyName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("mobiledeviceconfigurationprofiles/id/" + DpolicyId + "/subset/scope"));
                                           
                                           //Checking the mobile device group scope
                                           JSONObject jsonGroup = jsonAPPScope.getJSONObject("configuration_profile");
                                           JSONObject jsonScope = jsonGroup.getJSONObject("scope");
                                           JSONArray MobileGroupScopeARRAY = jsonScope.getJSONArray("mobile_device_groups");
                                           
                                           String MobileGroupScope = MobileGroupScopeARRAY.toString();
                                           // Comparing ID of the mobile device group
                                           
                                           if (!"[]".equals(MobileGroupScope)){
                                               
                                               for(int c=0; c < MobileGroupScopeARRAY.length(); c++){
                                                   JSONObject MobileGroupScopeObject = (JSONObject) MobileGroupScopeARRAY.get(c);
                                                   int ScopeDgroupId = MobileGroupScopeObject.getInt("id");
                                                   String MobileGroupSLIM = MobileGroupScope.replace("[", "").replace("]", "");
                                                   String[] MobileGroupARRAY = MobileGroupSLIM.split("name:");
                                                   for (String MobileGroupARRAY1 : MobileGroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           
                                                           writer.write(PolicyName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                   }
                                   writer.newLine();
                                   updateProgress(i, jsonPolicyArray.length());
                               }
                           }
                           
                       }
                       else if (getUserGroupsButton().isSelected()){
                           try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "UserGroupsScopeReport.csv"))) {
                                setReport("UserGroupsScopeReport.csv");
                               JSONObject jsonPolicy = new JSONObject(getApi().get("usergroups"));
                               JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("user_groups");
                               writer.write("Total User Groups are:" + jsonPolicyArray.length());
                               writer.newLine();
                               for(int i=0; i<jsonPolicyArray.length(); i++){
                                   JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                                   
                                   int id = dataObj.getInt("id");
                                   String name = dataObj.getString("name");
                                   String[] UserGroupsArray = name.split("name");
                                   for (String UserGroupsArray1 : UserGroupsArray) {
                                       writer.write("Group *" + UserGroupsArray1 + "*is in Scope for: ");
                                       writer.newLine();
                                       //Getting all the applcations
                                       JSONObject jsonMobileApps = new JSONObject(getApi().get("mobiledeviceapplications"));
                                       JSONArray jsonAppArray = jsonMobileApps.getJSONArray("mobile_device_applications");
                                       
                                       writer.write("Mobile Applications:,");
                                       
                                       //Getting the scope of all the applications
                                       for(int count=0; count<jsonAppArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonAppArray.get(count);
                                           int DappId = dataObj2.getInt("id");
                                           String AppName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("mobiledeviceapplications/id/" + DappId + "/subset/scope"));
                                           
                                           //Checking the mobile device group scope
                                           JSONObject jsonGroupAPP = jsonAPPScope.getJSONObject("mobile_device_application");
                                           JSONObject jsonAppScope = jsonGroupAPP.getJSONObject("scope");
                                           JSONArray JSSuserScopeARRAY = jsonAppScope.getJSONArray("jss_user_groups");
                                           
                                           String JSSuserScope = JSSuserScopeARRAY.toString();
                                           // Comparing ID of the User group
                                           if (!"[]".equals(JSSuserScope)){
                                               
                                               for(int c=0; c < JSSuserScopeARRAY.length(); c++){
                                                   JSONObject JSSuserScopeObject = (JSONObject) JSSuserScopeARRAY.get(c);
                                                   int ScopeDgroupId = JSSuserScopeObject.getInt("id");
                                                   String UserGroupSLIM = JSSuserScope.replace("[", "").replace("]", "");
                                                   String[] UserGroupARRAY = UserGroupSLIM.split("name:");
                                                   for (String UserGroupARRAY1 : UserGroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           writer.write(AppName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                       JSONObject jsonMacApps = new JSONObject(getApi().get("macapplications"));
                                       JSONArray jsonMacAppArray = jsonMacApps.getJSONArray("mac_applications");
                                       writer.newLine();
                                       writer.write("Mac Applications:,");
                                      
                                       //Getting the scope of all the applications
                                       for(int count=0; count<jsonMacAppArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonMacAppArray.get(count);
                                           int DappId = dataObj2.getInt("id");
                                           String AppName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("macapplications/id/" + DappId + "/subset/scope"));
                                           
                                           //Checking the mobile device group scope
                                           JSONObject jsonGroupAPP = jsonAPPScope.getJSONObject("mac_application");
                                           JSONObject jsonAppScope = jsonGroupAPP.getJSONObject("scope");
                                           JSONArray JSSuserScopeARRAY = jsonAppScope.getJSONArray("jss_user_groups");
                                           
                                           String JSSuserScope = JSSuserScopeARRAY.toString();
                                           // Comparing ID of the User group
                                           if (!"[]".equals(JSSuserScope)){
                                               
                                               for(int c=0; c < JSSuserScopeARRAY.length(); c++){
                                                   JSONObject JSSuserScopeObject = (JSONObject) JSSuserScopeARRAY.get(c);
                                                   int ScopeDgroupId = JSSuserScopeObject.getInt("id");
                                                   String UserGroupSLIM = JSSuserScope.replace("[", "").replace("]", "");
                                                   String[] UserGroupARRAY = UserGroupSLIM.split("name:");
                                                   for (String UserGroupARRAY1 : UserGroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           writer.write(AppName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
//                                       //Getting all the Mac Profiles
//                                       JSONObject jsonUserProfiles = new JSONObject(getApi().get("osxconfigurationprofiles"));
//                                       JSONArray jsonUserProfilesArray = jsonUserProfiles.getJSONArray("os_x_configuration_profiles");
//                                       writer.newLine();
//                                       writer.write("Mac Profiles:,");
//                                                                                       System.out.println("here");
//                                       //Getting the scope of all the Mac Profiles
//                                       for(int count=0; count<jsonUserProfilesArray.length(); count++){
//                                           JSONObject dataObj2 = (JSONObject) jsonUserProfilesArray.get(count);
//                                           int DprofileId = dataObj2.getInt("id");
//                                           String MacProfileName = dataObj2.getString("name");
//                                           JSONObject jsonProfileScope = new JSONObject(getApi().get("osxconfigurationprofiles/id/" + DprofileId + "/subset/scope"));
//                                           //Checking the mobile device group scope
//                                           JSONObject jsonGroup = jsonProfileScope.getJSONObject("os_x_configuration_profile");
//                                           JSONObject jsonScope = jsonGroup.getJSONObject("scope");
//                                           JSONObject GroupScopeObject = jsonScope.getJSONObject("jss_user_groups");               
//
//                                           String GroupScope = GroupScopeObject.toString();
//                                           // Comparing ID of the user group
//
//                                           if (!"{}".equals(GroupScope)){
//                                                                                          System.out.println("here2");
//                                            JSONObject UserGroupScopeObject = GroupScopeObject.getJSONObject("user_group");
//                                                                                       System.out.println("here3");
//                                            JSONArray  GroupScopeARRAY = UserGroupScopeObject.toJSONArray(UserGroupScopeObject);
//                                               for(int c=0; c < GroupScopeARRAY.length(); c++){
//                                                   JSONObject UserGroupScope = (JSONObject) GroupScopeARRAY.get(c);
//                                                   int ScopeDgroupId = UserGroupScope.getInt("id");
//                                                   String GroupSLIM = GroupScope.replace("[", "").replace("]", "");
//                                                   String[] GroupARRAY = GroupSLIM.split("name:");
//                                                   for (String GroupARRAY1 : GroupARRAY) {
//                                                       if(id == ScopeDgroupId){
//                                                           
//                                                           writer.write(MacProfileName + ",");
//                                                       }
//                                                   }
//                                               }
//                                           }
//                                       }                                       
                                       //Getting all the Ebooks
                                       JSONObject jsonMobileEbooks = new JSONObject(getApi().get("ebooks"));
                                       JSONArray jsonEbooksArray = jsonMobileEbooks.getJSONArray("ebooks");
                                       writer.newLine();
                                       writer.write("Ebooks:,");
                                       
                                       //Getting the scope of all the ebooks
                                       for(int count=0; count<jsonEbooksArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonEbooksArray.get(count);
                                           int DebookId = dataObj2.getInt("id");
                                           String EbookName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("ebooks/id/" + DebookId + "/subset/scope"));
                                           
                                           //Checking the user group scope
                                           JSONObject jsonGroup = jsonAPPScope.getJSONObject("ebook");
                                           JSONObject jsonScope = jsonGroup.getJSONObject("scope");
                                           JSONArray JSSuserScopeARRAY = jsonScope.getJSONArray("jss_user_groups");
                                           
                                           String JSSuserScope = JSSuserScopeARRAY.toString();
                                           // Comparing ID of the User group
                                           if (!"[]".equals(JSSuserScope)){
                                               
                                               for(int c=0; c < JSSuserScopeARRAY.length(); c++){
                                                   JSONObject JSSuserScopeObject = (JSONObject) JSSuserScopeARRAY.get(c);
                                                   int ScopeDgroupId = JSSuserScopeObject.getInt("id");
                                                   String UserGroupSLIM = JSSuserScope.replace("[", "").replace("]", "");
                                                   String[] UserGroupARRAY = UserGroupSLIM.split("name:");
                                                   for (String UserGroupARRAY1 : UserGroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           writer.write(EbookName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                       //Getting all the Profiles
                                       JSONObject jsonMobileProfiles = new JSONObject(getApi().get("mobiledeviceconfigurationprofiles"));
                                       JSONArray jsonProfilesArray = jsonMobileProfiles.getJSONArray("configuration_profiles");
                                       writer.newLine();
                                       writer.write("Mobile Profiles:,");
                                       
                                       //Getting the scope of all the Profiles
                                       for(int count=0; count<jsonProfilesArray.length(); count++){
                                           JSONObject dataObj2 = (JSONObject) jsonProfilesArray.get(count);
                                           int DpolicyId = dataObj2.getInt("id");
                                           String PolicyName = dataObj2.getString("name");
                                           JSONObject jsonAPPScope = new JSONObject(getApi().get("mobiledeviceconfigurationprofiles/id/" + DpolicyId + "/subset/scope"));
                                           
                                           //Checking the mobile device group scope
                                           JSONObject jsonGroup = jsonAPPScope.getJSONObject("configuration_profile");
                                           JSONObject jsonScope = jsonGroup.getJSONObject("scope");
                                           JSONArray JSSuserScopeARRAY = jsonScope.getJSONArray("jss_user_groups");
                                           
                                           String JSSuserScope = JSSuserScopeARRAY.toString();
                                           // Comparing ID of the User group
                                           if (!"[]".equals(JSSuserScope)){
                                               
                                               for(int c=0; c < JSSuserScopeARRAY.length(); c++){
                                                   JSONObject JSSuserScopeObject = (JSONObject) JSSuserScopeARRAY.get(c);
                                                   int ScopeDgroupId = JSSuserScopeObject.getInt("id");
                                                   String UserGroupSLIM = JSSuserScope.replace("[", "").replace("]", "");
                                                   String[] UserGroupARRAY = UserGroupSLIM.split("name:");
                                                   for (String UserGroupARRAY1 : UserGroupARRAY) {
                                                       if(id == ScopeDgroupId){
                                                           writer.write(PolicyName + ",");
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                   }
                                   writer.newLine();
                                   updateProgress(i, jsonPolicyArray.length());
                               }
                           }
                           
                       }
                       else if (getMobileProfilesCategoryButton().isSelected()){
                           
                           try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "MobileProfilesScopeReport.csv"))) {
                               setReport("MobileProfiles.csv");                            
                               JSONObject jsonPolicy = new JSONObject(getApi().get("mobiledeviceconfigurationprofiles"));
                               JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("configuration_profiles");
                               writer.write("Total Mobile Profiles are:" + jsonPolicyArray.length());
                               writer.newLine();
                               for(int i=0; i<jsonPolicyArray.length(); i++){
                                   JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                                   int id = dataObj.getInt("id");
                                   writer.newLine();
                                   writer.write("id: " + id);
                                   writer.newLine();
                                   String name = dataObj.getString("name");
                                   writer.newLine();
                                   writer.write("Mobile Profile: " + name + ", is scoped to: \n");
                                   writer.newLine();
                                   
                                   JSONObject jsonScope = new JSONObject(getApi().get("mobiledeviceconfigurationprofiles/id/" + id + "/subset/scope"));
                                   JSONObject scopeObject = jsonScope.getJSONObject("configuration_profile");
                                   JSONObject scope = scopeObject.getJSONObject("scope");
                                   
                                   Object ALLmobileScopeChk = scope.get("all_mobile_devices").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all mobile devices = " + ALLmobileScopeChk);
                                   writer.newLine();
                                   if (ALLmobileScopeChk == "false"){
                                       Object ALLmobileScope = scope.get("mobile_devices").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLmobileScope.toString())){
                                           String ALLmobileScopeSLIM = ALLmobileScope.toString().replace("[", "").replace("]", "");
                                           String[] ALLmobileScopeSLIMARRAY = ALLmobileScopeSLIM.split("name:");
                                           writer.newLine();
                                           writer.write("\nMobile Devices:");
                                           writer.newLine();
                                           for (String ALLmobileScopeSLIMARRAY1 : ALLmobileScopeSLIMARRAY) {
                                               writer.write(ALLmobileScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       Object MobileGroupScope = scope.get("mobile_device_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(MobileGroupScope.toString())){
                                           String MobileGroupSLIM = MobileGroupScope.toString().replace("[", "").replace("]", "");
                                           String[] MobileGroupARRAY = MobileGroupSLIM.split("name:");
                                           writer.newLine();
                                           writer.write("\nMobile Device Groups:");
                                           writer.newLine();
                                           for (String MobileGroupARRAY1 : MobileGroupARRAY) {
                                               writer.write(MobileGroupARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                   }
                                   
                                   Object ALLjssUserScopeChk = scope.get("all_jss_users").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all JAMF users = " + ALLjssUserScopeChk);
                                   writer.newLine();
                                   if (ALLjssUserScopeChk == "false"){
                                       Object ALLjssUserScope = scope.get("jss_users").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLjssUserScope.toString())){
                                           String ALLjssUserScopeSLIM = ALLjssUserScope.toString().replace("[", "").replace("]", "");
                                           String[] ALLjssUserScopeSLIMARRAY = ALLjssUserScopeSLIM.split("name:");
                                           writer.newLine();
                                           writer.write("\nJAMF Users:");
                                           writer.newLine();
                                           for (String ALLjssUserScopeSLIMARRAY1 : ALLjssUserScopeSLIMARRAY) {
                                               writer.write(ALLjssUserScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       Object UserGroupScope = scope.get("jss_user_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(UserGroupScope.toString())){
                                           String UserGroupScopeSLIM = UserGroupScope.toString().replace("[", "").replace("]", "");
                                           String[] UserGroupScopeSLIMARRAY = UserGroupScopeSLIM.split("name:");
                                           writer.newLine();
                                           writer.write("\nJAMF User Groups:");
                                           writer.newLine();
                                           for (String UserGroupScopeSLIMARRAY1 : UserGroupScopeSLIMARRAY) {
                                               writer.write(UserGroupScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                   }
                                   
                                   Object buildings = scope.get("buildings").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(buildings.toString())){
                                       String buildingsSLIM = buildings.toString().replace("[", "").replace("]", "");
                                       String[] buildingsSLIMARRAY = buildingsSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nBuildings:");
                                       writer.newLine();
                                       for (String buildingsSLIMARRAY1 : buildingsSLIMARRAY) {
                                           writer.write(buildingsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object departments = scope.get("departments").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(departments.toString())){
                                       String departmentsSLIM = departments.toString().replace("[", "").replace("]", "");
                                       String[] departmentsSLIMARRAY = departmentsSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nDepartments:");
                                       for (String departmentsSLIMARRAY1 : departmentsSLIMARRAY) {
                                           writer.write(departmentsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject limitations = scope.getJSONObject("limitations");
                                   Object network_segments = limitations.get("network_segments");
                                   if (!"[]".equals(network_segments.toString())){
                                       String networksSLIM = network_segments.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] networksSLIMARRAY = networksSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nLimited to Network Segments:");
                                       writer.newLine();
                                       for (String networksSLIMARRAY1 : networksSLIMARRAY) {
                                           writer.write(networksSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object users = limitations.get("users");
                                   if (!"[]".equals(users.toString())){
                                       String userLimitaitons1SLIM = limitations.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userLimitaitons1SLIMARRAY = userLimitaitons1SLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nLimited to Users:");
                                       writer.newLine();
                                       for (String userLimitaitons1SLIMARRAY1 : userLimitaitons1SLIMARRAY) {
                                           writer.write(userLimitaitons1SLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object userGroups = limitations.get("user_groups");
                                   if (!"[]".equals(userGroups.toString())){
                                       String userGroupsSLIM = users.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userGroupsSLIMARRAY = userGroupsSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nLimited to User Groups:");
                                       writer.newLine();
                                       for (String userGroupsSLIMARRAY1 : userGroupsSLIMARRAY) {
                                           writer.write(userGroupsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject exclusions = scope.getJSONObject("exclusions");
                                   
                                   Object mobile_devices = exclusions.get("mobile_devices");
                                   if (!"[]".equals(mobile_devices.toString())){
                                       String mobile_devicesSLIM = mobile_devices.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] mobile_devicesSLIMARRAY = mobile_devicesSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded Mobile Devices:");
                                       writer.newLine();
                                       for (String mobile_devicesSLIMARRAY1 : mobile_devicesSLIMARRAY) {
                                           writer.write(mobile_devicesSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object mobile_device_groups = exclusions.get("mobile_device_groups");
                                   if (!"[]".equals(mobile_device_groups.toString())){
                                       String mobile_device_groupsSLIM = mobile_device_groups.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] mobile_device_groupsSLIMARRAY = mobile_device_groupsSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded Mobile Device Groups:");
                                       writer.newLine();
                                       for (String mobile_device_groupsSLIMARRAY1 : mobile_device_groupsSLIMARRAY) {
                                           writer.write(mobile_device_groupsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object buildingsEX = exclusions.get("buildings");
                                   if (!"[]".equals(buildingsEX.toString())){
                                       String buildingsEXSLIM = buildingsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = buildingsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded buildings:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object departmentsEX = exclusions.get("departments");
                                   if (!"[]".equals(departmentsEX.toString())){
                                       String departmentsEXSLIM = departmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = departmentsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded departments:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object JSSusersEX = exclusions.get("jss_users");
                                   if (!"[]".equals(JSSusersEX.toString())){
                                       String JSSusersEXSLIM = JSSusersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] JSSusersEXSLIMARRAY = JSSusersEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded JAMF users:");
                                       writer.newLine();
                                       for (String JSSusersEXSLIMARRAY1 : JSSusersEXSLIMARRAY) {
                                           writer.write(JSSusersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object JSSuser_groupsEX = exclusions.get("jss_user_groups");
                                   if (!"[]".equals(JSSuser_groupsEX.toString())){
                                       String JSSuser_groupsEXSLIM = JSSuser_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] JSSuser_groupsEXSLIMARRAY = JSSuser_groupsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded to JAMF user groups:");
                                       writer.newLine();
                                       for (String JSSuser_groupsEXSLIMARRAY1 : JSSuser_groupsEXSLIMARRAY) {
                                           writer.write(JSSuser_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object usersEX = exclusions.get("users");
                                   if (!"[]".equals(usersEX.toString())){
                                       String usersEXSLIM = usersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] usersEXSLIMARRAY = usersEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded users:");
                                       writer.newLine();
                                       for (String usersEXSLIMARRAY1 : usersEXSLIMARRAY) {
                                           writer.write(usersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object user_groupsEX = exclusions.get("user_groups");
                                   if (!"[]".equals(user_groupsEX.toString())){
                                       String user_groupsEXSLIM = user_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] user_groupsEXSLIMARRAY = user_groupsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded user groups:");
                                       writer.newLine();
                                       for (String user_groupsEXSLIMARRAY1 : user_groupsEXSLIMARRAY) {
                                           writer.write(user_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object network_segmentsEX = exclusions.get("network_segments");
                                   if (!"[]".equals(network_segmentsEX.toString())){
                                       String network_segmentsEXSLIM = network_segmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] network_segmentsEXSLIMARRAY = network_segmentsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded to Network Segements:");
                                       writer.newLine();
                                       for (String network_segmentsEXSLIMARRAY1 : network_segmentsEXSLIMARRAY) {
                                           writer.write(network_segmentsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object ibeaconsEX = exclusions.get("ibeacons");
                                   if (!"[]".equals(ibeaconsEX.toString())){
                                       String ibeaconsEXSLIM = ibeaconsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] ibeaconsEXSLIMARRAY = ibeaconsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded iBeacons:");
                                       writer.newLine();
                                       for (String ibeaconsEXSLIMARRAY1 : ibeaconsEXSLIMARRAY) {
                                           writer.write(ibeaconsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   writer.newLine();
                                   writer.write("--------------------------------------------");
                                   writer.newLine();
                                   writer.flush();
                                   updateProgress(i, jsonPolicyArray.length());
                               }
                           }
                       }
                       else if (getMobileAppsCategoryButton().isSelected()){
                           
                           try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "MobileApps.csv"))) {
                                setReport("MobileApps.csv");
                               JSONObject jsonPolicy = new JSONObject(getApi().get("mobiledeviceapplications"));
                               JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("mobile_device_applications");
                               writer.write("Total Mobile Applcaitons are:" + jsonPolicyArray.length());
                               writer.newLine();
                               
                               for(int i=0; i<jsonPolicyArray.length(); i++){
                                   JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                                   int id = dataObj.getInt("id");
                                   writer.newLine();
                                   writer.write("id: " + id);
                                   writer.newLine();
                                   String name = dataObj.getString("name");
                                   
                                   writer.write("Mobile Application: " + name + ", is scoped to: \n");
                                   writer.newLine();
                                   
                                   JSONObject jsonScope = new JSONObject(getApi().get("mobiledeviceapplications/id/" + id + "/subset/scope"));
                                   JSONObject scopeObject = jsonScope.getJSONObject("mobile_device_application");
                                   JSONObject scope = scopeObject.getJSONObject("scope");
                                   
                                   Object ALLmobileScopeChk = scope.get("all_mobile_devices").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all mobile devices = " + ALLmobileScopeChk);
                                   writer.newLine();
                                   if (ALLmobileScopeChk == "false"){
                                       Object ALLmobileScope = scope.get("mobile_devices").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLmobileScope.toString())){
                                           String ALLmobileScopeSLIM = ALLmobileScope.toString().replace("[", "").replace("]", "");
                                           String[] ALLmobileScopeSLIMARRAY = ALLmobileScopeSLIM.split("name:");
                                           writer.newLine();
                                           writer.write("\nMobile Devices:");
                                           writer.newLine();
                                           for (String ALLmobileScopeSLIMARRAY1 : ALLmobileScopeSLIMARRAY) {
                                               writer.write(ALLmobileScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       Object MobileGroupScope = scope.get("mobile_device_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(MobileGroupScope.toString())){
                                           String MobileGroupSLIM = MobileGroupScope.toString().replace("[", "").replace("]", "");
                                           String[] MobileGroupARRAY = MobileGroupSLIM.split("name:");
                                           writer.newLine();
                                           writer.write("\nMobile Device Groups:");
                                           writer.newLine();
                                           for (String MobileGroupARRAY1 : MobileGroupARRAY) {
                                               writer.write(MobileGroupARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                   }
                                   
                                   Object ALLjssUserScopeChk = scope.get("all_jss_users").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all JAMF users = " + ALLjssUserScopeChk);
                                   writer.newLine();
                                   if (ALLjssUserScopeChk == "false"){
                                       Object ALLjssUserScope = scope.get("jss_users").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLjssUserScope.toString())){
                                           String ALLjssUserScopeSLIM = ALLjssUserScope.toString().replace("[", "").replace("]", "");
                                           String[] ALLjssUserScopeSLIMARRAY = ALLjssUserScopeSLIM.split("name:");
                                           writer.newLine();
                                           writer.write("\nJAMF Users:");
                                           writer.newLine();
                                           for (String ALLjssUserScopeSLIMARRAY1 : ALLjssUserScopeSLIMARRAY) {
                                               writer.write(ALLjssUserScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       Object UserGroupScope = scope.get("jss_user_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(UserGroupScope.toString())){
                                           String UserGroupScopeSLIM = UserGroupScope.toString().replace("[", "").replace("]", "");
                                           String[] UserGroupScopeSLIMARRAY = UserGroupScopeSLIM.split("name:");
                                           writer.newLine();
                                           writer.write("\nJAMF User Groups:");
                                           writer.newLine();
                                           for (String UserGroupScopeSLIMARRAY1 : UserGroupScopeSLIMARRAY) {
                                               writer.write(UserGroupScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                   }
                                   
                                   Object buildings = scope.get("buildings").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(buildings.toString())){
                                       String buildingsSLIM = buildings.toString().replace("[", "").replace("]", "");
                                       String[] buildingsSLIMARRAY = buildingsSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nBuildings:");
                                       writer.newLine();
                                       for (String buildingsSLIMARRAY1 : buildingsSLIMARRAY) {
                                           writer.write(buildingsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object departments = scope.get("departments").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(departments.toString())){
                                       String departmentsSLIM = departments.toString().replace("[", "").replace("]", "");
                                       String[] departmentsSLIMARRAY = departmentsSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nDepartments:");
                                       writer.newLine();
                                       for (String departmentsSLIMARRAY1 : departmentsSLIMARRAY) {
                                           writer.write(departmentsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject limitations = scope.getJSONObject("limitations");
                                   Object network_segments = limitations.get("network_segments");
                                   if (!"[]".equals(network_segments.toString())){
                                       String networksSLIM = network_segments.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] networksSLIMARRAY = networksSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nLimited to Network Segments:");
                                       writer.newLine();
                                       for (String networksSLIMARRAY1 : networksSLIMARRAY) {
                                           writer.write(networksSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object users = limitations.get("users");
                                   if (!"[]".equals(users.toString())){
                                       String userLimitaitons1SLIM = limitations.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userLimitaitons1SLIMARRAY = userLimitaitons1SLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nLimited to Users:");
                                       writer.newLine();
                                       for (String userLimitaitons1SLIMARRAY1 : userLimitaitons1SLIMARRAY) {
                                           writer.write(userLimitaitons1SLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object userGroups = limitations.get("user_groups");
                                   if (!"[]".equals(userGroups.toString())){
                                       String userGroupsSLIM = users.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userGroupsSLIMARRAY = userGroupsSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nLimited User Groups:");
                                       writer.newLine();
                                       for (String userGroupsSLIMARRAY1 : userGroupsSLIMARRAY) {
                                           writer.write(userGroupsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject exclusions = scope.getJSONObject("exclusions");
                                   
                                   Object mobile_devices = exclusions.get("mobile_devices");
                                   if (!"[]".equals(mobile_devices.toString())){
                                       String mobile_devicesSLIM = mobile_devices.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] mobile_devicesSLIMARRAY = mobile_devicesSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded Mobile Devices:");
                                       writer.newLine();
                                       for (String mobile_devicesSLIMARRAY1 : mobile_devicesSLIMARRAY) {
                                           writer.write(mobile_devicesSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object mobile_device_groups = exclusions.get("mobile_device_groups");
                                   if (!"[]".equals(mobile_device_groups.toString())){
                                       String mobile_device_groupsSLIM = mobile_device_groups.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] mobile_device_groupsSLIMARRAY = mobile_device_groupsSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded Mobile Device Groups:");
                                       writer.newLine();
                                       for (String mobile_device_groupsSLIMARRAY1 : mobile_device_groupsSLIMARRAY) {
                                           writer.write(mobile_device_groupsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object buildingsEX = exclusions.get("buildings");
                                   if (!"[]".equals(buildingsEX.toString())){
                                       String buildingsEXSLIM = buildingsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = buildingsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded buildings:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object departmentsEX = exclusions.get("departments");
                                   if (!"[]".equals(departmentsEX.toString())){
                                       String departmentsEXSLIM = departmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = departmentsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded departments:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object JSSusersEX = exclusions.get("jss_users");
                                   if (!"[]".equals(JSSusersEX.toString())){
                                       String JSSusersEXSLIM = JSSusersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] JSSusersEXSLIMARRAY = JSSusersEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded JAMF users:");
                                       writer.newLine();
                                       for (String JSSusersEXSLIMARRAY1 : JSSusersEXSLIMARRAY) {
                                           writer.write(JSSusersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object JSSuser_groupsEX = exclusions.get("jss_user_groups");
                                   if (!"[]".equals(JSSuser_groupsEX.toString())){
                                       String JSSuser_groupsEXSLIM = JSSuser_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] JSSuser_groupsEXSLIMARRAY = JSSuser_groupsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded to JAMF user groups:");
                                       writer.newLine();
                                       for (String JSSuser_groupsEXSLIMARRAY1 : JSSuser_groupsEXSLIMARRAY) {
                                           writer.write(JSSuser_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object usersEX = exclusions.get("users");
                                   if (!"[]".equals(usersEX.toString())){
                                       String usersEXSLIM = usersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] usersEXSLIMARRAY = usersEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded users:");
                                       writer.newLine();
                                       for (String usersEXSLIMARRAY1 : usersEXSLIMARRAY) {
                                           writer.write(usersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object user_groupsEX = exclusions.get("user_groups");
                                   if (!"[]".equals(user_groupsEX.toString())){
                                       String user_groupsEXSLIM = user_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] user_groupsEXSLIMARRAY = user_groupsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded user groups:");
                                       writer.newLine();
                                       for (String user_groupsEXSLIMARRAY1 : user_groupsEXSLIMARRAY) {
                                           writer.write(user_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object network_segmentsEX = exclusions.get("network_segments");
                                   if (!"[]".equals(network_segmentsEX.toString())){
                                       String network_segmentsEXSLIM = network_segmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] network_segmentsEXSLIMARRAY = network_segmentsEXSLIM.split("name:");
                                       writer.newLine();
                                       writer.write("\nExcluded to Network Segements:");
                                       writer.newLine();
                                       for (String network_segmentsEXSLIMARRAY1 : network_segmentsEXSLIMARRAY) {
                                           writer.write(network_segmentsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   writer.newLine();
                                   writer.write("--------------------------------------------");
                                   writer.newLine();
                                   writer.flush();
                                   updateProgress(i, jsonPolicyArray.length());
                               }
                           }
                           
                       }
                       else if (getMacProfilesCategoryButton().isSelected()){
                           try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "MacProfiles.csv"))) {
                                setReport("MacProfiles.csv");
                               JSONObject jsonPolicy = new JSONObject(getApi().get("osxconfigurationprofiles"));
                               JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("os_x_configuration_profiles");
                               writer.write("Total Mac Configuration Profiles are:" + jsonPolicyArray.length());
                               writer.newLine();
                               for(int i=0; i<jsonPolicyArray.length(); i++){
                                   JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                                   int id = dataObj.getInt("id");
                                   writer.newLine();
                                   writer.write("id: " + id);
                                   writer.newLine();
                                   String name = dataObj.getString("name");
                                   writer.newLine();
                                   writer.write("Mac Config Profile: " + name + ", is scoped to: \n");
                                   writer.newLine();
                                   
                                   JSONObject jsonScope = new JSONObject(getApi().get("osxconfigurationprofiles/id/" + id + "/subset/scope"));
                                   JSONObject scopeObject = jsonScope.getJSONObject("os_x_configuration_profile");
                                   JSONObject scope = scopeObject.getJSONObject("scope");
                                   
                                   Object ALLcompscopechk = scope.get("all_computers").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all computers = " + ALLcompscopechk);
                                   writer.newLine();
                                   if (ALLcompscopechk == "false"){
                                       Object ALLcompscoped = scope.get("computers").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLcompscoped.toString())){
                                           writer.write("\n Scoped to Computers:");
                                           writer.newLine();
                                           String ALLcompscopedSLIM = ALLcompscoped.toString().replace("[", "").replace("]", "");
                                           String[] ALLcompscopedARRAY = ALLcompscopedSLIM.split("name:");
                                           for (String ALLjssUserScopeSLIMARRAY1 : ALLcompscopedARRAY) {
                                               writer.write(ALLjssUserScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       
                                       Object compscope = scope.get("computer_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(compscope.toString())){
                                           Object compscopeGRP = compscope.toString().replace("[", "").replace("]", "");
                                           writer.write("\nComputer Groups:");
                                           writer.newLine();
                                           String compscopeGRPSLIM = compscopeGRP.toString().replace("[", "").replace("]", "");
                                           String[] compscopeGRPSLIMARRAY = compscopeGRPSLIM.split("name:");
                                           for (String compscopeGRPSLIMARRAY1 : compscopeGRPSLIMARRAY) {
                                               writer.write(compscopeGRPSLIMARRAY1);
                                               writer.newLine();
                                           }
                                           
                                       }
                                   }
                                   
                                   Object ALLjssUserScopeChk = scope.get("all_jss_users").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all JAMF users = " + ALLjssUserScopeChk);
                                   writer.newLine();
                                   if (ALLjssUserScopeChk == "false"){
                                       Object ALLjssUserScope = scope.get("jss_users").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLjssUserScope.toString())){
                                           String ALLjssUserScopeSLIM = ALLjssUserScope.toString().replace("[", "").replace("]", "");
                                           String[] ALLjssUserScopeSLIMARRAY = ALLjssUserScopeSLIM.split("name:");
                                           writer.write("\nJAMF Users:");
                                           writer.newLine();
                                           for (String ALLjssUserScopeSLIMARRAY1 : ALLjssUserScopeSLIMARRAY) {
                                               writer.write(ALLjssUserScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       Object UserGroupScope = scope.get("jss_user_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"".equals(UserGroupScope.toString())){
                                           writer.write(UserGroupScope.toString());
                                           String UserGroupScopeSLIM = UserGroupScope.toString().replace("[", "").replace("]", "");
                                           String[] UserGroupScopeSLIMARRAY = UserGroupScopeSLIM.split("name:");
                                           writer.write("\nJAMF User Groups:");
                                           writer.newLine();
                                           for (String UserGroupScopeSLIMARRAY1 : UserGroupScopeSLIMARRAY) {
                                               writer.write(UserGroupScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                   }
                                   
                                   Object buildings = scope.get("buildings").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(buildings.toString())){
                                       String buildingsSLIM = buildings.toString().replace("[", "").replace("]", "");
                                       String[] buildingsSLIMARRAY = buildingsSLIM.split("name:");
                                       writer.write("\nBuildings:");
                                       writer.newLine();
                                       for (String buildingsSLIMARRAY1 : buildingsSLIMARRAY) {
                                           writer.write(buildingsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object departments = scope.get("departments").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(departments.toString())){
                                       String departmentsSLIM = departments.toString().replace("[", "").replace("]", "");
                                       String[] departmentsSLIMARRAY = departmentsSLIM.split("name:");
                                       writer.write("\nDepartments:");
                                       writer.newLine();
                                       for (String departmentsSLIMARRAY1 : departmentsSLIMARRAY) {
                                           writer.write(departmentsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject limitations = scope.getJSONObject("limitations");
                                   Object network_segments = limitations.get("network_segments");
                                   if (!"[]".equals(network_segments.toString())){
                                       String networksSLIM = network_segments.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] networksSLIMARRAY = networksSLIM.split("name:");
                                       writer.write("\nLimited to Network Segments:");
                                       writer.newLine();
                                       for (String networksSLIMARRAY1 : networksSLIMARRAY) {
                                           writer.write(networksSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object users = limitations.get("users");
                                   if (!"[]".equals(users.toString())){
                                       String userLimitaitons1SLIM = limitations.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userLimitaitons1SLIMARRAY = userLimitaitons1SLIM.split("name:");
                                       writer.write("\nLimited to Users:");
                                       writer.newLine();
                                       for (String userLimitaitons1SLIMARRAY1 : userLimitaitons1SLIMARRAY) {
                                           writer.write(userLimitaitons1SLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object userGroups = limitations.get("user_groups");
                                   if (!"[]".equals(userGroups.toString())){
                                       String userGroupsSLIM = users.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userGroupsSLIMARRAY = userGroupsSLIM.split("name:");
                                       writer.write("\nLimited User Groups:");
                                       writer.newLine();
                                       for (String userGroupsSLIMARRAY1 : userGroupsSLIMARRAY) {
                                           writer.write(userGroupsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject exclusions = scope.getJSONObject("exclusions");
                                   Object ALLcompscoped = exclusions.get("computers");
                                   if (!"[]".equals(ALLcompscoped.toString())){
                                       writer.write("\nExcluded Computers:");
                                       writer.newLine();
                                       String ALLcompscopedSLIM = ALLcompscoped.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] ALLcompscopedARRAY = ALLcompscopedSLIM.split("name:");
                                       for (String ALLjssUserScopeSLIMARRAY1 : ALLcompscopedARRAY) {
                                           writer.write(ALLjssUserScopeSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object ALLcompGroup = exclusions.get("computer_groups");
                                   if (!"[]".equals(ALLcompGroup.toString())){
                                       Object compscopeGRP = ALLcompGroup.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       writer.write("\nExcluded Computer Groups:");
                                       writer.newLine();
                                       String compscopeGRPSLIM = compscopeGRP.toString().replace("[", "").replace("]", "");
                                       String[] compscopeGRPSLIMARRAY = compscopeGRPSLIM.split("name:");
                                       for (String compscopeGRPSLIMARRAY1 : compscopeGRPSLIMARRAY) {
                                           writer.write(compscopeGRPSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object buildingsEX = exclusions.get("buildings");
                                   if (!"[]".equals(buildingsEX.toString())){
                                       String buildingsEXSLIM = buildings.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = buildingsEXSLIM.split("name:");
                                       writer.write("\nExcluded buildings:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   
                                   Object departmentsEX = exclusions.get("departments");
                                   if (!"[]".equals(departmentsEX.toString())){
                                       String departmentsEXSLIM = departmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = departmentsEXSLIM.split("name:");
                                       writer.write("\nExcluded departments:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object JSSusersEX = exclusions.get("jss_users");
                                   if (!"[]".equals(JSSusersEX.toString())){
                                       String JSSusersEXSLIM = JSSusersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] JSSusersEXSLIMARRAY = JSSusersEXSLIM.split("name:");
                                       writer.write("\nExcluded JAMF users:");
                                       writer.newLine();
                                       for (String JSSusersEXSLIMARRAY1 : JSSusersEXSLIMARRAY) {
                                           writer.write(JSSusersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object JSSuser_groupsEX = exclusions.get("jss_user_groups");
                                   if (!"[]".equals(JSSuser_groupsEX.toString())){
                                       String JSSuser_groupsEXSLIM = JSSuser_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] JSSuser_groupsEXSLIMARRAY = JSSuser_groupsEXSLIM.split("name:");
                                       writer.write("\nExcluded JAMF user groups:");
                                       writer.newLine();
                                       for (String JSSuser_groupsEXSLIMARRAY1 : JSSuser_groupsEXSLIMARRAY) {
                                           writer.write(JSSuser_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object usersEX = exclusions.get("users");
                                   if (!"[]".equals(usersEX.toString())){
                                       String usersEXSLIM = usersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] usersEXSLIMARRAY = usersEXSLIM.split("name:");
                                       writer.write("\nExcluded users:");
                                       writer.newLine();
                                       for (String usersEXSLIMARRAY1 : usersEXSLIMARRAY) {
                                           writer.write(usersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object user_groupsEX = exclusions.get("user_groups");
                                   if (!"[]".equals(user_groupsEX.toString())){
                                       String user_groupsEXSLIM = user_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] user_groupsEXSLIMARRAY = user_groupsEXSLIM.split("name:");
                                       writer.write("\nExcluded user groups:");
                                       writer.newLine();
                                       for (String user_groupsEXSLIMARRAY1 : user_groupsEXSLIMARRAY) {
                                           writer.write(user_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object network_segmentsEX = exclusions.get("network_segments");
                                   if (!"[]".equals(network_segmentsEX.toString())){
                                       String network_segmentsEXSLIM = network_segmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] network_segmentsEXSLIMARRAY = network_segmentsEXSLIM.split("name:");
                                       writer.write("\nExcluded to Network Segements:");
                                       writer.newLine();
                                       for (String network_segmentsEXSLIMARRAY1 : network_segmentsEXSLIMARRAY) {
                                           writer.write(network_segmentsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object ibeaconsEX = exclusions.get("ibeacons");
                                   if (!"[]".equals(ibeaconsEX.toString())){
                                       String ibeaconsEXSLIM = ibeaconsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] ibeaconsEXSLIMARRAY = ibeaconsEXSLIM.split("name:");
                                       writer.write("\nExcluded iBeacons:");
                                       writer.newLine();
                                       for (String ibeaconsEXSLIMARRAY1 : ibeaconsEXSLIMARRAY) {
                                           writer.write(ibeaconsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   writer.newLine();
                                   writer.write("--------------------------------------------");
                                   writer.newLine();
                                   writer.flush();
                                   updateProgress(i, jsonPolicyArray.length());
                               }
                           }
                       }
                       else if (getMacAppsCategoryButton().isSelected()){
                           try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "MacApps.csv"))) {
                                setReport("MacApps.csv");
                               JSONObject jsonPolicy = new JSONObject(getApi().get("macapplications"));
                               JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("mac_applications");
                               
                               writer.write("Total Mac Applications are:" + jsonPolicyArray.length());
                               writer.newLine();
                               
                               for(int i=0; i<jsonPolicyArray.length(); i++){
                                   JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                                   int id = dataObj.getInt("id");
                                   writer.newLine();
                                   writer.write("id: " + id);
                                   writer.newLine();
                                   String name = dataObj.getString("name");
                                   
                                   writer.write("Mac App: " + name + ", is scoped to: \n");
                                   writer.newLine();
                                   
                                   JSONObject jsonScope = new JSONObject(getApi().get("macapplications/id/" + id + "/subset/scope"));
                                   JSONObject scopeObject = jsonScope.getJSONObject("mac_application");
                                   JSONObject scope = scopeObject.getJSONObject("scope");
                                   
                                   Object ALLcompscopechk = scope.get("all_computers").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   
                                   writer.write("Scoped to all computers = " + ALLcompscopechk);
                                   writer.newLine();
                                   if (ALLcompscopechk == "false"){
                                       Object ALLcompscoped = scope.get("computers").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLcompscoped.toString())){
                                           writer.write("\n Scoped to Computers:");
                                           writer.newLine();
                                           String ALLcompscopedSLIM = ALLcompscoped.toString().replace("[", "").replace("]", "");
                                           String[] ALLcompscopedARRAY = ALLcompscopedSLIM.split("name:");
                                           for (String ALLjssUserScopeSLIMARRAY1 : ALLcompscopedARRAY) {
                                               writer.write(ALLjssUserScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       
                                       Object compscope = scope.get("computer_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(compscope.toString())){
                                           Object compscopeGRP = compscope.toString().replace("[", "").replace("]", "");
                                           writer.write("\nComputer Groups:");
                                           writer.newLine();
                                           String compscopeGRPSLIM = compscopeGRP.toString().replace("[", "").replace("]", "");
                                           String[] compscopeGRPSLIMARRAY = compscopeGRPSLIM.split("name:");
                                           for (String compscopeGRPSLIMARRAY1 : compscopeGRPSLIMARRAY) {
                                               writer.write(compscopeGRPSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                   }
                                   
                                   Object ALLjssUserScopeChk = scope.get("all_jss_users").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all JAMF users = " + ALLjssUserScopeChk);
                                   writer.newLine();
                                   if (ALLjssUserScopeChk == "false"){
                                       Object ALLjssUserScope = scope.get("jss_users").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLjssUserScope.toString())){
                                           String ALLjssUserScopeSLIM = ALLjssUserScope.toString().replace("[", "").replace("]", "");
                                           String[] ALLjssUserScopeSLIMARRAY = ALLjssUserScopeSLIM.split("name:");
                                           writer.write("\nJAMF Users:");
                                           writer.newLine();
                                           for (String ALLjssUserScopeSLIMARRAY1 : ALLjssUserScopeSLIMARRAY) {
                                               writer.write(ALLjssUserScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       Object UserGroupScope = scope.get("jss_user_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(UserGroupScope.toString())){
                                           String UserGroupScopeSLIM = UserGroupScope.toString().replace("[", "").replace("]", "");
                                           String[] UserGroupScopeSLIMARRAY = UserGroupScopeSLIM.split("name:");
                                           writer.write("\nJAMF User Groups:");
                                           writer.newLine();
                                           for (String UserGroupScopeSLIMARRAY1 : UserGroupScopeSLIMARRAY) {
                                               writer.write(UserGroupScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                   }
                                   
                                   Object buildings = scope.get("buildings").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(buildings.toString())){
                                       String buildingsSLIM = buildings.toString().replace("[", "").replace("]", "");
                                       String[] buildingsSLIMARRAY = buildingsSLIM.split("name:");
                                       writer.write("\nBuildings:");
                                       writer.newLine();
                                       for (String buildingsSLIMARRAY1 : buildingsSLIMARRAY) {
                                           writer.write(buildingsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object departments = scope.get("departments").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(departments.toString())){
                                       String departmentsSLIM = departments.toString().replace("[", "").replace("]", "");
                                       String[] departmentsSLIMARRAY = departmentsSLIM.split("name:");
                                       writer.write("\nDepartments:");
                                       writer.newLine();
                                       for (String departmentsSLIMARRAY1 : departmentsSLIMARRAY) {
                                           writer.write(departmentsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject limitations = scope.getJSONObject("limitations");
                                   Object network_segments = limitations.get("network_segments");
                                   if (!"[]".equals(network_segments.toString())){
                                       String networksSLIM = network_segments.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] networksSLIMARRAY = networksSLIM.split("name:");
                                       writer.write("\nLimited to Network Segments:");
                                       writer.newLine();
                                       for (String networksSLIMARRAY1 : networksSLIMARRAY) {
                                           writer.write(networksSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object users = limitations.get("users");
                                   if (!"[]".equals(users.toString())){
                                       String userLimitaitons1SLIM = limitations.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userLimitaitons1SLIMARRAY = userLimitaitons1SLIM.split("name:");
                                       writer.write("\nLimited to Users:");
                                       writer.newLine();
                                       for (String userLimitaitons1SLIMARRAY1 : userLimitaitons1SLIMARRAY) {
                                           writer.write(userLimitaitons1SLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object userGroups = limitations.get("user_groups");
                                   if (!"[]".equals(userGroups.toString())){
                                       String userGroupsSLIM = users.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userGroupsSLIMARRAY = userGroupsSLIM.split("name:");
                                       writer.write("\nLimited to User Groups:");
                                       writer.newLine();
                                       for (String userGroupsSLIMARRAY1 : userGroupsSLIMARRAY) {
                                           writer.write(userGroupsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject exclusions = scope.getJSONObject("exclusions");
                                   Object ALLcompscoped = exclusions.get("computers");
                                   if (!"[]".equals(ALLcompscoped.toString())){
                                       writer.write("\nExcluded Computers:");
                                       writer.newLine();
                                       String ALLcompscopedSLIM = ALLcompscoped.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] ALLcompscopedARRAY = ALLcompscopedSLIM.split("name:");
                                       for (String ALLjssUserScopeSLIMARRAY1 : ALLcompscopedARRAY) {
                                           writer.write(ALLjssUserScopeSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object ALLcompGroup = exclusions.get("computer_groups");
                                   if (!"[]".equals(ALLcompGroup.toString())){
                                       Object compscopeGRP = ALLcompGroup.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       writer.write("\nExcluded Computer Groups:");
                                       writer.newLine();
                                       String compscopeGRPSLIM = compscopeGRP.toString().replace("[", "").replace("]", "");
                                       String[] compscopeGRPSLIMARRAY = compscopeGRPSLIM.split("name:");
                                       for (String compscopeGRPSLIMARRAY1 : compscopeGRPSLIMARRAY) {
                                           writer.write(compscopeGRPSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object buildingsEX = exclusions.get("buildings");
                                   if (!"[]".equals(buildingsEX.toString())){
                                       String buildingsEXSLIM = buildings.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = buildingsEXSLIM.split("name:");
                                       writer.write("\nExcluded buildings:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object departmentsEX = exclusions.get("departments");
                                   if (!"[]".equals(departmentsEX.toString())){
                                       String departmentsEXSLIM = departmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = departmentsEXSLIM.split("name:");
                                       writer.write("\nExcluded departments:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object JSSusersEX = exclusions.get("jss_users");
                                   if (!"[]".equals(JSSusersEX.toString())){
                                       String JSSusersEXSLIM = JSSusersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] JSSusersEXSLIMARRAY = JSSusersEXSLIM.split("name:");
                                       writer.write("\nExcluded JAMF users:");
                                       writer.newLine();
                                       for (String JSSusersEXSLIMARRAY1 : JSSusersEXSLIMARRAY) {
                                           writer.write(JSSusersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object JSSuser_groupsEX = exclusions.get("jss_user_groups");
                                   if (!"[]".equals(JSSuser_groupsEX.toString())){
                                       String JSSuser_groupsEXSLIM = JSSuser_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] JSSuser_groupsEXSLIMARRAY = JSSuser_groupsEXSLIM.split("name:");
                                       writer.write("\nExcluded JAMF user groups:");
                                       writer.newLine();
                                       for (String JSSuser_groupsEXSLIMARRAY1 : JSSuser_groupsEXSLIMARRAY) {
                                           writer.write(JSSuser_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object usersEX = exclusions.get("users");
                                   if (!"[]".equals(usersEX.toString())){
                                       String usersEXSLIM = usersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] usersEXSLIMARRAY = usersEXSLIM.split("name:");
                                       writer.write("\nExcluded users:");
                                       writer.newLine();
                                       for (String usersEXSLIMARRAY1 : usersEXSLIMARRAY) {
                                           writer.write(usersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object user_groupsEX = exclusions.get("user_groups");
                                   if (!"[]".equals(user_groupsEX.toString())){
                                       String user_groupsEXSLIM = user_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] user_groupsEXSLIMARRAY = user_groupsEXSLIM.split("name:");
                                       writer.write("\nExcluded user groups:");
                                       writer.newLine();
                                       for (String user_groupsEXSLIMARRAY1 : user_groupsEXSLIMARRAY) {
                                           writer.write(user_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object network_segmentsEX = exclusions.get("network_segments");
                                   if (!"[]".equals(network_segmentsEX.toString())){
                                       String network_segmentsEXSLIM = network_segmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] network_segmentsEXSLIMARRAY = network_segmentsEXSLIM.split("name:");
                                       writer.write("\nExcluded Network Segements:");
                                       writer.newLine();
                                       for (String network_segmentsEXSLIMARRAY1 : network_segmentsEXSLIMARRAY) {
                                           writer.write(network_segmentsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   writer.newLine();
                                   writer.write("--------------------------------------------");
                                   writer.newLine();
                                   writer.flush();
                                   updateProgress(i, jsonPolicyArray.length());
                               }
                           }
                           
                       }
                       else if (getEbooksCategoryButton().isSelected()){
                           try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "Ebooks.csv"))) {
                                setReport("Ebooks.csv");
                               JSONObject jsonPolicy = new JSONObject(getApi().get("ebooks"));
                               JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("ebooks");
                               writer.write("Total Ebooks are:" + jsonPolicyArray.length());
                               writer.newLine();
                               for(int i=0; i<jsonPolicyArray.length(); i++){
                                   JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                                   int id = dataObj.getInt("id");
                                   writer.newLine();
                                   writer.write("id: " + id);
                                   writer.newLine();
                                   String name = dataObj.getString("name");
                                   writer.write("Ebook: " + name + ", is scoped to: \n");
                                   writer.newLine();
                                   
                                   JSONObject jsonScope = new JSONObject(getApi().get("ebooks/id/" + id + "/subset/scope"));
                                   JSONObject scopeObject = jsonScope.getJSONObject("ebook");
                                   JSONObject scope = scopeObject.getJSONObject("scope");
                                   
                                   Object ALLcompscopechk = scope.get("all_computers").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all computers = " + ALLcompscopechk);
                                   writer.newLine();
                                   if (ALLcompscopechk == "false"){
                                       Object ALLcompscoped = scope.get("computers").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLcompscoped.toString())){
                                           writer.write("\n Scoped to Computers: ");
                                           writer.newLine();
                                           String ALLcompscopedSLIM = ALLcompscoped.toString().replace("[", "").replace("]", "");
                                           String[] ALLcompscopedARRAY = ALLcompscopedSLIM.split("name:");
                                           for (String ALLjssUserScopeSLIMARRAY1 : ALLcompscopedARRAY) {
                                               writer.write(ALLjssUserScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       
                                       Object compscope = scope.get("computer_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(compscope.toString())){
                                           Object compscopeGRP = compscope.toString().replace("[", "").replace("]", "");
                                           writer.write("\nComputer Groups:");
                                           writer.newLine();
                                           String compscopeGRPSLIM = compscopeGRP.toString().replace("[", "").replace("]", "");
                                           String[] compscopeGRPSLIMARRAY = compscopeGRPSLIM.split("name:");
                                           for (String compscopeGRPSLIMARRAY1 : compscopeGRPSLIMARRAY) {
                                               writer.write(compscopeGRPSLIMARRAY1);
                                               writer.newLine();
                                           }
                                           
                                       }
                                   }
                                   
                                   Object ALLmobileScopeChk = scope.get("all_mobile_devices").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all mobile devices = " + ALLmobileScopeChk);
                                   writer.newLine();
                                   if (ALLmobileScopeChk == "false"){
                                       Object ALLmobileScope = scope.get("mobile_devices").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLmobileScope.toString())){
                                           String ALLmobileScopeSLIM = ALLmobileScope.toString().replace("[", "").replace("]", "");
                                           String[] ALLmobileScopeSLIMARRAY = ALLmobileScopeSLIM.split("name:");
                                           writer.write("\nMobile Devices:");
                                           writer.newLine();
                                           for (String ALLmobileScopeSLIMARRAY1 : ALLmobileScopeSLIMARRAY) {
                                               writer.write(ALLmobileScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       Object MobileGroupScope = scope.get("mobile_device_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(MobileGroupScope.toString())){
                                           String MobileGroupSLIM = MobileGroupScope.toString().replace("[", "").replace("]", "");
                                           String[] MobileGroupARRAY = MobileGroupSLIM.split("name:");
                                           writer.write("\nMobile Device Groups:");
                                           writer.newLine();
                                           for (String MobileGroupARRAY1 : MobileGroupARRAY) {
                                               writer.write(MobileGroupARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                   }
                                   
                                   Object ALLjssUserScopeChk = scope.get("all_jss_users").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all JAMF users = " + ALLjssUserScopeChk);
                                   writer.newLine();
                                   if (ALLjssUserScopeChk == "false"){
                                       Object ALLjssUserScope = scope.get("jss_users").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLjssUserScope.toString())){
                                           String ALLjssUserScopeSLIM = ALLjssUserScope.toString().replace("[", "").replace("]", "");
                                           String[] ALLjssUserScopeSLIMARRAY = ALLjssUserScopeSLIM.split("name:");
                                           writer.write("\nJAMF Users:");
                                           writer.newLine();
                                           for (String ALLjssUserScopeSLIMARRAY1 : ALLjssUserScopeSLIMARRAY) {
                                               writer.write(ALLjssUserScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       Object UserGroupScope = scope.get("jss_user_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(UserGroupScope.toString())){
                                           String UserGroupScopeSLIM = UserGroupScope.toString().replace("[", "").replace("]", "");
                                           String[] UserGroupScopeSLIMARRAY = UserGroupScopeSLIM.split("name:");
                                           writer.write("\nJAMF User Groups:");
                                           writer.newLine();
                                           for (String UserGroupScopeSLIMARRAY1 : UserGroupScopeSLIMARRAY) {
                                               writer.write(UserGroupScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                   }
                                   
                                   Object buildings = scope.get("buildings").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(buildings.toString())){
                                       String buildingsSLIM = buildings.toString().replace("[", "").replace("]", "");
                                       String[] buildingsSLIMARRAY = buildingsSLIM.split("name:");
                                       writer.write("\nBuildings:");
                                       writer.newLine();
                                       for (String buildingsSLIMARRAY1 : buildingsSLIMARRAY) {
                                           writer.write(buildingsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object departments = scope.get("departments").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(departments.toString())){
                                       String departmentsSLIM = departments.toString().replace("[", "").replace("]", "");
                                       String[] departmentsSLIMARRAY = departmentsSLIM.split("name:");
                                       writer.write("\nDepartments:");
                                       writer.newLine();
                                       for (String departmentsSLIMARRAY1 : departmentsSLIMARRAY) {
                                           writer.write(departmentsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object classes = scope.get("classes").toString().replace("{", "").replace("}", "").replace("\"", "");
                                   if (!"[]".equals(classes.toString())){
                                       String classesSLIM = classes.toString().replace("[", "").replace("]", "");
                                       String[] classesSLIMARRAY = classesSLIM.split("name:");
                                       writer.write("\nClasses:");
                                       writer.newLine();
                                       for (String networksSLIMARRAY1 : classesSLIMARRAY) {
                                           writer.write(networksSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject limitations = scope.getJSONObject("limitations");
                                   Object network_segments = limitations.get("network_segments");
                                   if (!"[]".equals(network_segments.toString())){
                                       String networksSLIM = network_segments.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] networksSLIMARRAY = networksSLIM.split("name:");
                                       writer.write("\nlimited Network Segments:");
                                       writer.newLine();
                                       for (String networksSLIMARRAY1 : networksSLIMARRAY) {
                                           writer.write(networksSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object users = limitations.get("users");
                                   if (!"[]".equals(users.toString())){
                                       String userLimitaitons1SLIM = limitations.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userLimitaitons1SLIMARRAY = userLimitaitons1SLIM.split("name:");
                                       writer.write("\nLimited to Users:");
                                       writer.newLine();
                                       for (String userLimitaitons1SLIMARRAY1 : userLimitaitons1SLIMARRAY) {
                                           writer.write(userLimitaitons1SLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object userGroups = limitations.get("user_groups");
                                   if (!"[]".equals(userGroups.toString())){
                                       String userGroupsSLIM = users.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userGroupsSLIMARRAY = userGroupsSLIM.split("name:");
                                       writer.write("\nLimited User Groups:");
                                       writer.newLine();
                                       for (String userGroupsSLIMARRAY1 : userGroupsSLIMARRAY) {
                                           writer.write(userGroupsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject exclusions = scope.getJSONObject("exclusions");
                                   Object ALLcompscoped = exclusions.get("computers");
                                   if (!"[]".equals(ALLcompscoped.toString())){
                                       writer.write("\nExcluded Computers: ");
                                       writer.newLine();
                                       String ALLcompscopedSLIM = ALLcompscoped.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] ALLcompscopedARRAY = ALLcompscopedSLIM.split("name:");
                                       for (String ALLjssUserScopeSLIMARRAY1 : ALLcompscopedARRAY) {
                                           writer.write(ALLjssUserScopeSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object ALLcompGroup = exclusions.get("computer_groups");
                                   if (!"[]".equals(ALLcompGroup.toString())){
                                       Object compscopeGRP = ALLcompGroup.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       writer.write("\nExcluded Computer Groups:");
                                       writer.newLine();
                                       String compscopeGRPSLIM = compscopeGRP.toString().replace("[", "").replace("]", "");
                                       String[] compscopeGRPSLIMARRAY = compscopeGRPSLIM.split("name:");
                                       for (String compscopeGRPSLIMARRAY1 : compscopeGRPSLIMARRAY) {
                                           writer.write(compscopeGRPSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object mobile_devices = exclusions.get("mobile_devices");
                                   if (!"[]".equals(mobile_devices.toString())){
                                       String mobile_devicesSLIM = mobile_devices.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] mobile_devicesSLIMARRAY = mobile_devicesSLIM.split("name:");
                                       writer.write("\nExcluded Mobile Devices:");
                                       writer.newLine();
                                       for (String mobile_devicesSLIMARRAY1 : mobile_devicesSLIMARRAY) {
                                           writer.write(mobile_devicesSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object mobile_device_groups = exclusions.get("mobile_device_groups");
                                   if (!"[]".equals(mobile_device_groups.toString())){
                                       String mobile_device_groupsSLIM = mobile_device_groups.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] mobile_device_groupsSLIMARRAY = mobile_device_groupsSLIM.split("name:");
                                       writer.write("\nExcluded Mobile Device Groups:");
                                       writer.newLine();
                                       for (String mobile_device_groupsSLIMARRAY1 : mobile_device_groupsSLIMARRAY) {
                                           writer.write(mobile_device_groupsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object buildingsEX = exclusions.get("buildings");
                                   if (!"[]".equals(buildingsEX.toString())){
                                       String buildingsEXSLIM = buildingsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = buildingsEXSLIM.split("name:");
                                       writer.write("\nExcluded buildings:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object departmentsEX = exclusions.get("departments");
                                   if (!"[]".equals(departmentsEX.toString())){
                                       String departmentsEXSLIM = departmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = departmentsEXSLIM.split("name:");
                                       writer.write("\nExcluded departments:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object JSSusersEX = exclusions.get("jss_users");
                                   if (!"[]".equals(JSSusersEX.toString())){
                                       String JSSusersEXSLIM = JSSusersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] JSSusersEXSLIMARRAY = JSSusersEXSLIM.split("name:");
                                       writer.write("\nExcluded JAMF users:");
                                       writer.newLine();
                                       for (String JSSusersEXSLIMARRAY1 : JSSusersEXSLIMARRAY) {
                                           writer.write(JSSusersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object JSSuser_groupsEX = exclusions.get("jss_user_groups");
                                   if (!"[]".equals(JSSuser_groupsEX.toString())){
                                       String JSSuser_groupsEXSLIM = JSSuser_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] JSSuser_groupsEXSLIMARRAY = JSSuser_groupsEXSLIM.split("name:");
                                       writer.write("\nExcluded to JAMF user groups:");
                                       writer.newLine();
                                       for (String JSSuser_groupsEXSLIMARRAY1 : JSSuser_groupsEXSLIMARRAY) {
                                           writer.write(JSSuser_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object usersEX = exclusions.get("users");
                                   if (!"[]".equals(usersEX.toString())){
                                       String usersEXSLIM = usersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] usersEXSLIMARRAY = usersEXSLIM.split("name:");
                                       writer.write("\nExcluded users:");
                                       writer.newLine();
                                       for (String usersEXSLIMARRAY1 : usersEXSLIMARRAY) {
                                           writer.write(usersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object user_groupsEX = exclusions.get("user_groups");
                                   if (!"[]".equals(user_groupsEX.toString())){
                                       String user_groupsEXSLIM = user_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] user_groupsEXSLIMARRAY = user_groupsEXSLIM.split("name:");
                                       writer.write("\nExcluded user groups:");
                                       writer.newLine();
                                       for (String user_groupsEXSLIMARRAY1 : user_groupsEXSLIMARRAY) {
                                           writer.write(user_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object network_segmentsEX = exclusions.get("network_segments");
                                   if (!"[]".equals(network_segmentsEX.toString())){
                                       String network_segmentsEXSLIM = network_segmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] network_segmentsEXSLIMARRAY = network_segmentsEXSLIM.split("name:");
                                       writer.write("\nExcluded to Network Segements:\n");
                                       writer.newLine();
                                       for (String network_segmentsEXSLIMARRAY1 : network_segmentsEXSLIMARRAY) {
                                           writer.write(network_segmentsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   writer.newLine();
                                   writer.write("--------------------------------------------");
                                   writer.newLine();
                                   writer.flush();
                                   updateProgress(i, jsonPolicyArray.length());
                               }
                           }
                       }
                       else if (getPoliciesCategoryButton().isSelected()){
                           try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "Polcies.csv"))) {
                                setReport("Policies.csv");
                               JSONObject jsonPolicy = new JSONObject(getApi().get("policies"));
                               JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("policies");
                               writer.newLine();
                               writer.write("Total Policy Count is: " + jsonPolicyArray.length() + ",");
                               
                               
                               for(int i=0; i<jsonPolicyArray.length(); i++){
                                   JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                                   int id = dataObj.getInt("id");
                                   writer.newLine();
                                   writer.write("id: " + id);
                                   writer.newLine();
                                   String name = dataObj.getString("name");
                                   writer.write("Policy: " + name + ", is scoped to: \n");
                                   writer.newLine();
                                   
                                   
                                   JSONObject jsonScope = new JSONObject(getApi().get("policies/id/" + id + "/subset/scope"));
                                   JSONObject scopeObject = jsonScope.getJSONObject("policy");
                                   JSONObject scope = scopeObject.getJSONObject("scope");
                                   
                                   Object ALLcompscopechk = scope.get("all_computers").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                                   writer.write("Scoped to all computers = " + ALLcompscopechk);
                                   writer.newLine();
                                   if (ALLcompscopechk == "false"){
                                       Object ALLcompscoped = scope.get("computers").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(ALLcompscoped.toString())){
                                           writer.write("\n Computers:");
                                           writer.newLine();
                                           String ALLcompscopedSLIM = ALLcompscoped.toString().replace("[", "").replace("]", "");
                                           String[] ALLcompscopedARRAY = ALLcompscopedSLIM.split("name:");
                                           for (String ALLjssUserScopeSLIMARRAY1 : ALLcompscopedARRAY) {
                                               writer.write(ALLjssUserScopeSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       
                                       Object compscope = scope.get("computer_groups").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(compscope.toString())){
                                           Object compscopeGRP = compscope.toString().replace("[", "").replace("]", "");
                                           writer.write("\nComputer Groups:");
                                           writer.newLine();
                                           String compscopeGRPSLIM = compscopeGRP.toString().replace("[", "").replace("]", "");
                                           String[] compscopeGRPSLIMARRAY = compscopeGRPSLIM.split("name:");
                                           for (String compscopeGRPSLIMARRAY1 : compscopeGRPSLIMARRAY) {
                                               writer.write(compscopeGRPSLIMARRAY1);
                                               writer.newLine();
                                           }
                                           
                                       }
                                       Object buildings = scope.get("buildings").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(buildings.toString())){
                                           String buildingsSLIM = buildings.toString().replace("[", "").replace("]", "");
                                           String[] buildingsSLIMARRAY = buildingsSLIM.split("name:");
                                           writer.write("\nBuildings:");
                                           writer.newLine();
                                           for (String buildingsSLIMARRAY1 : buildingsSLIMARRAY) {
                                               writer.write(buildingsSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                       Object departments = scope.get("departments").toString().replace("{", "").replace("}", "").replace("\"", "");
                                       if (!"[]".equals(departments.toString())){
                                           String departmentsSLIM = departments.toString().replace("[", "").replace("]", "");
                                           String[] departmentsSLIMARRAY = departmentsSLIM.split("name:");
                                           writer.write("\nDepartments:");
                                           writer.newLine();
                                           for (String departmentsSLIMARRAY1 : departmentsSLIMARRAY) {
                                               writer.write(departmentsSLIMARRAY1);
                                               writer.newLine();
                                           }
                                       }
                                   }
                                   
                                   JSONObject userLimitaitons1 = scope.getJSONObject("limit_to_users");
                                   Object LimitToUsers = userLimitaitons1.get("user_groups");
                                   if (!"[]".equals(LimitToUsers.toString())){
                                       String userLimitaitons1SLIM = LimitToUsers.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       writer.write("Limit to user groups:" + userLimitaitons1SLIM);
                                       writer.newLine();
                                   }
                                   
                                   JSONObject limitations = scope.getJSONObject("limitations");
                                   Object network_segments = limitations.get("network_segments");
                                   if (!"[]".equals(network_segments.toString())){
                                       String networksSLIM = network_segments.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] networksSLIMARRAY = networksSLIM.split("name:");
                                       writer.write("\nLimited to Network Segments:");
                                       writer.newLine();
                                       for (String networksSLIMARRAY1 : networksSLIMARRAY) {
                                           writer.write(networksSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object users = limitations.get("users");
                                   if (!"[]".equals(users.toString())){
                                       String userLimitaitons1SLIM = limitations.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userLimitaitons1SLIMARRAY = userLimitaitons1SLIM.split("name:");
                                       writer.write("\nLimited to Users:");
                                       writer.newLine();
                                       for (String userLimitaitons1SLIMARRAY1 : userLimitaitons1SLIMARRAY) {
                                           writer.write(userLimitaitons1SLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object userGroups = limitations.get("user_groups");
                                   if (!"[]".equals(userGroups.toString())){
                                       String userGroupsSLIM = users.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] userGroupsSLIMARRAY = userGroupsSLIM.split("name:");
                                       writer.write("\nLimited User Groups:");
                                       writer.newLine();
                                       for (String userGroupsSLIMARRAY1 : userGroupsSLIMARRAY) {
                                           writer.write(userGroupsSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object ibeacons = limitations.get("ibeacons");
                                   if (!"[]".equals(ibeacons.toString())){
                                       String ibeaconsEXSLIM = ibeacons.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] ibeaconsEXSLIMARRAY = ibeaconsEXSLIM.split("name:");
                                       writer.write("\nExcluded iBeacons: ");
                                       writer.newLine();
                                       for (String ibeaconsEXSLIMARRAY1 : ibeaconsEXSLIMARRAY) {
                                           writer.write(ibeaconsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   JSONObject exclusions = scope.getJSONObject("exclusions");
                                   
                                   Object ALLcompscoped = exclusions.get("computers");
                                   if (!"[]".equals(ALLcompscoped.toString())){
                                       writer.write("\nExcluded Computers: ");
                                       writer.newLine();
                                       String ALLcompscopedSLIM = ALLcompscoped.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] ALLcompscopedARRAY = ALLcompscopedSLIM.split("name:");
                                       for (String ALLjssUserScopeSLIMARRAY1 : ALLcompscopedARRAY) {
                                           writer.write(ALLjssUserScopeSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object ALLcompGroup = exclusions.get("computer_groups");
                                   if (!"[]".equals(ALLcompGroup.toString())){
                                       Object compscopeGRP = ALLcompGroup.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       writer.write("\nExcluded Computer Groups:");
                                       writer.newLine();
                                       String compscopeGRPSLIM = compscopeGRP.toString().replace("[", "").replace("]", "");
                                       String[] compscopeGRPSLIMARRAY = compscopeGRPSLIM.split("name:");
                                       for (String compscopeGRPSLIMARRAY1 : compscopeGRPSLIMARRAY) {
                                           writer.write(compscopeGRPSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object buildingsEX = exclusions.get("buildings");
                                   if (!"[]".equals(buildingsEX.toString())){
                                       String buildingsEXSLIM = buildingsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = buildingsEXSLIM.split("name:");
                                       writer.write("\nExcluded buildings:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object departmentsEX = exclusions.get("departments");
                                   if (!"[]".equals(departmentsEX.toString())){
                                       String departmentsEXSLIM = departmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] buildingsEXSLIMARRAY = departmentsEXSLIM.split("name:");
                                       writer.write("\nExcluded departments:");
                                       writer.newLine();
                                       for (String buildingsEXSLIMARRAY1 : buildingsEXSLIMARRAY) {
                                           writer.write(buildingsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object usersEX = exclusions.get("users");
                                   if (!"[]".equals(usersEX.toString())){
                                       String usersEXSLIM = usersEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] usersEXSLIMARRAY = usersEXSLIM.split("name:");
                                       writer.write("\nExcluded users:");
                                       writer.newLine();
                                       for (String usersEXSLIMARRAY1 : usersEXSLIMARRAY) {
                                           writer.write(usersEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object user_groupsEX = exclusions.get("user_groups");
                                   if (!"[]".equals(user_groupsEX.toString())){
                                       String user_groupsEXSLIM = user_groupsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] user_groupsEXSLIMARRAY = user_groupsEXSLIM.split("name:");
                                       writer.write("\nExcluded user groups:");
                                       writer.newLine();
                                       for (String user_groupsEXSLIMARRAY1 : user_groupsEXSLIMARRAY) {
                                           writer.write(user_groupsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   
                                   Object network_segmentsEX = exclusions.get("network_segments");
                                   if (!"[]".equals(network_segmentsEX.toString())){
                                       String network_segmentsEXSLIM = network_segmentsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] network_segmentsEXSLIMARRAY = network_segmentsEXSLIM.split("name:");
                                       writer.write("\nExcluded to Network Segements:");
                                       writer.newLine();
                                       for (String network_segmentsEXSLIMARRAY1 : network_segmentsEXSLIMARRAY) {
                                           writer.write(network_segmentsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   Object ibeaconsEX = exclusions.get("ibeacons");
                                   if (!"[]".equals(ibeaconsEX.toString())){
                                       String ibeaconsEXSLIM = ibeaconsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                                       String[] ibeaconsEXSLIMARRAY = ibeaconsEXSLIM.split("name:");
                                       writer.write("\nExcluded iBeacons:");
                                       writer.newLine();
                                       for (String ibeaconsEXSLIMARRAY1 : ibeaconsEXSLIMARRAY) {
                                           writer.write(ibeaconsEXSLIMARRAY1);
                                           writer.newLine();
                                       }
                                   }
                                   writer.newLine();
                                   writer.write("--------------------------------------------");
                                   writer.newLine();
                                   writer.flush();
                                   updateProgress(i, jsonPolicyArray.length());
                               }
                           }
                       }
                       else {
                           OutputMessage.setText("Please Select a way to organize the list");
                       }
                   } catch (ApiCallsException | JSONException | IOException ex ) { //Main try end
                                            
                       throw new RuntimeException("foobar");
                       //OutputMessage.setText( "URL, username and/or password not accepted.");
                       //Logger.getLogger(TheScopeReport.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   
                   updateProgress(10, 10);
                   return null;
               }
               
           };
      OutputMessage.setText("Starting API calls and writing to the file...");
      pForm.activateProgressBar(mainTask);
            
      mainTask.setOnSucceeded(e -> {                
                    pForm.getDialogStage().close();
                    btn.setDisable(false);
                    OutputMessage.setText("Done writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + getReport());
            });
      mainTask.setOnFailed(e ->
      {                
                    pForm.getDialogStage().close();
                    btn.setDisable(false);
                    Integer Response = api.getLastResponseCode();
            switch (Response) {
                case 401:
                    OutputMessage.setText("Username and/or password not accepted.");
                    break;
                case 0:
                    OutputMessage.setText("URL was not found.");
                    break;
                default:
                    OutputMessage.setText("Something really went wrong. Please file an issue on Github");
                    break;
            }
                            });
      
                  btn.setDisable(true);
                  
            Thread thread = new Thread(mainTask);
            thread.start();
        
       });//button end
}
    /**
     * @return the ComputerGroupsButton
     */
    public RadioButton getComputerGroupsButton() {
        return ComputerGroupsButton;
    }

    /**
     * @param ComputerGroupsButton the ComputerGroupsButton to set
     */
    public void setComputerGroupsButton(RadioButton ComputerGroupsButton) {
        this.ComputerGroupsButton = ComputerGroupsButton;
    }

    /**
     * @return the MobileGroupsButton
     */
    public RadioButton getMobileGroupsButton() {
        return MobileGroupsButton;
    }

    /**
     * @param MobileGroupsButton the MobileGroupsButton to set
     */
    public void setMobileGroupsButton(RadioButton MobileGroupsButton) {
        this.MobileGroupsButton = MobileGroupsButton;
    }

    /**
     * @return the UserGroupsButton
     */
    public RadioButton getUserGroupsButton() {
        return UserGroupsButton;
    }

    /**
     * @param UserGroupsButton the UserGroupsButton to set
     */
    public void setUserGroupsButton(RadioButton UserGroupsButton) {
        this.UserGroupsButton = UserGroupsButton;
    }

    /**
     * @return the MobileProfilesCategoryButton
     */
    public RadioButton getMobileProfilesCategoryButton() {
        return MobileProfilesCategoryButton;
    }

    /**
     * @param MobileProfilesCategoryButton the MobileProfilesCategoryButton to set
     */
    public void setMobileProfilesCategoryButton(RadioButton MobileProfilesCategoryButton) {
        this.MobileProfilesCategoryButton = MobileProfilesCategoryButton;
    }

    /**
     * @return the MacProfilesCategoryButton
     */
    public RadioButton getMacProfilesCategoryButton() {
        return MacProfilesCategoryButton;
    }

    /**
     * @param MacProfilesCategoryButton the MacProfilesCategoryButton to set
     */
    public void setMacProfilesCategoryButton(RadioButton MacProfilesCategoryButton) {
        this.MacProfilesCategoryButton = MacProfilesCategoryButton;
    }

    /**
     * @return the MobileAppsCategoryButton
     */
    public RadioButton getMobileAppsCategoryButton() {
        return MobileAppsCategoryButton;
    }

    /**
     * @param MobileAppsCategoryButton the MobileAppsCategoryButton to set
     */
    public void setMobileAppsCategoryButton(RadioButton MobileAppsCategoryButton) {
        this.MobileAppsCategoryButton = MobileAppsCategoryButton;
    }

    /**
     * @return the MacAppsCategoryButton
     */
    public RadioButton getMacAppsCategoryButton() {
        return MacAppsCategoryButton;
    }

    /**
     * @param MacAppsCategoryButton the MacAppsCategoryButton to set
     */
    public void setMacAppsCategoryButton(RadioButton MacAppsCategoryButton) {
        this.MacAppsCategoryButton = MacAppsCategoryButton;
    }

    /**
     * @return the PoliciesCategoryButton
     */
    public RadioButton getPoliciesCategoryButton() {
        return PoliciesCategoryButton;
    }

    /**
     * @param PoliciesCategoryButton the PoliciesCategoryButton to set
     */
    public void setPoliciesCategoryButton(RadioButton PoliciesCategoryButton) {
        this.PoliciesCategoryButton = PoliciesCategoryButton;
    }

    /**
     * @return the EbooksCategoryButton
     */
    public RadioButton getEbooksCategoryButton() {
        return EbooksCategoryButton;
    }

    /**
     * @param EbooksCategoryButton the EbooksCategoryButton to set
     */
    public void setEbooksCategoryButton(RadioButton EbooksCategoryButton) {
        this.EbooksCategoryButton = EbooksCategoryButton;
    }

    /**
     * @return the api
     */
    public ApiCalls getApi() {
        return api;
    }

    /**
     * @param api the api to set
     */
    public void setApi(ApiCalls api) {
        this.api = api;
    }

    /**
     * @return the Report
     */
    public String getReport() {
        return Report;
    }

    /**
     * @param Report the Report to set
     */
    public void setReport(String Report) {
        this.Report = Report;
    }
}
