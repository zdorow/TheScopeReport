package thescopereport;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javafx.scene.image.Image;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import org.json.*;
import org.json.JSONObject;
/**
 *
 * @author ZD
 */
public class TheScopeReport extends Application {
        public static void main(String[] args)
    {
        launch(args);
    }

//Radio button naming   
RadioButton ComputerGroupsButton = new RadioButton("Computer Groups");
RadioButton MobileGroupsButton = new RadioButton("Mobile Groups");
RadioButton MobileProfilesCategoryButton = new RadioButton("Mobile Profiles");
RadioButton MacProfilesCategoryButton = new RadioButton("Mac Profiles");
RadioButton MobileAppsCategoryButton = new RadioButton("Mobile Applications");
RadioButton MacAppsCategoryButton = new RadioButton("Mac Applications");
RadioButton PoliciesCategoryButton = new RadioButton("Policies");
RadioButton EbooksCategoryButton = new RadioButton("Ebooks");

private ApiCalls api;


    @Override
    public void start(Stage primaryStage) throws ApiCallsException {
        
//Radio button configurations
       ToggleGroup DeviceGroup = new ToggleGroup();
 //      ToggleGroup CategoryGroup = new ToggleGroup();
       DeviceGroup.getToggles().addAll(ComputerGroupsButton, MobileGroupsButton, MobileProfilesCategoryButton, MobileAppsCategoryButton, MacProfilesCategoryButton, MacAppsCategoryButton, PoliciesCategoryButton, EbooksCategoryButton);  
//       CategoryGroup.getToggles().addAll(ProfilesCategoryButton, AppsCategoryButton, PoliciesCategoryButton, EbooksCategoryButton);
       
//Selections for testing
//ComputerGroupsButton.setSelected(true);
//MobileGroupsButton.setSelected(false);
MobileProfilesCategoryButton.setSelected(true);
MacProfilesCategoryButton.setSelected(false);
MobileAppsCategoryButton.setSelected(false);
MacAppsCategoryButton.setSelected(false);
PoliciesCategoryButton.setSelected(false);
EbooksCategoryButton.setSelected(false);
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
        btn.setText("Export Scope Report to text file");
        btn.setAlignment(Pos.BOTTOM_CENTER);    
      
//Vbox for input group
        VBox Input = new VBox(JamfProLabel, JamfProURLInput, JamfProUsernameLabel,
        JamfProUsernameInput, JamfProPasswordLabel, JamfProPasswordInput, NamesLabel);
        Input.setAlignment(Pos.TOP_CENTER);
        Input.setPadding(new Insets(10, 10, 10, 10));
        Input.setSpacing(5);
        
//Radial Buttons        ComputerGroupsButton, MobileGroupsButton, to be added soon
        HBox DeviceGroupOptions = new HBox(PoliciesCategoryButton, EbooksCategoryButton);
        DeviceGroupOptions.setAlignment(Pos.CENTER);
        DeviceGroupOptions.setPadding(new Insets(10, 10, 10, 10));
        DeviceGroupOptions.setSpacing(18);
        
        HBox CategoryOptions = new HBox(MobileProfilesCategoryButton, MobileAppsCategoryButton, MacProfilesCategoryButton, MacAppsCategoryButton);
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
        try {            
            String url = JamfProURLInput.getText();
            String username = JamfProUsernameInput.getText();
            String password = JamfProPasswordInput.getText();
            String devices = "mobiledevicegroups";

            
            api = new ApiCalls(url, username, password, ApiCalls.FORMAT.JSON, ApiCalls.FORMAT.JSON);
            List<String> lines = Arrays.asList(url, username);
            DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            
            if (ComputerGroupsButton.isSelected()){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "CompPolcies.csv"))) {
                    OutputMessage.setText("Writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + "PolicyScopeReport.csv");
                    JSONObject jsonPolicy = new JSONObject(api.get("policies"));
                    JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("policies");
                    for(int i=0; i<jsonPolicyArray.length(); i++){
                        JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                        int id = dataObj.getInt("id");
                        String name = dataObj.getString("name");
                        writer.write("Policy: " + name + ", is scoped to: \n");
//                        writer.newLine();
                        
                        JSONObject jsonScope = new JSONObject(api.get("policies/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("policy");
                        Object scope = scopeObject.getJSONObject("scope").toString().replace("[{", "").replace("}]", "").replace(",", "\n").replace("\"", "").replace("}}", "\n").replace("{", "").replace("}", "\n");
 /*                        writer.write(scope);
                       Object ALLcompscope = scope.get("all_computers").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                       Object ALLuserscope = scope.get("limit_to_users").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                       Object compscope = scope.get("computer_groups").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                        writer.newLine();
                       writer.write((String) ALLcompscope);
                        writer.newLine();
                        writer.write((String) compscope);
                        writer.newLine();
                        writer.write((String) ALLuserscope);
*/                        writer.write("--------------------------------------------");
//                        writer.newLine();
//                        writer.flush();
                    }
                }
            }
            else if (MobileGroupsButton.isSelected()){

            }
            else if (MobileProfilesCategoryButton.isSelected()){
                OutputMessage.setText( "Starting writing to the file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MobileProfiles.csv");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "MobileProfiles.csv"))) {
                    OutputMessage.setText( "Done writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MobileProfiles.csv");
                    JSONObject jsonPolicy = new JSONObject(api.get("mobiledeviceconfigurationprofiles"));
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
                        
                        JSONObject jsonScope = new JSONObject(api.get("mobiledeviceconfigurationprofiles/id/" + id + "/subset/scope"));
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
                             writer.write("\nMobile Devices:\n");
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
                            writer.write("\nMobile Device Groups:\n");
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
                            writer.write("\nJAMF Users:\n");
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
                            writer.write("\nJAMF User Groups:\n");
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
                            writer.write("\nBuildings:\n");
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
                            writer.write("\nDepartments:\n");
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
                            writer.write("\nNetwork Segments:\n");
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
                            writer.write("\nLimited to Users:\n");
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
                            writer.write("\nLimited User Groups:\n");
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
                            writer.write("\nExcluded Mobile Devices:\n");
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
                            writer.write("\nExcluded Mobile Device Groups:\n");
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
                                writer.write("\nExcluded buildings:\n");
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
                                writer.write("\nExcluded departments:\n");
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
                                writer.write("\nExcluded JAMF users:\n");
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
                                writer.write("\nExcluded to JAMF user groups:\n");
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
                                writer.write("\nExcluded users:\n");
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
                                writer.write("\nExcluded user groups:\n");
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
                                writer.write("\nExcluded to Network Segements:\n");
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
                        writer.write("\nExcluded iBeacons:\n ");
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
                    }
                }
            }                
            else if (MobileAppsCategoryButton.isSelected()){
                OutputMessage.setText( "Staritng writing to the file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MobileApps.csv");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "MobileApps.csv"))) {
                    OutputMessage.setText( "Done writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MobileApps.csv");
                    JSONObject jsonPolicy = new JSONObject(api.get("mobiledeviceapplications"));
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
                        
                        JSONObject jsonScope = new JSONObject(api.get("mobiledeviceapplications/id/" + id + "/subset/scope"));
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
                             writer.write("\nMobile Devices:\n");
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
                            writer.write("\nMobile Device Groups:\n");
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
                            writer.write("\nJAMF Users:\n");
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
                            writer.write("\nJAMF User Groups:\n");
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
                            writer.write("\nBuildings:\n");
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
                            writer.write("\nDepartments:\n");
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
                            writer.write("\nNetwork Segments:\n");
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
                            writer.write("\nLimited to Users:\n");
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
                            writer.write("\nLimited User Groups:\n");
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
                            writer.write("\nExcluded Mobile Devices:\n");
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
                            writer.write("\nExcluded Mobile Device Groups:\n");
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
                                writer.write("\nExcluded buildings:\n");
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
                                writer.write("\nExcluded departments:\n");
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
                                writer.write("\nExcluded JAMF users:\n");
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
                                writer.write("\nExcluded to JAMF user groups:\n");
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
                                writer.write("\nExcluded users:\n");
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
                                writer.write("\nExcluded user groups:\n");
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
                   }
                }
                
            }
            else if (MacProfilesCategoryButton.isSelected()){
                OutputMessage.setText( "Starting writing to the file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MacProfiles.csv");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + " MacProfiles.csv"))) {
                    OutputMessage.setText( "Doen writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MacProfiles.csv");
                    JSONObject jsonPolicy = new JSONObject(api.get("osxconfigurationprofiles"));
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
                        
                        JSONObject jsonScope = new JSONObject(api.get("osxconfigurationprofiles/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("os_x_configuration_profile");
                        JSONObject scope = scopeObject.getJSONObject("scope");
                        
                        Object ALLcompscopechk = scope.get("all_computers").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                            writer.write("Scoped to all computers = " + ALLcompscopechk);
                            writer.newLine();
                        if (ALLcompscopechk == "false"){
                            Object ALLcompscoped = scope.get("computers").toString().replace("{", "").replace("}", "").replace("\"", "");
                            if (!"[]".equals(ALLcompscoped.toString())){
                            writer.write("\n Scoped to Computers: \n");
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
                            writer.write("\nComputer Groups:\n");
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
                            writer.write("\nJAMF Users:\n");
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
                            writer.write("\nJAMF User Groups:\n");
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
                            writer.write("\nBuildings:\n");
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
                            writer.write("\nDepartments:\n");
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
                            writer.write("\nNetwork Segments:\n");
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
                            writer.write("\nLimited to Users:\n");
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
                            writer.write("\nLimited User Groups:\n");
                            writer.newLine();
                                for (String userGroupsSLIMARRAY1 : userGroupsSLIMARRAY) {
                                    writer.write(userGroupsSLIMARRAY1);
                                    writer.newLine();
                                } 
                        }         
                        
                        JSONObject exclusions = scope.getJSONObject("exclusions");
                       Object ALLcompscoped = exclusions.get("computers");
                            if (!"[]".equals(ALLcompscoped.toString())){
                            writer.write("\nExcluded Computers: \n");
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
                            writer.write("\nExcluded Computer Groups:\n");
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
                                writer.write("\nExcluded buildings:\n");
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
                                writer.write("\nExcluded departments:\n");
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
                                writer.write("\nExcluded JAMF users:\n");
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
                                writer.write("\nExcluded to JAMF user groups:\n");
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
                                writer.write("\nExcluded users:\n");
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
                                writer.write("\nExcluded user groups:\n");
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
                        Object ibeaconsEX = exclusions.get("ibeacons");
                        if (!"[]".equals(ibeaconsEX.toString())){
                        String ibeaconsEXSLIM = ibeaconsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                        String[] ibeaconsEXSLIMARRAY = ibeaconsEXSLIM.split("name:");
                        writer.write("\nExcluded iBeacons:\n ");
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
                  }
                }
            }
            else if (MacAppsCategoryButton.isSelected()){
                OutputMessage.setText("Starting writing to the file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MacApps.csv");
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + " MacApps.csv"))) {
                    OutputMessage.setText("Done writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MacApps.csv");
                    JSONObject jsonPolicy = new JSONObject(api.get("macapplications"));
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
                    
                        JSONObject jsonScope = new JSONObject(api.get("macapplications/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("mac_application");
                        JSONObject scope = scopeObject.getJSONObject("scope");
                        
                        Object ALLcompscopechk = scope.get("all_computers").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                            
                            writer.write("Scoped to all computers = " + ALLcompscopechk);
                            writer.newLine();
                            if (ALLcompscopechk == "false"){
                            Object ALLcompscoped = scope.get("computers").toString().replace("{", "").replace("}", "").replace("\"", "");
                            if (!"[]".equals(ALLcompscoped.toString())){                            
                            writer.write("\n Scoped to Computers: \n"); 
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
                            writer.write("\nComputer Groups:\n");
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
                            writer.write("\nJAMF Users:\n");
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
                            writer.write("\nJAMF User Groups:\n");
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
                            writer.write("\nBuildings:\n");
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
                            writer.write("\nDepartments:\n");
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
                            writer.write("\nNetwork Segments:\n");
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
                            writer.write("\nLimited to Users:\n");
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
                            writer.write("\nLimited User Groups:\n");
                            writer.newLine();
                                for (String userGroupsSLIMARRAY1 : userGroupsSLIMARRAY) {
                                    writer.write(userGroupsSLIMARRAY1);
                                    writer.newLine();
                                } 
                        }          
                        
                        JSONObject exclusions = scope.getJSONObject("exclusions");
                       Object ALLcompscoped = exclusions.get("computers");
                            if (!"[]".equals(ALLcompscoped.toString())){
                            writer.write("\nExcluded Computers: \n");
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
                            writer.write("\nExcluded Computer Groups:\n");
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
                                writer.write("\nExcluded buildings:\n");
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
                                writer.write("\nExcluded departments:\n");
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
                                writer.write("\nExcluded JAMF users:\n");
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
                                writer.write("\nExcluded to JAMF user groups:\n");
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
                                writer.write("\nExcluded users:\n");
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
                                writer.write("\nExcluded user groups:\n");
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
                    } 
                }
                
            }
            else if (EbooksCategoryButton.isSelected()){
                OutputMessage.setText( "Starting writing to the file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " Ebooks.csv");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + " Ebooks.csv"))) {
                    OutputMessage.setText( "Done writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " Ebooks.csv");
                    JSONObject jsonPolicy = new JSONObject(api.get("ebooks"));
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
                       
                        JSONObject jsonScope = new JSONObject(api.get("ebooks/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("ebook");
                        JSONObject scope = scopeObject.getJSONObject("scope");
                        
                        Object ALLcompscopechk = scope.get("all_computers").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                            writer.write("Scoped to all computers = " + ALLcompscopechk);
                            writer.newLine();
                        if (ALLcompscopechk == "false"){
                            Object ALLcompscoped = scope.get("computers").toString().replace("{", "").replace("}", "").replace("\"", "");
                            if (!"[]".equals(ALLcompscoped.toString())){
                            writer.write("\n Scoped to Computers: \n");
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
                            writer.write("\nComputer Groups:\n");
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
                             writer.write("\nMobile Devices:\n");
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
                            writer.write("\nMobile Device Groups:\n");
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
                            writer.write("\nJAMF Users:\n");
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
                            writer.write("\nJAMF User Groups:\n");
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
                            writer.write("\nBuildings:\n");
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
                            writer.write("\nDepartments:\n");
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
                            writer.write("\nClasses:\n");
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
                            writer.write("\nNetwork Segments:\n");
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
                            writer.write("\nLimited to Users:\n");
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
                            writer.write("\nLimited User Groups:\n");
                            writer.newLine();
                                for (String userGroupsSLIMARRAY1 : userGroupsSLIMARRAY) {
                                    writer.write(userGroupsSLIMARRAY1);
                                    writer.newLine();
                                } 
                        }           
                        
                        JSONObject exclusions = scope.getJSONObject("exclusions");
                            Object ALLcompscoped = exclusions.get("computers");
                            if (!"[]".equals(ALLcompscoped.toString())){
                            writer.write("\nExcluded Computers: \n");
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
                            writer.write("\nExcluded Computer Groups:\n");
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
                            writer.write("\nExcluded Mobile Devices:\n");
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
                            writer.write("\nExcluded Mobile Device Groups:\n");
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
                                writer.write("\nExcluded buildings:\n");
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
                                writer.write("\nExcluded departments:\n");
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
                                writer.write("\nExcluded JAMF users:\n");
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
                                writer.write("\nExcluded to JAMF user groups:\n");
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
                                writer.write("\nExcluded users:\n");
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
                                writer.write("\nExcluded user groups:\n");
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
                    }                   
                }
            }
            else if (PoliciesCategoryButton.isSelected()){
                OutputMessage.setText("Starting writing to the file " + timeStampPattern.format(java.time.LocalDateTime.now()) + "PolicyScopeReport.csv");
                
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + " Polcies.csv"))) {
                    
                    JSONObject jsonPolicy = new JSONObject(api.get("policies"));
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

                        
                        JSONObject jsonScope = new JSONObject(api.get("policies/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("policy");
                        JSONObject scope = scopeObject.getJSONObject("scope");

                        Object ALLcompscopechk = scope.get("all_computers").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                        writer.write("Scoped to all computers = " + ALLcompscopechk);
                        writer.newLine();
                        if (ALLcompscopechk == "false"){
                             Object ALLcompscoped = scope.get("computers").toString().replace("{", "").replace("}", "").replace("\"", "");
                            if (!"[]".equals(ALLcompscoped.toString())){
                            writer.write("\n Scoped to Computers: \n");
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
                            writer.write("\nComputer Groups:\n");
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
                            writer.write("\nBuildings:\n");
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
                            writer.write("\nDepartments:\n");
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
                            writer.write("\nNetwork Segments:\n");
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
                            writer.write("\nLimited to Users:\n");
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
                            writer.write("\nLimited User Groups:\n");
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
                        writer.write("\nExcluded iBeacons:\n ");
                        writer.newLine();
                                    for (String ibeaconsEXSLIMARRAY1 : ibeaconsEXSLIMARRAY) {
                                    writer.write(ibeaconsEXSLIMARRAY1);
                                    writer.newLine();
                                } 
                        }
                        
                        JSONObject exclusions = scope.getJSONObject("exclusions");
                        
                            Object ALLcompscoped = exclusions.get("computers");
                            if (!"[]".equals(ALLcompscoped.toString())){
                            writer.write("\nExcluded Computers: \n");
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
                            writer.write("\nExcluded Computer Groups:\n");
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
                                writer.write("\nExcluded buildings:\n");
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
                                writer.write("\nExcluded departments:\n");
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
                                writer.write("\nExcluded users:\n");
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
                                writer.write("\nExcluded user groups:\n");
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
                        Object ibeaconsEX = exclusions.get("ibeacons");
                        if (!"[]".equals(ibeaconsEX.toString())){
                        String ibeaconsEXSLIM = ibeaconsEX.toString().replace("{", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
                        String[] ibeaconsEXSLIMARRAY = ibeaconsEXSLIM.split("name:");
                        writer.write("\nExcluded iBeacons:\n ");
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
                    }
                }
                OutputMessage.setText("Done writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + "PolicyScopeReport.csv");
            }
            else
                OutputMessage.setText("Please Select a way to organize the list");
        } catch (ApiCallsException | JSONException | IOException ex) {
            OutputMessage.setText( "URL, username and/or password not accepted.");
            Logger.getLogger(TheScopeReport.class.getName()).log(Level.SEVERE, null, ex);           
        }
    });
}
}